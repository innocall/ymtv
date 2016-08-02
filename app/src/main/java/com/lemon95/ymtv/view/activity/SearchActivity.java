package com.lemon95.ymtv.view.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lemon95.androidtvwidget.keyboard.SkbContainer;
import com.lemon95.androidtvwidget.keyboard.SoftKey;
import com.lemon95.androidtvwidget.keyboard.SoftKeyBoardListener;
import com.lemon95.androidtvwidget.view.GridViewTV;
import com.lemon95.ymtv.R;

/**
 * Created by WXT on 2016/8/1.
 */
public class SearchActivity extends BaseActivity{

    SkbContainer skbContainer;
    SoftKey mOldSoftKey;
    private GridViewTV lemon_gridview;
    private EditText lemon_search_msg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupViews() {
        lemon_gridview = (GridViewTV) findViewById(R.id.lemon_gridview);
        lemon_search_msg = (EditText) findViewById(R.id.lemon_search_msg);
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
        skbContainer.setSoftKeySelectPadding((int) getResources().getDimension(R.dimen.px16)); // 设置移动边框相差的间距.
        skbContainer.setMoveDuration(20); // 设置移动边框的时间(默认:300)
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框在最前面.
    }

    @Override
    protected void initialized() {
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
            }

            @Override
            public void onBack(SoftKey key) {
                finish();
            }

            @Override
            public void onDelete(SoftKey key) {
                String text = lemon_search_msg.getText().toString();
                lemon_search_msg.setText(text.substring(0, text.length() - 1));
            }

        });
        // DEMO（测试键盘失去焦点和获取焦点)
        skbContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mOldSoftKey != null)
                        skbContainer.setKeySelected(mOldSoftKey);
                    else
                        skbContainer.setDefualtSelectKey(0, 0);
                } else {
                    mOldSoftKey = skbContainer.getSelectKey();
                    skbContainer.setKeySelected(null);
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
        if (skbContainer.onSoftKeyUp(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
