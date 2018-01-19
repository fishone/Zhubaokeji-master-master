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
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.adapter.CaratSectionAdapter;
import com.zhubaokeji.android.bean.LzyListResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.utils.FlagUtil;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

import com.lzy.okgo.model.Response;
import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;

/**
 * Created by Yuizhi on 2017/1/18.
 */

public class JpSearchActivity extends BaseActivity {
    @BindView(R.id.jp_recycler_shape)
    RecyclerView jpRecyclerShape;
    @BindView(R.id.jp_recycler_color)
    RecyclerView jpRecyclerColor;
    @BindView(R.id.jp_recycler_clarity)
    RecyclerView jpRecyclerClarity;
    @BindView(R.id.jp_recycler_threeEx)
    RecyclerView jpRecyclerThreeEx;
    @BindView(R.id.jp_recycler_cut)
    RecyclerView jpRecyclerCut;
    @BindView(R.id.jp_recycler_polish)
    RecyclerView jpRecyclerPolish;
    @BindView(R.id.jp_recycler_symmetry)
    RecyclerView jpRecyclerSymmetry;
    @BindView(R.id.jp_recycler_fluore)
    RecyclerView jpRecyclerFluore;
    @BindView(R.id.jp_search_repoetNo)
    EditText jpSearchRepoetNo;
    @BindView(R.id.jp_search_caratStart)
    EditText jpSearchCaratStart;
    @BindView(R.id.jp_search_caratEnd)
    EditText jpSearchCaratEnd;
    @BindView(R.id.jp_search_caratSection)
    Button jpSearchCaratSection;
    @BindView(R.id.jp_search_hk)
    Button jpSearchHk;
    @BindView(R.id.jp_search_ind)
    Button jpSearchInd;
    ArrayList<String> shapeList;
    ArrayList<String> colorList;
    ArrayList<String> clarityList;
    ArrayList<String> threeExList;
    ArrayList<String> threeList;
    ArrayList<String> fluoreList;
    ArrayList<String> milkyList;
    ArrayList<String> colshList;
    ArrayList<String> greenList;
    @BindView(R.id.jp_shape)
    TextView jpShape;
    Map<String, String> shapeMap = new HashMap<String, String>();
    Map<String, String> threeExMap = new HashMap<String, String>();
    ArrayList<String> caratData = new ArrayList<>();
    @BindView(R.id.jp_color)
    TextView jpColor;
    @BindView(R.id.jp_clarity)
    TextView jpClarity;
    PopupWindow mpopupWindow;
    View popupView;
    JpSearchRequest jpSearchRequest;
    JpUserInfo jpUserInfo;
    @BindView(R.id.jp_search_query)
    Button jpSearchQuery;
    @BindView(R.id.jp_search_resetting)
    Button jpSearchResetting;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    ArrayList<ZhubaoDiamondResponse> responseList = new ArrayList<ZhubaoDiamondResponse>();
    protected static final int SUCCESS = 1;
    protected static final int ERROR = -1;
    private Activity mContext;
    EditText clickEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_search);
        ButterKnife.bind(this);
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_search_title);
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

        titleBar.setTitle("JP白钻查询");
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
        mContext=this;
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            jpUserInfo = preferencesUtil.getJpInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData();
        initView();
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

    private void query(final JpSearchRequest jpSearchRequest) {
        final String path = Urls.URL + Urls.JPSEARCHURL + jpSearchRequest.toJson();
        OkGo.<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                                                     // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>>(mContext) {
                    @Override
                    public void onSuccess(Response<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>> response) {
                                try {
                                    if (null != response && null !=response.body()) {
                                        LzyListResponse<ArrayList<ZhubaoDiamondResponse>> lzyResponse=response.body();
                                        String message = lzyResponse.message;
                                        switch (lzyResponse.status) {
                                            case 1:
                                                LzyListResponse<ArrayList<ZhubaoDiamondResponse>>.ServerModle serverModle=lzyResponse.getMsgdata();
                                                responseList=GsonUtil.FromJson(serverModle.rows,ZhubaoDiamondResponse.class);
                                                if (responseList != null) {
                                                    Intent result_intent = new Intent();
                                                    result_intent.setClass(JpSearchActivity.this, JpResultActivity.class);
                                                    result_intent.putParcelableArrayListExtra("list", responseList);
                                                    Logger.d(responseList.size());
                                                    result_intent.putExtra("request", jpSearchRequest);
                                                    startActivity(result_intent);
                                                }
                                                break;
                                            case -404: case 404:
                                                jp_Login_Boolean = false;
                                                ToastUtil.show(mContext,"登录超时,请重新登录");
                                                Intent login_intent = new Intent();
                                                login_intent.setClass(JpSearchActivity.this, JpLoginActivity.class);
                                                startActivity(login_intent);
                                                break;
                                            default:
                                                ToastUtil.show(mContext,message);
                                                break;
                                        }
                                    } else {
                                        ToastUtil.show(mContext,"查询出错,请重试");
                                    }
                                } catch (Exception e) {
                                    ToastUtil.show(mContext,"查询出错,请重试");
                                    Logger.e(e, "message");
                                }
                            }
                    @Override
                    public void onError(Response<LzyListResponse<ArrayList<ZhubaoDiamondResponse>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                    }
                });
    }
    private void initData() {
        shapeList = new ArrayList<>();
        shapeList.add("圆形");
        shapeList.add("梨形");
        shapeList.add("公主方");
        shapeList.add("心形");
        shapeList.add("马眼形");
        shapeList.add("椭圆形");
        shapeList.add("雷迪恩");
        shapeList.add("祖母绿");
        shapeList.add("垫形");
        shapeList.add("三角形");
        colorList = new ArrayList<>();
        colorList.add("D");
        colorList.add("E");
        colorList.add("F");
        colorList.add("G");
        colorList.add("H");
        colorList.add("I");
        colorList.add("J");
        colorList.add("K");
        colorList.add("L");
        colorList.add("M");
        colorList.add("N");
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
        threeExList = new ArrayList<>();
        threeExList.add("3EX");
        threeExList.add("3VG");
        threeExList.add("3GD");
        threeExMap.put("3EX", "4");
        threeExMap.put("3VG", "3");
        threeExMap.put("3GD", "2");
        threeList = new ArrayList<>();
        threeList.add("EX");
        threeList.add("VG");
        threeList.add("GD");
        fluoreList = new ArrayList<>();
        fluoreList.add("N");
        fluoreList.add("F");
        fluoreList.add("M");
        fluoreList.add("S");
        fluoreList.add("VS");
        milkyList=new ArrayList<>();
        milkyList.add("M0");
        milkyList.add("M1");
        milkyList.add("M2");
        colshList =new ArrayList<>();
        colshList.add("B0");
        colshList.add("B1");
        colshList.add("B2");
        greenList =new ArrayList<>();
        greenList.add("G0");
        greenList.add("G1");
        greenList.add("G2");
        shapeMap.put("圆形", "2");
        shapeMap.put("梨形", "256");
        shapeMap.put("公主方", "8");
        shapeMap.put("心形", "16");
        shapeMap.put("马眼形", "128");
        shapeMap.put("椭圆形", "4");
        shapeMap.put("雷迪恩", "512");
        shapeMap.put("祖母绿", "64");
        shapeMap.put("垫形", "32");
        shapeMap.put("三角形", "1024");
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
    }

    private void initView() {
        jpSearchRequest = new JpSearchRequest();
        //设置形状布局管理器
        jpRecyclerShape.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        SearchAdapter mAdapter = new SearchAdapter(this, shapeList);
        jpRecyclerShape.setAdapter(mAdapter);
        jpShape.setText("圆形");
        jpSearchRequest.setS_shape("2");
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpShape.setText(stringBuffer.toString());
                String[] shapeStr = stringBuffer.toString().split(",");
                StringBuilder shapeBuilder = new StringBuilder();
                String shape = null;
                for (int i = 0; i < shapeStr.length; i++) {
                    if (null != shapeStr[i] && i == 0) {
                        shape = shapeMap.get(shapeStr[i]);
                        shapeBuilder.append(shape);
                    } else if (null != shapeStr[i]) {
                        shape = shapeMap.get(shapeStr[i]);
                        shapeBuilder.append(",");
                        shapeBuilder.append(shape);
                    }
                }
                if (StringUtil.isValidString(shapeBuilder.toString())) {
                    jpSearchRequest.setS_shape(shapeBuilder.toString());
                } else {
                    jpSearchRequest.setS_shape("");
                }
            }
        });


        //设置颜色布局管理器
        jpRecyclerColor.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, colorList);
        jpRecyclerColor.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpColor.setText(stringBuffer.toString());
                jpSearchRequest.setS_color(stringBuffer.toString());
            }
        });
        //设置净度布局管理器
        jpRecyclerClarity.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, clarityList);
        jpRecyclerClarity.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpClarity.setText(stringBuffer.toString());
                jpSearchRequest.setS_clarity(stringBuffer.toString());
            }
        });
        //设置三项布局管理器
        jpRecyclerThreeEx.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeExList);
        jpRecyclerThreeEx.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpSearchRequest.setS_threepara(stringBuffer.toString());
            }
        });
        //设置切工布局管理器
        jpRecyclerCut.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        jpRecyclerCut.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpSearchRequest.setS_cut(stringBuffer.toString());
            }
        });
        //设置抛光布局管理器
        jpRecyclerPolish.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        jpRecyclerPolish.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpSearchRequest.setS_polish(stringBuffer.toString());
            }
        });

        //设置对称布局管理器
        jpRecyclerSymmetry.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, threeList);
        jpRecyclerSymmetry.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpSearchRequest.setS_symmetry(stringBuffer.toString());
            }
        });
        //设置荧光布局管理器
        jpRecyclerFluore.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SearchAdapter(this, fluoreList);
        jpRecyclerFluore.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, StringBuilder stringBuffer) {
                jpSearchRequest.setS_fluorescence(stringBuffer.toString());
            }
        });

    }
    @OnTouch({R.id.jp_search_caratStart, R.id.jp_search_caratEnd})
    public boolean onTouch(View view, MotionEvent event){
        clickEdit= (EditText) view;
        return false;
    }
    @OnTextChanged({R.id.jp_search_caratStart, R.id.jp_search_caratEnd})
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

    @OnTextChanged({R.id.jp_search_caratStart, R.id.jp_search_caratEnd})
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("输入前的状态", "i:" + s + "+" + start + "+" + count + "+" + after);
    }

    @OnTextChanged({R.id.jp_search_caratStart, R.id.jp_search_caratEnd})
    public void afterTextChanged(Editable s) {
    }
    @OnClick({R.id.jp_search_caratSection,R.id.jp_search_hk, R.id.jp_search_ind, R.id.jp_search_query, R.id.jp_search_resetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jp_search_caratSection:
                popupView = LayoutInflater.from(JpSearchActivity.this).inflate(R.layout.caratsection_popupwindow, null);
                mpopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                mpopupWindow.setTouchable(true);
                mpopupWindow.setOutsideTouchable(true);
                mpopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

                jpSearchCaratSection.setBackgroundResource(R.drawable.ic_expand_less_black_24dp);

                // 计算x轴方向的偏移量，使得PopupWindow在Title的正下方显示，此处的单位是pixels
                int xoffInPixels = this.getWindowManager().getDefaultDisplay().getWidth() / 2 - jpSearchCaratSection.getWidth() / 2;
                // 将pixels转为dip
                int xoffInDip = ScreenTools.px2dip(this, xoffInPixels);
                mpopupWindow.showAsDropDown(jpSearchCaratSection, -xoffInDip, 10);
                RecyclerView recyclerCarat = (RecyclerView) popupView.findViewById(R.id.recycler_caratSection);
                //设置布局管理器
                recyclerCarat.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                CaratSectionAdapter caratSectionAdapter = new CaratSectionAdapter(JpSearchActivity.this, caratData);
                recyclerCarat.setAdapter(caratSectionAdapter);
                caratSectionAdapter.setOnItemClickListener(new CaratSectionAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int tag, String data) {
                        String[] carat = data.split("-");
                        jpSearchCaratStart.setText(carat[0].toString());
                        jpSearchCaratEnd.setText(carat[1].toString());
                        jpSearchCaratSection.setBackgroundResource(R.drawable.ic_expand_more_black_24dp);
                        mpopupWindow.dismiss();
                    }
                });
                break;
            case R.id.jp_search_hk:
                if (jpSearchHk.isSelected()) {
                    jpSearchHk.setSelected(false);
                    jpSearchRequest.setS_spot("");
                    if (jpSearchInd.isSelected()){
                        jpSearchRequest.setS_spot("0");
                    }
                } else {
                    jpSearchRequest.setS_spot("1");
                    jpSearchHk.setSelected(true);
                }
                break;
            case R.id.jp_search_ind:
                if (jpSearchInd.isSelected()) {
                    jpSearchRequest.setS_spot("");
                    jpSearchInd.setSelected(false);
                    if (jpSearchHk.isSelected()){
                        jpSearchRequest.setS_spot("1");
                    }
                } else {
                    jpSearchRequest.setS_spot("0");
                    jpSearchInd.setSelected(true);
                }
                break;
            case R.id.jp_search_query:
                condition();
                break;
            case R.id.jp_search_resetting:
                Intent refresh = new Intent(JpSearchActivity.this, JpSearchActivity.class);
                refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
        }
    }

    private void condition() {
        if(NetUtil.isJpQuery(mContext) !=true){
            startActivity(new Intent(mContext, JpLoginActivity.class));
            return;
        }
        if (jpSearchHk.isSelected() == true && jpSearchInd.isSelected() == true) {
            jpSearchRequest.setS_spot("1,0");
        }
        jpSearchRequest.setS_qorderdir("asc");
        jpSearchRequest.setS_qorder("saleback");
        jpSearchRequest.setPage(1);
        jpSearchRequest.setSize(25);
        jpSearchRequest.setIspageed("1");
        jpSearchRequest.setS_protype("0");
        jpSearchRequest.setAppVersion("1.0.0");
        jpSearchRequest.setS_reportno(jpSearchRepoetNo.getText().toString());
        jpSearchRequest.setS_carat1(jpSearchCaratStart.getText().toString());
        jpSearchRequest.setS_carat2(jpSearchCaratEnd.getText().toString());
        jpSearchRequest.setToken(jpUserInfo.getToken());
        query(jpSearchRequest);
    }
}
