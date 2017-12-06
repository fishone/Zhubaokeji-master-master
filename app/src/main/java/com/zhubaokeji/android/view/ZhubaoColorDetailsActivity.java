package com.zhubaokeji.android.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.User;
import com.zhubaokeji.android.bean.ZhubaoColorResponse;
import com.zhubaokeji.android.databinding.ZhubaoColorDetailsBinding;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.library.TitleBar;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

/**
 * 创建人:YUIZHI
 * 包名：com.zhubaokeji.android.view
 * 创建时间：2017/8/7
 */
public class ZhubaoColorDetailsActivity extends BaseActivity {

    ZhubaoColorResponse diamondResponse = new ZhubaoColorResponse();
    @BindView(R.id.color_details_reportNo)
    TextView colorDetailsReportNo;
    @BindView(R.id.zhubao_color_details)
    LinearLayout zhubaoColorDetails;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    User user = new User();
    @BindView(R.id.zb_color_percentage)
    EditText zbColorPercentage;
    @BindView(R.id.zb_color_rate)
    EditText zbColorRate;
    @BindView(R.id.zb_color_rmbprice)
    TextView zbColorRmbprice;
    @BindView(R.id.zb_color_rmbPerCt)
    TextView zbColorRmbPerCt;
    @BindView(R.id.zb_color_offer)
    TextView zbColorOffer;
    @BindView(R.id.zhubao_color_details_title)
    TitleBar zhubaoColorDetailsTitle;
    @BindView(R.id.saledollorCt)
    TextView saledollorCt;
    @BindView(R.id.zb_color_carat)
    TextView zbColorCarat;
    EditText clickEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZhubaoColorDetailsBinding colorDetailsBinding = DataBindingUtil.setContentView(this, R.layout.zhubao_color_details);
        ButterKnife.bind(this);

        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_color_details_title);

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

        titleBar.setTitle("钻石详情");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);

        diamondResponse = getIntent().getParcelableExtra("detailsDiamond"); //接收点击详情的数据
        JpSearchRequest diamondRequest = getIntent().getParcelableExtra("diamondRequest");     //请求的条件
        diamondResponse.setRate(diamondRequest.getRate());
        diamondResponse.setPercentage(diamondRequest.getPercentage());
        zbColorRate.setText(diamondRequest.getRate());
        zbColorPercentage.setText(diamondRequest.getPercentage());
        colorDetailsBinding.setColorDetails(diamondResponse);
    }

    @OnClick({R.id.zb_color_offer, R.id.color_details_reportNo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zb_color_offer:
                Intent Intent_Result = new Intent();
                Intent_Result.setClass(this, OfferActivity.class);
                diamondResponse.setRmbprice(zbColorRmbprice.getText().toString());
                Intent_Result.putExtra("colorOfferInfo", diamondResponse);
                startActivity(Intent_Result);
                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                break;
            case R.id.color_details_reportNo:
                Intent_Result = new Intent();
                Intent_Result.setClass(ZhubaoColorDetailsActivity.this, ZhengshuActivity.class);
                Intent_Result.putExtra("reportNo", diamondResponse.getReportno());
                Intent_Result.putExtra("reportType", diamondResponse.getReport());
                startActivity(Intent_Result);
                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                break;
        }
    }

//    //点击非Edittext ,取消焦点
//    @OnTouch({R.id.zhubao_color_details})
//    public boolean OnTouch(View view, MotionEvent event) {
//        zhubaoColorDetails.setFocusable(true);
//        zhubaoColorDetails.setFocusableInTouchMode(true);
//        zhubaoColorDetails.requestFocus();
//        return false;
//    }


    @OnTouch({R.id.zb_color_percentage, R.id.zb_color_rate})
    public boolean onTouch(View view, MotionEvent event){
        clickEdit= (EditText) view;
        return false;
    }

    @OnTextChanged({R.id.zb_color_percentage, R.id.zb_color_rate})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (clickEdit !=null && clickEdit.getText().toString().indexOf(".") >= 0 ) {
            if(clickEdit.getText().toString().equals(".")){
                clickEdit.setText("");
            }
            if (clickEdit.getText().toString().indexOf(".", clickEdit.getText().toString().indexOf(".") + 1) > 0) {
                clickEdit.setText(clickEdit.getText().toString().substring(0, clickEdit.getText().toString().length() - 1));
                clickEdit.setSelection(clickEdit.getText().toString().length());
            }
        }
    }

    @OnTextChanged({R.id.zb_color_percentage, R.id.zb_color_rate})
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("输入前的状态", "i:" + s + "+" + start + "+" + count + "+" + after);
    }

    @OnTextChanged({R.id.zb_color_percentage, R.id.zb_color_rate})
    public void afterTextChanged(Editable s) {
        // RMB/CT=USD/CT * 汇率 * 倍率
        BigDecimal bigCarat = new BigDecimal(diamondResponse.getCarat());
        BigDecimal bigSaledollorCt = new BigDecimal(diamondResponse.getSaledollorctprice());
        BigDecimal bigPercentage =null;
        BigDecimal bigRate = null;
        if (StringUtils.isTrimEmpty(zbColorRate.getText().toString())) {
            bigRate = new BigDecimal(0.00);
        }else{
            bigRate=new BigDecimal(zbColorRate.getText().toString());
        }
        if (StringUtils.isTrimEmpty(zbColorPercentage.getText().toString())) {
            bigPercentage = new BigDecimal(0.00);
        }else{
            bigPercentage= new BigDecimal(zbColorPercentage.getText().toString());
        }
        BigDecimal bigRmbPerCt = bigSaledollorCt.multiply(bigRate).multiply(bigPercentage);
        BigDecimal rmbPrice = bigRmbPerCt.multiply(bigCarat).setScale(0, BigDecimal.ROUND_HALF_UP);
        zbColorRmbprice.setText(String.valueOf(rmbPrice));
        zbColorRmbPerCt.setText(String.valueOf(bigRmbPerCt.setScale(0, BigDecimal.ROUND_HALF_UP)));
    }
}
