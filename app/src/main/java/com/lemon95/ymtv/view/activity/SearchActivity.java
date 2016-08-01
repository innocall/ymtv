package com.lemon95.ymtv.view.activity;

import com.lemon95.androidtvwidget.keyboard.SkbContainer;
import com.lemon95.androidtvwidget.keyboard.SoftKey;
import com.lemon95.ymtv.R;

/**
 * Created by WXT on 2016/8/1.
 */
public class SearchActivity extends BaseActivity{

    SkbContainer skbContainer;
    SoftKey mOldSoftKey;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupViews() {
        skbContainer = (SkbContainer) findViewById(R.id.skbContainer);
        skbContainer.setSkbLayout(R.xml.sbd_qwerty);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);
        // 设置属性(默认是不移动的选中边框)
        setSkbContainerMove();
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框最前面.
    }

    private void setSkbContainerMove() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(true); // 设置是否移动按键边框.
        skbContainer.setSoftKeySelectPadding((int) getResources().getDimension(R.dimen.px25)); // 设置移动边框相差的间距.
        skbContainer.setMoveDuration(200); // 设置移动边框的时间(默认:300)
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框在最前面.
    }

    @Override
    protected void initialized() {

    }
}
