package com.zhubaokeji.android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.bean.DataHolder;
import com.zhubaokeji.android.bean.LzyJavaResponse;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.utils.GlideApp;
import com.zhubaokeji.android.view.ImagePreviewActivity;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.CertificateRequest;
import com.zhubaokeji.android.bean.GiaPic;
import com.zhubaokeji.android.bean.IgiCertificateResponse;
import com.zhubaokeji.android.databinding.IgiZhengshuBinding;

import org.json.JSONObject;

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
import com.lzy.okgo.model.Response;

/**
 * Created by Yuizhi on 2017/1/11.
 */

public class IgiFragment extends Fragment {
    IgiCertificateResponse igiCertificateResponse = new IgiCertificateResponse();
    @BindView(R.id.igi_reportpic)
    ImageView igiReportpic;
    @BindView(R.id.igi_plotPic)
    ImageView igiPlotPic;
    public File imageInfo = null;
    public List<File> mImageInfoList = null;
    List<Bitmap> reportImage = new ArrayList<>();
    CertificateRequest repoetRequest = new CertificateRequest();
    public static final int PREVIEW_REQUEST_CODE = 1000;
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    private int imageIndex=0;
    private Activity mContext;
    public static Map<String,Bitmap> bitmapMap=new HashMap<>();
    @BindView(R.id.igi_loading_report)
    ImageView igiLoadingReport;
    @BindView(R.id.igi_loading_plot)
    ImageView igiLoadingPlot;
    AnimationDrawable reportAnimation;
    AnimationDrawable plotAnimation;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        IgiZhengshuBinding igiZhengshuBinding = DataBindingUtil.inflate(inflater, R.layout.igi_zhengshu, container, false);
        Bundle bundle = getArguments();
        mContext=getActivity();
        igiCertificateResponse = bundle.getParcelable("igiReport");
        igiZhengshuBinding.setIgi(igiCertificateResponse);
        ButterKnife.bind(this, igiZhengshuBinding.getRoot());
        if (null == igiCertificateResponse.getReportpicLg()) {
            repoetRequest.setReportno(igiCertificateResponse.getReportnumber());
            repoetRequest.setType("IGI");
            igiLoadingReport.setVisibility(View.VISIBLE);
            igiLoadingPlot.setVisibility(View.VISIBLE);
            reportAnimation = (AnimationDrawable) igiLoadingReport.getBackground();
            plotAnimation= (AnimationDrawable) igiLoadingPlot.getBackground();
            reportAnimation.start();
            plotAnimation.start();
            query(repoetRequest);
        }
        Reportimage();
        return igiZhengshuBinding.getRoot();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Util.isOnMainThread() && !mContext.isFinishing())
        {
            Glide.with(this).pauseRequests();
        }
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
                            if (null != response && null !=response.body()) {
                                LzyJavaResponse<GiaPic> lzyResponse=response.body();
                                igiLoadingReport.setVisibility(View.GONE);
                                igiLoadingPlot.setVisibility(View.GONE);
                                switch (lzyResponse.status){
                                    case 375:
                                        GiaPic giaPic = new GiaPic();
                                        giaPic=lzyResponse.getResult();
                                        if(giaPic !=null){
                                            final GiaPic finalGiaPic = giaPic;
                                            GlideApp.with(mContext).asBitmap().load(giaPic.getReportpicSm()).into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    igiReportpic.setImageBitmap(resource);
                                                    reportImage.add(resource);
                                                    //拉取净度图
                                                    GlideApp.with(mContext).asBitmap().load(finalGiaPic.getPlotSmPic()).into(new SimpleTarget<Bitmap>() {
                                                        @Override
                                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                            igiPlotPic.setImageBitmap(resource);
                                                            reportImage.add(resource);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        break;
                                    default:
                                        ToastUtil.show(mContext,"没有查询到图片");
                                        break;
                                }
                            }
                        }catch (Exception e){
                            igiLoadingReport.setVisibility(View.GONE);
                            igiLoadingPlot.setVisibility(View.GONE);
                            ToastUtil.show(mContext,"没有查询到图片");
                        }
                    }
                    @Override
                    public void onError(Response<LzyJavaResponse<GiaPic>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        igiLoadingReport.setVisibility(View.GONE);
                        igiLoadingPlot.setVisibility(View.GONE);
                        ToastUtil.show(mContext,"查询出错,请重试");
                    }
                });
    }

    void Reportimage() {
        GlideApp.with(getActivity()).asBitmap().load(igiCertificateResponse.getReportpicSm()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                igiReportpic.setImageBitmap(resource);
                reportImage.add(resource);
                //拉取净度
                GlideApp.with(getActivity()).asBitmap().load(igiCertificateResponse.getPlotSmPic()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        igiPlotPic.setImageBitmap(resource);
                        reportImage.add(resource);
                    }
                });
            }
        });
    }

    @OnClick({R.id.igi_reportpic, R.id.igi_plotPic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.igi_reportpic:
                if (null == reportImage || reportImage.size() == 0) {
                    break;
                }
                Intent previewIntent = new Intent(getContext(), ImagePreviewActivity.class);
                imageIndex=0;
                DataHolder.save("IMAGE", reportImage);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_FLAG, imageIndex);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_MAPID, "IMAGE");
                startActivity(previewIntent);
                break;
            case R.id.igi_plotPic:
                if (null == reportImage || reportImage.size() == 0 || reportImage.size() <= 1) {
                    break;
                }
                previewIntent = new Intent(getContext(), ImagePreviewActivity.class);
                imageIndex=1;
                DataHolder.save("IMAGE", reportImage);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_FLAG, imageIndex);
                previewIntent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_MAPID, "IMAGE");
                startActivity(previewIntent);
                break;
        }
    }
}
