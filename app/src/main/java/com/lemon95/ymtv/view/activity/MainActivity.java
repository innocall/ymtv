package com.lemon95.ymtv.view.activity;

import android.animation.Animator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;

import com.lemon95.androidtvwidget.bridge.EffectNoDrawBridge;
import com.lemon95.androidtvwidget.bridge.OpenEffectBridge;
import com.lemon95.androidtvwidget.utils.OPENLOG;
import com.lemon95.androidtvwidget.view.MainUpView;
import com.lemon95.androidtvwidget.view.OpenTabHost;
import com.lemon95.androidtvwidget.view.ReflectItemView;
import com.lemon95.androidtvwidget.view.TextViewWithTTF;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.adapter.OpenTabTitleAdapter;
import com.lemon95.ymtv.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OpenTabHost.OnTabSelectListener {

    private List<View> viewList;// view数组
    private View view1, view2, view3, view4;
    ViewPager viewpager;
    OpenTabHost mOpenTabHost;
    OpenTabTitleAdapter mOpenTabTitleAdapter;

    private OpenEffectBridge mSavebridge;
    private View mOldFocus;
    private Button lemon_but_search;  //搜索
    private ReflectItemView page1_item1,page1_item2,page1_item3,page1_item4;
    private ReflectItemView page2_item1,page2_item2,page2_item3,page2_item4,page2_item5;

    @Override
    protected int getLayoutId() {
        return R.layout.content_main;
    }

    @Override
    protected void setupViews() {
       // OPENLOG.initTag("hailongqiu", true); // 测试LOG输出.
        // 初始化标题栏.
        initAllTitleBar();
        // 初始化viewpager.
        initAllViewPager();
        // 初始化.
        initViewMove();

        initView();
    }

    private void initView() {
        lemon_but_search = (Button)findViewById(R.id.lemon_but_search);
        page1_item1 = (ReflectItemView)findViewById(R.id.page1_item1);
        page1_item2 = (ReflectItemView)findViewById(R.id.page1_item2);
        page1_item3 = (ReflectItemView)findViewById(R.id.page1_item3);
        page1_item4 = (ReflectItemView)findViewById(R.id.page1_item4);
        page2_item1 = (ReflectItemView)findViewById(R.id.page2_item1);
        page2_item2 = (ReflectItemView)findViewById(R.id.page2_item2);
        page2_item3 = (ReflectItemView)findViewById(R.id.page2_item3);
        page2_item4 = (ReflectItemView)findViewById(R.id.page2_item4);
        page2_item5 = (ReflectItemView)findViewById(R.id.page2_item5);
        lemon_but_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int pos = viewpager.getCurrentItem();
                int id = R.id.title_bar1;
                switch (pos) {
                    case 0:
                        break;
                    case 1:
                        id = R.id.title_bar2;
                        break;
                    case 2:
                        id = R.id.title_bar3;
                        break;
                    case 3:
                        id = R.id.title_bar4;
                        break;
                }
                if (hasFocus) {
                    lemon_but_search.setNextFocusDownId(id);
                }
            }
        });
        List<View> viewList = mOpenTabHost.getAllTitleView();
        if (viewList != null) {
            viewList.get(0).setNextFocusDownId(R.id.page1_item1);
            viewList.get(1).setNextFocusDownId(R.id.page2_item1);
            viewList.get(2).setNextFocusDownId(R.id.page3_item1);
            viewList.get(3).setNextFocusDownId(R.id.page4_item1);
        }
    }

    private void initAllTitleBar() {
        mOpenTabHost = (OpenTabHost) findViewById(R.id.openTabHost);
        mOpenTabTitleAdapter = new OpenTabTitleAdapter();
        mOpenTabHost.setOnTabSelectListener(this);
        mOpenTabHost.setAdapter(mOpenTabTitleAdapter);
    }

    private void initAllViewPager() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        //
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.fragment01, null);
        view2 = inflater.inflate(R.layout.fragment02, null); // gridview demo.
        view3 = inflater.inflate(R.layout.fragment03, null);
        view4 = inflater.inflate(R.layout.fragment04, null);
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewpager.setAdapter(new FragmentPagerAdapter());
        // 全局焦点监听.
        viewpager.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                int pos = viewpager.getCurrentItem();
                final MainUpView mainUpView = (MainUpView) viewList.get(pos).findViewById(R.id.mainUpView1);
                final OpenEffectBridge bridge = (OpenEffectBridge) mainUpView.getEffectBridge();
