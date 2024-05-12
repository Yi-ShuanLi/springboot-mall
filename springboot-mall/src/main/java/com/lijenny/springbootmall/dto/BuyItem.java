package com.lijenny.springbootmall.dto;

import com.lijenny.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;
//對應到前端所傳過來的object 裡面的buyItemList的 object
public class BuyItem {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public static class ProductQueryParams {
        private ProductCategory category;
        private String search;
        private String orderBy;
        private String sort;
        private Integer limit;
        private Integer offset;

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

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public ProductCategory getCategory() {
            return category;
        }

        public void setCategory(ProductCategory category) {
            this.category = category;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }
}
