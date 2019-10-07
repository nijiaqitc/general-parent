package com.njq.common.base.excel;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.njq.common.base.excel.model.ReadExcelRequest;

public interface ExcelReadHelper {
    /**
     * 读取excel
     * @param readExcelRequest 请求
     * @param <T> 行数据类型
     * @return 数据列表
     */
    @NotNull
    <T> List<T> readExcel(@NotNull ReadExcelRequest<T> readExcelRequest);
}
