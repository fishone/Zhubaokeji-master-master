package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.zhubaokeji.android.bean.BasicSetting;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fisho on 2017/3/20.
 */

public class BasicSettingsActivity extends BaseActivity {
    BasicSetting basicSetting = new BasicSetting();
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    @BindView(R.id.setting_rate)
    EditText settingRate;
    @BindView(R.id.setting_DiscountPoint)
    EditText settingDiscountPoint;
    @BindView(R.id.setting_add)
    Button settingAdd;
    @BindView(R.id.setting_subtract)
    Button settingSubtract;
    @BindView(R.id.setting_percentage)
    EditText settingPercentage;
    @BindView(R.id.setting_submit)
    Button settingSubmit;
    private Activity mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicsettings);
        ButterKnife.bind(this);
        mContext=this;
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.basicSettings_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
            }
        });

        titleBar.setTitle("设置");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        preferencesUtil=new SharedPreferencesUtil(getApplicationContext());
        try {
            basicSetting= preferencesUtil.getBasicSetting();
            settingRate.setText(basicSetting.getRate().toString());
            settingDiscountPoint.setText(basicSetting.getDiscountPoint().toString());
            settingPercentage.setText(basicSetting.getPercentage().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.setting_add, R.id.setting_subtract, R.id.setting_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_add:
                BigDecimal discountPoint = new BigDecimal(settingDiscountPoint.getText().toString());
                BigDecimal IntDiscountPoint =discountPoint.add(new BigDecimal(1.00));
                settingDiscountPoint.setText(IntDiscountPoint.toString());
                break;
            case R.id.setting_subtract:
                discountPoint = new BigDecimal(settingDiscountPoint.getText().toString());
                IntDiscountPoint =discountPoint.subtract(new BigDecimal(1.00));
                settingDiscountPoint.setText(IntDiscountPoint.toString());
                break;
            case R.id.setting_submit:
                basicSetting.setRate(settingRate.getText().toString());
                basicSetting.setDiscountPoint(settingDiscountPoint.getText().toString());
                basicSetting.setPercentage(settingPercentage.getText().toString());
                preferencesUtil.saveBasicSetting(basicSetting);
                finish();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
                ToastUtil.show(mContext,"设置成功");
                break;
        }
    }

    // 设置点击非EditText，隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
