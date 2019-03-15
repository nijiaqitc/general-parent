package com.njq.basis.service.impl;

import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.redis.lock.JedisLock;
import com.njq.common.base.redis.lock.JedisLockFactory;
import com.njq.common.model.po.BaseFile;
import com.njq.common.model.ro.BaseFileSaveRequest;
import com.njq.common.model.ro.BaseFileSaveRequestBuilder;
import com.njq.common.util.encrypt.Base64Util;
import com.njq.common.util.grab.SendConstants;
import com.njq.common.util.grab.UrlChangeUtil;
import com.njq.common.util.string.IdGen;
import com.njq.common.util.string.StringUtil2;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/2/15
 */
@Service
public class BaseFileService {
    private static final Logger logger = LoggerFactory.getLogger(BaseFileService.class);
    @Resource
    private DaoCommon<BaseFile> fileDao;
    @Resource
    private JedisLockFactory jedisLockFactory;

    public String dealImgSrc(Long typeId, String channel, String prefix, String src, String shortName, String savePlace, String imgPlace) {
        if (src.startsWith("data:image/png;base64")) {
            return dealBase64Src(typeId, channel, prefix, src, shortName, savePlace, imgPlace).getfilePlace();
        } else {
            return dealPicSrc(typeId, channel, prefix, src, shortName, savePlace, imgPlace).getfilePlace();
        }
    }

    public BaseFile dealBase64Src(Long typeId, String channel, String prefix, String src, String shortName, String savePlace, String imgPlace) {
        String picName = IdGen.get().toString();
        String picPlace = Base64Util.GenerateImage(src.split("base64,")[1], picName, savePlace);
        Pair<Boolean, String> resultPair = null;
        if (!StringUtils.isEmpty(picPlace)) {
            resultPair = Pair.of(true, "");
        } else {
            resultPair = Pair.of(false, "base64位图片生成失败！");
        }
        return getFileInfo(new BaseFileSaveRequestBuilder()
                .ofChannel(channel)
                .ofName(picName)
                .ofOldName(picName)
                .ofFilePlace(imgPlace + picPlace)
                .ofRealPlace(savePlace + picPlace)
                .ofTypeId(typeId)
                .ofOldSrc("base64")
                .ofResultPair(resultPair)
                .build());
    }

