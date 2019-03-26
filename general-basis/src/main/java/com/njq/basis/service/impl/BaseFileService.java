package com.njq.basis.service.impl;

import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.other.TokenCheck;
import com.njq.common.base.redis.lock.JedisLock;
import com.njq.common.base.redis.lock.JedisLockFactory;
import com.njq.common.enumreg.FileType;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.model.po.BaseFile;
import com.njq.common.model.ro.BaseFileDealRequest;
import com.njq.common.model.ro.BaseFileSaveRequest;
import com.njq.common.model.ro.BaseFileSaveRequestBuilder;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import com.njq.common.util.string.IdGen;
import com.njq.common.util.string.StringUtil;
import com.njq.common.util.string.StringUtil2;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.SaveFileInfo;
import com.njq.file.load.api.model.UpFileInfoRequestBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
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
    @Resource
    private FileLoadService fileLoadService;

    public String dealImgSrc(Long typeId, ChannelType channel, String prefix, String src) {
    	if(StringUtil2.isEmpty(src)) {
    		return "";
    	}
    	if(!(src.split("\\.").length>2)) {
    		src = prefix + src;
    	}
    	if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
    		src = SendConstants.HTTP_PREFIX+"://"+src;
    	}
    	List<BaseFile> list = getFileListByCon(typeId, channel.getValue(), getOldName(src));
    	if(CollectionUtils.isEmpty(list)) {
    		BaseFile file;
    		if (src.startsWith("data:image/png;base64")) {
    			file = dealBase64Src(typeId, channel, src);
    		} else {
    			file = dealPicSrc(typeId, channel, src);
    		}
    		return "&{|" + file.getId() + "|}";
    	}else {
    		return "&{|" + list.get(0).getId() + "|}"; 
    	}
    }

    public String dealFileUrl(BaseFileDealRequest request) {
    	if(StringUtil2.isEmpty(request.getSrc())) {
    		return "";
    	}
        String src = request.getSrc();
        if(!(src.split("\\.").length>2)) {
    		src = request.getPrefix() + src;
    	}
    	if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
    		src = SendConstants.HTTP_PREFIX+"://"+src;
    	}
        String lockKey = StringUtil2.format("oldName-{0}-oldSrc-{1}", getOldName(src), request.getSrc());
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("并发获取锁失败！"+lockKey);
            }
            List<BaseFile> list = getFileListByCon(request.getTypeId(), request.getChannel(), getOldName(src));
            if (CollectionUtils.isEmpty(list)) {
            	SaveFileInfo fileInfo = fileLoadService.loadFile(UpFileInfoRequestBuilder.anUpFileInfoRequest()
            			.ofUrl(src)
            			.ofType(ChannelType.getChannelType(request.getChannel()))
            			.ofDebugFlag(TokenCheck.debugType())
            			.ofCookieStr(HtmlGrabUtil.build(request.getChannel()).getCookieStr())
            			.build());
            	BaseFile file = saveInfo(BaseFileSaveRequestBuilder.aBaseFileSaveRequest()
            			.ofChannel(request.getChannel())
            			.ofName(fileInfo.getFileNewName())
            			.ofOldName(fileInfo.getFileOldName())
            			.ofFilePlace(fileInfo.getFilePlace())
            			.ofRealPlace(fileInfo.getRealPlace())
            			.ofTypeId(request.getTypeId())
            			.ofOldSrc(fileInfo.getOldSrc())
            			.ofResultPair(fileInfo.getResultPair())
            			.ofFileType(StringUtil.urlPostfix(fileInfo.getFilePlace()))
            			.build());
            	return "&{|" + file.getId() + "|}";
            }
            return "&{|" + list.get(0).getId() + "|}";
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public BaseFile dealBase64Src(Long typeId, ChannelType channel, String src) {
    	String lockKey = StringUtil2.format("oldName-{0}-oldSrc-{1}", "base64", src);
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("并发获取锁失败！"+lockKey);
            }
            List<BaseFile> list = getFileListByCon(typeId, channel.getValue(), getOldName(src));
            if (CollectionUtils.isEmpty(list)) {
            	SaveFileInfo fileInfo = fileLoadService.loadBase64(UpFileInfoRequestBuilder.anUpFileInfoRequest()
                        .ofUrl(src)
                        .ofType(channel)
                        .ofDebugFlag(TokenCheck.debugType())
                        .ofCookieStr(HtmlGrabUtil.build(channel.getValue()).getCookieStr())
                        .build());
                return saveInfo(BaseFileSaveRequestBuilder.aBaseFileSaveRequest()
                        .ofChannel(channel.getValue())
                        .ofName(fileInfo.getFileNewName())
                        .ofOldName(fileInfo.getFileOldName())
                        .ofFilePlace(fileInfo.getFilePlace())
                        .ofRealPlace(fileInfo.getRealPlace())
                        .ofTypeId(typeId)
                        .ofOldSrc(fileInfo.getOldSrc())
                        .ofResultPair(fileInfo.getResultPair())
                        .ofFileType(FileType.IMAGE.getValue())
                        .build());
            }
            return list.get(0);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public BaseFile dealPicSrc(Long typeId, ChannelType channel, String src) {
    	String lockKey = StringUtil2.format("oldName-{0}-oldSrc-{1}", getOldName(src), src);
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("并发获取锁失败！"+lockKey);
            }
            List<BaseFile> list = getFileListByCon(typeId, channel.getValue(), getOldName(src));
            if (CollectionUtils.isEmpty(list)) {
            	SaveFileInfo fileInfo = fileLoadService.loadPic(UpFileInfoRequestBuilder.anUpFileInfoRequest()
                        .ofUrl(src)
                        .ofType(channel)
                        .ofDebugFlag(TokenCheck.debugType())
                        .ofCookieStr(HtmlGrabUtil.build(channel.getValue()).getCookieStr())
                        .build());
                return saveInfo(BaseFileSaveRequestBuilder.aBaseFileSaveRequest()
                        .ofChannel(channel.getValue())
                        .ofName(fileInfo.getFileNewName())
                        .ofOldName(fileInfo.getFileOldName())
                        .ofFilePlace(fileInfo.getFilePlace())
                        .ofRealPlace(fileInfo.getRealPlace())
                        .ofTypeId(typeId)
                        .ofOldSrc(fileInfo.getOldSrc())
                        .ofResultPair(fileInfo.getResultPair())
                        .ofFileType(FileType.IMAGE.getValue())
                        .build());
            }
            return list.get(0);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private List<BaseFile> getFileListByCon(Long typeId,String channel,String oldName){
    	ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("oldName", oldName);
        if (typeId != null) {
            conditionsCommon.addEqParam("typeId", typeId);
        }
        conditionsCommon.addEqParam("channel", channel);
        return fileDao.queryTByParam(conditionsCommon);
    }

    public BaseFile saveInfo(BaseFileSaveRequest request) {
        BaseFile file = new BaseFile();
        file.setChannel(request.getChannel());
        file.setCreateDate(new Date());
        file.setName(request.getName());
        file.setOldName(request.getOldName());
        file.setFilePlace(request.getFilePlace());
        file.setRealPlace(request.getRealPlace());
        file.setTypeId(request.getTypeId());
        file.setFileType(request.getFileType());
        file.setLoadFlag(request.getResultPair().getKey());
        file.setColumDesc(request.getResultPair().getValue());
        if (StringUtil2.IsNotEmpty(request.getOldSrc())) {
            file.setOldSrc(request.getOldSrc().length() > 240 ? request.getOldSrc().substring(0, 240) + "......" : request.getOldSrc());
        }
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


    public void reloadFile() {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("loadFlag", false);
        conditionsCommon.addEqParam("fileType", FileType.IMAGE.getValue());
        List<BaseFile> fileList = fileDao.queryTByParam(conditionsCommon);
        fileList.forEach(n -> {
            SaveFileInfo fileInfo = fileLoadService.fileQuery(UpFileInfoRequestBuilder.anUpFileInfoRequest()
                    .ofUrl(n.getOldSrc())
                    .ofType(ChannelType.getChannelType(n.getChannel()))
                    .ofRealSavePlace(n.getRealPlace())
                    .ofCookieStr(HtmlGrabUtil.build(n.getChannel()).getCookieStr())
                    .build());
            BaseFileService impl = SpringContextUtil.getBean(BaseFileService.class);
            impl.updateFileLoadFlag(n, fileInfo.getResultPair());
        });
    }

    public void updateLoadQuery() {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("loadFlag", false);
        List<BaseFile> fileList = fileDao.queryTByParam(conditionsCommon);
        fileList.forEach(n -> {
            SaveFileInfo info = fileLoadService.fileQuery(UpFileInfoRequestBuilder.anUpFileInfoRequest()
                    .ofUrl(n.getOldSrc())
                    .ofType(ChannelType.getChannelType(n.getChannel()))
                    .ofRealSavePlace(n.getRealPlace())
                    .ofCookieStr(HtmlGrabUtil.build(n.getChannel()).getCookieStr())
                    .build());
            BaseFileService impl = SpringContextUtil.getBean(BaseFileService.class);
            impl.updateFileFlag(n,info);
        });
    }

    public void updateFileFlag(BaseFile f,SaveFileInfo info){
        if (StringUtil.IsNotEmpty(info.getFileNewName())) {
            BaseFile f1 = new BaseFile();
            f1.setId(f.getId());
            f1.setLoadFlag(true);
            fileDao.updateByPrimaryKeySelective(f1);
        }
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


    public BaseFile queryById(Long id) {
        return fileDao.queryTById(id);
    }
}
