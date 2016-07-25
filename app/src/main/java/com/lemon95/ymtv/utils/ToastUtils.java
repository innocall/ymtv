package com.lemon95.ymtv.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon95.ymtv.R;

/**
 * Created by WXT on 2016/7/25.
 */
public class ToastUtils {

    public static void showBgToast(String msg,Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lemon_toast, null);
        TextView chapterNameTV = (TextView) view.findViewById(R.id.chapterName);
        chapterNameTV.setText(msg);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

}
