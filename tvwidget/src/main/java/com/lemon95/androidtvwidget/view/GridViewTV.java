package com.lemon95.androidtvwidget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

/**
 * GridView TV版本.
 * @author hailongqiu 356752238@qq.com
 *
 */
public class GridViewTV extends GridView {

	private boolean isSearch = false;
	private int point = 0;

	public void setPoint(int point) {
		this.point = point;
	}

	public void setIsSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}

	public GridViewTV(Context context) {
		super(context);
		init(context, null);
	}

	public GridViewTV(Context context, AttributeSet attrs) {
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
		// position = getSelectedItemPosition() - getFirstVisiblePosition();
		return mWidgetTvViewBring.getChildDrawingOrder(childCount, i);
	}

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		/*if(19 <= Build.VERSION.SDK_INT){
			super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		} else {*/
			int lastSelectItem = getSelectedItemPosition();
			if (point != 0) {
				lastSelectItem = point;
			}
			super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
			if (gainFocus) {
				setSelection(lastSelectItem);
			}
		//}

	}

	/*@Override
	public void setSelection(int position) {
		super.setSelection(position);
		if (!isInTouchMode()) {
			setNextSelectedPositionInt(position);
		} else {
			mResurrectToPosition = position;
		}
		mLayoutMode = LAYOUT_SET_SELECTION;
		if (mPositionScroller != null) {
			mPositionScroller.stop();
		}
		requestLayout();
	}*/

	@Override
	public boolean isInTouchMode() {
		if (isSearch) {
			if(19 <= Build.VERSION.SDK_INT){
				return !(hasFocus() && !super.isInTouchMode());
			}else{
				return super.isInTouchMode();
			}
		} else {
			return super.isInTouchMode();
		}
	}

}
