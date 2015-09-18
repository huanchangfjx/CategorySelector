package com.brcorner.categoryselector.biz;

import com.brcorner.categoryselector.listener.OnCategoryListener;
import com.brcorner.categoryselector.model.CategoryInfo;

import java.util.List;

/**
 * Created by dong on 2015/9/11 0011.
 */
public interface ICategoryBiz {
    public void getCategorys(OnCategoryListener onCategoryListener);
}
