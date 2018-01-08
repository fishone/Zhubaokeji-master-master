package com.zhubaokeji.android.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.utils.ImageSaveUtils;
import com.zhubaokeji.android.view.CalculatorActivity;
import com.zhubaokeji.android.utils.GlideApp;
import com.zhubaokeji.android.view.GoldPriceActivity;
import com.zhubaokeji.android.view.OnlinepriceTableActivity;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.view.ZhengshuActivity;
import com.zhubaokeji.android.view.ZhubaoColorSearchActivity;
import com.zhubaokeji.android.view.ZhubaoLoginActivity;
import com.zhubaokeji.android.view.ZhubaoSearchActivity;
import com.zhubaokeji.android.view.ZhubaoUserInfoActivity;
import com.zhubaokeji.android.utils.GridLayout;
import com.zhubaokeji.library.ColorDialog;
import com.zhubaokeji.library.TitleBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * Created by Yuizhi on 2016/11/23.
 */

public class ZhubaoHomeFragment extends Fragment {
    GridLayout grid;
    @BindView(R.id.zhubao_zoomCenter)
    BGABanner zhubaoZoomCenter;
    private BGABanner mContentBanner;
    public static boolean zhubao_Login_boolean = false;
    private ImageView mCollectView;
    private Activity mContext;
    List<String> imgList;
    public String[] titles = {"白钻查询", "彩站查询", "证书查询", "计算器", "金价查询",
            "国际报价表","期待更多"};
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    public int[] imgs = {R.drawable.zhubao_home_icon1, R.drawable.zhubao_home_icon2, R.drawable.zhubao_home_icon5,
            R.drawable.zhubao_home_icon6, R.drawable.zhubao_home_icon7,
            R.drawable.zhubao_home_icon8, R.drawable.zhubao_home_icon10};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View settingLayout = inflater.inflate(R.layout.zhubao_home,
                container, false);
        mContext=getActivity();
        preferencesUtil = new SharedPreferencesUtil(getContext());
        /*设置标题*/
        boolean isImmersive = false;
        if (hasKitKat() && !hasLollipop()) {
            isImmersive = true;
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
        }

