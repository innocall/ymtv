package com.lemon95.ymtv.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemon95.androidtvwidget.view.TextViewWithTTF;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.bean.QueryConditions;

import java.util.ArrayList;

/**
 * Created by WXT on 2016/7/19.
 */
public class ConditionsAdapter extends BaseAdapter {

    private ArrayList<QueryConditions> conditionsArrayList;
    private Context context;

    public ConditionsAdapter(ArrayList<QueryConditions> conditionsArrayList,Context context) {
        this.conditionsArrayList = conditionsArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return conditionsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return conditionsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(context, R.layout.lemon_item_video, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        QueryConditions queryConditions = conditionsArrayList.get(position);
        holder.lemon_video_tv.setText(queryConditions.getName());
        if (position == 0) {
            holder.lemon_video_tv.setTextColor(Color.WHITE);
        }
        return view;
    }

    private static class ViewHolder {
        TextView lemon_video_tv;

        public ViewHolder(View view) {
            lemon_video_tv = (TextView) view.findViewById(R.id.lemon_video_tv);
        }
    }


}
