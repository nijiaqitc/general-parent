/**
 * 公用的用户类，用于直接获取登录的用户信息
 */
package com.njq.admin.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.njq.basis.service.impl.BaseUserService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.model.po.BaseUser;
import com.njq.common.util.rely.APIUtil;

@Component
public class UserCommon {

	@Resource
	public BaseUserService userService;
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static BaseUser getUser(){
		HttpSession session = APIUtil.getCurrentSession();
		if (session != null) {
			BaseUser user = (BaseUser) session.getAttribute("user");
			return user;
		}
		return null;
	}
	
	/**
	 * 获取当前用户id
	 * @return
	 */
	public static Long getUserId(){
		BaseUser user=getUser();
		if(user!=null){
			return  getUser().getId();
		}else{
			return null;
		}
	}

	/**
	 * 判断传入的id是否是系统管理员的id
	 * @param userId
	 * @return
	 */
	public static boolean checkUserId(Long userId){
		if(ConstantsCommon.Oper_User.ADMIN.equals(userId)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断当前登录的id是否是系统管理员
	 * @return
	 * 2016年7月4日
	 * author njq
	 */
	public static boolean checkUserId(){
		if(ConstantsCommon.Oper_User.ADMIN.equals(getUserId())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取当前HttpServletRequest
	 * @return
	 *//*
	public static HttpServletRequest getRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}
	
	public static HttpServletResponse getResp(){
		HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return null;
	}*/
}
