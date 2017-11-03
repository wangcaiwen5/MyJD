package com.wen.controlnumview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Time:2017/10/18 14:57
 * Author:王才文
 * Description:
 */

public class ControlNumView extends LinearLayout implements View.OnClickListener{
    private int num = 1; //购买数量
    private int goods_num = 1; //商品库存
    private Button btLeft;
    private Button btRight;
    private TextView tvCenter;
    private OnNumViewChangeListener mListener;

    public ControlNumView(Context context) {
        this(context,null);
    }

    public ControlNumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ControlNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

         LayoutInflater.from(context).inflate(R.layout.layout, this);
        btLeft = findViewById(R.id.bt_left);
        btRight = findViewById(R.id.bt_right);
        tvCenter = findViewById(R.id.tv_center);

        btLeft.setOnClickListener(this);
        btRight.setOnClickListener(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControlNumView);

        int btWidth = typedArray.getDimensionPixelSize(R.styleable.ControlNumView_btWidth,50);
        int btHeight = typedArray.getDimensionPixelSize(R.styleable.ControlNumView_btHeight, 50);
        int btTextColor = typedArray.getColor(R.styleable.ControlNumView_btTextColor, 0xffffff);
        int btTextSzie = typedArray.getDimensionPixelSize(R.styleable.ControlNumView_btTextSize, 20);
        int tvTextSize = typedArray.getDimensionPixelSize(R.styleable.ControlNumView_tvTextSize, 20);
        int tvTextColor = typedArray.getColor(R.styleable.ControlNumView_tvTextColor, 0x000000);
        int btBackground = typedArray.getColor(R.styleable.ControlNumView_btBackground, 0x000000);
        int tvWidth = typedArray.getDimensionPixelSize(R.styleable.ControlNumView_tvWidth, 50);

        typedArray.recycle();//回收

        LayoutParams btParams = new LayoutParams(btWidth, btHeight);//设置宽高
        btLeft.setLayoutParams(btParams);
        btRight.setLayoutParams(btParams);
/**
 * 这时可以用另一个函数形式setTextSize(int unit, int size)，可以指定单位unit，unit有三个单位：

 TypedValue.COMPLEX_UNIT_PX : Pixels  即px
 TypedValue.COMPLEX_UNIT_SP : Scaled Pixels  即sp
 TypedValue.COMPLEX_UNIT_DIP : Device Independent Pixels 即dp（dip）
 CSDN博客说明:http://blog.csdn.net/qq_28484355/article/details/52744937
 */
        if(btTextSzie!=0){
            btLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX,btTextSzie);
            btRight.setTextSize(TypedValue.COMPLEX_UNIT_PX,btTextSzie);
            btLeft.setTextColor(btTextColor);
            btRight.setTextColor(btTextColor);
            btLeft.setBackgroundColor(btBackground);
            btRight.setBackgroundColor(btTextColor);
        }

        LayoutParams layoutParams = new LayoutParams(tvWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        tvCenter.setLayoutParams(layoutParams);

        if(tvTextSize!=0){
            tvCenter.setTextSize(tvTextSize);
            tvCenter.setTextColor(tvTextColor);
        }



    }

    public void setGoodsNum(int goods_num) {
        this.goods_num = goods_num;
    }

    @Override
    public void onClick(View view) {


        int id = view.getId();
        if (id == R.id.bt_left) {
            if(num>1){
                num--;
                tvCenter.setText(num);
            }
        }else if (id==R.id.bt_right){
            if(num<goods_num){
                num++;
                tvCenter.setText(num);

            }
        }

        if(mListener!=null){
            mListener.onNumViewChange(this,num);
        }


    }

    public void setListener(Object object){

        this.mListener = (OnNumViewChangeListener) object;
    }

    interface OnNumViewChangeListener{
        void onNumViewChange(View view, int num);
    }
}
