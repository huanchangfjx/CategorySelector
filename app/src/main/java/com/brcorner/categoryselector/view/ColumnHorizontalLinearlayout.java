package com.brcorner.categoryselector.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.brcorner.categoryselector.R;
import com.brcorner.categoryselector.model.CategoryInfo;
import com.brcorner.categoryselector.utils.CustomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong on 2015/8/18 0018.
 */
public class ColumnHorizontalLinearlayout extends LinearLayout {

    private Context context;
    private List<View> itemList;
    private int focusIndex = -1;
    private int focusTime;
    private TextView emptyTv;
    private ProgressBar pb_follow;
    private FrameLayout view_classify_bg;



    public ColumnHorizontalLinearlayout(Context context) {
        super(context);
        this.context = context;
        itemList = new ArrayList<>();
        init();
    }

    public ColumnHorizontalLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        itemList = new ArrayList<>();
        init();
    }

    public void init()
    {
        view_classify_bg = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.view_classify_bg,null);
        emptyTv = (TextView) view_classify_bg.findViewById(R.id.tv_no_data);
        pb_follow = (ProgressBar) view_classify_bg.findViewById(R.id.pb_loading);
        this.addView(view_classify_bg);
    }

    public void loading()
    {
        emptyTv.setVisibility(View.GONE);
        pb_follow.setVisibility(View.VISIBLE);
    }

    public void setRefresh(OnClickListener itemOnClickListener)
    {
        emptyTv.setOnClickListener(itemOnClickListener);
    }

    public void setData(List<CategoryInfo> list,OnClickListener itemOnClickListener) {
        int listSize = list.size();
        if(listSize != 0)
        {
            view_classify_bg.setVisibility(View.GONE);
            int screenWidth = CustomUtils.getScreenWidth(context) / listSize;
            for (int i = 0; i < listSize; i++) {
                View item_classify = LayoutInflater.from(context).inflate(R.layout.item_classify, null);
                item_classify.setLayoutParams(new ViewGroup.LayoutParams(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT));
                TextView tv_classify = (TextView) item_classify.findViewById(R.id.tv_classify);
                tv_classify.setText(list.get(i).getCategoryName());
                item_classify.setTag(R.id.key, i);
                item_classify.setOnClickListener(itemOnClickListener);
                itemList.add(item_classify);
                this.addView(item_classify);
            }

        }
        else
        {
            this.unloading();
        }
    }


    public void setFocus(int index) {

        if(focusIndex != index)
        {
            View focus_item_classify = itemList.get(index);
            Animation focus_loadAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);
            focus_loadAnimation.setFillAfter(true);
            ImageView focus_iv_direction = (ImageView) focus_item_classify.findViewById(R.id.iv_direction);
            focus_iv_direction.startAnimation(focus_loadAnimation);

            if (focusIndex != -1) {
                View item_classify = itemList.get(focusIndex);
                Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_reverse);
                loadAnimation.setFillAfter(true);
                ImageView iv_direction = (ImageView) item_classify.findViewById(R.id.iv_direction);
                iv_direction.startAnimation(loadAnimation);//转回去
            }

            focusTime = 1;
        }
        else
        {
            if(focusTime % 2 == 1)
            {
                View item_classify = itemList.get(focusIndex);
                Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_reverse);
                ImageView iv_direction = (ImageView) item_classify.findViewById(R.id.iv_direction);
                loadAnimation.setFillAfter(true);
                iv_direction.startAnimation(loadAnimation);//转回去
            }
            else
            {
                View focus_item_classify = itemList.get(index);
                Animation focus_loadAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);
                ImageView focus_iv_direction = (ImageView) focus_item_classify.findViewById(R.id.iv_direction);
                focus_loadAnimation.setFillAfter(true);
                focus_iv_direction.startAnimation(focus_loadAnimation);
            }
            focusTime ++;
        }

        focusIndex = index;
    }

    private boolean isShow = true;

    public void showView()
    {
        if(!isShow)
        {
            float[] f = new float[2];
            f[0] = -this.getHeight();
            f[1] = 0F;
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "translationY", f);
            animator1.setDuration(200);
            animator1.setInterpolator(new AccelerateDecelerateInterpolator());
            animator1.start();
            isShow = true;
        }
    }

    public void hideView()
    {
        if(isShow)
        {
            float[] f = new float[2];
            f[0] = 0.0F;
            f[1] = -this.getHeight();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "translationY", f);
            animator1.setInterpolator(new AccelerateDecelerateInterpolator());
            animator1.setDuration(200);
            animator1.start();
            isShow = false;
        }

    }

    public void unloading() {
        if(!emptyTv.isShown())
        {
            emptyTv.setVisibility(View.VISIBLE);
        }
        if(pb_follow.isShown())
        {
            pb_follow.setVisibility(View.GONE);
        }
    }
}