        final TitleBar titleBar = (TitleBar) settingLayout.findViewById(R.id.zhubao_home_title);

        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setTitle("助宝网");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_account_circle_white_36dp) {
            @Override
            public void performAction(View view) {
                 if (zhubao_Login_boolean) {
                    startActivity(new Intent(getContext(), ZhubaoUserInfoActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                } else {
                    startActivity(new Intent(getContext(), ZhubaoLoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
            }
        });
        ButterKnife.bind(this, settingLayout);
        return settingLayout;
    }


    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grid = (GridLayout) getActivity().findViewById(R.id.zhubao_list_item);
        grid.setGridAdapter(new GridLayout.GridAdatper() {

            @Override
            public View getView(int index) {
                @SuppressLint("RestrictedApi") LayoutInflater inflater = getLayoutInflater(savedInstanceState);
                View view = inflater.inflate(R.layout.home_item,null);
                ImageView iv = (ImageView) view.findViewById(R.id.home_image);
                TextView tv = (TextView) view.findViewById(R.id.home_title);
                iv.setImageResource(imgs[index]);
                tv.setText(titles[index]);
                return view;
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });
        grid.setOnItemClickListener(new GridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                NetUtil.isZhubaoQuery(getContext());
                switch (index) {
                    case 0:
                     if (zhubao_Login_boolean) {
                            startActivity(new Intent(v.getContext(), ZhubaoSearchActivity.class));
                            ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                            break;
                     }else {
                            alertDialog(v);
                            break;
                        }
                    case 1:
                       if (zhubao_Login_boolean) {
                            startActivity(new Intent(v.getContext(), ZhubaoColorSearchActivity.class));
                            ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                        } else {
                            alertDialog(v);
                        }
                        break;
                    case 2:
                        startActivity(new Intent(v.getContext(), ZhengshuActivity.class));
                        ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.in_left);
                        break;
                    case 3:
                        startActivity(new Intent(v.getContext(), CalculatorActivity.class));
                        ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.in_left);
                        break;
                    case 4:
                        startActivity(new Intent(v.getContext(), GoldPriceActivity.class));
                        ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.in_left);
                        break;
                    case 5:
                        startActivity(new Intent(v.getContext(), OnlinepriceTableActivity.class));
                        ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.in_left);
                        break;
//                    case 6:
//                            startActivity(new Intent(v.getContext(), GG.class));
//                            ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.in_left);
//                            break;
//                    case 7:
//                        if (zhubao_Login_boolean) {
//                            startActivity(new Intent(v.getContext(), ZhubaoOrderActivity.class));
//                            ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.in_left);
//                            break;
//                        }
//                        break;
                }
            }
        });

        mContentBanner = (BGABanner) getActivity().findViewById(R.id.zhubao_zoomCenter);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width=dm.widthPixels;
        int height=(int)(width/1.85f);
        ViewGroup.LayoutParams layoutParams=mContentBanner.getLayoutParams();
        layoutParams.height=height;
        layoutParams.width=width;
        mContentBanner.setLayoutParams(layoutParams);
        mContentBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, final ImageView itemView, final String model, int position) {
                GlideApp.with(getActivity())
                        .asBitmap()
                        .load(model)
                        .placeholder(R.drawable.zhubao_default)
                        .error(R.drawable.zhubao_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL )
                        .skipMemoryCache(true)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        //获取内存中的图片地址
        try {
            imgList =new ArrayList<String>();
            imgList=preferencesUtil.getZbImageAddress();
            if(imgList !=null && imgList.size()>0){
                mContentBanner.setData(imgList, Arrays.asList("", "", ""));
            }
        }catch (Exception e){
            Logger.e("获取图片地址",e);
        }
        String path = Urls.ZHUBAOURL + Urls.ZHUBAOIMAGE;
        OkGo.<String>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        final List<String> imageList = new ArrayList<String>();
                        final List<String> imgAddressList = new ArrayList<String>();
                        final List<String> tipsList = new ArrayList<String>();
                        try {
                            if (response.body() != null && !response.body().equals("")) {
                                JSONObject jsonObject = new JSONObject(response.body());
                                int status = jsonObject.getInt("status");
                                switch (status) {
                                    case 1:
                                        Gson gson = new Gson();
                                        JSONArray jsonArray = jsonObject.getJSONArray("msgdata");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            imageList.add(Urls.ZHUBAOHEADURL + String.valueOf(jsonArray.get(i)));
                                            final String imgUrl=imageList.get(i);
                                            final String imgName=imgUrl.split("/")[5].split("\\?")[0];
                                            String storePath = getContext().getFilesDir() + "/";
                                            imgAddressList.add(storePath+imgName);
                                            GlideApp.with(getActivity()).asBitmap().load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                                                    .into(new SimpleTarget<Bitmap>() {
                                                        @Override
                                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                            ImageSaveUtils.saveImageToGallery(mContext,resource,imgName);
                                                        }
                                                    });
                                        }
                                        preferencesUtil.saveZbImageAddress(imgAddressList);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "助宝图片滚动");
                        }
                        mContentBanner.setData(imageList, tipsList);
                    }
                });
    }



    void alertDialog(View view) {
        ColorDialog dialog = new ColorDialog(view.getContext());
        dialog.setTitle("提示");
        dialog.setAnimationEnable(true);
        dialog.setContentText("请您登陆或注册后使用");
//        dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
        dialog.setPositiveListener("登录/注册", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
                startActivity(new Intent(dialog.getContext(), ZhubaoLoginActivity.class));
                ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
            }
        })
                .setNegativeListener("取消", new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {

                        dialog.dismiss();
                    }
                }).show();
    }
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @OnClick(R.id.zhubao_zoomCenter)
    public void onClick() {
    }
}
