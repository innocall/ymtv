package com.lemon95.ymtv.view.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon95.androidtvwidget.bridge.EffectNoDrawBridge;
import com.lemon95.androidtvwidget.bridge.OpenEffectBridge;
import com.lemon95.androidtvwidget.view.ListViewTV;
import com.lemon95.androidtvwidget.view.MainLayout;
import com.lemon95.androidtvwidget.view.MainUpView;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.adapter.ConditionsAdapter;
import com.lemon95.ymtv.adapter.GridViewAdapter;
import com.lemon95.ymtv.bean.QueryConditions;
import com.lemon95.ymtv.bean.VideoSearchList;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.presenter.VideoListPresenter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends BaseActivity {

    private ListViewTV lemon_video_menu_id;
    private MainUpView mainUpView2;
    OpenEffectBridge mOpenEffectBridge;
    private ProgressBar lemon_movie_details_pro1,lemon_movie_details_pro2;
    private LinearLayout lemon_movie_details_main;
    private boolean isKeyDown = false;
    private VideoListPresenter videoListPresenter = new VideoListPresenter(this);
    private ConditionsAdapter conditionsAdapter;
    private View mOldView;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void setupViews() {
        lemon_video_menu_id = (ListViewTV) findViewById(R.id.lemon_video_menu_id);
        gridView = (GridView) findViewById(R.id.gridView);
        lemon_movie_details_pro1 = (ProgressBar) findViewById(R.id.lemon_movie_details_pro1);
        lemon_movie_details_pro2 = (ProgressBar) findViewById(R.id.lemon_movie_details_pro2);
        lemon_movie_details_main = (LinearLayout) findViewById(R.id.lemon_movie_details_main);
        lemon_video_menu_id.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                if (lemon_video_menu_id.getChildCount() > 0) {
                    lemon_video_menu_id.setSelection(0);
                    View newView = lemon_video_menu_id.getChildAt(0);
                    newView.bringToFront();
                    mOldView = lemon_video_menu_id.getChildAt(0);
                }
            }
        });
        initViewMove();
    }

    @Override
    protected void initialized() {
        String videoType = getIntent().getStringExtra("videoType");
        TextView lemon_movie_title = (TextView)findViewById(R.id.lemon_movie_title);
        if (AppConstant.MOVICE.equals(videoType)) {
            lemon_movie_title.setText(getString(R.string.lemon95_movie));
        } else if(AppConstant.SERIALS.equals(videoType)) {
            lemon_movie_title.setText("电视剧");
        } else if(AppConstant.FUNNY.equals(videoType)) {
            lemon_movie_title.setText("每日一乐");
        }
        videoListPresenter.getCombQueryConditions(videoType);
    }

    public void showPro() {
        lemon_movie_details_pro1.setVisibility(View.VISIBLE);
        lemon_movie_details_main.setVisibility(View.GONE);
    }

    public void hidePro() {
        lemon_movie_details_pro1.setVisibility(View.GONE);
        lemon_movie_details_main.setVisibility(View.VISIBLE);
        isKeyDown = false;
    }

    public void initViewMove() {
        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        mOpenEffectBridge = (OpenEffectBridge) mainUpView2.getEffectBridge();
        switchNoDrawBridgeVersion();
    }

    private void switchNoDrawBridgeVersion() {
        EffectNoDrawBridge effectNoDrawBridge = new EffectNoDrawBridge();
        effectNoDrawBridge.setTranDurAnimTime(100);
        mainUpView2.setEffectBridge(effectNoDrawBridge); // 4.3以下版本边框移动.
        mainUpView2.setUpRectResource(R.drawable.health_focus_border); // 设置移动边框的图片.
        mainUpView2.setDrawUpRectPadding(new Rect(10, -6, 9, -41)); // 边框图片设置间距.
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
        return isKeyDown;
    }

    /**
     * 显示listView
     * @param conditionsArrayList
     */
    public void showListView(ArrayList<QueryConditions> conditionsArrayList) {
        conditionsAdapter = new ConditionsAdapter(conditionsArrayList,context);
        lemon_video_menu_id.setAdapter(conditionsAdapter);
        hidePro();
        lemon_video_menu_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    // 子控件置顶，必需使用ListViewTV才行，
                    // 不然焦点会错乱.
                    // 不要忘记这句关键的话哦.
                    view.bringToFront();
                    mOldView = view;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        lemon_video_menu_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "position : " + position, Toast.LENGTH_LONG).show();
            }
        });
        lemon_video_menu_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lemon_video_menu_id.setSelector(R.drawable.lemon_liangguang_03);
                    //((TextView)v).setTextColor(Color.WHITE);
                } else {
                    lemon_video_menu_id.setSelector(R.color.lemon_0E0A0B);
                   // ((TextView)v).setTextColor(getResources().getColor(R.color.lemon_b3aeae));
                }
            }
        });
    }

    public void showPro2() {
        lemon_movie_details_pro2.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
    }

    public void hidePro2() {
        lemon_movie_details_pro2.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示gridView
     * @param videoBriefsList
     */
    public void showGridView(List<VideoSearchList.Data.VideoBriefs> videoBriefsList) {
        gridViewAdapter = new GridViewAdapter(videoBriefsList,context);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.notifyDataSetChanged();
        hidePro2();
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //
        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 这里注意要加判断是否为NULL.
                 * 因为在重新加载数据以后会出问题.
                 */
                if (view != null) {
                    view.bringToFront();
                    mainUpView2.setFocusView(view, mOldView, 1.1f);
                }
                mOldView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_LONG).show();
            }
        });
        gridView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mOpenEffectBridge.setVisibleWidget(false);
                } else {
                    mOpenEffectBridge.setVisibleWidget(true); // 隐藏
                    mainUpView2.setUnFocusView(mOldView);// .
                }
            }
        });
    }

}
