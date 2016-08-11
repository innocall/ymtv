package com.lemon95.ymtv.view.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon95.androidtvwidget.bridge.EffectNoDrawBridge;
import com.lemon95.androidtvwidget.bridge.OpenEffectBridge;
import com.lemon95.androidtvwidget.keyboard.SkbContainer;
import com.lemon95.androidtvwidget.keyboard.SoftKey;
import com.lemon95.androidtvwidget.keyboard.SoftKeyBoardListener;
import com.lemon95.androidtvwidget.view.GridViewTV;
import com.lemon95.androidtvwidget.view.MainUpView;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.adapter.FavoritesAdapter;
import com.lemon95.ymtv.adapter.SearchAdapter;
import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.FirstLettersSearch;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.presenter.SearchPresenter;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXT on 2016/8/1.
 */
public class SearchActivity extends BaseActivity{

    SkbContainer skbContainer;
    SoftKey mOldSoftKey;
    private GridViewTV lemon_gridview;
    private EditText lemon_search_msg;
    private SearchPresenter searchPresenter = new SearchPresenter(this);
    private List<FirstLettersSearch.Data> firstData = new ArrayList<>();
    private List<FirstLettersSearch.Data> dataList;
    private int page = 1;
    private SearchAdapter searchAdapter;
    private boolean isPage = true; //是否在翻页
    private MainUpView mainUpView1;
    OpenEffectBridge mOpenEffectBridge;
    private View mOldView,mNewView;
    private TextView lemon95_movie_msg_id;
    private int i = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupViews() {
        lemon_gridview = (GridViewTV) findViewById(R.id.lemon_gridview);
        lemon_gridview.setIsSearch(true);
        lemon_search_msg = (EditText) findViewById(R.id.lemon_search_msg);
        skbContainer = (SkbContainer) findViewById(R.id.skbContainer);
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        lemon95_movie_msg_id = (TextView) findViewById(R.id.lemon95_movie_msg_id);

        // 建议使用 NoDraw.
        mainUpView1.setEffectBridge(new EffectNoDrawBridge());
        mOpenEffectBridge = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
        mOpenEffectBridge.setTranDurAnimTime(1);
        // 移动方框缩小的距离.
        mainUpView1.setDrawUpRectPadding(new Rect(10, -10, -2, -43));
        lemon_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mainUpView1.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.

        skbContainer.setSkbLayout(R.xml.sbd_qwerty);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);
        // 设置属性(默认是不移动的选中边框)
        setSkbContainerMove();
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框最前面.
        skbContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mOldSoftKey != null)
                        skbContainer.setKeySelected(mOldSoftKey);
                    else
                        skbContainer.setDefualtSelectKey(0, 0);
                } else {
                    lemon_gridview.setFocusable(true);
                    mOldSoftKey = skbContainer.getSelectKey();
                    skbContainer.setKeySelected(null);
                }
            }
        });
    }


    private void setSkbContainerMove() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(true); // 设置是否移动按键边框.
        skbContainer.setSoftKeySelectPadding((int) getResources().getDimension(R.dimen.px16)); // 设置移动边框相差的间距.
        skbContainer.setMoveDuration(20); // 设置移动边框的时间(默认:300)
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框在最前面.
    }

    @Override
    protected void initialized() {
        searchAdapter = new SearchAdapter(firstData,context);
        lemon_gridview.setAdapter(searchAdapter);
        //监听键盘事件.
        skbContainer.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
                int keyCode = softKey.getKeyCode();
                String keyLabel = softKey.getKeyLabel();
                keyCode = softKey.getKeyCode();
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    String text = lemon_search_msg.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        Toast.makeText(getApplicationContext(), "文本已空", Toast.LENGTH_LONG).show();
                    } else {
                        lemon_search_msg.setText(text.substring(0, text.length() - 1));
                    }
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                } else if (keyCode == 260) {
                    lemon_search_msg.setText("");
                } else {
                    lemon_search_msg.setText(lemon_search_msg.getText() + softKey.getKeyLabel());
                }
                String ss = lemon_search_msg.getText().toString().trim();
              //  lemon_gridview.smoothScrollToPosition(0);  //定位到顶部
                if (StringUtils.isNotBlank(ss)) {
                    firstData.clear();
                    searchAdapter.notifyDataSetChanged();
                   // i = 0;
                    searchPresenter.searchData(ss, page);
                }
            }

            @Override
            public void onBack(SoftKey key) {
                finish();
            }

            @Override
            public void onDelete(SoftKey key) {
                String text = lemon_search_msg.getText().toString();
                lemon_search_msg.setText(text.substring(0, text.length() - 1));
                String ss = lemon_search_msg.getText().toString().trim();
                if (StringUtils.isNotBlank(ss)) {
                    firstData.clear();
                    searchAdapter.notifyDataSetChanged();
                   // i = 0;
                   // lemon_gridview.smoothScrollToPosition(0);  //定位到顶部
                    searchPresenter.searchData(ss, page);
                }
            }

        });
        lemon_gridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 这里注意要加判断是否为NULL.
                 * 因为在重新加载数据以后会出问题.
                 */
                LogUtils.i(TAG, "焦点改变:" + position);
                /*if(19 <= Build.VERSION.SDK_INT){
                    i = 1;
                }*/
                if (view != null && i != 0) {
                    mainUpView1.setUpRectResource(R.drawable.health_focus_border);
                    view.bringToFront();
                    mainUpView1.setFocusView(view, mOldView, 1.1f);
                }
                i = 1;
                mOldView = view;
                int size = firstData.size();
                if (size - 15 < position && dataList != null && dataList.size() == Integer.parseInt(AppConstant.PAGESIZE)) {
                    if (isPage) {
                        //翻页
                        LogUtils.i(TAG, "翻页");
                        page = page + 1;
                        String ss = lemon_search_msg.getText().toString().trim();
                        if (StringUtils.isNotBlank(ss)) {
                            searchPresenter.searchData(ss, page);
                        }
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
                FirstLettersSearch.Data video = firstData.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("videoId", video.getVideoId());
                bundle.putString("videoType", video.getVideoTypeId());
                startActivity(MovieDetailsActivity.class, bundle);
            }
        });
        /*lemon_gridview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                if (lemon_gridview.getChildCount() > 0) {
                    // int v1 = lemon_gridview.getSelectedItemPosition();
                    lemon_gridview.setSelection(0);
                    View newView = lemon_gridview.getChildAt(0);
                    newView.bringToFront();
                    mainUpView1.setFocusView(newView, 1.1f);
                    mOldView = lemon_gridview.getChildAt(0);
                }
            }
        });*/
        lemon_gridview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (firstData != null) {
                        if (mOldView == null) {
                            mOldView =  lemon_gridview.getChildAt(0);
                            lemon_gridview.setFocusable(true);
                            lemon_gridview.setFocusableInTouchMode(true);
                            lemon_gridview.setSelection(0);
                        }
                        mainUpView1.setUpRectResource(R.drawable.health_focus_border);
                        mOpenEffectBridge.setVisibleWidget(false); // 隐藏
                       // View view = lemon_gridview.getSelectedView();
                        mOldView.bringToFront();
                        mainUpView1.setFocusView(mOldView, 1.1f);
                       // mOldView = view;
                    } else {
                        mainUpView1.setUpRectResource(R.drawable.test_rectangle);
                        mOpenEffectBridge.setVisibleWidget(true); // 隐藏
                        //第一次获取焦点
//                        mainUpView1.setUpRectResource(R.drawable.health_focus_border);
//                        mOpenEffectBridge.setVisibleWidget(false); // 隐藏
//                        View view = lemon_gridview.getChildAt(0);
//                        mainUpView1.setFocusView(view,1.1f);
                    }
                    LogUtils.i(TAG, "gridView 获取焦点");
                } else {
                    //int v1 = lemon_gridview.getSelectedItemPosition();
                    //View newView = lemon_gridview.getChildAt(v1);
                    mainUpView1.setUpRectResource(R.drawable.test_rectangle);
                    mOpenEffectBridge.setVisibleWidget(true); // 隐藏
                    mainUpView1.setUnFocusView(mOldView);
                   // mOldView = newView;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyDown(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyUp(keyCode, event)){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            } else {
                return true;
            }
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //初始化收藏数据
    public void showFavoriteData(List<FirstLettersSearch.Data> listData) {
        isPage = true;
        this.dataList = listData;
        if (listData != null) {
            firstData.addAll(listData);
            searchAdapter.notifyDataSetChanged();
            hidePro();
        }
    }

    private void hidePro() {
        lemon95_movie_msg_id.setVisibility(View.GONE);
        lemon_gridview.setVisibility(View.VISIBLE);
    }

    public void showError(String str) {
        lemon95_movie_msg_id.setText(str);
        lemon95_movie_msg_id.setVisibility(View.VISIBLE);
        lemon_gridview.setVisibility(View.GONE);
    }
}
