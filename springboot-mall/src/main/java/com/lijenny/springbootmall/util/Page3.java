package com.lijenny.springbootmall.util;


import java.util.List;

public class Page3<T>{
    private Integer limit;
    private Integer offset;
    private Integer total;
    private Integer totalShoppingAmount;
    private List <T> results;

    public Integer getTotalShoppingAmount() {
        return totalShoppingAmount;
    }

    public void setTotalShoppingAmount(Integer totalShoppingAmount) {
        this.totalShoppingAmount = totalShoppingAmount;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
