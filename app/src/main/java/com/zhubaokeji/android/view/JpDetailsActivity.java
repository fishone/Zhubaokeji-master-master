package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhubaokeji.android.bean.DataHolder;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.GlideApp;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.CertificateRequest;
import com.zhubaokeji.android.bean.GiaPic;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.LzyJavaResponse;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.databinding.JpDetailsBinding;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.library.TitleBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by fisho on 2017/5/24.
 */

public class JpDetailsActivity extends BaseActivity {
    @BindView(R.id.jp_details_report)
    TextView jpDetailsReport;
    ZhubaoDiamondResponse diamondResponse = new ZhubaoDiamondResponse();
    JpSearchRequest diamondRequest;
    @BindView(R.id.addShoppingCart)
    TextView addShoppingCart;
    @BindView(R.id.reportpic)
    ImageView reportpic;
    @BindView(R.id.loading_report)
    ImageView loadingReport;
    @BindView(R.id.plotPic)
    ImageView plotPic;
    @BindView(R.id.loading_plot)
    ImageView loadingPlot;
    @BindView(R.id.back)
    TextView back;
    private int i = 0;
    public File imageInfo = null;
    public List<File> mImageInfoList = null;
    List<Bitmap> reportImage = new ArrayList<>();
    public static final int PREVIEW_REQUEST_CODE = 1000;
    CertificateRequest certificateRequest = new CertificateRequest();
    AnimationDrawable reportAnimation;
    AnimationDrawable plotAnimation;
    private Activity mContext;
    private int imageIndex=0;
    public static Map<String,Bitmap> bitmapMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JpDetailsBinding detailsBinding = DataBindingUtil.setContentView(this, R.layout.jp_details);
        ButterKnife.bind(this);
          /*设置标题*/
        boolean isImmersive = true;
        mContext=this;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_details_title);

        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setTitle("JP详情");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                // 获取用户计算后的结果
                int three = Integer.parseInt(i + "");
                intent.putExtra("three", three); //将计算的值回传回去
                //通过intent对象返回结果，必须要调用一个setResult方法，
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                setResult(2, intent);
                finish();
            }
        });

        diamondResponse = getIntent().getParcelableExtra("detailsDiamond"); //接收点击详情的数据
        diamondRequest = getIntent().getParcelableExtra("diamondRequest"); //接收点击详情的条件
        detailsBinding.setDetails(diamondResponse);
        certificateRequest.setReportno(diamondResponse.getReportno());
        certificateRequest.setType(diamondResponse.getReport());
        loadingReport.setVisibility(View.VISIBLE);
        loadingPlot.setVisibility(View.VISIBLE);
        reportAnimation = (AnimationDrawable) loadingReport.getBackground();
        plotAnimation = (AnimationDrawable) loadingPlot.getBackground();
        reportAnimation.start();
        plotAnimation.start();
        back.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  //中划线
        query(certificateRequest);
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    private void query(final CertificateRequest request) {
        final String path = Urls.ZHENGSHUSEARCHURL;
        final Gson gson = new Gson();
        String jsonString = gson.toJson(request);
        OkGo.<LzyJavaResponse<GiaPic>>post(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .upJson(jsonString)
                .execute(new JsonCallback<LzyJavaResponse<GiaPic>>() {
                    @Override
                    public void onSuccess(Response<LzyJavaResponse<GiaPic>> response) {
                        try {
                            if (null != response && null != response.body()) {
                                LzyJavaResponse<GiaPic> lzyResponse = response.body();
                                loadingReport.setVisibility(View.GONE);
                                loadingPlot.setVisibility(View.GONE);
                                switch (lzyResponse.status) {
                                    case 375:
                                        GiaPic giaPic = new GiaPic();
                                        giaPic = lzyResponse.getResult();
                                        if (giaPic != null) {
                                            final GiaPic finalGiaPic = giaPic;
                                            GlideApp.with(getApplicationContext()).asBitmap().load(giaPic.getReportpicSm()).into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    reportpic.setImageBitmap(resource);
                                                    reportImage.add(resource);
                                                    //拉取净度图
                                                    GlideApp.with(getApplicationContext()).asBitmap().load(finalGiaPic.getPlotSmPic()).into(new SimpleTarget<Bitmap>() {
                                                        @Override
                                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                            plotPic.setImageBitmap(resource);
                                                            reportImage.add(resource);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        break;
                                    default:
                                        ToastUtil.show(mContext, "没有查询到图片");
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e("jp 查询证书图片", e);
                        }
                    }
                    @Override
                    public void onError(Response<LzyJavaResponse<GiaPic>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                    }
                });

    }

    @OnClick({R.id.reportpic, R.id.plotPic, R.id.jp_details_report, R.id.addShoppingCart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reportpic:
                if (null == reportImage || reportImage.size() == 0) {
                    break;
                }
                Intent previewIntent = new Intent(mContext, ImagePreviewActivity.class);
                imageIndex=0;
                DataHolder.save("IMAGE", reportImage);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_FLAG, imageIndex);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_MAPID, "IMAGE");
                startActivity(previewIntent);
                break;
            case R.id.plotPic:
                if (null == reportImage || reportImage.size() == 0 || reportImage.size() <= 1) {
                    break;
                }
                previewIntent = new Intent(mContext, ImagePreviewActivity.class);
                imageIndex=1;
                DataHolder.save("IMAGE", reportImage);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_FLAG, imageIndex);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_MAPID, "IMAGE");
                startActivity(previewIntent);
                break;
            case R.id.jp_details_report:
                if (!jpDetailsReport.getText().equals("")) {
                    Intent result_intent = new Intent();
                    result_intent.setClass(JpDetailsActivity.this, ZhengshuActivity.class);
                    result_intent.putExtra("reportNo", diamondResponse.getReportno());
                    result_intent.putExtra("reportType", diamondResponse.getReport());
                    startActivity(result_intent);
                }
                break;
            case R.id.addShoppingCart:
                final String path = Urls.URL + Urls.JPADDSHOPPINGCART + "&needno=" + diamondResponse.getDiano() + "&token=" + diamondRequest.getToken();
                OkGo.<LzyResponse>get(path)     // 请求方式和请求url
                        .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                        .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
                            @Override
                            public void onSuccess(Response<LzyResponse> response) {
                                try {
                                    if (null != response && null != response.body()) {
                                        LzyResponse lzyResponse = response.body();
                                        String message = lzyResponse.message;
                                        switch (lzyResponse.status) {
                                            case 1:
                                                i = 1;
                                                ToastUtil.show(mContext, "添加购物车成功");
                                                break;
                                            case -1:
                                                ToastUtil.show(mContext, message);
                                            case 404:
                                            case -404:
                                                jp_Login_Boolean = false;
                                                ToastUtil.show(mContext, "登录超时,请重新登录");
                                                Intent login_intent = new Intent();
                                                login_intent.setClass(JpDetailsActivity.this, JpLoginActivity.class);
                                                startActivity(login_intent);
                                                break;
                                            default:
                                                ToastUtil.show(mContext, message);
                                                break;
                                        }
                                    }
                                } catch (Exception e) {
                                    Logger.e(e, "Jp添加购物车");
                                }
                            }
                            @Override
                            public void onError(Response<LzyResponse> response) {
                                //网络请求失败的回调,一般会弹个Toast
                                NetUtil.myException(mContext,response.getException(),FlagUtil.JP);
                            }
                        });
                break;
        }
    }
}
