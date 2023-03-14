package com.nhnacademy.edu.domain;

import lombok.Data;

@Data
public class Pagination {
    private int pageSize;
    private int blockSize = 5;
    private int page;
    private int block;
    private int totalCount;
    private int totalPageCnt;
    private int totalBlockCnt;
    private int startPage;
    private int endPage;
    private int prevBlockLastPage;
    private int nextBlockFirstPage;

    public Pagination(int totalCount, int nowPage, int offset) {
        setPageSize(offset);
        setPage(nowPage);
        setTotalCount(totalCount);
        setTotalPageCnt((int) Math.ceil((double) totalCount / (double) pageSize));
        if (totalCount == 0) { setTotalPageCnt(1); }
        setTotalBlockCnt((int) Math.ceil((double) totalPageCnt / (double) blockSize));
        setBlock((int) Math.ceil((double) page / blockSize));
        setStartPage((block - 1) * blockSize + 1);
        setEndPage(startPage + blockSize - 1);

        if (endPage > totalPageCnt) {
            setEndPage(getTotalPageCnt());
        }

        setPrevBlockLastPage((block * blockSize) - blockSize);

        if (prevBlockLastPage < 1) {
            this.prevBlockLastPage = 1;
        }
        setNextBlockFirstPage((block * blockSize) + 1);

        if (nextBlockFirstPage > totalPageCnt) {
            nextBlockFirstPage = totalPageCnt;
        }

        if (totalPageCnt == 0) {
            setNextBlockFirstPage(1);
            setPrevBlockLastPage(1);
            setEndPage(1);
        }
    }
}
