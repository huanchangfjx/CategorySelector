package com.brcorner.categoryselector.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.brcorner.categoryselector.R;
import com.brcorner.categoryselector.adapter.CategoryAdapter;
import com.brcorner.categoryselector.model.CategoryInfo;
import com.brcorner.categoryselector.model.CategoryItemInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong on 2015/8/26 0026.
 */
public class PullDownListView extends LinearLayout implements View.OnClickListener{

    private Context context;
    private ColumnHorizontalLinearlayout chll_topicType;//二级列表
    private LinearLayout ll_pulldown;
    private List<CategoryInfo> classifyList;

    private ListView list_topicType;//三级列表
    private CategoryAdapter categoryAdapter;
    private List<CategoryItemInfo> categoryItemInfos = new ArrayList<>();//

    private List<String> selectList;//用于记录item选择
    private Animation loadAnimation;
    private Animation alphaAnimation;

    private int classifyFocusIndex = -1;//-1 不显示
    private boolean isShowPop;

    private View view_bg;

    private List<String> preSelectList;//用于记录上一次item的选择

    public PullDownListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PullDownListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void init()
    {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.section_listview_pulldown,this);
        TextView tv_cancel = (TextView) rootView.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) rootView.findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        chll_topicType = (ColumnHorizontalLinearlayout) rootView.findViewById(R.id.chll_topicType);
        ll_pulldown = (LinearLayout) rootView.findViewById(R.id.ll_pulldown);
        ll_pulldown.setVisibility(View.GONE);
        view_bg = rootView.findViewById(R.id.view_bg);
        view_bg.setVisibility(View.GONE);
        list_topicType = (ListView) rootView.findViewById(R.id.list_topicType);
        selectList = new ArrayList<>();//默认全选
        preSelectList = new ArrayList<>();//之前选择的
        categoryAdapter = new CategoryAdapter(context, R.layout.item_pulldown, categoryItemInfos, selectList);
        list_topicType.setAdapter(categoryAdapter);
        list_topicType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)//选择全部
                {
                    //取消其他的
                    if (categoryAdapter.isSelectedAll()) {
                        for (CategoryItemInfo categoryItemInfo : categoryItemInfos) {
                            if (!TextUtils.isEmpty(categoryItemInfo.getCategoryItemId())) {
                                if (selectList.contains(categoryItemInfo.getCategoryItemId())) {
                                    selectList.remove(categoryItemInfo.getCategoryItemId());
                                }
                            }
                        }
                    } else {
                        for (CategoryItemInfo classifyItemInfo : categoryItemInfos) {
                            if (!TextUtils.isEmpty(classifyItemInfo.getCategoryItemId())) {
                                if (!selectList.contains(classifyItemInfo.getCategoryItemId())) {
                                    selectList.add(classifyItemInfo.getCategoryItemId());
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (categoryAdapter.isSelectedAll())
                    {
                        for (CategoryItemInfo classifyItemInfo : categoryItemInfos) {
                            if (!TextUtils.isEmpty(classifyItemInfo.getCategoryItemId())) {
                                if (selectList.contains(classifyItemInfo.getCategoryItemId())) {
                                    selectList.remove(classifyItemInfo.getCategoryItemId());
                                }
                            }
                        }
                        selectList.add(categoryItemInfos.get(position).getCategoryItemId());
                    }
                    else
                    {
                        if (selectList.contains(categoryItemInfos.get(position).getCategoryItemId())) {
                            selectList.remove(categoryItemInfos.get(position).getCategoryItemId());
                        } else {
                            selectList.add(categoryItemInfos.get(position).getCategoryItemId());
                        }
                    }
                }

                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setRefresh(OnClickListener itemOnClickListener)
    {
        chll_topicType.setRefresh(itemOnClickListener);
    }

    public void loading()
    {
        chll_topicType.loading();
    }

    public void unLoading()
    {
        chll_topicType.unloading();
    }

    public void setClassifyData(List<CategoryInfo> list)
    {
        classifyList = list;
        //默认全选
        for (CategoryInfo categoryInfo:list)
        {
            List<CategoryItemInfo> infoList = categoryInfo.getCategoryItemInfos();
            for(CategoryItemInfo categoryItemInfo:infoList)
            {
                selectList.add(categoryItemInfo.getCategoryItemId());
                preSelectList.add(categoryItemInfo.getCategoryItemId());
            }
        }

        chll_topicType.setData(list,classifyClickListener);
    }


    private OnClickListener classifyClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (int) v.getTag(R.id.key);
            if (i != classifyFocusIndex) {

                List<CategoryItemInfo> mitemList = classifyList.get(i).getCategoryItemInfos();
                categoryItemInfos.clear();
                categoryItemInfos.addAll(mitemList);
                categoryAdapter.notifyDataSetChanged();
                classifyFocusIndex = i;
                if (!isShowPop) {
                    ll_pulldown.setVisibility(View.VISIBLE);
                    loadAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_pulldown_in);
                    ll_pulldown.startAnimation(loadAnimation);
                    isShowPop = true;
                    view_bg.setVisibility(View.VISIBLE);
                    alphaAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_deep);
                    view_bg.startAnimation(alphaAnimation);
                }
                chll_topicType.setFocus(i);
            } else {
                invisiblePopWindow();
            }
        }
    };




    public void invisiblePopWindow()
    {
        loadAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_pulldown_out);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_pulldown.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        ll_pulldown.startAnimation(loadAnimation);
        isShowPop = false;
        chll_topicType.setFocus(classifyFocusIndex);

        alphaAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_light);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view_bg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        view_bg.startAnimation(alphaAnimation);
        classifyFocusIndex = -1;
    }


    @Override
    public void onClick(View v) {

    }


    public ColumnHorizontalLinearlayout getChll_topicType() {
        return chll_topicType;
    }

    public List<String> getSelectList() {
        return selectList;
    }

    public List<String> getPreSelectList() {
        return preSelectList;
    }
}
