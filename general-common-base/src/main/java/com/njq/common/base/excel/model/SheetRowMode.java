package com.njq.common.base.excel.model;
public class SheetRowMode {
    private int sheetNumber;
    private int rowNumber;

    public SheetRowMode(int sheetNumber, int rowNumber) {
        this.sheetNumber = sheetNumber;
        this.rowNumber = rowNumber;
    }

    public static SheetRowMode getDefaultMode(){
        return new SheetRowMode(0,1);
    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
