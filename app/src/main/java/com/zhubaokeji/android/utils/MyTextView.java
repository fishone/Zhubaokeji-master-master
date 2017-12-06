package com.zhubaokeji.android.utils;

import android.content.Context;
import android.util.AttributeSet;
import  android.support.v7.widget.AppCompatTextView;

/**
 * 作者：YUIZHI
 * Zhubaokeji-master
 * 创建时间: 2017/8/5
 */

public class MyTextView extends AppCompatTextView{
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写isFocused方法，让其一直返回true
    @Override
    public boolean isFocused() {
        return true;
    }
}
