package com.njq.common.base.excel.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.njq.common.base.excel.ExcelConstant;
import com.njq.common.base.excel.ExcelReadHelper;
import com.njq.common.base.excel.model.ReadExcelRequest;
import com.njq.common.exception.BaseKnownException;

@Component
public class ExcelReadHelperImpl implements ExcelReadHelper {
    /**
     * 读取excel
     * @param request 请求
     * @param <T> 行数据类型
     * @return 数据列表
     */
    @SuppressWarnings("resource")
	@Override
    @NotNull
    public <T> List<T> readExcel(@NotNull ReadExcelRequest<T> request) {
        InputStream inputStream;
        Workbook workbook;
        List<T> dataList = new ArrayList<>();
        try {
            inputStream = request.getExcel().getInputStream();
            if (StringUtils.endsWith(request.getExcel().getOriginalFilename(),
                    ExcelConstant.SUFFIX_XLSX)) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (StringUtils.endsWith(request.getExcel().getOriginalFilename(),
                    ExcelConstant.SUFFIX_XLS)) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                throw new BaseKnownException("上传的文件不是excel格式");
            }
            Sheet sheet = workbook.getSheetAt(request.getSheetRowMode().getSheetNumber());
            for (int i = request.getSheetRowMode().getRowNumber(); i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                T data = request.getRowReader().readRowData(row);
                dataList.add(data);
            }
        } catch (IOException | NullPointerException e) {
            throw new BaseKnownException("上传的Excel文件解析错误，错误信息：" + e.getMessage());
        }
        return dataList;
    }
}