package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.CertificateRequest;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.GiaCertificateResponse;
import com.zhubaokeji.android.bean.IgiCertificateResponse;
import com.zhubaokeji.android.fragment.GiaFragment;
import com.zhubaokeji.android.fragment.IgiFragment;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.ButtonM;
import com.zhubaokeji.android.utils.LoadingDialog;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.StringUtil;
import com.google.gson.Gson;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import com.lzy.okgo.model.Response;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by Yuizhi on 2017/1/3.
 */

public class ZhengshuActivity extends BaseActivity{
    CertificateRequest reportRequest = new CertificateRequest();
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    GiaCertificateResponse giaResponse = new GiaCertificateResponse();
    IgiCertificateResponse igiResponse = new IgiCertificateResponse();
    String typeStr = null;
    @BindView(R.id.button_rapoet_gia)
    ButtonM buttonRapoetGia;
    @BindView(R.id.button_rapoet_igi)
    ButtonM buttonRapoetIgi;
    @BindView(R.id.text_RapoetNo)
    TextView textRapoetNo;
    @BindView(R.id.edit_reportNo)
    EditText editReportNo;
    @BindView(R.id.btn_zhengshu_query)
    Button btnZhengshuQuery;
    @BindView(R.id.certificate)
    FrameLayout certificate;
    @BindView(R.id.zhengshu)
    LinearLayout zhengshu;
    private Activity mContext;
    LoadingDialog dialog;
    private FragmentTransaction transaction;
    private FragmentManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhengshu);
        ButterKnife.bind(this);
        mContext=this;
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhengshu_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitle("证书查询");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        String  reportNo= getIntent().getStringExtra("reportNo");
        String  reportType=getIntent().getStringExtra("reportType");

        if(StringUtil.isValidString(reportNo)){
            if(reportType.equals("GIA")){
                buttonRapoetGia.setIsSelected(true);
            }else {
                buttonRapoetGia.setIsSelected(false);
                buttonRapoetIgi.setIsSelected(true);
            }
            editReportNo.setText(reportNo.trim());
            btnZhengshuQuery.performClick();
        }
        if(!buttonRapoetIgi.isSelected()){
            buttonRapoetGia.setIsSelected(true);
        }
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            zhubao_Login_boolean=false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    private void query(final CertificateRequest reportRequest) {
//        if(NetUtil.isZhubaoQuery(mContext) ==false){
//            return;
//        }
        dialog=new LoadingDialog(mContext,"玩命加载中...");
        dialog.show();
        final String path = Urls.ZHENGSHUURL;
        final Gson gson = new Gson();
        String jsonString = gson.toJson(reportRequest);
        OkGo.<String>post(path)     // 请求方式和请求url
                .tag(this)                         // 请求的 tag, 主要用于取消对应的请求
                .upJson(jsonString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                int status=jsonObject.getInt("status");
                                dialog.close();
                                switch (status) {
                                    case 370:
                                        JSONObject msgdata = jsonObject.getJSONObject("result"); //取出证书信息
                                        //对象中拿到集合,igi，gia,
                                        if (reportRequest.getType().equals("GIA")) {
                                            giaResponse = gson.fromJson(String.valueOf(msgdata), GiaCertificateResponse.class);
                                        } else {
                                            igiResponse = gson.fromJson(String.valueOf(msgdata), IgiCertificateResponse.class);
                                        }
                                        if (giaResponse != null || igiResponse != null) {
                                            if (typeStr.equals("GIA")) {
                                                GiaFragment giaFragment = new GiaFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putParcelable("giaRreport", giaResponse);
                                                giaFragment.setArguments(bundle);
                                                //如果transaction  commit（）过  那么我们要重新得到transaction
                                                manager = getSupportFragmentManager();
                                                transaction = manager.beginTransaction();
                                                transaction.replace(R.id.certificate, giaFragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();
                                            } else {
                                                IgiFragment igiFragment = new IgiFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putParcelable("igiReport", igiResponse);
                                                igiFragment.setArguments(bundle);
                                                //如果transaction  commit（）过  那么我们要重新得到transaction
                                                manager = getSupportFragmentManager();
                                                transaction = manager.beginTransaction();
                                                transaction.replace(R.id.certificate, igiFragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();
                                            }
                                        }
                                        break;
                                    case 371:  case ERROR:
                                        ToastUtil.show(getApplicationContext(),"未找到证书");
                                        break;
                                    case 372:
                                        ToastUtil.show(getApplicationContext(),"证书制作中");
                                        break;
                                    default:
                                        ToastUtil.show(getApplicationContext(),"未找到证书");
                                        break;

                                }
                            }
                        }catch (Exception e){
                            dialog.close();
                            ToastUtil.show(mContext,"未找到证书");
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        dialog.close();
                        //网络请求失败的回调,一般会弹个Toast
                        Throwable throwable =response.getException();
                        if(throwable !=null)throwable.printStackTrace();
                        if(throwable instanceof UnknownHostException ||throwable instanceof ConnectException){
                            ToastUtil.show(mContext,"网络未连接,请连接网络");
                        }else if(throwable instanceof SocketTimeoutException){
                            ToastUtil.show(mContext,"网络请求超时");
                        }else {
                            ToastUtil.show(mContext,"未找到证书");
                        }
                    }
                });
    }

    @OnClick({R.id.button_rapoet_gia, R.id.button_rapoet_igi, R.id.btn_zhengshu_query, R.id.zhengshu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_rapoet_gia:
                if (buttonRapoetGia.isSelected()) {
                    buttonRapoetGia.setIsSelected(true);
                } else {
                    buttonRapoetIgi.setIsSelected(false);
                    buttonRapoetGia.setIsSelected(true);
                }
                break;
            case R.id.button_rapoet_igi:
                if (buttonRapoetIgi.isSelected()) {
                    buttonRapoetIgi.setIsSelected(true);
                } else {
                    buttonRapoetGia.setIsSelected(false);
                    buttonRapoetIgi.setIsSelected(true);
                }
                break;
            case R.id.btn_zhengshu_query:
                if (buttonRapoetGia.isSelected() == true) {
                    typeStr =buttonRapoetGia.getText().toString();
                }
                if (buttonRapoetIgi.isSelected() == true) {
                    typeStr =buttonRapoetIgi.getText().toString();
                }
                String reportNo = editReportNo.getText().toString();
                if (StringUtil.isValidString(reportNo)) {
                    reportRequest.setReportno(reportNo);
                    reportRequest.setType(typeStr);
                    query(reportRequest);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
                break;
            case R.id.zhengshu:
                break;
        }
    }
    //点击非Edittext ,取消焦点
    @OnTouch({R.id.zhengshu})
    public boolean OnTouch(View view, MotionEvent event) {
        zhengshu.setFocusable(true);
        zhengshu.setFocusableInTouchMode(true);
        zhengshu.requestFocus();
        return true;
    }
}
