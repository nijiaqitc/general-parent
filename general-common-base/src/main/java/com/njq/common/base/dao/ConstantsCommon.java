/**
 * 公用的常量类，存放所有常量
 */
package com.njq.common.base.dao;

public class ConstantsCommon {

    /**
     * 数据删除状态
     *
     * @author NJQ
     */
    public static class Del_Status {
        /**
         * 有效状态
         */
        public static final int YES = 1;
        /**
         * 删除状态
         */
        public static final int NO = 0;
    }

    /**
     * sql符号
     *
     * @author NJQ
     */
    public static class Sql_Sign {
        /**
         * like
         */
        public static final String LIKE = "like";
        /**
         * is null
         */
        public static final String ISNULL = "is null";
        /**
         * is not null
         */
        public static final String ISNOTNULL = "is not null";
        /**
         * >
         */
        public static final String GT = ">";
        /**
         * >=
         */
        public static final String GTE = ">=";
        /**
         * <
         */
        public static final String LT = "<";
        /**
         * <=
         */
        public static final String LTE = "<=";
        /**
         * =
         */
        public static final String EQ = "=";
        /**
         * !=
         */
        public static final String NOTEQ = "!=";
        /**
         * in
         */
        public static final String IN = "in";
        /**
         * not in
         */
        public static final String NOTIN = "not in";
        /**
         * between
         */
        public static final String BETWEEN = "between";
    }

    /**
     * 几个特殊的用户id
     *
     * @author NJQ
     */
    public static class Oper_User {
        /**
         * 超级管理员
         */
        public static final Long ADMIN = 1L;
        /**
         * 非本系统用户，如注册时还没有生成自己的id则用此
         */
        public static final Long NO_USER = 0L;
    }

    /**
     * 组织id
     *
     * @author NJQ
     */
    public static class Org_Id {
        /**
         * 顶级栏目的id
         */
        public static final Long FIRST_ORG_ID = 0L;
        /**
         * 其他篇，未选择顶部类型时默认使用此篇
         */
        public static final Long SECOND_ORG_ID = 1L;
        /**
         * 类型的排序值
         */
        public static final int IN_TURN = 1;
    }

    /**
     * 数据字典中的具体参数
     *
     * @author NJQ
     */
    public static class Code_No {

        /**
         * 重置用户信息
         */
        public static final String RESET_PASS = "reset";
        /**
         * 图片上传真实地址
         */
        public static final String PIC_UP = "picUp";
        /**
         * 服务器域名地址
         */
        public static final String SERVER_URL = "serverImage";

    }

    /**
     * 文章类型
     *
     * @author njq
     */
    public static class Doc_Type {
        /**
         * 普通文章
         */
        public static final int NORMAL = 1;
        /**
         * tbk文章
         */
        public static final int TBK = 2;
    }

    public static class Email_Type {
        /**
         * 需要发送邮件
         */
        public static final int TO_SEND = 1;
        /**
         * 不需要发送
         */
        public static final int NO_SEND = 0;
        /**
         * 已阅读
         */
        public static final int READ = 1;
        /**
         * 未阅读
         */
        public static final int NO_READ = 0;
    }

    /**
     * 用户操作类型
     *
     * @author njq
     */
    public static class User_Op {
        /**
         * 浏览
         */
        public static final String VIEW = "1";
        /**
         * 点赞
         */
        public static final String UP = "2";
        /**
         * 踩
         */
        public static final String DOWN = "3";
    }

    /**
     * 发表类型
     *
     * @author njq
     */
    public static class Novel_Type {
        /**
         * 书名
         */
        public static final int TYPE_DOC = 1;
        /**
         * 卷名
         */
        public static final int TYPE_JUAN = 2;
        /**
         * 标题
         */
        public static final int TYPE_TITLE = 3;
    }

    /**
     * 完成情况
     *
     * @author njq
     */
    public static class Finish_Status {
        /**
         * 未开始
         */
        public static final String NO_START = "0";
        /**
         * 开始中
         */
        public static final String STARTING = "1";
        /**
         * 已完成
         */
        public static final String FINISH = "2";
    }

    /**
     * 使用情况
     *
     * @author njq
     */
    public static class Use_Type {
        /**
         * 不使用
         */
        public static final String UN_USE = "0";
        /**
         * 使用
         */
        public static final String USED = "1";

    }


    public static class Common_Value {
        public static final String C_ZERO = "0";
        public static final String C_ONE = "1";
        public static final String C_TWO = "2";
    }
}
