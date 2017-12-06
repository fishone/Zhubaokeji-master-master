package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.BasicSetting;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import com.lzy.okgo.model.Response;

import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by Yuizhi on 2016/11/28.
 */

public class ZhubaoSearchActivity extends BaseActivity {
    @BindView(R.id.carat_caratSection)
    Button caratCaratSection;
    @BindView(R.id.zhubao_caratStart)
    EditText zhubaoCaratStart;
    @BindView(R.id.zhubao_caratEnd)
    EditText zhubaoCaratEnd;
    @BindView(R.id.zhubao_search)
    LinearLayout zhubaoSearch;
    @BindView(R.id.zhubao_shape)
    TextView zhubaoShape;
    @BindView(R.id.zhubao_recycler_shape)
    RecyclerView zhubaoRecyclerShape;
    @BindView(R.id.zhubao_color)
    TextView zhubaoColor;
    @BindView(R.id.zhubao_recycler_color)
    RecyclerView zhubaoRecyclerColor;
    @BindView(R.id.zhubao_clarity)
    TextView zhubaoClarity;
    @BindView(R.id.zhubao_recycler_clarity)
    RecyclerView zhubaoRecyclerClarity;
    @BindView(R.id.zhubao_recycler_threeEx)
    RecyclerView zhubaoRecyclerThreeEx;
    @BindView(R.id.zhubao_recycler_cut)
    RecyclerView zhubaoRecyclerCut;
    @BindView(R.id.zhubao_recycler_polish)
    RecyclerView zhubaoRecyclerPolish;
    @BindView(R.id.zhubao_recycler_symmetry)
    RecyclerView zhubaoRecyclerSymmetry;
    @BindView(R.id.zhubao_recycler_fluore)
    RecyclerView zhubaoRecyclerFluore;
    List<String> shapeList;
    List<String> colorList;
    List<String> clarityList;
    List<String> threeExList;
    List<String> threeList;
    List<String> fluoreList;
    JpSearchRequest request;
    ArrayList<ZhubaoDiamondResponse> responseList = new ArrayList<ZhubaoDiamondResponse>();
    @BindView(R.id.btn_zhubao_gia)
    Button btnZhubaoGia;
    @BindView(R.id.btn_zhubao_igi)
    Button btnZhubaoIgi;
    @BindView(R.id.zb_search_rapoetNo)
    EditText zbSearchRapoetNo;
    @BindView(R.id.zb_search_discountPoint)
    EditText zbSearchDiscountPoint;
    @BindView(R.id.zb_search_rate)
    EditText zbSearchRate;
    @BindView(R.id.zb_search_query)
    Button zbSearchQuery;
    @BindView(R.id.zb_search_resetting)
    Button zbSearchResetting;
    PopupWindow mpopupWindow;
    View popupView;
    BasicSetting basicSetting = new BasicSetting();
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    Map<String, String> threeExMap = new HashMap<String, String>();
    List<String> caratList = new ArrayList<>();
    JpUserInfo jpUserInfo=new JpUserInfo();
    private Activity mContext;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhubao_search);
        ButterKnife.bind(this);
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.zhubao_search_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setTitle("助宝白钻查询");
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
            }
        });
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.addAction(new TitleBar.TextAction("查询") {
            @Override
            public void performAction(View view) {
                condition();
            }
        });
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        mContext = this;
        try {
            basicSetting = preferencesUtil.getBasicSetting();
            zbSearchRate.setText(basicSetting.getRate().toString());
            zbSearchDiscountPoint.setText(basicSetting.getDiscountPoint().toString());
            jpUserInfo = preferencesUtil.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnZhubaoGia.setSelected(true);
        initData();
        initView();
        TextChange textChange = new TextChange();
        zbSearchDiscountPoint.addTextChangedListener(textChange);
        zbSearchRate.addTextChangedListener(textChange);
        zhubaoCaratStart.addTextChangedListener(textChange);
        zhubaoCaratEnd.addTextChangedListener(textChange);
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
        zhubaoRecyclerShape.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        SearchAdapter mAdapter = new SearchAdapter(this, shapeList);
        zhubaoRecyclerShape.setAdapter(mAdapter);
        zhubaoShape.setText("圆形");
        request.setS_shape("圆形");
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                zhubaoShape.setText(stringBuffer.toString());
                if (StringUtil.isValidString(stringBuffer.toString())) {
                    request.setS_shape(stringBuffer.toString());
                } else {
                    request.setS_shape("");
                }
            }
        });

        //设置颜色布局管理器
        zhubaoRecyclerColor.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, colorList);
        zhubaoRecyclerColor.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                zhubaoColor.setText(stringBuffer.toString());
                request.setS_color(stringBuffer.toString());
            }
        });
        //设置净度布局管理器
        zhubaoRecyclerClarity.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, clarityList);
        zhubaoRecyclerClarity.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                zhubaoClarity.setText(stringBuffer.toString());
                request.setS_clarity(stringBuffer.toString());
            }
        });
        //设置三项布局管理器
        zhubaoRecyclerThreeEx.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeExList);
        zhubaoRecyclerThreeEx.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_threepara(stringBuffer.toString());
            }
        });
        //设置切工布局管理器
        zhubaoRecyclerCut.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        zhubaoRecyclerCut.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_cut(stringBuffer.toString());
            }
        });
        //设置抛光布局管理器
        zhubaoRecyclerPolish.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        zhubaoRecyclerPolish.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_polish(stringBuffer.toString());
            }
        });

        //设置对称布局管理器
        zhubaoRecyclerSymmetry.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        zhubaoRecyclerSymmetry.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_symmetry(stringBuffer.toString());
            }
        });
        //设置荧光布局管理器
        zhubaoRecyclerFluore.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, fluoreList);
        zhubaoRecyclerFluore.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                request.setS_fluorescence(stringBuffer.toString());
            }
        });

    }

    private void query(final JpSearchRequest request) {
        final String path = Urls.ZHUBAOURL+ Urls.ZHUBAOSEARCHURL +request.toJson();
        OkGo.<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>>(mContext) {
                    @Override
                    public void onSuccess(Response<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyListResponse<ArrayList<ZhubaoDiamondResponse>> lzyResponse=response.body();
                                String message =lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        LzyListResponse<ArrayList<ZhubaoDiamondResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                        responseList=GsonUtil.FromJson(serverModle.rows,ZhubaoDiamondResponse.class);
                                        if (responseList != null && responseList.size() !=0) {
                                            Intent result_intent = new Intent();
                                            result_intent.setClass(ZhubaoSearchActivity.this, ZhubaoResultActivity.class);
                                            result_intent.putParcelableArrayListExtra("list", responseList);
                                            Logger.d(responseList.size());
                                            result_intent.putExtra("request", request);
                                            startActivity(result_intent);
                                            overridePendingTransition(R.anim.in_left, R.anim.out_lef);
                                        }else {
                                            ToastUtil.show(mContext,"没有查询到钻石");
                                        }
                                        break;
                                    case -1:
                                        zhubao_Login_boolean = false;
                                        ToastUtil.show(mContext,"登录超时,请重新登录");
                                        startActivity(new Intent(mContext, ZhubaoLoginActivity.class));
                                        overridePendingTransition(R.anim.in_left, R.anim.in_left);
                                        break;
                                    default:
                                        ToastUtil.show(mContext,"查询失败,请重试");
                                        break;
                                }
                            }
                        }catch (Exception e){
                            ToastUtil.show(mContext,"查询失败,请重试");
                         }
                        }
                    @Override
                    public void onError(Response<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.zbException(mContext,response.getException());
                    }
                });
    }

    /**
     * 初始化数据
     */

    private void initData() {
        String [] shapeStr= new  String[]{"圆形","梨形","公主方","心形","马眼形","椭圆形","雷迪恩","祖母绿","垫形","三角形"};
        String[] colorStr=new String[]{"D","E","F","G","H","I","J","K","L","M","N"};
        String[] clarityStr=new String[]{"FL","IF","VVS1","VVS2","VS1","VS2","SI1","SI2","SI3","I1"};
        String[] threeExStr=new String[]{"3EX","3VG","3GD","3FR"};
        String[] threeStr=new String[]{"EX","VG","GD","FG"};
        String[] fluoreStr=new String[]{"N","F","M","S","VS"};
        String[] caratStr=new String[]{"0.30-0.32","0.40-0.42","0.50-0.52","0.60-0.62","0.70-0.72","0.80-0.82","1.00-1.03","1.50-1.59","2.00-2.09","2.50-2.59","3.00-3.45","3.50-3.99","4.00-4.99","5.00-30.00"};
        shapeList = Arrays.asList(shapeStr);
        colorList = Arrays.asList(colorStr);
        clarityList = Arrays.asList(clarityStr);
        threeExList = Arrays.asList(threeExStr);
        threeList = Arrays.asList(threeStr);
        fluoreList = Arrays.asList(fluoreStr);
        caratList =Arrays.asList(caratStr);
        threeExMap.put("3EX","4");
        threeExMap.put("3VG","3");
        threeExMap.put("3GD","2");
        threeExMap.put("3FR","1");

    }
    @OnTouch({R.id.zhubao_caratEnd, R.id.zhubao_caratStart,R.id.zb_search_discountPoint,
              R.id.zb_search_rate})
    public boolean onTouch(View view, MotionEvent event){
        editText = (EditText) view;
        return false;
    }

    @OnClick({R.id.carat_caratSection, R.id.bt_add, R.id.bt_subtract, R.id.btn_zhubao_gia, R.id.btn_zhubao_igi,R.id.zb_search_query, R.id.zb_search_resetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.carat_caratSection:
                popupView = LayoutInflater.from(ZhubaoSearchActivity.this).inflate(R.layout.caratsection_popupwindow, null);
                mpopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                mpopupWindow.setTouchable(true);
                mpopupWindow.setOutsideTouchable(true);
                mpopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

                caratCaratSection.setBackgroundResource(R.drawable.ic_expand_less_black_24dp);

                // 计算x轴方向的偏移量，使得PopupWindow在Title的正下方显示，此处的单位是pixels
                int xoffInPixels = this.getWindowManager().getDefaultDisplay().getWidth() / 2 - caratCaratSection.getWidth() / 2;
                // 将pixels转为dip
                int xoffInDip = ScreenTools.px2dip(this, xoffInPixels);
                mpopupWindow.showAsDropDown(caratCaratSection, -xoffInDip, 10);
                RecyclerView recyclerCarat = (RecyclerView) popupView.findViewById(R.id.recycler_caratSection);
                //设置布局管理器
                recyclerCarat.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                CaratSectionAdapter caratSectionAdapter = new CaratSectionAdapter(ZhubaoSearchActivity.this, caratList);
                recyclerCarat.setAdapter(caratSectionAdapter);
                caratSectionAdapter.setOnItemClickListener(new CaratSectionAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int tag, String data) {
                        String[] carat = data.split("-");
                        zhubaoCaratStart.setText(carat[0].toString());
                        zhubaoCaratEnd.setText(carat[1].toString());
                        caratCaratSection.setBackgroundResource(R.drawable.ic_expand_more_black_24dp);
                        mpopupWindow.dismiss();
                    }
                });
                break;
            case R.id.bt_add:
                BigDecimal discountPoint = new BigDecimal(zbSearchDiscountPoint.getText().toString());
                BigDecimal IntDiscountPoint = discountPoint.add(new BigDecimal(1.00));
                zbSearchDiscountPoint.setText(IntDiscountPoint.toString());
                break;
            case R.id.bt_subtract:
                discountPoint = new BigDecimal(zbSearchDiscountPoint.getText().toString());
                IntDiscountPoint = discountPoint.subtract(new BigDecimal(1.00));
                zbSearchDiscountPoint.setText(IntDiscountPoint.toString());
                break;
            case R.id.btn_zhubao_gia:
                if (btnZhubaoGia.isSelected()) {
                    btnZhubaoGia.setSelected(false);
                    request.setS_report("");
                    if(btnZhubaoIgi.isSelected()){
                        request.setS_report("IGI");
                    }
                } else {
                    btnZhubaoGia.setSelected(true);
                    request.setS_report("GIA");
                }
                break;
            case R.id.btn_zhubao_igi:
                if (btnZhubaoIgi.isSelected()) {
                    btnZhubaoIgi.setSelected(false);
                    request.setS_report("");
                    if(btnZhubaoGia.isSelected()){
                        request.setS_report("GIA");
                    }
                } else {
                    btnZhubaoIgi.setSelected(true);
                    request.setS_report("IGI");
                }
                break;
            case R.id.zb_search_query:
                condition();
                break;
            case R.id.zb_search_resetting:
                Intent refresh = new Intent(ZhubaoSearchActivity.this, ZhubaoSearchActivity.class);
                refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
        }
    }
    void condition(){
        if (btnZhubaoGia.isSelected()) {
            request.setS_report("GIA");
        }
        if (btnZhubaoIgi.isSelected() == true && btnZhubaoGia.isSelected() == true) {
            request.setS_report("GIA,IGI");
        }
        request.setS_qorderdir("asc");
        request.setS_qorder("salediscount");
        request.setS_page(1);
        request.setS_size(25);
        request.setS_reportno(zbSearchRapoetNo.getText().toString());
        request.setRate(zbSearchRate.getText().toString());
        request.setDiscountPoint(zbSearchDiscountPoint.getText().toString());
        request.setToken(jpUserInfo.getToken());
        request.setS_carat1(zhubaoCaratStart.getText().toString());
        request.setS_carat2(zhubaoCaratEnd.getText().toString());
        query(request);
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
        }
    }
}
