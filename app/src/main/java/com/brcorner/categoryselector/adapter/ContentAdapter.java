package com.brcorner.categoryselector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brcorner.categoryselector.R;
import com.brcorner.categoryselector.model.CategoryItemInfo;

import java.util.List;

/**
 * Created by dong on 2015/9/11 0011.
 */
public class ContentAdapter extends ArrayAdapter<String>{

    private int resource;

    public ContentAdapter(Context context, int resource, List<String> lst) {
        super(context, resource, lst);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resource,null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (TextView) view.findViewById(R.id.tv);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        String str = getItem(position);
        viewHolder.iv.setText(str);
        return view;
    }

    private static class ViewHolder
    {
        TextView iv;
    }
}
