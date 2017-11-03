package com.wen.countdowntimer.view;

import android.content.Context;
import android.util.AttributeSet;

import com.wen.countdowntimer.view.base.BaseCountDownTimerView;


public class MainDownTimerView extends BaseCountDownTimerView {

	public MainDownTimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MainDownTimerView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MainDownTimerView(Context context) {
		this(context,null);
	}

	@Override
	protected String getStrokeColor() {
		return "00000";
	}

	@Override
	protected String getTextColor() {
		return "ffffff";
	}

	@Override
	protected int getCornerRadius() {
		return 1;
	}

	@Override
	protected int getTextSize() {
		return 18;
	}

	@Override
	protected String getBackgroundColor() {
		return "000000";
	}

	
	
}
