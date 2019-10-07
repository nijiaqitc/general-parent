package com.njq.common.base.excel;

import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface ExcelRowReader<T> {
    /**
     * 读取excel行数据方式
     *
     * @param row 行
     * @return 行数据
     */
    T readRowData(Row row);
}