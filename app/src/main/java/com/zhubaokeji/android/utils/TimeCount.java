package com.zhubaokeji.android.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by fisho on 2017/6/26.
 */

public class TimeCount extends CountDownTimer {
    TextView textView;
    public TimeCount(long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView=textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setBackgroundColor(Color.parseColor("#9E9E9E"));
        textView.setClickable(false);
        textView.setTextSize(12);
        textView.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
    }

    @Override
    public void onFinish() {
        textView.setText("重新获取验证码");
        textView.setClickable(true);
        textView.setBackgroundColor(Color.parseColor("#02a8f3"));

    }
}
