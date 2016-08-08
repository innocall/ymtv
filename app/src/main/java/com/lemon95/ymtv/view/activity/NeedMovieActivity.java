package com.lemon95.ymtv.view.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lemon95.androidtvwidget.bridge.EffectNoDrawBridge;
import com.lemon95.androidtvwidget.bridge.OpenEffectBridge;
import com.lemon95.androidtvwidget.view.MainUpView;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.adapter.HistoryAdapter;
import com.lemon95.ymtv.adapter.NeedMovieAdapter;
import com.lemon95.ymtv.bean.PersonalMovies;
import com.lemon95.ymtv.bean.WatchHistories;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.myview.ConfirmDialog;
import com.lemon95.ymtv.presenter.HistoryPresenter;
import com.lemon95.ymtv.presenter.NeedMoviePresenter;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 求片记录
 */
public class NeedMovieActivity extends BaseActivity {

    private GridView lemon_gridview;
    private MainUpView mainUpView1;
    private TextView lemon_msg;
    private NeedMoviePresenter favoritesPresenter = new NeedMoviePresenter(this);
    private ProgressBar lemon_movie_details_pro;
    private NeedMovieAdapter favoritesAdapter;
    private View mOldView;
    public List<PersonalMovies.Data> videoList = new ArrayList<>();
    private boolean isDelete = true;
    OpenEffectBridge mOpenEffectBridge;
    public int page = 1;
    private boolean isPage = true; //是否在翻页
    public String mac;
    public String userId;
    List<PersonalMovies.Data> dataList;
    private boolean isStart = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_need_movie;
    }

    @Override
    protected void setupViews() {
        lemon_gridview = (GridView)findViewById(R.id.lemon_gridview);
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        lemon_msg = (TextView) findViewById(R.id.lemon_msg);
        lemon_movie_details_pro = (ProgressBar) findViewById(R.id.lemon_movie_details_pro);
        // 建议使用 NoDraw.
        mainUpView1.setEffectBridge(new EffectNoDrawBridge());
        mOpenEffectBridge = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
        mOpenEffectBridge.setTranDurAnimTime(20);
        // 移动方框缩小的距离.
        mainUpView1.setDrawUpRectPadding(new Rect(10, -10, 4, -43));
        lemon_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        favoritesAdapter = new NeedMovieAdapter(videoList,context);
        lemon_gridview.setAdapter(favoritesAdapter);
        mainUpView1.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
        LogUtils.i(TAG,AppSystemUtils.getSDKVersion() + "");
        lemon_gridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 这里注意要加判断是否为NULL.
                 * 因为在重新加载数据以后会出问题.
                 */
                LogUtils.i(TAG, "焦点改变");
                if(19 < Build.VERSION.SDK_INT){
                    isStart = true;
                }
                if (view != null && isStart) {
                    view.bringToFront();
                    mainUpView1.setFocusView(view, mOldView, 1.1f);
                }
                isStart = true;
                mOldView = view;
                int size = videoList.size();
                if (size - 15 < position && dataList != null && dataList.size() == Integer.parseInt(AppConstant.PAGESIZE)) {
                    if (isPage) {
                        //翻页
                        LogUtils.i(TAG, "翻页");
                        page = page + 1;
                        favoritesPresenter.getFavorites(page + "");
                        isPage = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        lemon_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonalMovies.Data video = videoList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("videoId", video.getId());
                bundle.putBoolean("isPersonal",true);
                bundle.putString("videoType", video.getVideoTypeId());
                startActivity(MovieDetailsActivity.class, bundle);
            }
        });
        lemon_gridview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                if (lemon_gridview.getChildCount() > 0) {
                    // int v1 = lemon_gridview.getSelectedItemPosition();
                    if (isDelete) {
                        lemon_gridview.setSelection(0);
                        View newView = lemon_gridview.getChildAt(0);
                        newView.bringToFront();
                        mainUpView1.setFocusView(newView, 1.1f);
                        mOldView = lemon_gridview.getChildAt(0);
                        isDelete = false;
                    }
                }
            }
        });
        lemon_gridview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (lemon_gridview.getChildCount() > 0) {
                        // int v1 = lemon_gridview.getSelectedItemPosition();
                        // 设置移动边框的图片.
                        LogUtils.i(TAG, "焦点改变1");
                        mainUpView1.setUpRectResource(R.drawable.health_focus_border);
                        lemon_gridview.setSelection(0);
                        View newView = lemon_gridview.getChildAt(0);
                        newView.bringToFront();
                        mainUpView1.setFocusView(newView, 1.1f);
                        mOldView = lemon_gridview.getChildAt(0);
                    }
                    LogUtils.i(TAG,"gridView 获取焦点");
                }
            }
        });
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected void initialized() {
        //获取收藏数据
        showPro();
        mac = AppSystemUtils.getDeviceId();
        userId = PreferenceUtils.getString(context, AppConstant.USERID, "");
        favoritesPresenter.getFavorites(page + "");
    }

    public void showPro() {
        lemon_movie_details_pro.setVisibility(View.VISIBLE);
        lemon_msg.setVisibility(View.GONE);
        lemon_gridview.setVisibility(View.GONE);
    }

    public void hidePro() {
        lemon_movie_details_pro.setVisibility(View.GONE);
        lemon_msg.setVisibility(View.VISIBLE);
        lemon_gridview.setVisibility(View.VISIBLE);
    }

    public void showError(String msg) {
        mainUpView1.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
        lemon_msg.setText(msg);
        lemon_msg.setVisibility(View.VISIBLE);
        lemon_gridview.setVisibility(View.GONE);
        lemon_movie_details_pro.setVisibility(View.GONE);
    }

    //初始化观看记录数据
    public void showFavoriteData(List<PersonalMovies.Data> dataList) {
        isPage = true;
        this.dataList = dataList;
        if (dataList != null) {
            videoList.addAll(dataList);
            favoritesAdapter.notifyDataSetChanged();
            hidePro();
        }
    }
}
