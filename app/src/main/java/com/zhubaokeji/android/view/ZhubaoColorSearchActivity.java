package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.BasicSetting;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.ZhubaoColorResponse;
import com.zhubaokeji.android.adapter.CaratSectionAdapter;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.adapter.SearchAdapter;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.GsonUtil;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ScreenTools;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.StringUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.lzy.okgo.model.Response;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by fisho on 2017/3/24.
 */

public class ZhubaoColorSearchActivity extends BaseActivity {
    ArrayList<String> shapeList;
    ArrayList<String> colorList;
    ArrayList<String> clarityList;
    ArrayList<String> intensityList;
    ArrayList<String> glossList;
    ArrayList<String> threeList;
    ArrayList<String> fluoreList;
    ArrayList<String> percentageList;
    JpSearchRequest request;
    JpUserInfo userinfo;
    PopupWindow mpopupWindow;
    View popupView;
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    ArrayList<ZhubaoColorResponse> colorResponsesList;
    Map<String, String> shapeMap = new HashMap<String, String>();
    ArrayList<String> caratData = new ArrayList<>();
    BasicSetting basicSetting = new BasicSetting();
    @BindView(R.id.color_search_query)
    Button colorSearchQuery;
    @BindView(R.id.color_search_resetting)
    Button colorSearchResetting;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    @BindView(R.id.search_color_rapoetNo)
    EditText searchColorRapoetNo;
    @BindView(R.id.search_color_percentage)
    EditText searchColorPercentage;
    @BindView(R.id.search_color_percentageSection)
    Button searchColorPercentageSection;
    @BindView(R.id.search_color_rate)
    EditText searchColorRate;
    @BindView(R.id.search_color_caratStart)
    EditText searchColorCaratStart;
    @BindView(R.id.search_color_caratEnd)
    EditText searchColorCaratEnd;
    @BindView(R.id.search_color_caratSection)
    Button searchColorCaratSection;
    @BindView(R.id.search_color_gia)
    Button searchColorGia;
    @BindView(R.id.search_color_igi)
    Button searchColorIgi;
    @BindView(R.id.color_text_intensity)
    TextView colorTextIntensity;
    @BindView(R.id.color_recycler_intensity)
    RecyclerView colorRecyclerIntensity;
    @BindView(R.id.color_text_gloss)
    TextView colorTextGloss;
    @BindView(R.id.color_recycler_gloss)
    RecyclerView colorRecyclerGloss;
    @BindView(R.id.color_text_shape)
    TextView colorTextShape;
    @BindView(R.id.color_recycler_shape)
    RecyclerView colorRecyclerShape;
    @BindView(R.id.color_text_color)
    TextView colorTextColor;
    @BindView(R.id.color_recycler_color)
    RecyclerView colorRecyclerColor;
    @BindView(R.id.color_text_clarity)
    TextView colorTextClarity;
    @BindView(R.id.color_recycler_clarity)
    RecyclerView colorRecyclerClarity;
    @BindView(R.id.color_text_polish)
    TextView colorTextPolish;
    @BindView(R.id.color_recycler_polish)
    RecyclerView colorRecyclerPolish;
    @BindView(R.id.color_text_symmetry)
    TextView colorTextSymmetry;
    @BindView(R.id.color_recycler_symmetry)
    RecyclerView colorRecyclerSymmetry;
    @BindView(R.id.color_text_fluorescence)
    TextView colorTextFluorescence;
    @BindView(R.id.color_recycler_fluorescence)
    RecyclerView colorRecyclerFluorescence;
    private Activity mContext;
    EditText clickEdit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhubao_color_search);
        ButterKnife.bind(this);
        mContext=this;
        //获取存储在本地的参数
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            userinfo = new JpUserInfo();
            userinfo = preferencesUtil.getInfo();
            basicSetting = preferencesUtil.getBasicSetting();
            searchColorRate.setText(basicSetting.getRate().toString());
            searchColorPercentage.setText(basicSetting.getPercentage().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_color_search_title);

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

        titleBar.setTitle("助宝彩钻查询");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);

        titleBar.setActionTextColor(Color.WHITE);
        titleBar.addAction(new TitleBar.TextAction("查询") {
            @Override
            public void performAction(View view) {
                condition();
            }
        });

        searchColorGia.setSelected(true);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if(netMobile ==-1){
            zhubao_Login_boolean=false;
            startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
            mContext.overridePendingTransition(R.anim.in_left, R.anim.in_left);
        }
    }
    //初始化适配器
    private void initView() {
        request = new JpSearchRequest();
        //设置形状布局管理器
        colorRecyclerShape.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        SearchAdapter mAdapter = new SearchAdapter(this, shapeList);
        colorRecyclerShape.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                colorTextShape.setText(stringBuffer.toString());
                if (StringUtil.isValidString(stringBuffer.toString())) {
                    request.setS_shape(stringBuffer.toString());
                } else {
                    request.setS_shape("");
                }
            }
        });
        //设置颜色布局管理器
        colorRecyclerColor.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, colorList);
        colorRecyclerColor.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                colorTextColor.setText(stringBuffer.toString());
                request.setS_color(stringBuffer.toString());
            }
        });
        //设置净度布局管理器
        colorRecyclerClarity.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, clarityList);
        colorRecyclerClarity.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                colorTextClarity.setText(stringBuffer.toString());
                request.setS_clarity(stringBuffer.toString());
            }
        });
        //设置强度布局管理器
        colorRecyclerIntensity.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, intensityList);
        colorRecyclerIntensity.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                colorTextIntensity.setText(stringBuffer.toString());
                request.setS_intensity(stringBuffer.toString());
            }
        });
        //设置光彩布局管理器
        colorRecyclerGloss.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, glossList);
        colorRecyclerGloss.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                colorTextGloss.setText(stringBuffer.toString());
                request.setS_gloss(stringBuffer.toString());
            }
        });
        //设置抛光布局管理器
        colorRecyclerPolish.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        colorRecyclerPolish.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_polish(stringBuffer.toString());
            }
        });

        //设置对称布局管理器
        colorRecyclerSymmetry.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        colorRecyclerSymmetry.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_symmetry(stringBuffer.toString());
            }
        });
        //设置荧光布局管理器
        colorRecyclerFluorescence.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, fluoreList);
        colorRecyclerFluorescence.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_fluorescence(stringBuffer.toString());
            }
        });

    }

    private void query(final JpSearchRequest request) {
        if(NetUtil.isZhubaoQuery(mContext) ==false){
            startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
            mContext.overridePendingTransition(R.anim.in_left, R.anim.in_left);
            return;
        }
        final String path = Urls.ZHUBAOURL+ Urls.ZHUBAOSEARCHURL +request.toJson();
        OkGo.<LzyListResponse<ArrayList<ZhubaoColorResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                                                  // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<ZhubaoColorResponse>>>(mContext) {
                    @Override
                    public void onSuccess(Response<LzyListResponse<ArrayList<ZhubaoColorResponse>>> response) {
                        try {
                            if (null != response && null != response.body()) {
                                LzyListResponse<ArrayList<ZhubaoColorResponse>> lzyListResponse =response.body();
                                String message = lzyListResponse.message;
                                switch (lzyListResponse.status) {
                                    case 1:
                                        LzyListResponse<ArrayList<ZhubaoColorResponse>>.ServerModle serverModle= lzyListResponse.getMsgdata();
                                        colorResponsesList=GsonUtil.FromJson(serverModle.rows,ZhubaoColorResponse.class);
                                        if (colorResponsesList != null) {
                                            Intent result_intent = new Intent();
                                            result_intent.setClass(ZhubaoColorSearchActivity.this, ZhubaoColorResultActivity.class);
                                            result_intent.putParcelableArrayListExtra("list", colorResponsesList);
                                            result_intent.putExtra("request", request);
                                            startActivity(result_intent);
                                            overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                                        }
                                        break;
                                    case -1:
                                        zhubao_Login_boolean = false;
                                        ToastUtil.show(getApplicationContext(),"登录超时,请重新登录");
                                        startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
                                        mContext.overridePendingTransition(R.anim.in_left, R.anim.in_left);
                                        break;
                                    default:
                                        ToastUtil.show( mContext,"查询失败,请重试");
                                        break;
                                }
                            }
                        }catch (Exception e){
                            ToastUtil.show(mContext,"查询失败,请重试");
                        }
                    }
                    @Override
                    public void onError(Response<LzyListResponse<ArrayList<ZhubaoColorResponse>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.zbException(mContext,response.getException());
                    }
                });
    }
    /**
     * 初始化数据
     */

    private void initData() {
        shapeList = new ArrayList<>();
        shapeList.add("垫形");
        shapeList.add("雷迪恩");
        shapeList.add("公主方");
        shapeList.add("梨形");
        shapeList.add("心形");
        shapeList.add("椭圆形");
        shapeList.add("祖母绿");
        shapeList.add("马眼形");
        shapeList.add("圆形");
        shapeList.add("三角形");
        colorList = new ArrayList<>();
        colorList.add("Yellow");
        colorList.add("Pink");
        colorList.add("Red");
        colorList.add("Blue");
        colorList.add("Green");
        colorList.add("Orange");
        colorList.add("Purple");
        colorList.add("Black");
        colorList.add("Brown");
        colorList.add("Others");
        clarityList = new ArrayList<>();
        clarityList.add("FL");
        clarityList.add("IF");
        clarityList.add("VVS1");
        clarityList.add("VVS2");
        clarityList.add("VS1");
        clarityList.add("VS2");
        clarityList.add("SI1");
        clarityList.add("SI2");
        clarityList.add("SI3");
        clarityList.add("I1");
        intensityList = new ArrayList<>();
        intensityList.add("Faint");
        intensityList.add("Very Light");
        intensityList.add("Light");
        intensityList.add("Fancy Light");
        intensityList.add("Fancy");
        intensityList.add("Fancy Dark");
        intensityList.add("Fancy Intense");
        intensityList.add("Fancy Vivid");
        intensityList.add("Fancy Deep");
        glossList = new ArrayList<>();
        glossList.add("None");
        glossList.add("Yellow");
        glossList.add("Yellowish");
        glossList.add("Brown");
        glossList.add("Brownish");
        glossList.add("Green");
        glossList.add("Greenish");
        glossList.add("Pink");
        glossList.add("Pinkish");
        glossList.add("Purple");
        glossList.add("Purplish");
        glossList.add("Orange");
        glossList.add("Orangey");
        glossList.add("Gray");
        glossList.add("Grayish");
        glossList.add("Others");
        threeList = new ArrayList<>();
        threeList.add("EX");
        threeList.add("VG");
        threeList.add("GD");
        threeList.add("FG");
        fluoreList = new ArrayList<>();
        fluoreList.add("N");
        fluoreList.add("F");
        fluoreList.add("M");
        fluoreList.add("S");
        fluoreList.add("VS");
        caratData = new ArrayList<>();
        caratData.add("0.30-0.32");
        caratData.add("0.40-0.42");
        caratData.add("0.50-0.52");
        caratData.add("0.60-0.62");
        caratData.add("0.70-0.72");
        caratData.add("0.80-0.82");
        caratData.add("1.00-1.03");
        caratData.add("1.50-1.59");
        caratData.add("2.00-2.09");
        caratData.add("2.50-2.59");
        caratData.add("3.00-3.45");
        caratData.add("3.50-3.99");
        caratData.add("4.00-4.99");
        caratData.add("5.00-30.00");
        percentageList = new ArrayList<>();
        percentageList.add("1.05");
        percentageList.add("1.10");
        percentageList.add("1.15");
        percentageList.add("1.20");
        percentageList.add("1.25");
        percentageList.add("1.30");
        percentageList.add("1.40");
        percentageList.add("1.50");
        percentageList.add("1.60");
        percentageList.add("1.70");
        percentageList.add("1.80");
        percentageList.add("1.90");
        percentageList.add("2.00");
        percentageList.add("2.50");
        percentageList.add("3.00");
        percentageList.add("3.50");
        percentageList.add("4.00");
        percentageList.add("4.50");
        percentageList.add("5.00");

        shapeMap.put("圆形", "round");
        shapeMap.put("other", "other");
        shapeMap.put("梨形", "pear");
        shapeMap.put("公主方", "princess");
        shapeMap.put("心形", "heart");
        shapeMap.put("马眼形", "marquise");
        shapeMap.put("椭圆形", "oval");
        shapeMap.put("雷迪恩", "radiant");
        shapeMap.put("祖母绿", "emerald");
        shapeMap.put("垫形", "cushion");
    }

    void  condition(){
        if (searchColorGia.isSelected()) {
            request.setS_report("GIA");
        }
        if (searchColorGia.isSelected() == true && searchColorIgi.isSelected() == true) {
            request.setS_report("GIA,IGI");
        }
        request.setS_reportno(searchColorRapoetNo.getText().toString());
        request.setPercentage(searchColorPercentage.getText().toString());
        request.setRate(searchColorRate.getText().toString());
        request.setS_carat1(searchColorCaratStart.getText().toString());
        request.setS_carat2(searchColorCaratEnd.getText().toString());
        request.setS_protype("1");
        request.setS_page(1);
        request.setS_size(25);
        request.setToken(userinfo.getToken());
        request.setS_qorderdir("asc");
        request.setS_qorder("saledollorctprice");
        query(request);
    }
    @OnTouch({R.id.search_color_percentage, R.id.search_color_rate, R.id.search_color_caratStart, R.id.search_color_caratEnd})
    public boolean onTouch(View view, MotionEvent event){
        clickEdit= (EditText) view;
        return false;
    }
    @OnTextChanged({R.id.search_color_percentage, R.id.search_color_rate, R.id.search_color_caratStart, R.id.search_color_caratEnd})
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

    @OnTextChanged({R.id.search_color_percentage, R.id.search_color_rate, R.id.search_color_caratStart, R.id.search_color_caratEnd})
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("输入前的状态", "i:" + s + "+" + start + "+" + count + "+" + after);
    }

    @OnTextChanged({R.id.search_color_percentage, R.id.search_color_rate, R.id.search_color_caratStart, R.id.search_color_caratEnd})
    public void afterTextChanged(Editable s) {
    }

    @OnClick({R.id.search_color_percentageSection, R.id.search_color_caratSection, R.id.search_color_gia, R.id.search_color_igi,R.id.color_search_query, R.id.color_search_resetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_color_percentageSection:
                popupView = LayoutInflater.from(ZhubaoColorSearchActivity.this).inflate(R.layout.caratsection_popupwindow, null);
                mpopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                mpopupWindow.setTouchable(true);
                mpopupWindow.setOutsideTouchable(true);
                mpopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

                searchColorPercentageSection.setBackgroundResource(R.drawable.ic_expand_less_black_24dp);
                // 计算x轴方向的偏移量，使得PopupWindow在Title的正下方显示，此处的单位是pixels
                int xoffInPixels = this.getWindowManager().getDefaultDisplay().getWidth() / 2 - searchColorPercentageSection.getWidth() / 2;
                // 将pixels转为dip
                int xoffInDip = ScreenTools.px2dip(this, xoffInPixels);
                mpopupWindow.showAsDropDown(searchColorPercentageSection, -xoffInDip, 10);
                RecyclerView recyclerCarat = (RecyclerView) popupView.findViewById(R.id.recycler_caratSection);
                //设置布局管理器
                recyclerCarat.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
                CaratSectionAdapter caratSectionAdapter = new CaratSectionAdapter(ZhubaoColorSearchActivity.this, percentageList);
                recyclerCarat.setAdapter(caratSectionAdapter);
                caratSectionAdapter.setOnItemClickListener(new CaratSectionAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int tag, String data) {
                        searchColorPercentage.setText(data);
                        searchColorPercentageSection.setBackgroundResource(R.drawable.ic_expand_more_black_24dp);
                        mpopupWindow.dismiss();
                    }
                });
                break;
            case R.id.search_color_caratSection:
                popupView = LayoutInflater.from(ZhubaoColorSearchActivity.this).inflate(R.layout.caratsection_popupwindow, null);
                mpopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                mpopupWindow.setTouchable(true);
                mpopupWindow.setOutsideTouchable(true);
                mpopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

                searchColorCaratSection.setBackgroundResource(R.drawable.ic_expand_less_black_24dp);
                mpopupWindow.showAsDropDown(searchColorCaratSection, 10, 10);
                recyclerCarat = (RecyclerView) popupView.findViewById(R.id.recycler_caratSection);
                //设置布局管理器
                recyclerCarat.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                caratSectionAdapter = new CaratSectionAdapter(ZhubaoColorSearchActivity.this, caratData);
                recyclerCarat.setAdapter(caratSectionAdapter);
                caratSectionAdapter.setOnItemClickListener(new CaratSectionAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int tag, String data) {
                        String[] carat = data.split("-");
                        searchColorCaratStart.setText(carat[0].toString());
                        searchColorCaratEnd.setText(carat[1].toString());
                        searchColorCaratSection.setBackgroundResource(R.drawable.ic_expand_more_black_24dp);
                        mpopupWindow.dismiss();
                    }
                });
                break;
            case R.id.search_color_gia:
                if (searchColorGia.isSelected()) {
                    searchColorGia.setSelected(false);
                    request.setS_report("");
                    if(searchColorIgi.isSelected()){
                        request.setS_report("IGI");
                    }
                } else {
                    searchColorGia.setSelected(true);
                    request.setS_report("GIA");
                }
                break;
            case R.id.search_color_igi:
                if (searchColorIgi.isSelected()) {
                    searchColorIgi.setSelected(false);
                    request.setS_report("");
                    if(searchColorGia.isSelected()){
                        request.setS_report("GIA");
                    }
                } else {
                    searchColorIgi.setSelected(true);
                    request.setS_report("IGI");
                }
                break;
            case R.id.color_search_query:
                condition();
                break;
            case R.id.color_search_resetting:
                Intent refresh = new Intent(ZhubaoColorSearchActivity.this, ZhubaoColorSearchActivity.class);
                refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
        }
    }
}
