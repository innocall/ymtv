package com.lemon95.androidtvwidget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class ListViewTV extends ListView {

	private int listPon = 0;

	public ListViewTV(Context context) {
		super(context);
		init(context, null);
	}

	public ListViewTV(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	/**
	 * 崩溃了.
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		try {
			super.dispatchDraw(canvas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	WidgetTvViewBring mWidgetTvViewBring;

	private void init(Context context, AttributeSet attrs) {
		this.setChildrenDrawingOrderEnabled(true);
		mWidgetTvViewBring = new WidgetTvViewBring(this);
	}

	@Override
	public void bringChildToFront(View child) {
		mWidgetTvViewBring.bringChildToFront(this, child);
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		return mWidgetTvViewBring.getChildDrawingOrder(childCount, i);
	}

	public void setPoint(int listPon) {
		this.listPon = listPon;
	}

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		//int lastSelectItem = getSelectedItemPosition();
		int lastSelectItem = listPon;
		int b = 0;
		if (getSelectedView() != null) {
			b = (int)getSelectedView().getY();
		}
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		if (gainFocus) {
			setSelectionFromTop(lastSelectItem, b);
		}
	}

	@Override
	public void setSelectionFromTop(int position, int y) {
		super.setSelectionFromTop(position, y);
	}
}
