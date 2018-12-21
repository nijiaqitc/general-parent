package com.njq.start.testcontroller;

import com.njq.common.base.constants.EnumHelper;
import com.njq.common.base.constants.ValueDescription3;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

public enum YHSeller implements ValueDescription3 {
    MALL(1L, "次日达商城"),
    HYD(2L, "永辉生活"),
    BRAVO(3L, "永辉超市"),
    CSX(4L, "彩食鲜"),
    BRAVO_JB(5L, "Bravo精标店"),
    SUPER_SPECIES(6L, "超级物种"),
    SUPER_STORES(7L, "永辉超市"),
    CSX_MALL(8L, "彩食鲜商城"),
    EXCELLENT_STORES(9L, "红标优选店"),
    SHANG_GREENS(10L, "上蔬永辉"),
    JISUDA(11L, "极速达"),
    ZHONGBAI(13L, "永辉中百"),
    YONGHUIGUANJIA(14L, "永辉到家");

    private static List<YHSeller> VALUES = EnumUtils.getEnumList(YHSeller.class);
    private long value;
    private String description;

    private YHSeller(long value, String description) {
        this.value = value;
        this.description = description;
    }

    public static YHSeller getYHSeller(long value) {
        return (YHSeller) EnumHelper.getEnumFromLongWithoutException(VALUES, value);
    }

    @Override
    public long getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}