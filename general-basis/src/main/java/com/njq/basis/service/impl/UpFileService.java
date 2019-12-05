package com.njq.basis.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.njq.common.base.other.MessageCommon;
import com.njq.common.base.other.TokenCheck;
import com.njq.common.enumreg.FileType;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.ro.BaseFileSaveRequestBuilder;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.ByteRequestBuilder;
import com.njq.file.load.api.model.SaveFileInfo;
import com.njq.file.load.api.model.UpFileInfoRequestBuilder;

@Service
public class UpFileService {
	@Autowired
    private FileLoadService fileLoadService;
    @Autowired
    private BaseFileService  baseFileService;
	
	public Map<String, Object> upEditPic(HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
    	Map<String, MultipartFile> mms =  ((MultipartHttpServletRequest) req).getFileMap();
    	for (Map.Entry<String, MultipartFile> entry : mms.entrySet()) { 
    		try {
    			SaveFileInfo fileInfo = fileLoadService.upYxlByteFile(ByteRequestBuilder.aByteRequest()
    					.ofType(ChannelType.YXL.getValue())
    					.ofName(entry.getValue().getOriginalFilename())
    					.ofData(entry.getValue().getBytes())
    					.ofDebugFlag(TokenCheck.debugType())
    					.build());
    			baseFileService.saveInfo(BaseFileSaveRequestBuilder.aBaseFileSaveRequest()
    					.ofChannel(ChannelType.YXL.getValue())
    					.ofFilePlace(fileInfo.getFilePlace())
    					.ofName(fileInfo.getFileNewName())
    					.ofOldName(entry.getValue().getOriginalFilename())
    					.ofRealPlace(fileInfo.getRealPlace())
    					.ofResultPair(fileInfo.getResultPair())
                        .ofFileType(FileType.IMAGE.getValue())
    					.build());
    			resultMap.put(entry.getKey(), fileInfo.getFilePlace());				
			} catch (Exception e) {
				e.printStackTrace();
	            MessageCommon.getFalseMap(resultMap, "上传出现异常！");
			}
		}
    	
    	Map<String, String[]> strMap =  req.getParameterMap();
    	for(Map.Entry<String, String[]> entry:strMap.entrySet()) {
    		if(entry.getValue() != null) {
    			SaveFileInfo fileInfo=null;
    			if(entry.getValue()[0].startsWith("data")) {
    				fileLoadService.loadBase64(UpFileInfoRequestBuilder.anUpFileInfoRequest()
    						.ofUrl(entry.getValue()[0])
    						.ofType(ChannelType.YXL.getValue())
    						.ofDebugFlag(TokenCheck.debugType())
    						.build());
    			}else {
    				if(entry.getValue()[0].contains("njqityun.com")) {
    					continue;
    				}
    				fileInfo = fileLoadService.loadPic(UpFileInfoRequestBuilder.anUpFileInfoRequest()
    						.ofUrl(entry.getValue()[0])
    						.ofType(ChannelType.YXL.getValue())
    						.ofDebugFlag(TokenCheck.debugType())
    						.build());      				
    			}
    			baseFileService.saveInfo(BaseFileSaveRequestBuilder.aBaseFileSaveRequest()
    					.ofChannel(ChannelType.YXL.getValue())
    					.ofFilePlace(fileInfo.getFilePlace())
    					.ofName(fileInfo.getFileNewName())
    					.ofOldName(fileInfo.getFileOldName())
    					.ofRealPlace(fileInfo.getRealPlace())
    					.ofResultPair(fileInfo.getResultPair())
                        .ofFileType(FileType.IMAGE.getValue())
    					.build());
    			resultMap.put(entry.getKey(), fileInfo.getFilePlace());
    		}
    	}
    	return resultMap;
	}
}
