package com.njq.basis.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.BaseFile;
import com.njq.common.util.grab.SendConstants;
import com.njq.common.util.grab.UrlChangeUtil;
import com.njq.common.util.string.IdGen;

/**
 * @author: nijiaqi
 * @date: 2019/2/15
 */
@Service
public class BaseFileService {
    private static final Logger logger = LoggerFactory.getLogger(BaseFileService.class);
    @Resource
    private DaoCommon<BaseFile> fileDao;

    public String dealImgSrc(Long typeId, String channel, String prefix, String src, String shortName, String savePlace, String imgPlace) {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        String fileOldName = getOldName(src);
        conditionsCommon.addEqParam("oldName", fileOldName);
        if(typeId != null) {
        	conditionsCommon.addEqParam("typeId", typeId);        	
        }
        conditionsCommon.addEqParam("channel", channel);
        List<BaseFile> list = fileDao.queryTByParam(conditionsCommon);
        if (CollectionUtils.isEmpty(list)) {
            String fileNewName = getNewName(fileOldName);
            String place = getFilePlace(shortName, savePlace, fileNewName);
            BaseFileService.changeSrcUrl(prefix, src, shortName, savePlace + place);
            BaseFile file = saveInfo(channel, fileNewName, fileOldName, imgPlace + place, savePlace + place, typeId);
            return file.getfilePlace();
        } else {
            return list.get(0).getfilePlace();
        }
    }

    
    
    public BaseFile saveInfo(String channel, String name, String oldName, String filePlace, String realPlace, Long typeId) {
        BaseFile file = new BaseFile();
        file.setChannel(channel);
        file.setCreateDate(new Date());
        file.setName(name);
        file.setOldName(oldName);
        file.setfilePlace(filePlace);
        file.setRealPlace(realPlace);
        file.setTypeId(typeId);
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

    public static void changeSrcUrl(String prefix, String src, String shortName, String savePlace) {
        if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
            src = prefix + src;
        }
        try {
            UrlChangeUtil.downLoad(src, savePlace, shortName);
        } catch (Exception e) {
            logger.error("下载出错", e);
        }
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

    
    public String dealFileUrl(Long typeId,String channel,String prefix, String src, String shortName, String savePlace, String imgPlace) {
    	ConditionsCommon conditionsCommon = new ConditionsCommon();
        String fileOldName = getOldName(src);
        conditionsCommon.addEqParam("oldName", fileOldName);
        if(typeId != null ) {
        	conditionsCommon.addEqParam("typeId", typeId);        	
        }
        conditionsCommon.addEqParam("channel", channel);
        List<BaseFile> list = fileDao.queryTByParam(conditionsCommon);
        if (CollectionUtils.isEmpty(list)) {
             String place = getFilePlace(shortName, savePlace, fileOldName);
             changeFileUrl(prefix, src, shortName, savePlace + place);
             BaseFile file = saveInfo(channel, fileOldName, fileOldName, imgPlace +"/downLoadFile"+ place, savePlace + place, typeId);
             return file.getfilePlace();
        }else {
        	return list.get(0).getfilePlace();
        }
    }
    
    public static void changeFileUrl(String prefix, String src, String shortName, String savePlace) {
        if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
            try {
                UrlChangeUtil.downLoad(prefix + src, savePlace, shortName);
            } catch (Exception e) {
                logger.error("下载出错", e);
            }
        }
    }

}