    public BaseFile dealPicSrc(Long typeId, String channel, String prefix, String src, String shortName, String savePlace, String imgPlace) {
        String fileOldName = getOldName(src);
        String fileNewName = getNewName(fileOldName);
        String place = getFilePlace(shortName, savePlace, fileNewName);
        if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
            src = prefix + src;
        }
        Pair<Boolean, String> resultPair = BaseFileService.changeSrcUrl(src, shortName, savePlace + place);
        return getFileInfo(new BaseFileSaveRequestBuilder()
                .ofChannel(channel)
                .ofName(fileNewName)
                .ofOldName(fileOldName)
                .ofFilePlace(imgPlace + place)
                .ofRealPlace(savePlace + place)
                .ofTypeId(typeId)
                .ofOldSrc(src)
                .ofResultPair(resultPair)
                .build());
    }

    public BaseFile getFileInfo(BaseFileSaveRequest request) {
        String lockKey = StringUtil2.format("oldName-{0}-oldSrc-{1}", request.getOldName(), request.getOldSrc());
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("并发获取锁失败！");
            }
            ConditionsCommon conditionsCommon = new ConditionsCommon();
            conditionsCommon.addEqParam("oldName", request.getOldName());
            if (request.getTypeId() != null) {
                conditionsCommon.addEqParam("typeId", request.getTypeId());
            }
            conditionsCommon.addEqParam("channel", request.getChannel());
            List<BaseFile> list = fileDao.queryTByParam(conditionsCommon);
            if (CollectionUtils.isEmpty(list)) {
                return saveInfo(request);
            }
            return list.get(0);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseFile saveInfo(BaseFileSaveRequest request) {
        BaseFile file = new BaseFile();
        file.setChannel(request.getChannel());
        file.setCreateDate(new Date());
        file.setName(request.getName());
        file.setOldName(request.getOldName());
        file.setfilePlace(request.getFilePlace());
        file.setRealPlace(request.getRealPlace());
        file.setTypeId(request.getTypeId());
        file.setLoadFlag(request.getResultPair().getKey());
        file.setColumDesc(request.getResultPair().getValue());
        file.setOldSrc(request.getOldSrc().length() > 240 ? request.getOldSrc().substring(0, 240) + "......" : request.getOldSrc());
        fileDao.save(file);
        return file;
    }

    public static String getOldName(String src) {
        String[] img = src.split("\\?")[0].split("\\/");
        return img[img.length - 1];
    }

    public static String getNewName(String oldFileName) {
        String fileName = String.valueOf(IdGen.get().nextId());
        String[] imgName = oldFileName.split("\\.");
        if (imgName.length > 1) {
            fileName += "." + imgName[1];
        } else {
            fileName += ".png";
        }
        return fileName;
    }

    public static String getFilePlace(String shortName, String savePlace, String fileName) {
        return getSrc(shortName, savePlace) + "/" + fileName;
    }

    private static String getSrc(String shortName, String savePlace) {
        Date timeCur = new Date();
        SimpleDateFormat fmtYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat fmtMM = new SimpleDateFormat("MM");
        SimpleDateFormat fmtDD = new SimpleDateFormat("dd");
        String strYY = fmtYY.format(timeCur);
        String strMM = fmtMM.format(timeCur);
        String strDD = fmtDD.format(timeCur);
        String url = "/" + shortName + "/" + strYY + strMM + strDD;
        File dir = new File(savePlace + url);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return url;
    }


    public String dealFileUrl(Long typeId, String channel, String prefix, String src, String shortName, String savePlace, String imgPlace) {
        String fileOldName = "";
        try {
            fileOldName = URLDecoder.decode(getOldName(src), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("编码转换出错", e);
        }
        String place = getFilePlace(shortName, savePlace, fileOldName);
        if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
            src = prefix + src;
        }
        Pair<Boolean, String> resultPair = changeSrcUrl(src, shortName, savePlace + place);
        BaseFile file = getFileInfo(new BaseFileSaveRequestBuilder()
                .ofChannel(channel)
                .ofName(fileOldName)
                .ofOldName(fileOldName)
                .ofFilePlace(getSrc(shortName, savePlace) + "/downLoadFile?file=" + fileOldName)
                .ofRealPlace(savePlace + place)
                .ofTypeId(typeId)
                .ofOldSrc(src)
                .ofResultPair(resultPair)
                .build());
        return file.getfilePlace();
    }

    public static Pair<Boolean, String> changeSrcUrl(String src, String shortName, String savePlace) {
        try {
            UrlChangeUtil.downLoad(src, savePlace, shortName);
            return Pair.of(true, "");
        } catch (Exception e) {
            logger.error("下载出错", e);
            return Pair.of(false, e.getMessage());
        }
    }


    public void reloadFile() {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("loadFlag", false);
        List<BaseFile> fileList = fileDao.queryTByParam(conditionsCommon);
        fileList.forEach(n -> {
            Pair<Boolean, String> resultPair = changeSrcUrl(n.getOldSrc(), n.getChannel(), n.getRealPlace());
            BaseFileService impl = SpringContextUtil.getBean(BaseFileService.class);
            impl.updateFileLoadFlag(n, resultPair);
        });
    }

    public void updateFileLoadFlag(BaseFile baseFile, Pair<Boolean, String> resultPair) {
        BaseFile f = new BaseFile();
        f.setId(baseFile.getId());
        f.setLoadFlag(resultPair.getKey());
        if (!resultPair.getKey()) {
            f.setColumDesc(resultPair.getValue());
        }
        fileDao.updateByPrimaryKeySelective(f);
    }
}
