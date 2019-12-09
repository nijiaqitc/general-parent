package com.njq.grab.service.impl.txsq;

import java.io.Serializable;

public class Payload implements Serializable {
        private int columnId;
        private int pageNumber;
        private int pageSize;
        private int tagId;

        public int getColumnId() {
            return columnId;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }
    }