package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.bean.SupplierResponse;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.databinding.ZhubaoDetailsBinding;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.StringUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.library.TitleBar;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Yuizhi on 2016/12/27.
 */

public class ZhubaoDetailsActivity extends BaseActivity {
    @BindView(R.id.carat)
    TextView carat;
    @BindView(R.id.onlineprice)
    TextView onlineprice;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.edit_presell_add)
    EditText editPresellAdd;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.bt_subtract)
    Button btSubtract;
    @BindView(R.id.username_layout)
    FrameLayout usernameLayout;
    @BindView(R.id.edit_rate)
    EditText editRate;
    @BindView(R.id.presellBack)
    TextView presellBack;
    @BindView(R.id.rmbprice)
    TextView rmbprice;
    @BindView(R.id.rmbPerCt)
    TextView rmbPerCt;
    @BindView(R.id.offer)
    TextView offer;

    ZhubaoDiamondResponse diamondResponse = new ZhubaoDiamondResponse();
    @BindView(R.id.zb_details_report)
    TextView zbDetailsReport;
    @BindView(R.id.zhubao_details)
    LinearLayout zhubaoDetails;
    private Activity mContext;
    ZhubaoDetailsBinding detailsBinding;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.zhubao_details);
        ButterKnife.bind(this);
        mContext = this;
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_details_title);
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
        detailsBinding.setDetails(diamondResponse);
        editRate.setText(diamondRequest.getRate());
        editPresellAdd.setText(diamondRequest.getDiscountPoint());
        count();
        supplier();
        TextChange textChange = new TextChange();
        editPresellAdd.addTextChangedListener(textChange);
        editPresellAdd.setOnFocusChangeListener(new MobileOnFocusChanageListener());
        editRate.addTextChangedListener(textChange);
        editRate.setOnFocusChangeListener(new MobileOnFocusChanageListener());
    }

    //MobileOnFocusChanageListener焦点监听器
    private class MobileOnFocusChanageListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean hasFocus){
            switch (view.getId()) {
                case R.id.edit_presell_add:  case R.id.edit_rate:
                    editText = (EditText) view;
                    break;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    void supplier() {
        String path = Urls.SUPPLIER + Urls.ZHUBAOSUPPLIER + "&s_sup=" + diamondResponse.getRapnetid();
        OkGo.<LzyResponse<SupplierResponse>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyResponse<SupplierResponse>>(mContext) {
                    @Override
                    public void onSuccess(Response<LzyResponse<SupplierResponse>> response) {
                        try {
                            if (null != response && null != response.body()) {
                                LzyResponse<SupplierResponse> lzyResponse = response.body();
                                SupplierResponse supplierResponse = lzyResponse.getMsgdata();
                                detailsBinding.setSupplier(supplierResponse);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<LzyResponse<SupplierResponse>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.zbException(mContext, response.getException());
                    }
                });
    }


    @OnClick({R.id.bt_add, R.id.bt_subtract, R.id.offer, R.id.zb_details_report})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                EditText discountPoint = (EditText) findViewById(R.id.edit_presell_add);
                BigDecimal bigDiscountPoint=null;
                BigDecimal bigIntDiscountPoint=null;
                if(!StringUtils.isTrimEmpty(discountPoint.getText().toString())){
                    bigDiscountPoint = new BigDecimal(discountPoint.getText().toString());
                    bigIntDiscountPoint = bigDiscountPoint.add(new BigDecimal(1.00));
                    discountPoint.setText(bigIntDiscountPoint.toString());
                    break;
                }else{
                    discountPoint.setText("1.00");
                    break;
                }

            case R.id.bt_subtract:
                discountPoint = (EditText) findViewById(R.id.edit_presell_add);
                if(!StringUtils.isTrimEmpty(discountPoint.getText().toString())){
                    bigDiscountPoint = new BigDecimal(discountPoint.getText().toString());
                    bigIntDiscountPoint = bigDiscountPoint.subtract(new BigDecimal(1.00));
                    discountPoint.setText(bigIntDiscountPoint.toString());
                    break;
                }else{
                    discountPoint.setText("-1.00");
                    break;
                }
            case R.id.offer:
                Intent Intent_Result = new Intent();
                Intent_Result.setClass(this, OfferActivity.class);
                overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                diamondResponse.setPresellBack(presellBack.getText().toString());
                diamondResponse.setRmbprice(rmbprice.getText().toString());
                Intent_Result.putExtra("offerInfo", diamondResponse);
                this.startActivity(Intent_Result);
                break;
            case R.id.zb_details_report:
                if (!zbDetailsReport.getText().equals("")) {
                    Intent result_intent = new Intent();
                    result_intent.setClass(ZhubaoDetailsActivity.this, ZhengshuActivity.class);
                    overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                    result_intent.putExtra("reportNo", diamondResponse.getReportno());
                    result_intent.putExtra("reportType", diamondResponse.getReport());
                    startActivity(result_intent);
                }
                break;
        }
    }

    //点击非Edittext ,取消焦点
    @OnTouch({R.id.zhubao_details})
    public boolean OnTouch(View view, MotionEvent event) {
        zhubaoDetails.setFocusable(true);
        zhubaoDetails.setFocusableInTouchMode(true);
        zhubaoDetails.requestFocus();
        return false;
    }


    private class TextChange implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (editText !=null && editText.getText().toString().indexOf(".") >= 0 ) {
                if(editText.getText().toString().equals(".")){
                    editText.setText("");
                }
                if (editText.getText().toString().indexOf(".", editText.getText().toString().indexOf(".") + 1) > 0) {
                    editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                    editText.setSelection(editText.getText().toString().length());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            count();
        }
    }

    private void count() {
        //    RMB/ct = 国际报价*(100+退点+预售加点)/100*汇率。四舍五入，保留两位小数。
        //    RMB/粒 = RMB/ct*克拉。向上取整，保留两位小数。
        BigDecimal bigCarat = new BigDecimal(diamondResponse.getCarat().trim());
        BigDecimal bigRmbPerCt = null;
        BigDecimal bigRmbPrice = null;
        BigDecimal bigOnlineprice = new BigDecimal(diamondResponse.getOnlineprice().toString().trim());
        String rate = editRate.getText().toString().trim();
        if (rate.equals("") || rate == null ) {
            rate = String.valueOf(0);
        }
        String presell_add = editPresellAdd.getText().toString();
        if (!StringUtil.isValidString(presell_add)) {
            presell_add = String.valueOf(0.00);
        }
        BigDecimal bigAddBack = new BigDecimal(presell_add);
        BigDecimal bigRate = new BigDecimal(rate);
        BigDecimal bigBack = new BigDecimal(diamondResponse.getSaleback().trim());
        BigDecimal presell = bigBack.add(bigAddBack);
        presellBack.setText(String.valueOf(presell));
        bigRmbPerCt = bigOnlineprice.multiply(new BigDecimal(100).add(bigBack).add(bigAddBack)).divide(new BigDecimal(100)).multiply(bigRate);
        rmbPerCt.setText(String.valueOf(bigRmbPerCt.setScale(0, BigDecimal.ROUND_HALF_UP)));
        bigRmbPrice = bigRmbPerCt.multiply(bigCarat);
        rmbprice.setText(String.valueOf(bigRmbPrice.setScale(0, BigDecimal.ROUND_HALF_UP)));
    }
}
