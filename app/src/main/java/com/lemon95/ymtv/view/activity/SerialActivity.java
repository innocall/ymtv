package com.lemon95.ymtv.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemon95.androidtvwidget.view.GridViewTV;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.adapter.SerialAdapter;
import com.lemon95.ymtv.bean.SerialDitions;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class SerialActivity extends BaseActivity {

    private DisplayImageOptions options;
    private GridViewTV lemon_gridview;
    private ArrayList<SerialDitions.Data.SerialEpisodes> serialEpisodes;
    private SerialAdapter serialAdapter;
    private View oldView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_serial;
    }

    @Override
    protected void setupViews() {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.lemon_details_small_def)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.lemon_details_small_def)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.lemon_details_small_def)       // 设置图片加载或解码过程中发生错误显示的图片
                .build();
        serialEpisodes = getIntent().getParcelableArrayListExtra("SerialEpisodes");
        lemon_gridview = (GridViewTV) findViewById(R.id.lemon_gridview);
        String picUrl = getIntent().getStringExtra("PicturePath");
        ImageView movie_details_img_id = (ImageView)findViewById(R.id.movie_details_img_id);
        ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + ImageUtils.getBigImg(picUrl), movie_details_img_id,options);
    }

    @Override
    protected void initialized() {
        serialAdapter = new SerialAdapter(serialEpisodes,context);
        lemon_gridview.setAdapter(serialAdapter);
        lemon_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SerialDitions.Data.SerialEpisodes ser = serialEpisodes.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("videoId", ser.getId());  //剧集ID
                bundle.putString("videoName", getIntent().getStringExtra("videoName") + "(第" + ser.getSerialIndex() + "集)");
                bundle.putString("videoType", AppConstant.SERIALS);
                bundle.putString("SerialEpisodeId", ser.getSerialId());
                startActivity(PlayActivity.class, bundle);
            }
        });
        lemon_gridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView button = (TextView) view.findViewById(R.id.lemon_item_but);
                button.setTextColor(Color.parseColor("#57fffa"));
                button.setBackgroundResource(R.drawable.lemon_servial_pree);
                if (oldView != null) {
                    TextView button2 = (TextView) oldView.findViewById(R.id.lemon_item_but);
                    button2.setTextColor(Color.parseColor("#b3aeae"));
                    button2.setBackgroundResource(R.drawable.lemon_servial_none);
                }
                oldView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        lemon_gridview.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                //初始化选择
                oldView = lemon_gridview.getChildAt(0 - lemon_gridview.getFirstVisiblePosition());
                if (oldView != null) {
                    TextView button = (TextView) oldView.findViewById(R.id.lemon_item_but);
                    button.setTextColor(Color.parseColor("#57fffa"));
                    button.setBackgroundResource(R.drawable.lemon_servial_pree);
                }
            }
        });


    }


}
