package com.njq.common.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.BaseBanner;
import com.njq.common.model.po.BaseChannel;
import com.njq.common.model.po.BaseCode;
import com.njq.common.model.po.BaseLog;
import com.njq.common.model.po.BaseMenu;
import com.njq.common.model.po.BaseMessage;
import com.njq.common.model.po.BaseRule;
import com.njq.common.model.po.BaseRuleChannelConfig;
import com.njq.common.model.po.BaseTip;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleGrab;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.BaseType;
import com.njq.common.model.po.BaseTypeNum;
import com.njq.common.model.po.BaseUser;
import com.njq.common.model.po.BaseUserChannelConfig;
import com.njq.common.model.po.BaseUserRuleConfig;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.po.TbkDoc;
import com.njq.common.model.po.TbkDocPicConfig;
import com.njq.common.model.po.TbkDocTipConfig;
import com.njq.common.model.po.TbkPic;
import com.njq.common.model.po.TbkRecommendDocView;
import com.njq.common.model.po.TbkTip;
import com.njq.common.model.po.TbkType;
import com.njq.common.model.po.TbkTypeDocConfig;
import com.njq.common.model.po.TbkTypePicConfig;
import com.njq.common.model.po.ToolFeeling;
import com.njq.common.model.po.ToolResourceshare;
import com.njq.common.model.po.XsDocDetail;
import com.njq.common.model.po.XsDocDiscuss;
import com.njq.common.model.po.XsDocGeneralInfo;
import com.njq.common.model.po.XsDocUserOp;
import com.njq.common.model.po.XsTitleDesign;
import com.njq.common.model.po.XsTitleDetail;
import com.njq.common.model.po.YxlColumnDefine;
import com.njq.common.model.po.YxlColumnStore;
import com.njq.common.model.po.YxlDoc;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.po.YxlDocTipConfig;
import com.njq.common.model.po.YxlFolder;
import com.njq.common.model.po.YxlNote;
import com.njq.common.model.po.YxlNoteGeneral;
import com.njq.common.model.po.YxlTip;
import com.njq.common.model.po.YxlType;
import com.njq.common.model.po.YxlTypeName;

@Configuration
public class DaoBeanConfig {

//	<!-- 	系统基本功能开始 -->
	@Bean
	public DaoCommon<BaseUser> userDao() {
		return new DaoCommon<BaseUser>(BaseUser.class);
	}

	@Bean
	public DaoCommon<BaseChannel> channelDao() {
		return new DaoCommon<BaseChannel>(BaseChannel.class);
	}

	@Bean
	public DaoCommon<BaseCode> codeDao() {
		return new DaoCommon<BaseCode>(BaseCode.class);
	}

	@Bean
	public DaoCommon<BaseLog> logDao() {
		return new DaoCommon<BaseLog>(BaseLog.class);
	}

	@Bean
	public DaoCommon<BaseRule> ruleDao() {
		return new DaoCommon<BaseRule>(BaseRule.class);
	}

	@Bean
	public DaoCommon<BaseRuleChannelConfig> ruleChannelConfigDao() {
		return new DaoCommon<BaseRuleChannelConfig>(BaseRuleChannelConfig.class);
	}

	@Bean
	public DaoCommon<BaseUserChannelConfig> userChannelConfigDao() {
		return new DaoCommon<BaseUserChannelConfig>(BaseUserChannelConfig.class);
	}

	@Bean
	public DaoCommon<BaseUserRuleConfig> userRuleConfigDao() {
		return new DaoCommon<BaseUserRuleConfig>(BaseUserRuleConfig.class);
	}

	@Bean
	public DaoCommon<BaseMessage> messageDao() {
		return new DaoCommon<BaseMessage>(BaseMessage.class);
	}

	@Bean
	public DaoCommon<BaseBanner> bannerDao() {
		return new DaoCommon<BaseBanner>(BaseBanner.class);
	}

	@Bean
	public DaoCommon<BaseTitle> baseTitleDao() {
		return new DaoCommon<BaseTitle>(BaseTitle.class);
	}

	@Bean
	public DaoCommon<BaseTitleLoading> baseTitleLoadingDao() {
		return new DaoCommon<BaseTitleLoading>(BaseTitleLoading.class);
	}

	@Bean
	public DaoCommon<BaseTip> baseTipDao() {
		return new DaoCommon<BaseTip>(BaseTip.class);
	}

	@Bean
	public DaoCommon<BaseTitleGrab> baseTitleGrabDao() {
		return new DaoCommon<BaseTitleGrab>(BaseTitleGrab.class);
	}

	@Bean
	public DaoCommon<BaseType> baseTypeDao() {
		return new DaoCommon<BaseType>(BaseType.class);
	}

	@Bean
	public DaoCommon<BaseTypeNum> baseTypeNumDao() {
		return new DaoCommon<BaseTypeNum>(BaseTypeNum.class);
	}

	@Bean
	public DaoCommon<BaseMenu> baseMenuDao() {
		return new DaoCommon<BaseMenu>(BaseMenu.class);
	}

	@Bean
	public DaoCommon<GrabDoc> grabDocDao() {
		return new DaoCommon<GrabDoc>(GrabDoc.class);
	}
	
