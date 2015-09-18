package com.brcorner.categoryselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.brcorner.categoryselector.adapter.ContentAdapter;
import com.brcorner.categoryselector.biz.CategoryBiz;
import com.brcorner.categoryselector.biz.ICategoryBiz;
import com.brcorner.categoryselector.listener.ListScrollDistanceCalculator;
import com.brcorner.categoryselector.listener.OnCategoryListener;
import com.brcorner.categoryselector.model.CategoryInfo;
import com.brcorner.categoryselector.view.PullDownListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ICategoryBiz categoryBiz;
    private ListView listView;
    private ContentAdapter contentAdapter;
    private int content_r = R.layout.item_content;
    private List<String> list = new ArrayList<>();

    private PullDownListView section_listview_pulldown;
    private FrameLayout fl_headview;
    private View view_headview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryBiz = new CategoryBiz();
        initViews();
    }

    public void initViews(){
        listView = (ListView) findViewById(R.id.listview);
        section_listview_pulldown = (PullDownListView) findViewById(R.id.section_listview_pulldown);
        contentAdapter = new ContentAdapter(this,content_r,list);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
        fl_headview = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.section_header_empty, null);
        view_headview = fl_headview.findViewById(R.id.view_headview);
        view_headview.setLayoutParams(layoutParams);
        listView.addHeaderView(fl_headview);
        listView.setAdapter(contentAdapter);
        ListScrollDistanceCalculator listScrollDistanceCalculator = new ListScrollDistanceCalculator();
        listScrollDistanceCalculator.setScrollDistanceListener(new ListScrollDistanceCalculator.ScrollDistanceListener() {
            @Override
            public void onScrollDistanceChanged(int delta, int total, int mFirstVisibleTop, int restCount) {
//                if (restCount < Constant.REFRESHSIZE) {
//                    sendTopicList();
//                }
                //滑动delta值大于20或者 滑到顶部
                if (delta > 20 || mFirstVisibleTop == 0) {
                    section_listview_pulldown.getChll_topicType().showView();
                } else if (delta < -20) {
                    section_listview_pulldown.getChll_topicType().hideView();
                }
            }
        });
        listView.setOnScrollListener(listScrollDistanceCalculator);
        initData();
    }

    public void initData()
    {
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        list.add("Fuck Actor up");
        getCategorys();
    }

    public void changeViews()
    {
        contentAdapter.notifyDataSetChanged();
    }

    public void getCategorys()
    {
        categoryBiz.getCategorys(new OnCategoryListener() {
            @Override
            public void getCategorySuccess(List<CategoryInfo> categoryInfos) {
                changeViews();
                section_listview_pulldown.setClassifyData(categoryInfos);
            }

            @Override
            public void getCategoryFail(String failMessage) {
                Toast.makeText(MainActivity.this,failMessage,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
