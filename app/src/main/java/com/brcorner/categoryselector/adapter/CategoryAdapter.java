package com.brcorner.categoryselector.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.brcorner.categoryselector.R;
import com.brcorner.categoryselector.model.CategoryItemInfo;

import java.util.List;

/**
 * Created by dong on 2015/9/11 0011.
 */
public class CategoryAdapter extends ArrayAdapter<CategoryItemInfo>{
    private int resourceId;
    private List<String> selectList;
    private List<CategoryItemInfo> list;

    public CategoryAdapter(Context context, int resource, List<CategoryItemInfo> list, List<String> selectList) {
        super(context, resource,list);
        resourceId = resource;
        this.selectList = selectList;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    //判断是否全选
    public boolean isSelectedAll()
    {
        for (CategoryItemInfo categoryItemInfo:list) {
            if(!TextUtils.isEmpty(categoryItemInfo.getCategoryItemId()))
            {
                if(!selectList.contains(categoryItemInfo.getCategoryItemId()))
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        CategoryItemInfo categoryItemInfo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_typeName = (TextView) view.findViewById(R.id.tv_typeName);
            viewHolder.cb_typeIsSelect = (CheckBox) view.findViewById(R.id.cb_typeIsSelect);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //第一项的id是空
        if(!TextUtils.isEmpty(categoryItemInfo.getCategoryItemId()) && selectList.contains(categoryItemInfo.getCategoryItemId()) && (!isSelectedAll()))
        {
            viewHolder.cb_typeIsSelect.setChecked(true);
        }else if(TextUtils.isEmpty(categoryItemInfo.getCategoryItemId()) && isSelectedAll())
        {
            viewHolder.cb_typeIsSelect.setChecked(true);
        }
        else
        {
            viewHolder.cb_typeIsSelect.setChecked(false);
        }
        viewHolder.tv_typeName.setText(categoryItemInfo.getCategoryItemName());
        return view;
    }

    class ViewHolder{
        TextView tv_typeName;
        CheckBox cb_typeIsSelect;
    }
}
