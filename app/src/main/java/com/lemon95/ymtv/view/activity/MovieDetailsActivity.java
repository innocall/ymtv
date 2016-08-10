package com.lemon95.ymtv.view.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lemon95.androidtvwidget.bridge.EffectNoDrawBridge;
import com.lemon95.androidtvwidget.bridge.OpenEffectBridge;
import com.lemon95.androidtvwidget.view.MainLayout;
import com.lemon95.androidtvwidget.view.MainUpView;
import com.lemon95.androidtvwidget.view.ReflectItemView;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.bean.Favorite;
import com.lemon95.ymtv.bean.GenresMovie;
import com.lemon95.ymtv.bean.SerialDitions;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.presenter.MovieDetailsPresenter;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.ImageUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by WXT on 2016/7/18.
 */
public class MovieDetailsActivity extends BaseActivity implements View.OnClickListener{

    private ReflectItemView details_play,details_serial,details_sc;
    private MainUpView mainUpView2;
    View mOldFocus; // 4.3以下版本需要自己保存.
    OpenEffectBridge mOpenEffectBridge;
    private ProgressBar lemon_movie_details_pro;
    private LinearLayout lemon_movie_details_main;
    private com.lemon95.ymtv.bean.Movie.Data data; //影片数据
    private List<GenresMovie.Data> dataList; //相关影视
    private MovieDetailsPresenter movieDetailsActivity = new MovieDetailsPresenter(this);
    private boolean isKeyDown = false;
    private SerialDitions.Data serialData; //电视剧数据数据
    String videoType = "1";
    private String userId;
    private DisplayImageOptions options;
    private String videoId;
    private Boolean isPersonal = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moviedetails;
    }

    @Override
    protected void setupViews() {
        initViewMove();
        initView();
        initOnclick();
    }

    private void initView() {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.lemon_details_small_def)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.lemon_details_small_def)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.lemon_details_small_def)       // 设置图片加载或解码过程中发生错误显示的图片
                .build();
        lemon_movie_details_pro = (ProgressBar)findViewById(R.id.lemon_movie_details_pro);
        lemon_movie_details_main = (LinearLayout)findViewById(R.id.lemon_movie_details_main);
        details_serial = (ReflectItemView) findViewById(R.id.details_serial);
        details_sc = (ReflectItemView) findViewById(R.id.details_sc);
    }

    @Override
    protected void initialized() {
        isPersonal = getIntent().getBooleanExtra("isPersonal",false);
        videoId = getIntent().getStringExtra("videoId");
        userId = PreferenceUtils.getString(context, AppConstant.USERID, ""); //获取用户ID
        videoType = getIntent().getStringExtra("videoType");
        TextView textView = (TextView)findViewById(R.id.lemon95_movie_title_id);
        LogUtils.i(TAG, videoId + ";" + userId);
        if (AppConstant.MOVICE.equals(videoType)) {
            textView.setText(getString(R.string.lemon95_movie));
            movieDetailsActivity.initPageData(videoId, userId,isPersonal);
            details_serial.setVisibility(View.GONE);
        } else if(AppConstant.SERIALS.equals(videoType)) {
            textView.setText("电视剧");
            details_serial.setVisibility(View.VISIBLE);
            movieDetailsActivity.initSerialData(videoId);
        }
    }

    public void initViewMove() {
        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        mOpenEffectBridge = (OpenEffectBridge) mainUpView2.getEffectBridge();
        switchNoDrawBridgeVersion();
        /*if (Utils.getSDKVersion() == 17) { // 测试 android 4.2版本.
        } else { // 其它版本（android 4.3以上）.
            mainUpView1.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
            mainUpView1.setShadowResource(R.drawable.item_shadow); // 设置移动边框的阴影.
            mainUpView2.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
            mainUpView2.setShadowResource(R.drawable.item_shadow); // 设置移动边框的阴影.
        }*/
        MainLayout main_lay11 = (MainLayout) findViewById(R.id.main_lay);
        main_lay11.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
                if (newFocus != null)
                    newFocus.bringToFront(); // 防止放大的view被压在下面. (建议使用MainLayout)
                float scale = 1.1f;
                mainUpView2.setFocusView(newFocus, mOldFocus, scale);
                mOldFocus = newFocus; // 4.3以下需要自己保存.
                // 测试是否让边框绘制在下面，还是上面. (建议不要使用此函数)
                if (newFocus != null) {
                    testTopDemo(newFocus, scale);
                }
            }
        });
        //初始化焦点
        details_play = (ReflectItemView)findViewById(R.id.details_play);
        mainUpView2.setFocusView(details_play, mOldFocus, 1.1f);
        mOldFocus = details_play;
        details_play.setFocusableInTouchMode(true);
        details_play.requestFocus();
        details_play.setFocusable(true);
        mOpenEffectBridge.setVisibleWidget(false);
        mainUpView2.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
    }

    public void testTopDemo(View newView, float scale) {
        if (newView.getId() == R.id.details_play || newView.getId() == R.id.details_sc || newView.getId() == R.id.details_serial) { // 小人在外面的测试.
            LogUtils.e(TAG,"隐藏");
            mOpenEffectBridge.setVisibleWidget(false);
            mainUpView2.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
        } else {
            mOpenEffectBridge.setVisibleWidget(true);
            mainUpView2.setUpRectResource(R.drawable.health_focus_border); // 设置移动边框的图片.
            if (newView instanceof  TextView) {
                ((TextView)newView).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                ((TextView)newView).setSingleLine(true);
                ((TextView)newView).setMarqueeRepeatLimit(6);
            }
            LogUtils.e(TAG, "显示");
        }
    }

    private void switchNoDrawBridgeVersion() {
        EffectNoDrawBridge effectNoDrawBridge = new EffectNoDrawBridge();
        effectNoDrawBridge.setTranDurAnimTime(10);
        mainUpView2.setEffectBridge(effectNoDrawBridge); // 4.3以下版本边框移动.
        mainUpView2.setUpRectResource(R.drawable.health_focus_border); // 设置移动边框的图片.
        mainUpView2.setDrawUpRectPadding(new Rect(10, 10, 8, -28)); // 边框图片设置间距.
    }

    /**
     * 显示页面数据
     * @param data
     */
    public void initViewMoveDate(com.lemon95.ymtv.bean.Movie.Data data) {
        this.data = data;
        ((TextView)findViewById(R.id.movie_details_name)).setText("《" + data.getMovieName() + "》");
        ((TextView)findViewById(R.id.movie_details_type)).setText("类型：" + data.getVideoGenres());
        String f = data.getScore();
        if (StringUtils.isNotBlank(f)) {
            f = f.substring(0,3);
        }
        ((TextView)findViewById(R.id.movie_details_grade)).setText("评分：" + f);
        ((TextView)findViewById(R.id.movie_details_direct)).setText("导演：" + data.getDirector());
        ((TextView)findViewById(R.id.movie_details_act)).setText("主演：" + data.getStarring());
        ((TextView)findViewById(R.id.movie_details_descri)).setText(data.getDescription());
        ImageView imageView = (ImageView)findViewById(R.id.movie_details_img_id);
        LogUtils.e(TAG, ImageUtils.getBigImg(data.getPicturePath()));
        ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + ImageUtils.getBigImg(data.getPicturePath()), imageView, options);
    }

    public void showPro() {
        lemon_movie_details_pro.setVisibility(View.VISIBLE);
        lemon_movie_details_main.setVisibility(View.GONE);
    }

    public void hidePro() {
        lemon_movie_details_pro.setVisibility(View.GONE);
        lemon_movie_details_main.setVisibility(View.VISIBLE);
        isKeyDown = false;
    }

    /**
     * 显示相关影视
     * @param dataList
     */
    public void initViewByGenresMoveDate(List<GenresMovie.Data> dataList) {
        this.dataList = dataList;
        com.lemon95.ymtv.bean.GenresMovie.Data d1 = dataList.get(0);
        if (d1 != null) {
            ReflectItemView itemView1 = (ReflectItemView)findViewById(R.id.details_item1);
            itemView1.setVisibility(View.VISIBLE);
            itemView1.setOnClickListener(this);
            ImageView details_img1 = (ImageView)findViewById(R.id.details_img1);
            ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + d1.getPicturePath(), details_img1,options);
            ((TextView)findViewById(R.id.details_name1)).setText(d1.getVideoName());
            com.lemon95.ymtv.bean.GenresMovie.Data d2 = dataList.get(1);
            if (d2 != null) {
                ReflectItemView itemView2 = (ReflectItemView)findViewById(R.id.details_item2);
                itemView2.setVisibility(View.VISIBLE);
                itemView2.setOnClickListener(this);
                ImageView details_img2 = (ImageView)findViewById(R.id.details_img2);
                ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + d2.getPicturePath(), details_img2,options);
                ((TextView)findViewById(R.id.details_name2)).setText(d2.getVideoName());
                com.lemon95.ymtv.bean.GenresMovie.Data d3 = dataList.get(2);
                if (d3 != null) {
                    ReflectItemView itemView3 = (ReflectItemView)findViewById(R.id.details_item3);
                    itemView3.setVisibility(View.VISIBLE);
                    itemView3.setOnClickListener(this);
                    ImageView details_img3 = (ImageView)findViewById(R.id.details_img3);
                    ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + d3.getPicturePath(), details_img3,options);
                    ((TextView)findViewById(R.id.details_name3)).setText(d3.getVideoName());
                    com.lemon95.ymtv.bean.GenresMovie.Data d4 = dataList.get(3);
                    if (d4 != null) {
                        ReflectItemView itemView4 = (ReflectItemView)findViewById(R.id.details_item4);
                        itemView4.setVisibility(View.VISIBLE);
                        itemView4.setOnClickListener(this);
                        ImageView details_img4 = (ImageView)findViewById(R.id.details_img4);
                        ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + d4.getPicturePath(), details_img4,options);
                        ((TextView)findViewById(R.id.details_name4)).setText(d4.getVideoName());
                        com.lemon95.ymtv.bean.GenresMovie.Data d5 = dataList.get(4);
                        if (d5 != null) {
                            ReflectItemView itemView5 = (ReflectItemView)findViewById(R.id.details_item5);
                            itemView5.setVisibility(View.VISIBLE);
                            itemView5.setOnClickListener(this);
                            ImageView details_img5 = (ImageView)findViewById(R.id.details_img5);
                            ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + d5.getPicturePath(), details_img5,options);
                            ((TextView)findViewById(R.id.details_name5)).setText(d5.getVideoName());
                            com.lemon95.ymtv.bean.GenresMovie.Data d6 = dataList.get(5);
                            if (d6 != null) {
                                ReflectItemView itemView6 = (ReflectItemView)findViewById(R.id.details_item6);
                                itemView6.setVisibility(View.VISIBLE);
                                itemView6.setOnClickListener(this);
                                ImageView details_img6 = (ImageView)findViewById(R.id.details_img6);
                                ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + d6.getPicturePath(), details_img6,options);
                                ((TextView)findViewById(R.id.details_name6)).setText(d6.getVideoName());
                                com.lemon95.ymtv.bean.GenresMovie.Data d7 = dataList.get(6);
                                if (d7 != null) {
                                    ReflectItemView itemView7 = (ReflectItemView)findViewById(R.id.details_item7);
                                    itemView7.setVisibility(View.VISIBLE);
                                    itemView7.setOnClickListener(this);
                                    ImageView details_img7 = (ImageView)findViewById(R.id.details_img7);
                                    ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + d7.getPicturePath(), details_img7,options);
                                    ((TextView)findViewById(R.id.details_name7)).setText(d7.getVideoName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void initOnclick() {
        details_play.setOnClickListener(this);
        details_serial.setOnClickListener(this);
        details_sc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (lemon_movie_details_pro.getVisibility() == View.GONE) {
            switch (v.getId()) {
                case R.id.details_play:
                    Bundle bundle = new Bundle();
                    if (AppConstant.MOVICE.equals(videoType)) {
                        bundle.putString("videoId", data.getId());
                        bundle.putString("SerialEpisodeId", "");
                        bundle.putBoolean("isPersonal", isPersonal);
                        bundle.putString("videoName", data.getMovieName());
                    } else if(AppConstant.SERIALS.equals(videoType)) {
                        bundle.putString("SerialEpisodeId", serialData.getSerialEpisodes().get(0).getId());  //剧集ID
                        bundle.putString("videoId", serialData.getSerialEpisodes().get(0).getSerialId());
                        bundle.putString("videoName", serialData.getSerialName());
                        bundle.putInt("index",0 + 1);
                        bundle.putParcelableArrayList("SerialEpisodes",serialData.getSerialEpisodes());
                    }
                    bundle.putString("videoType",videoType);
                    startActivity(PlayActivity.class,bundle);
                    break;
                case R.id.details_serial:
                    //选择剧集
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelableArrayList("SerialEpisodes",serialData.getSerialEpisodes());
                    bundle1.putString("PicturePath", serialData.getPicturePath());
                    bundle1.putString("videoName", serialData.getSerialName());
                    startActivity(SerialActivity.class,bundle1);
                    break;
                case R.id.details_sc:
                    //添加收藏
                    isKeyDown = true;
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    String deviceId = AppSystemUtils.getDeviceId();
                    Favorite favorite = new Favorite();
                    favorite.setMAC(deviceId);
                    favorite.setUserId(userId);
                    favorite.setVideoId(videoId);
                    favorite.setVideoTypeId(videoType);
                    movieDetailsActivity.addFavorite(favorite);
                    break;
                case R.id.details_item1:
                    isKeyDown = true;
                    videoId = dataList.get(0).getVideoId();
                    face(dataList.get(0),videoId);
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    break;
                case R.id.details_item2:
                    isKeyDown = true;
                    videoId = dataList.get(1).getVideoId();
                    face(dataList.get(1),videoId);
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    break;
                case R.id.details_item3:
                    isKeyDown = true;
                    videoId = dataList.get(2).getVideoId();
                    face(dataList.get(2), videoId);
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    break;
                case R.id.details_item4:
                    isKeyDown = true;
                    videoId = dataList.get(3).getVideoId();
                    face(dataList.get(3), videoId);
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    break;
                case R.id.details_item5:
                    isKeyDown = true;
                    videoId = dataList.get(4).getVideoId();
                    face(dataList.get(4), videoId);
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    break;
                case R.id.details_item6:
                    isKeyDown = true;
                    videoId = dataList.get(5).getVideoId();
                    face(dataList.get(5), videoId);
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    break;
                case R.id.details_item7:
                    isKeyDown = true;
                    videoId = dataList.get(6).getVideoId();
                    face(dataList.get(6), videoId);
                    lemon_movie_details_pro.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public void face(GenresMovie.Data data ,String videoId) {
        if (AppConstant.SERIALS.equals(data.getVideoTypeId())) {
            movieDetailsActivity.initSerialData(videoId);
        } else {
            movieDetailsActivity.initPageData(videoId, userId, false);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*if(keyCode==KeyEvent.KEYCODE_DPAD_UP){
        } else if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT) {
        } else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT) {
        } else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
        } else if (keyCode==KeyEvent.KEYCODE_DPAD_CENTER||keyCode==KeyEvent.KEYCODE_ENTER) {
        }*/
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
        return isKeyDown;
    }

    /**
     * 初始化电视剧数据
     * @param data
     */
    public void initViewSerialDate(SerialDitions.Data data) {
        this.serialData = data;
        ((TextView)findViewById(R.id.movie_details_name)).setText("《" + data.getSerialName() + "》");
        ((TextView)findViewById(R.id.movie_details_type)).setText("类型：" + data.getVideoGenres());
        String f = data.getScore();
        if (StringUtils.isNotBlank(f)) {
            f = f.substring(0,3);
        }
        ((TextView)findViewById(R.id.movie_details_grade)).setText("评分：" + f);
        ((TextView)findViewById(R.id.movie_details_direct)).setText("导演：" + data.getDirector());
        ((TextView)findViewById(R.id.movie_details_act)).setText("主演：" + data.getStarring());
        ((TextView)findViewById(R.id.movie_details_descri)).setText(data.getDescription());
        ImageView imageView = (ImageView)findViewById(R.id.movie_details_img_id);
        LogUtils.e(TAG, ImageUtils.getBigImg(data.getPicturePath()));
        ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + ImageUtils.getBigImg(data.getPicturePath()), imageView,options);
    }
}
