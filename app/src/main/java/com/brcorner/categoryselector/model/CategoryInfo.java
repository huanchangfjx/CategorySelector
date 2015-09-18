package com.brcorner.categoryselector.model;

import java.util.List;

/**
 * Created by dong on 2015/9/11 0011.
 */
public class CategoryInfo {
    private String categoryId;
    private String categoryName;
    private List<CategoryItemInfo> categoryItemInfos;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<CategoryItemInfo> getCategoryItemInfos() {
        return categoryItemInfos;
    }

    public void setCategoryItemInfos(List<CategoryItemInfo> categoryItemInfos) {
        this.categoryItemInfos = categoryItemInfos;
    }
}
