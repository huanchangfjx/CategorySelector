package com.brcorner.categoryselector.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.alibaba.fastjson.JSON;
import com.brcorner.categoryselector.application.MyApplication;
import com.brcorner.categoryselector.model.CategoryInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by dong on 2015/9/18 0018.
 */
public class CustomUtils {
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static List<CategoryInfo> getDiyPicInfo(String jsonName) {
        List<CategoryInfo> categoryInfos = null;

        StringBuffer jsonStr = new StringBuffer();
        try {
            InputStream inputStream = MyApplication.getContext().getAssets().open(jsonName);
            InputStreamReader isr = new InputStreamReader(inputStream, "utf8");
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                jsonStr.append(line);
            }
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonStr.toString());
            String resultString = jsonObject.getString("result");
            categoryInfos = JSON.parseArray(resultString, CategoryInfo.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryInfos;
    }
}