	@Bean
	public DaoCommon<GrabUrlInfo> grabUrlInfoDao() {
		return new DaoCommon<GrabUrlInfo>(GrabUrlInfo.class);
	}
//	<!-- 	系统基本功能结束 -->

//	<!-- 	tbk部分开始 -->
	@Bean
	public DaoCommon<TbkDoc> tbkdocDao() {
		return new DaoCommon<TbkDoc>(TbkDoc.class);
	}

	@Bean
	public DaoCommon<TbkDocPicConfig> tbkdocpicconfigDao() {
		return new DaoCommon<TbkDocPicConfig>(TbkDocPicConfig.class);
	}

	@Bean
	public DaoCommon<TbkDocTipConfig> tbkdoctipconfigDao() {
		return new DaoCommon<TbkDocTipConfig>(TbkDocTipConfig.class);
	}

	@Bean
	public DaoCommon<TbkPic> tbkpicDao() {
		return new DaoCommon<TbkPic>(TbkPic.class);
	}

	@Bean
	public DaoCommon<TbkTip> tbktipDao() {
		return new DaoCommon<TbkTip>(TbkTip.class);
	}

	@Bean
	public DaoCommon<TbkType> tbktypeDao() {
		return new DaoCommon<TbkType>(TbkType.class);
	}

	@Bean
	public DaoCommon<TbkTypeDocConfig> tbktypedocconfigDao() {
		return new DaoCommon<TbkTypeDocConfig>(TbkTypeDocConfig.class);
	}

	@Bean
	public DaoCommon<TbkTypePicConfig> tbktypepicconfigDao() {
		return new DaoCommon<TbkTypePicConfig>(TbkTypePicConfig.class);
	}

//	<!-- 	tbk部分结束 -->
//	<!-- 	其他开始 -->
	@Bean
	public DaoCommon<ToolFeeling> feelingDao() {
		return new DaoCommon<ToolFeeling>(ToolFeeling.class);
	}

	@Bean
	public DaoCommon<ToolResourceshare> resourceshareDao() {
		return new DaoCommon<ToolResourceshare>(ToolResourceshare.class);
	}
//	<!-- 	其他结束 -->

//	<!-- 小说区域开始 -->
	@Bean
	public DaoCommon<XsTitleDesign> titleDao() {
		return new DaoCommon<XsTitleDesign>(XsTitleDesign.class);
	}

	@Bean
	public DaoCommon<XsDocUserOp> docUserOpDao() {
		return new DaoCommon<XsDocUserOp>(XsDocUserOp.class);
	}

	@Bean
	public DaoCommon<XsDocGeneralInfo> docGeneralInfoDao() {
		return new DaoCommon<XsDocGeneralInfo>(XsDocGeneralInfo.class);
	}

	@Bean
	public DaoCommon<XsDocDiscuss> docDiscussDao() {
		return new DaoCommon<XsDocDiscuss>(XsDocDiscuss.class);
	}

	@Bean
	public DaoCommon<XsDocDetail> docDetailDao() {
		return new DaoCommon<XsDocDetail>(XsDocDetail.class);
	}

	@Bean
	public DaoCommon<XsTitleDetail> titleDetailDao() {
		return new DaoCommon<XsTitleDetail>(XsTitleDetail.class);
	}

//	<!-- 小说区域结束 -->
//  <!-- 系列文章开始 -->
	@Bean
	public DaoCommon<YxlDoc> yxlDocDao() {
		return new DaoCommon<YxlDoc>(YxlDoc.class);
	}

	@Bean
	public DaoCommon<YxlType> yxlTypeDao() {
		return new DaoCommon<YxlType>(YxlType.class);
	}

	@Bean
	public DaoCommon<YxlDocSearch> yxlDocSearchDao() {
		return new DaoCommon<YxlDocSearch>(YxlDocSearch.class);
	}

	@Bean
	public DaoCommon<YxlTip> yxlTipDao() {
		return new DaoCommon<YxlTip>(YxlTip.class);
	}

	@Bean
	public DaoCommon<YxlDocTipConfig> yxlDocTipConfigDao() {
		return new DaoCommon<YxlDocTipConfig>(YxlDocTipConfig.class);
	}

	@Bean
	public DaoCommon<YxlNote> yxlNoteDao() {
		return new DaoCommon<YxlNote>(YxlNote.class);
	}

	@Bean
	public DaoCommon<YxlNoteGeneral> yxlNoteGeneralDao() {
		return new DaoCommon<YxlNoteGeneral>(YxlNoteGeneral.class);
	}

	@Bean
	public DaoCommon<YxlFolder> yxlFolderDao() {
		return new DaoCommon<YxlFolder>(YxlFolder.class);
	}

	@Bean
	public DaoCommon<YxlTypeName> yxlTypeNameDao() {
		return new DaoCommon<YxlTypeName>(YxlTypeName.class);
	}

	@Bean
	public DaoCommon<YxlColumnDefine> yxlColumnDefineDao() {
		return new DaoCommon<YxlColumnDefine>(YxlColumnDefine.class);
	}

	@Bean
	public DaoCommon<YxlColumnStore> yxlColumnStoreDao() {
		return new DaoCommon<YxlColumnStore>(YxlColumnStore.class);
	}

//	<!-- 系列文章结束 -->
//	<!-- 	视图对象开始 -->
	@Bean
	public DaoCommon<TbkRecommendDocView> tbkRecommendDocViewDao() {
		return new DaoCommon<TbkRecommendDocView>(TbkRecommendDocView.class);
	}

//	<!-- 	视图对象结束 -->

}
