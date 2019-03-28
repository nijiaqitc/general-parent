package com.njq.zxgj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.LogCommon;
import com.njq.common.model.po.ToolResourceshare;
import com.njq.common.model.vo.ResourceshareVO;

@Service
public class ToolResourceShareService {

	@Resource
	private DaoCommon<ToolResourceshare> resourceshareDao;
	
	/**
	 * 查询所有分享资源(分页)
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<ResourceshareVO> queryAllRc(Map<String, Object> paramMap,
			int page, int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, 10);
		Map<String, Object> map=new HashMap<String, Object>();
		String str=" from ToolResourceshare where isChecked=1  ";
		if(paramMap.get("isDetail").equals(0)){
			if(!paramMap.get("type1").equals(-1L)){
				str+=" and codeIdA=:codeIdA";
				map.put("codeIdA",paramMap.get("type1"));
			}
		}else{
			if(!paramMap.get("type1").equals(-1L)){
				str+=" and codeIdA=:codeIdA";
				map.put("codeIdA",paramMap.get("type1"));
				if(!paramMap.get("type2").equals(-1L)){
					str+=" and codeIdB=:codeIdB ";
					map.put("codeIdB",paramMap.get("type2"));
					if(!paramMap.get("type3").equals(-1L)){
						str+=" and codeIdC= '"+paramMap.get("type3").toString()+"'";
					}
				}
			}
		}
		str+=" order by createDate desc ";
		PageList<ToolResourceshare> list= resourceshareDao.queryHqlByParamForPage(str, cc, map);
		PageList<ResourceshareVO> voList=new PageList<ResourceshareVO>();
		List<ResourceshareVO> vo=new ArrayList<ResourceshareVO>();
		String imgUrl = SpringContextUtil.getValue("image.url");
		for(ToolResourceshare share:list.getList()){
			ResourceshareVO v=new ResourceshareVO();
			v.setId(share.getId());
			v.setResourceDesc(share.getResourceDesc());
			v.setCodeIdA(share.getCodeIdA());
			v.setCodeIdB(share.getCodeIdB());
			v.setCodeIdC(Long.parseLong(share.getCodeIdC()));
			v.setPwd(share.getPwd());
			v.setPicUrlBase(imgUrl+share.getPicUrlBase());
			v.setPicUrlA(imgUrl+share.getPicUrlA());
			v.setPicUrlB(imgUrl+share.getPicUrlB());
			v.setPicUrlC(imgUrl+share.getPicUrlC());
			vo.add(v);
		}
		voList.setTotal(list.getTotal());
		voList.setList(vo);
		return voList;
	}

	/**
	 * 根据id检索出分享资源
	 * @param id
	 * @return
	 */
	public ToolResourceshare queryRcById(Long id) {
		return resourceshareDao.queryTById(id);
	}

	/**
	 * 保存分享资源
	 * @param rc
	 * @param userId
	 * @param map
	 */
	public void saveRc(ToolResourceshare rc, Long userId,Map<String, Object> map) {
		resourceshareDao.save(rc);
	}

	/**
	 * 根据id修改分享资源
	 * @param rc
	 * @param userId
	 * @param map
	 */
	public void updateRcById(ToolResourceshare rc, Long userId,
			Map<String, Object> map) {
		ToolResourceshare m = resourceshareDao.queryTById(rc.getId());
		resourceshareDao.update(m);
		//日志记录
		LogCommon.saveLog(userId, "修改", "消息表", "对行"+m.getId()+"进行修改");
//		ResourceshareCommon.getSuccessMap(map);
		
	}

	/**
	 * 删除分享资源
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteRcById(Long[] ids, Long userId,
			Map<String, Object> map) {
//		int num = resourceshareDao.delT(ids);
		/*if(num>0){
			ResourceshareCommon.getSuccessMap(map);
			//日志记录
			logService.saveLog(userId, "删除", "消息表", "对行"+StringUtil.LongToString(ids)+"进行删除");
		}else{
			ResourceshareCommon.getFalseMap(map);
			map.put("rc", "请检查数据是否正确，再进行操作！");
		}*/
	}

}
