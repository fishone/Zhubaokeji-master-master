package com.zhubaokeji.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.utils.ImageSaveUtils;
import com.zhubaokeji.android.view.JpBulletinActivity;
import com.zhubaokeji.android.view.JpLoginActivity;
import com.zhubaokeji.android.view.JpOrderActivity;
import com.zhubaokeji.android.view.JpSearchActivity;
import com.zhubaokeji.android.view.JpShoppingCartActivity;
import com.zhubaokeji.android.view.JpUserInfoActivity;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.Item;
import com.zhubaokeji.android.bean.JpShoppingCartResponse;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.adapter.HomeLatticeAdapter;
import com.zhubaokeji.android.utils.DividerItemDecoration;
import com.zhubaokeji.android.utils.GridLayout;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.library.ColorDialog;
import com.zhubaokeji.library.TitleBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

import static com.zhubaokeji.android.view.JpShoppingCartActivity.responseList;
/**
 * Created by Yuizhi on 2016/11/23.
 */
public class JpHomeFragment extends Fragment {

    GridLayout grid;
    @BindView(R.id.jp_recycler_home)
    RecyclerView jpRecyclerHome;
    @BindView(R.id.jp_textRoll_home)
    TextView jpTextRollHome;
    private ImageView mCollectView;
    private BGABanner mContentBanner;
    private HomeLatticeAdapter homeLatticeAdapter;
    private List<Item> viewData = new ArrayList<Item>();
    public static boolean jp_Login_Boolean = false;
    public static String R0llTEXT;
    List<String> imgList;
    private  Activity mContext;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    public static ArrayList<JpShoppingCartResponse> jpShoppingCartResponses = new ArrayList<JpShoppingCartResponse>();
    JpUserInfo jpUserInfo;
    public String[] titles = {"白钻查询", "彩站查询", "钻石订单", "客户须知"};
    public int[] imgs = {R.drawable.jp_homepage_icon1, R.drawable.jp_homepage_icon2, R.drawable.jp_homepage_icon3,
            R.drawable.jp_homepage_icon4};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jp_home, container, false);
        mContext=getActivity();
        ButterKnife.bind(this, view);
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

        final TitleBar titleBar = (TitleBar) view.findViewById(R.id.jp_home_title);

        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setTitle("Jewelry Paradise");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_account_circle_white_36dp) {
            @Override
            public void performAction(View view) {
                if (jp_Login_Boolean) {
                    startActivity(new Intent(getContext(), JpUserInfoActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                } else {
                    startActivity(new Intent(getContext(), JpLoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
            }
        });
        preferencesUtil = new SharedPreferencesUtil(getContext());
        try {
            jpUserInfo = preferencesUtil.getJpInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rollTextl();
        return view;
    }

    private void loadDatas() {
        viewData = new ArrayList<>();
        viewData.add(new Item("白钻查询", R.drawable.jp_homepage_icon1, "White Diamond"));
        viewData.add(new Item("订单查询", R.drawable.jp_homepage_icon3, "Diamond Order"));
        viewData.add(new Item("购物车", R.drawable.jp_homepage_icon2, "Shopping Cart"));
        viewData.add(new Item("客户须知", R.drawable.jp_homepage_icon4, "Customer Notice"));
    }

    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentBanner = (BGABanner) getActivity().findViewById(R.id.jp_zoomCenter);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = (int) (width / 1.5f);
        ViewGroup.LayoutParams layoutParams = mContentBanner.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        mContentBanner.setLayoutParams(layoutParams);
        mContentBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .placeholder(R.drawable.jp_default)
                        .error(R.drawable.jp_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        //获取内存中的图片地址
        try {
            imgList =new ArrayList<String>();
            imgList=preferencesUtil.getJpImageAddress();
            if(imgList !=null && imgList.size()>0){
                mContentBanner.setData(imgList, Arrays.asList("", "", ""));
            }
        }catch (Exception e){
            Logger.e("获取图片地址",e);
        }
        loadDatas();
        initViews();
        initImage();
    }

    private void initImage() {
        String path = Urls.URL + Urls.JPIMAGEROLL;
        OkGo.<String>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<String> imageList = new ArrayList<String>();
                        List<String> tipsList = new ArrayList<String>();
                        final List<String> imgAddressList = new ArrayList<String>();
                        try {
                            if (response.body() != null && !response.body().equals("")) {
                                JSONObject jsonObject = new JSONObject(response.body());
                                int status = jsonObject.getInt("status");
                                switch (status) {
                                    case 1:
                                        JSONArray jsonArray = jsonObject.getJSONArray("msgdata");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            imageList.add(Urls.JPHEADURL + String.valueOf(jsonArray.get(i)));
                                            final String imgUrl=imageList.get(i);
                                            final String imgName=imgUrl.split("/")[5].split("\\?")[0];
                                            String storePath = getContext().getFilesDir() + "/";
                                            imgAddressList.add(storePath+imgName);
                                            Glide.with(getActivity()).load(imgUrl).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true)
                                                    .into(new SimpleTarget<Bitmap>() {
                                                        @Override
                                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                            ImageSaveUtils.saveImageToGallery(mContext,resource,imgName);
                                                        }
                                                    });
                                        }
                                        preferencesUtil.saveJpImageAddress(imgAddressList);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "jp图片滚动");
                        }
                        mContentBanner.setData(imageList, tipsList);
                    }
                });

        if(jp_Login_Boolean){
            final String ShoppingCartPath = Urls.URL + Urls.JPSHOPPINGCART + "&token=" + jpUserInfo.getToken();
            OkGo.<LzyListResponse<ArrayList<JpShoppingCartResponse>>>get(ShoppingCartPath)     // 请求方式和请求url
                    .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                    .execute(new JsonCallback<LzyListResponse<ArrayList<JpShoppingCartResponse>>>() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<LzyListResponse<ArrayList<JpShoppingCartResponse>>> response) {
                            try {
                                if (null != response && null !=response.body()) {
                                    LzyListResponse<ArrayList<JpShoppingCartResponse>> lzyResponse=response.body();
                                    switch (lzyResponse.status) {
                                        case 1:
                                            LzyListResponse<ArrayList<JpShoppingCartResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                            jpShoppingCartResponses=GsonUtil.FromJson(serverModle.rows,JpShoppingCartResponse.class);
                                            if (jpShoppingCartResponses != null) {
                                                homeLatticeAdapter.setBadg(jpShoppingCartResponses.size());
                                                homeLatticeAdapter.notifyDataSetChanged();
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            } catch (Exception e) {
                                Logger.e(e, "JP购物车查询");
                            }
                        }
                    });
        }
    }

    /**
     * 相片按相框的比例动态缩放
     * @param context
     * @param width 模板宽度
     * @param height 模板高度
     * @return
     */
    public static Bitmap upImageSize(Context context, Bitmap bmp, int width, int height) {
        if(bmp==null){
            return null;
        }
        // 计算比例
        float scaleX = (float)width / bmp.getWidth();// 宽的比例
        float scaleY = (float)height / bmp.getHeight();// 高的比例
        //新的宽高
        int newW = 0;
        int newH = 0;
        if(scaleX > scaleY){
            newW = (int) (bmp.getWidth() * scaleX);
            newH = (int) (bmp.getHeight() * scaleX);
        }else if(scaleX <= scaleY){
            newW = (int) (bmp.getWidth() * scaleY);
            newH = (int) (bmp.getHeight() * scaleY);
        }
        return Bitmap.createScaledBitmap(bmp, newW, newH, true);
    }

    private void initViews() {
        // 设置布局显示方式，这里我使用都是垂直方式——LinearLayoutManager.VERTICAL
        jpRecyclerHome.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：800p）
        int width = (screenWidth) / 2;
        // 初始化適配器
        homeLatticeAdapter = new HomeLatticeAdapter(getActivity(), viewData, width);
        //分割线
        jpRecyclerHome.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL_LIST));
        jpRecyclerHome.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        // 设置适配器
        jpRecyclerHome.setAdapter(homeLatticeAdapter);
        homeLatticeAdapter.setOnItemClickListener(new HomeLatticeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag) {
                NetUtil.isJpQuery(getContext());
                switch (tag) {
                    case 0:
                         if (jp_Login_Boolean) {
                            startActivity(new Intent(view.getContext(), JpSearchActivity.class));
                            ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                            break;
                        } else {
                            alertDialog(view);
                        }
                        break;
                    case 1:
                        if (jp_Login_Boolean) {
                            startActivity(new Intent(view.getContext(), JpOrderActivity.class));
                            ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                            break;
                        } else {
                            alertDialog(view);
                        }
                        break;
                    case 2:
                        if (jp_Login_Boolean) {
                            Intent result_intent = new Intent();
                            result_intent.setClass(view.getContext(), JpShoppingCartActivity.class);
                            startActivityForResult(result_intent, 2);
                            ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                            break;
                        } else {
                            alertDialog(view);
                        }
                        break;
                    case 3:
                        startActivity(new Intent(view.getContext(), JpBulletinActivity.class));
                        ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                        break;
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == responseList.size()) {
            if (requestCode == 2) {
                int three = data.getIntExtra("three", 0);
                homeLatticeAdapter.setBadg(resultCode);
                homeLatticeAdapter.notifyDataSetChanged();
            }
        }
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
                startActivity(new Intent(dialog.getContext(), JpLoginActivity.class));
                ((Activity) getContext()).overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                dialog.dismiss();
            }
        })
                .setNegativeListener("取消", new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    //    public void onViewCreated(View view, final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        grid = (GridLayout) getActivity().findViewById(R.id.home);
//
//    }
    void rollTextl() {
        String path = "http://img.zuanshiku.net/jpapp/scrollInfo.txt";
        OkGo.<String>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        R0llTEXT=response.body().toString();
                        jpTextRollHome.setText(response.body().toString());
                    }
                });
    }


    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
