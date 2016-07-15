package com.lemon95.ymtv.view.activity;

import android.animation.Animator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemon95.androidtvwidget.bridge.EffectNoDrawBridge;
import com.lemon95.androidtvwidget.bridge.OpenEffectBridge;
import com.lemon95.androidtvwidget.utils.OPENLOG;
import com.lemon95.androidtvwidget.view.MainUpView;
import com.lemon95.androidtvwidget.view.OpenTabHost;
import com.lemon95.androidtvwidget.view.ReflectItemView;
import com.lemon95.androidtvwidget.view.TextViewWithTTF;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.adapter.OpenTabTitleAdapter;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.bean.VideoType;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.db.DataBaseDao;
import com.lemon95.ymtv.presenter.MainPresenter;
import com.lemon95.ymtv.presenter.SplashPresenter;
import com.lemon95.ymtv.utils.ImageUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OpenTabHost.OnTabSelectListener,View.OnClickListener {

    private List<View> viewList;// view数组
    private View view1, view2, view3, view4;
    ViewPager viewpager;
    OpenTabHost mOpenTabHost;
    OpenTabTitleAdapter mOpenTabTitleAdapter;
    private MainPresenter mainPresenter = new MainPresenter(this);
    private OpenEffectBridge mSavebridge;
    private DataBaseDao dataBaseDao;
    private View mOldFocus;
    private Button lemon_but_search;  //搜索
    private ReflectItemView page1_item1,page1_item2,page1_item3,page1_item4;
    private ImageView lemon_page2_img1,lemon_page2_img2,lemon_page2_img3,lemon_page2_img4,lemon_page2_img5,lemon_page2_img6;
    private ImageView lemon_page3_img1,lemon_page3_img2,lemon_page3_img3;
    private TextView lemon_page3_name1,lemon_page3_name2,lemon_page3_name3;
    private  List<VideoType.Data> videoTypeList; //影视分类
    private List<Video> videoList;  //每日推荐

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
        initRecommends(); //初始化每日推荐数据
        initVideoType(); //初始化影视分类
        initOnClick();
    }

    /**
     * 初始化影视类型
     */
    private void initVideoType() {
        //从数据库获取数据
        dataBaseDao = new DataBaseDao(context);
        videoTypeList = dataBaseDao.getAllVideoTypeList();
        if (videoTypeList != null && videoTypeList.size() == 3) {
            showVideoType(videoTypeList);
        } else {
            if (videoTypeList.size() > 3) {
                dataBaseDao.deleteVideoType();
            }
            mainPresenter.getVideoType(); //重新获取数据，并保存数据库
        }
    }

    /**
     * 初始化每日推荐数据
     */
    private void initRecommends() {
        //从数据库获取数据
        dataBaseDao = new DataBaseDao(context);
        videoList = dataBaseDao.getAllVideoList();
        if (videoList != null && videoList.size() == 6) {
            showRecommends(videoList);
        } else {
            if (videoList.size() > 6) {
                dataBaseDao.deleteVideo();
            }
            mainPresenter.getRecommends(); //重新获取数据，并保存数据库
        }
    }

    private void initView() {
        lemon_but_search = (Button)findViewById(R.id.lemon_but_search);
        page1_item1 = (ReflectItemView)view1.findViewById(R.id.page1_item1);
        page1_item2 = (ReflectItemView)view1.findViewById(R.id.page1_item2);
        page1_item3 = (ReflectItemView)view1.findViewById(R.id.page1_item3);
        page1_item4 = (ReflectItemView)view1.findViewById(R.id.page1_item4);
        lemon_page2_img1 = (ImageView)view2.findViewById(R.id.lemon_page2_img1);
        lemon_page2_img2 = (ImageView)view2.findViewById(R.id.lemon_page2_img2);
        lemon_page2_img3 = (ImageView)view2.findViewById(R.id.lemon_page2_img3);
        lemon_page2_img4 = (ImageView)view2.findViewById(R.id.lemon_page2_img4);
        lemon_page2_img5 = (ImageView)view2.findViewById(R.id.lemon_page2_img5);
        lemon_page2_img6 = (ImageView)view2.findViewById(R.id.lemon_page2_img6);
        lemon_page3_img1 = (ImageView)view3.findViewById(R.id.lemon_page3_img1);
        lemon_page3_img2 = (ImageView)view3.findViewById(R.id.lemon_page3_img2);
        lemon_page3_img3 = (ImageView)view3.findViewById(R.id.lemon_page3_img3);
        lemon_page3_name1 = (TextView)view3.findViewById(R.id.lemon_page3_name1);
        lemon_page3_name2 = (TextView)view3.findViewById(R.id.lemon_page3_name2);
        lemon_page3_name3 = (TextView)view3.findViewById(R.id.lemon_page3_name3);
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
                if (!(newFocus instanceof ReflectItemView)) { // 不是 ReflectitemView 的话.
                    OPENLOG.D("onGlobalFocusChanged no ReflectItemView + " + (newFocus instanceof GridView));
                    mainUpView.setUnFocusView(mOldFocus);
                    bridge.setVisibleWidget(true); // 隐藏.
                    mSavebridge = null;
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

    /**
     * 控制每日推荐图片显示
     * @param list
     */
    public void showRecommends(List<Video> list) {
        videoList = list;
        for (int i=0;i<list.size();i++) {
            Video video = list.get(i);
            final String orderNum = video.getOrderNum();
            if ("1".equals(orderNum)) {
                viewImage(video, orderNum,lemon_page2_img1);
            } else if ("2".equals(orderNum)) {
                viewImage(video, orderNum,lemon_page2_img2);
            } else if ("3".equals(orderNum)) {
                viewImage(video, orderNum,lemon_page2_img3);
            } else if ("4".equals(orderNum)) {
                viewImage(video, orderNum,lemon_page2_img4);
            } else if ("5".equals(orderNum)) {
                viewImage(video, orderNum,lemon_page2_img5);
            } else if ("6".equals(orderNum)) {
                viewImage(video, orderNum,lemon_page2_img6);
            }
        }
    }

    /**
     * 控制影视分类显示
     * @param videoList
     */
    public void showVideoType(List<VideoType.Data> videoList) {
        videoTypeList = videoList;
        for (int i=0;i<videoList.size();i++) {
            VideoType.Data videoType = videoList.get(i);
            if (i == 0) {
                lemon_page3_name1.setText(videoType.getTitle());
                viewVideoTypeImg(videoType, lemon_page3_img1);
            } else if(i == 1) {
                lemon_page3_name2.setText(videoType.getTitle());
                viewVideoTypeImg(videoType, lemon_page3_img2);
            } else if(i == 2) {
                lemon_page3_name3.setText(videoType.getTitle());
                viewVideoTypeImg(videoType,lemon_page3_img3);
            }
        }
    }

    private void viewVideoTypeImg(VideoType.Data videoType, ImageView imagView) {
        final String downImg = videoType.getDownImg();
        if (StringUtils.isBlank(downImg)) {
            loadVideoTypeImg(videoType, downImg, imagView);
        } else {
            String SDCarePath = Environment.getExternalStorageDirectory().toString();
            File file = new File(SDCarePath + downImg);
            LogUtils.i(TAG, "图片保存地址：" + SDCarePath + downImg);
            if (file.exists()) {
                //文件存在，判断图片是否需要更新
                LogUtils.i(TAG,"加载本地显示");
                ImageLoader.getInstance().displayImage("file://" + SDCarePath + downImg, imagView);
            } else {
                //文件不存在从新下载
                loadVideoTypeImg(videoType, "", imagView);
            }
        }
    }

    private void loadVideoTypeImg(final VideoType.Data videoType,final String downImg, ImageView imagView) {
        ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + videoType.getPicturePath(), imagView,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imgUrl, View arg1,Bitmap arg2) {
                        //加载成功
                        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1,imgUrl.lastIndexOf("."));
                        ImageUtils.saveImage(arg2, fileName);
                        if (StringUtils.isNotBlank(downImg)) {
                            File file = new File(downImg);
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                        videoType.setDownImg("/myImage/ymtv/" + fileName + ".png");
                        dataBaseDao.addOrUpdateVideoType(videoType);
                        LogUtils.i(TAG, "加载成功" + imgUrl);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
    }

    private void viewImage(Video video, String orderNum,ImageView imagView) {
        final String downImg = video.getDownImg();
        if (StringUtils.isBlank(downImg)) {
            loadImg(video, orderNum, downImg,imagView);
        } else {
            String SDCarePath = Environment.getExternalStorageDirectory().toString();
            File file = new File(SDCarePath + downImg);
            LogUtils.i(TAG, "图片保存地址：" + SDCarePath + downImg);
            if (file.exists()) {
                //文件存在，判断图片是否需要更新
                LogUtils.i(TAG,"加载本地显示");
                ImageLoader.getInstance().displayImage("file://" + SDCarePath + downImg, imagView);
            } else {
                //文件不存在从新下载
                loadImg(video, orderNum, "",imagView);
            }
        }
    }

    /**
     * 加载网络图片
     * @param video
     * @param orderNum
     * @param downImg
     */
    private void loadImg(final Video video, final String orderNum, final String downImg,ImageView imagView) {
        ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + video.getPicturePath(), imagView,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imgUrl, View arg1,Bitmap arg2) {
                        //加载成功
                        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1,imgUrl.lastIndexOf("."));
                        ImageUtils.saveImage(arg2, fileName);
                        if (StringUtils.isNotBlank(downImg)) {
                            File file = new File(downImg);
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                        video.setDownImg("/myImage/ymtv/" + fileName + ".png");
                        dataBaseDao.addOrUpdateVideo(video);
                        LogUtils.i(TAG, "加载成功" + imgUrl);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
    }

    private void initOnClick() {
        lemon_page2_img1.setOnClickListener(this);
        lemon_page2_img2.setOnClickListener(this);
        lemon_page2_img3.setOnClickListener(this);
        lemon_page2_img4.setOnClickListener(this);
        lemon_page2_img5.setOnClickListener(this);
        lemon_page2_img6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lemon_page2_img1:

                break;
            case R.id.lemon_page2_img2:

                break;
            case R.id.lemon_page2_img3:

                break;
            case R.id.lemon_page2_img4:

                break;
            case R.id.lemon_page2_img5:

                break;
            case R.id.lemon_page2_img6:

                break;
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
