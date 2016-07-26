package com.lemon95.ymtv.view.activity;

import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.lemon95.androidtvwidget.bridge.EffectNoDrawBridge;
import com.lemon95.androidtvwidget.view.MainUpView;
import com.lemon95.ymtv.R;

public class HistoryActivity extends BaseActivity {

    private GridView lemon_gridview;
    private MainUpView mainUpView1;
    private TextView lemon_msg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void setupViews() {
        lemon_gridview = (GridView)findViewById(R.id.lemon_gridview);
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        lemon_msg = (TextView) findViewById(R.id.lemon_msg);
        // 建议使用 NoDraw.
        mainUpView1.setEffectBridge(new EffectNoDrawBridge());
        EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
        bridget.setTranDurAnimTime(100);
        // 设置移动边框的图片.
        mainUpView1.setUpRectResource(R.drawable.health_focus_border);
        // 移动方框缩小的距离.
        mainUpView1.setDrawUpRectPadding(new Rect(10, 10, 8, -28));
        showError("您当前没有收藏记录");
    }

    @Override
    protected void initialized() {

    }

    public void showError(String msg) {
        lemon_msg.setText(msg);
        lemon_msg.setVisibility(View.VISIBLE);
        lemon_gridview.setVisibility(View.GONE);
    }

}
