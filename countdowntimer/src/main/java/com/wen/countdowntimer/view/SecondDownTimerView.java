package com.wen.countdowntimer.view;

import android.content.Context;
import android.util.AttributeSet;

import com.wen.countdowntimer.view.base.BaseCountDownTimerView;


public class SecondDownTimerView extends BaseCountDownTimerView {

	public SecondDownTimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SecondDownTimerView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SecondDownTimerView(Context context) {
		this(context,null);
	}

	@Override
	protected String getStrokeColor() {
		return "000000";
	}

	@Override
	protected String getTextColor() {
		return "ffffff";
	}

	@Override
	protected int getCornerRadius() {
		return 3;
	}

	@Override
	protected int getTextSize() {
		return 22;
	}

	@Override
	protected String getBackgroundColor() {
		return "000000";
	}
	
}
