package com.brcorner.categoryselector.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by dong on 2015/8/21 0021.
 */
public class MyLinearLayout extends LinearLayout
{

    public MyLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i)
    {

        if (i == 0)
        {
            return 1;
        }
        else if (i == 1)
        {
            return 0;
        }

        return super.getChildDrawingOrder(childCount, i);

    }

}