//                if(newFocus instanceof TextViewWithTTF) {
//                    newFocus = mOpenTabHost.getTitleViewIndexAt(pos);
//                }
                if (!(newFocus instanceof ReflectItemView)) { // 不是 ReflectitemView 的话.
                    OPENLOG.D("onGlobalFocusChanged no ReflectItemView + " + (newFocus instanceof GridView));
                    mainUpView.setUnFocusView(mOldFocus);
                    bridge.setVisibleWidget(true); // 隐藏.
                    mSavebridge = null;
                    // 处理gridview的边框.
                   /* if ((newFocus instanceof GridView) && pos == 1) {
                        View newView = null;
                        bridge.setVisibleWidget(false);
                      //  newView = gridView.getSelectedView();
                        //
                        if (newView != null) {
                            newView.bringToFront();
                            mainUpView.setFocusView(newView, mOldFocus, 1.2f);
                        }
                        mOldFocus = newView;
                        return; // 反之最后的 mOldFocus 出问题.
                    }*/
                } else {
                    LogUtils.i(TAG, "onGlobalFocusChanged yes ReflectItemView");
                    newFocus.bringToFront();
                    mSavebridge = bridge;
                    // 动画结束才设置边框显示，
                    // 是位了防止翻页从另一边跑出来的问题.
                    bridge.setOnAnimatorListener(new OpenEffectBridge.NewAnimatorListener() {
                        @Override
                        public void onAnimationStart(OpenEffectBridge bridge, View view, Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(OpenEffectBridge bridge1, View view, Animator animation) {
                            if (mSavebridge == bridge1)
                                bridge.setVisibleWidget(false);
                        }
                    });
                    float scale = 1.03f;
                    // test scale.
                    if (pos == 1)
                        scale = 1.03f;
                    else if (pos == 2)
                        scale = 1.03f;
                    else if (pos == 3)
                        scale = 1.03f;
                    mainUpView.setFocusView(newFocus, mOldFocus, scale);
                }
                mOldFocus = newFocus;
            }
        });
        viewpager.setOffscreenPageLimit(4);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                LogUtils.i(TAG, "onPageSelected position:" + position);
                //position = viewpager.getCurrentItem();
                switchFocusTab(mOpenTabHost, position);
                // 这里加入是为了防止移动过去后，移动的边框还在的问题.
                // 从标题栏翻页就能看到上次的边框.
                if (position > 0) {
                    MainUpView mainUpView0 = (MainUpView) viewList.get(position - 1).findViewById(R.id.mainUpView1);
                    OpenEffectBridge bridge0 = (OpenEffectBridge) mainUpView0.getEffectBridge();
                    bridge0.setVisibleWidget(true);
                }
                //
                if (position < (viewpager.getChildCount() - 1)) {
                    MainUpView mainUpView1 = (MainUpView) viewList.get(position + 1).findViewById(R.id.mainUpView1);
                    OpenEffectBridge bridge1 = (OpenEffectBridge) mainUpView1.getEffectBridge();
                    bridge1.setVisibleWidget(true);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    @Override
    protected void initialized() {

    }

    @Override
    public void onTabSelect(OpenTabHost openTabHost, View titleWidget, int postion) {
        if (viewpager != null) {
            viewpager.setCurrentItem(postion);
        }
        switchTab(openTabHost, postion);
    }

    /**
     * demo
     * 设置标题栏被选中，<br>
     * 但是没有焦点的状态.
     */
    public void switchFocusTab(OpenTabHost openTabHost, int postion) {
        List<View> viewList = openTabHost.getAllTitleView();
        if (viewList != null && viewList.size() > 0) {
            for (int i = 0; i < viewList.size(); i++) {
                View viewC = viewList.get(i);
                if (i == postion) {
                    viewC.setSelected(true);
                } else {
                    viewC.setSelected(false);
                }
            }
        }
        switchTab(openTabHost, postion);
    }

    /**
     * demo
     * 将标题栏的文字颜色改变. <br>
     * 你可以写自己的东西，我这里只是DEMO.
     */
    public void switchTab(OpenTabHost openTabHost, int postion) {
        List<View> viewList = openTabHost.getAllTitleView();
        for (int i = 0; i < viewList.size(); i++) {
            TextViewWithTTF view = (TextViewWithTTF) openTabHost.getTitleViewIndexAt(i);
            if (view != null) {
                Resources res = view.getResources();
                if (res != null) {
                    if (i == postion) {
                        view.setTextColor(res.getColor(android.R.color.white));
                       // view.setTypeface(null, Typeface.BOLD);
                    } else {
                        view.setTextColor(res.getColor(R.color.lemon_color2));
                        view.setTypeface(null, Typeface.NORMAL);
                    }
                }
            }
        }
    }

    public void initViewMove() {
        for (View view : viewList) {
            MainUpView mainUpView = (MainUpView) view.findViewById(R.id.mainUpView1);
            // 建议使用 noDrawBridge.
            mainUpView.setEffectBridge(new EffectNoDrawBridge()); // 4.3以下版本边框移动.
            mainUpView.setUpRectResource(R.drawable.health_focus_border); // 设置移动边框的图片.
            //mainUpView.setDrawUpRectPadding(new Rect(12,14,14,14)); // 边框图片设置间距.
            mainUpView.setDrawUpRectPadding(new Rect(10,10,8,10)); // 边框图片设置间距.
            EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView.getEffectBridge();
            bridget.setTranDurAnimTime(100);
        }
    }

    class FragmentPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        };

    }

}
