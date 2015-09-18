package com.brcorner.categoryselector.biz;

import com.alibaba.fastjson.JSON;
import com.brcorner.categoryselector.listener.OnCategoryListener;
import com.brcorner.categoryselector.model.CategoryInfo;
import com.brcorner.categoryselector.utils.CustomUtils;

import java.util.List;

/**
 * Created by dong on 2015/9/11 0011.
 */
public class CategoryBiz implements ICategoryBiz{

    @Override
    public void getCategorys(OnCategoryListener onCategoryListener) {
        List<CategoryInfo> categoryInfos = CustomUtils.getDiyPicInfo("json/classify.json");

        if(categoryInfos.size() != 0)
        {
            onCategoryListener.getCategorySuccess(categoryInfos);
        }
        else
        {
            onCategoryListener.getCategoryFail("you can't get the message and fuck you up to the sky");
        }

    }
}
