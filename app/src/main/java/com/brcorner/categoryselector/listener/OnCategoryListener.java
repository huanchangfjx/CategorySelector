package com.brcorner.categoryselector.listener;

import com.brcorner.categoryselector.model.CategoryInfo;

import java.util.List;

/**
 * Created by dong on 2015/9/11 0011.
 */
public interface OnCategoryListener {
    void getCategorySuccess(List<CategoryInfo> categoryInfos);
    void getCategoryFail(String failMessage);
}
