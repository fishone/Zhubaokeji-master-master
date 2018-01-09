package com.zhubaokeji.android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.bean.DataHolder;
import com.zhubaokeji.android.bean.LzyJavaResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.GlideApp;
import com.zhubaokeji.android.view.ImagePreviewActivity;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.CertificateRequest;
import com.zhubaokeji.android.bean.GiaCertificateResponse;
import com.zhubaokeji.android.bean.GiaPic;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.databinding.GiaZhengshuBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzy.okgo.model.Response;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yuizhi on 2017/1/11.
 */

public class GiaFragment extends Fragment {
    GiaCertificateResponse giaCertificateResponse = new GiaCertificateResponse();
    @BindView(R.id.reportpic)
    ImageView reportpic;
    @BindView(R.id.plotPic)
    ImageView plotPic;
    @BindView(R.id.loading_report)
    ImageView loadingReport;
    @BindView(R.id.loading_plot)
    ImageView loadingPlot;

    /**
     * 相册信息列表
     */
    public byte[] imageInfo;
    public List<byte[]> mImageInfoList;
    List<Bitmap> reportImage = new ArrayList<Bitmap>();
    ArrayList<DataHolder> byteList=null;
    public static final int PREVIEW_REQUEST_CODE = 1000;
    CertificateRequest repoetRequest = new CertificateRequest();
    AnimationDrawable reportAnimation;
    AnimationDrawable plotAnimation;
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    private int imageIndex=0;
    private Activity mContext;
    public static Map<String,Bitmap> bitmapMap=new HashMap<>();
    public static final int REQUEST_CODE_PREVIEW = 101;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GiaZhengshuBinding giaZhengshuBinding = DataBindingUtil.inflate(inflater, R.layout.gia_zhengshu, container, false);
        mContext=getActivity();
        ButterKnife.bind(this, giaZhengshuBinding.getRoot());
        Bundle bundle = getArguments();
        giaCertificateResponse = bundle.getParcelable("giaRreport");
        giaZhengshuBinding.setGia(giaCertificateResponse);
        if (null == giaCertificateResponse.getReportpicLg()) {
            repoetRequest.setReportno(giaCertificateResponse.getReportno());
            repoetRequest.setType("GIA");
            loadingReport.setVisibility(View.VISIBLE);
            loadingPlot.setVisibility(View.VISIBLE);
            reportAnimation = (AnimationDrawable) loadingReport.getBackground();
            plotAnimation= (AnimationDrawable) loadingPlot.getBackground();
            reportAnimation.start();
            plotAnimation.start();
            query(repoetRequest);
        }
        Reportimage();
        return giaZhengshuBinding.getRoot();
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
                                loadingReport.setVisibility(View.GONE);
                                loadingPlot.setVisibility(View.GONE);
                                lzyResponse=null;
                                switch (lzyResponse.getStatus()){
                                    case 375:
                                        GiaPic giaPic = new GiaPic();
                                        giaPic=lzyResponse.getResult();
                                        if(giaPic !=null){
                                            final GiaPic finalGiaPic = giaPic;
                                            GlideApp.with(getContext()).asBitmap().load(giaPic.getReportpicSm()).into(new SimpleTarget<Bitmap>() {
                                                    @Override
                                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                        reportpic.setImageBitmap(resource);
                                                        reportImage.add(resource);
                                                        //拉取净度图
                                                        GlideApp.with(getContext()).asBitmap().load(finalGiaPic.getPlotSmPic()).into(new SimpleTarget<Bitmap>() {
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
                                        ToastUtil.show(mContext,"没有查询到图片");
                                        break;
                                }
                            }
                        }catch (Exception e){
                            ToastUtil.show(mContext,"没有查询到图片");
                        }
                    }
                    @Override
                    public void onError(Response<LzyJavaResponse<GiaPic>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        loadingReport.setVisibility(View.GONE);
                        loadingPlot.setVisibility(View.GONE);
                        ToastUtil.show(mContext,"查询出错,请重试");
                    }
                });
    }

    void Reportimage() {
        GlideApp.with(getActivity()).asBitmap().load(giaCertificateResponse.getReportpicSm()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                reportpic.setImageBitmap(resource);
                reportImage.add(resource);
                //拉取净度图
                GlideApp.with(mContext).asBitmap().load(giaCertificateResponse.getPlotSmPic()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        plotPic.setImageBitmap(resource);
                        reportImage.add(resource);
                    }
                });
            }
        });
    }

    @OnClick({R.id.reportpic, R.id.plotPic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reportpic:
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
            case R.id.plotPic:
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
