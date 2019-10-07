package com.njq.common.base.excel.model;

import org.springframework.web.multipart.MultipartFile;

import com.njq.common.base.excel.ExcelRowReader;
import com.njq.common.exception.BaseKnownException;

public class ReadExcelRequestBuilder<T> {
    protected MultipartFile excel;
    protected SheetRowMode sheetRowMode;
    protected ExcelRowReader<T> rowReader;

    public ReadExcelRequestBuilder<T> ofExcel(MultipartFile excel) {
        this.excel = excel;
        return this;
    }

    public ReadExcelRequestBuilder<T> useMode(SheetRowMode sheetRowMode) {
        this.sheetRowMode = sheetRowMode;
        return this;
    }

    public ReadExcelRequestBuilder<T> ofReader(ExcelRowReader<T> rowReader) {
        this.rowReader = rowReader;
        return this;
    }

    public ReadExcelRequest<T> build() {
        if (this.excel == null || this.excel.isEmpty()) {
            throw new BaseKnownException("表单不能为空");
        }
        if (this.sheetRowMode == null) {
            throw new BaseKnownException("表单读取模式不能为空");
        }
        if (this.sheetRowMode.getSheetNumber() < 0 || this.sheetRowMode.getRowNumber() < 0) {
            throw new BaseKnownException("表单读取模式异常");
        }
        if (this.rowReader == null) {
            throw new BaseKnownException("表单行数据读取操作不能为空");
        }

        return new ReadExcelRequest<>(this);
    }
}