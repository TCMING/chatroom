package com.uestc.controllerteam.chartservice.model;

public class PageDto {
    int startIndex;
    int pageSize;

    public PageDto(int startIndex, int pageSize) {
        this.startIndex = startIndex;
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
