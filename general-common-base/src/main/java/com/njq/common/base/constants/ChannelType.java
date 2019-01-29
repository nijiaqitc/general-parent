package com.njq.common.base.constants;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;

public enum ChannelType implements ValueDescription {
	CNBLOGS("cnblogs", "博客园"),
	YH_WIKI("yhwiki", "永辉的wiki文档");
	private String value;
	private String description;
	private static List<ChannelType> VALUES = EnumUtils.getEnumList(ChannelType.class);

	ChannelType(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public static ChannelType getChannelType(String value) {
		return EnumHelper.getEnum(VALUES, ChannelType.class, value);
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * 获取值列表
	 * @return
	 */
	public static List<String> getChannelValueList(){
		List<String> valueList = new ArrayList<>();
		VALUES.forEach(n->{
			valueList.add(n.value);
		});
		return valueList;
	}
	
}
