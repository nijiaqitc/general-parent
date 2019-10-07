package com.njq.common.base.excel.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

import com.njq.common.base.excel.ExcelRowReader;

public class ReadExcelRequest<T> {
    private MultipartFile excel;
    private SheetRowMode sheetRowMode;
    private ExcelRowReader<T> rowReader;

    public ReadExcelRequest() {
    }

    public ReadExcelRequest(ReadExcelRequestBuilder<T> builder) {
        this.excel = builder.excel;
        this.sheetRowMode = builder.sheetRowMode;
        this.rowReader = builder.rowReader;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.JSON_STYLE)
                .append("excel", excel)
                .append("sheetRowMode", sheetRowMode)
                .append("rowReader", rowReader)
                .toString();
    }

    public MultipartFile getExcel() {
        return excel;
    }

    public SheetRowMode getSheetRowMode() {
        return sheetRowMode;
    }

    public ExcelRowReader<T> getRowReader() {
        return rowReader;
    }
}