package com.zhubaokeji.android.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.BasicSetting;
import com.zhubaokeji.android.bean.CalculatorRequest;
import com.zhubaokeji.android.adapter.CalculatorAdapter;
import com.zhubaokeji.android.bean.CalculatorResponse;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.callback.JsonCallback;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.DividerItemDecoration;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;
import com.zhubaokeji.android.utils.StringUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.library.TitleBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * Created by fisho on 2017/2/15.
 */

public class CalculatorActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.edit_back)
    EditText editBack;
    @BindView(R.id.btn_calculator_one)
    Button btnCalculatorOne;
    @BindView(R.id.btn_calculator_two)
    Button btnCalculatorTwo;
    @BindView(R.id.btn_calculator_three)
    Button btnCalculatorThree;
    @BindView(R.id.btn_calculator_delete)
    Button btnCalculatorDelete;
    @BindView(R.id.btn_calculator_four)
    Button btnCalculatorFour;
    @BindView(R.id.btn_calculator_five)
    Button btnCalculatorFive;
    @BindView(R.id.btn_calculator_six)
    Button btnCalculatorSix;
    @BindView(R.id.btn_calculator_zero)
    Button btnCalculatorZero;
    @BindView(R.id.btn_calculator_seven)
    Button btnCalculatorSeven;
    @BindView(R.id.btn_calculator_eight)
    Button btnCalculatorEight;
    @BindView(R.id.btn_calculator_nine)
    Button btnCalculatorNine;
    @BindView(R.id.btn_calculator_dot)
    Button btnCalculatorDot;
    @BindView(R.id.btn_round)
    Button btnRound;
    @BindView(R.id.btn_dysmorphism)
    Button btnDysmorphism;
    @BindView(R.id.edit_carat)
    EditText editCarat;
    @BindView(R.id.edit_onlineprice)
    EditText editOnlineprice;
    @BindView(R.id.edit_rate)
    EditText editRate;
    @BindView(R.id.edit_discount)
    EditText editDiscount;
    @BindView(R.id.calculator)
    LinearLayout calculator;
    @BindView(R.id.dollorPrice)
    TextView dollorPrice;
    @BindView(R.id.rmbPrice)
    TextView rmbPrice;
    @BindView(R.id.dollorPriceCt)
    TextView dollorPriceCt;
    @BindView(R.id.rmbPriceCt)
    TextView rmbPriceCt;
    @BindView(R.id.bt_discount)
    Button btDiscount;
    @BindView(R.id.bt_back)
    Button btBack;


    private RecyclerView ColorRecycler;
    private RecyclerView ClarityRecycler;
    private EditText clickEdit;
    private List<String> colorList;
    private List<String> clarityList;
    private CalculatorAdapter mAdapter;
    public CalculatorRequest onlinepriceReqest = new CalculatorRequest();
    protected static final int SUCCESS = 0;
    protected static final int ERROR = 2;
    private Activity mContext;
    BasicSetting basicSetting = new BasicSetting();
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        ButterKnife.bind(this);
        /**
         * 设置标题栏
         */
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.calculator_title);
        titleBar.setImmersive(isImmersive);

        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("计算器");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_right, R.anim.out_right);
            }
        });
        mContext=this;
        onlinepriceReqest.setS_color("D");
        onlinepriceReqest.setS_clarity("FL");
        onlinepriceReqest.setS_shape("round");
        initialiseHolder();
        colorData();
        clarityData();
        ColorRecycler = (RecyclerView) findViewById(R.id.recycler_color);
        ClarityRecycler = (RecyclerView) findViewById(R.id.recycler_clarity);
        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        try {
            basicSetting = preferencesUtil.getBasicSetting();
            editRate.setText(basicSetting.getRate().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置颜色布局管理器
        ColorRecycler.setLayoutManager(new StaggeredGridLayoutManager(11,StaggeredGridLayoutManager.HORIZONTAL));
        //分割线
        ColorRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CalculatorAdapter(this, colorList);
        ColorRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CalculatorAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, String data) {
                onlinepriceReqest.setS_color(data);
                queryOnlineprice(onlinepriceReqest);
            }
        });
        //设置净度布局管理器
        ClarityRecycler.setLayoutManager(new StaggeredGridLayoutManager(11,StaggeredGridLayoutManager.HORIZONTAL));
        //分割线
        ClarityRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CalculatorAdapter(this, clarityList);
        ClarityRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CalculatorAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, String data) {
                onlinepriceReqest.setS_clarity(data);
                queryOnlineprice(onlinepriceReqest);
            }
        });
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
    private void queryOnlineprice(final CalculatorRequest onlineproice) {
        if(NetUtil.isConnected(this) != true) {
            ToastUtil.show(this,"网络未连接,请检查网络后重试");
            return;
        }
        final String path = Urls.ZHUBAOURL+ Urls.QUERYUNIFORMOFFER +onlineproice.toJson();
        OkGo.<LzyResponse<CalculatorResponse>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new JsonCallback<LzyResponse<CalculatorResponse>>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<CalculatorResponse>> response) {
                        try {
                            if(null !=response && null !=response.body()){
                                LzyResponse<CalculatorResponse> lzyResponse=response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status){
                                    case 1:
                                        CalculatorResponse calculatorResponse=lzyResponse.getMsgdata();
                                        if(StringUtils.isEmpty(calculatorResponse.price)){
                                            editOnlineprice.setText("");
                                        }else {
                                            editOnlineprice.setText(calculatorResponse.getPrice());
                                        }
                                         calculatorPrice();
                                        break;
                                    default:
                                        ToastUtil.show(mContext,message);
                                        break;
                                }
                            }
                        }catch (Exception e){
                            Logger.e("国际报价",e);
                        }
                    }
                });
    }

    private void initialiseHolder() {
        editCarat.setInputType(InputType.TYPE_NULL);
        btnRound.setSelected(true);
        editOnlineprice.setEnabled(false);
        editRate.setInputType(InputType.TYPE_NULL);
        editDiscount.setInputType(InputType.TYPE_NULL);
        editBack.setInputType(InputType.TYPE_NULL);

    }

    private void clarityData() {
        clarityList = new ArrayList<String>();
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
        clarityList.add("I2");
    }

    protected void colorData() {
        colorList = new ArrayList<String>();
        colorList.add("D");
        colorList.add("E");
        colorList.add("F");
        colorList.add("G");
        colorList.add("H");
        colorList.add("DataHolder");
        colorList.add("J");
        colorList.add("K");
        colorList.add("L");
        colorList.add("M");
        colorList.add("N");
    }
    int touch_flag=0;
    @OnTouch({R.id.edit_carat, R.id.edit_rate, R.id.edit_discount, R.id.edit_back})
    public boolean onTouch(View view, MotionEvent event){
        touch_flag++;
        if (touch_flag==2 ) {
            touch_flag=0;
            switch (view.getId()){
                case R.id.edit_carat:
                    editCarat.setFocusable(true);
                    editCarat.requestFocus();
                    editCarat.requestFocusFromTouch();
                    clickEdit = editCarat;
                    break;
                case R.id.edit_rate:
                    editRate.setFocusable(true);
                    editRate.requestFocus();
                    editRate.requestFocusFromTouch();
                    clickEdit = editRate;
                    break;
                case R.id.edit_discount:
                    editDiscount.setFocusable(true);
                    editDiscount.requestFocus();
                    editDiscount.requestFocusFromTouch();
                    clickEdit = editDiscount;
                    break;
                case R.id.edit_back:
                    editBack.setFocusable(true);
                    editBack.requestFocus();
                    editBack.requestFocusFromTouch();
                    clickEdit = editBack;
                    break;
            }
        }
        return true;
    }

    @OnClick({R.id.btn_calculator_one, R.id.btn_calculator_two, R.id.btn_calculator_three, R.id.btn_calculator_delete, R.id.btn_calculator_four, R.id.btn_calculator_five, R.id.btn_calculator_six, R.id.btn_calculator_zero, R.id.btn_calculator_seven, R.id.btn_calculator_eight, R.id.btn_calculator_nine, R.id.btn_calculator_dot, R.id.btn_round, R.id.btn_dysmorphism
            , R.id.edit_carat, R.id.edit_rate, R.id.edit_discount, R.id.edit_back,R.id.bt_discount, R.id.bt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calculator_one:
                if (clickEdit != null) {
                    clickEdit.append("1");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_two:
                if (clickEdit != null) {
                    clickEdit.append("2");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_three:
                if (clickEdit != null) {
                    clickEdit.append("3");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_delete:
                if (clickEdit != null) {
                    String clickStr = clickEdit.getText().toString();
                    if (clickStr.length() != 0) {
                        clickStr = clickStr.substring(0, clickStr.length() - 1);
                        clickEdit.setText(clickStr);
                        EditWatcher(clickEdit);
                    }
                    break;
                }
                break;
            case R.id.btn_calculator_four:
                if (clickEdit != null) {
                    clickEdit.append("4");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_five:
                if (clickEdit != null) {
                    clickEdit.append("5");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_six:
                if (clickEdit != null) {
                    clickEdit.append("6");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_zero:
                if (clickEdit != null) {
                    clickEdit.append("0");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_seven:
                if (clickEdit != null) {
                    clickEdit.append("7");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_eight:
                if (clickEdit != null) {
                    clickEdit.append("8");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_nine:
                if (clickEdit != null) {
                    clickEdit.append("9");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_calculator_dot:
                if (clickEdit != null) {
                    clickEdit.append(".");
                    EditWatcher(clickEdit);
                }
                break;
            case R.id.btn_round:
                if (btnRound.isSelected()) {
                    btnRound.setSelected(true);
                    onlinepriceReqest.setS_shape("round");
                    queryOnlineprice(onlinepriceReqest);
                } else {
                    btnRound.setSelected(true);
                    btnDysmorphism.setSelected(false);
                    onlinepriceReqest.setS_shape("round");
                    queryOnlineprice(onlinepriceReqest);
                }
                break;
            case R.id.btn_dysmorphism:
                if (btnDysmorphism.isSelected()) {
                    btnDysmorphism.setSelected(true);
                    onlinepriceReqest.setS_shape("irregular");
                    queryOnlineprice(onlinepriceReqest);
                } else {
                    btnDysmorphism.setSelected(true);
                    btnRound.setSelected(false);
                    onlinepriceReqest.setS_shape("irregular");
                    queryOnlineprice(onlinepriceReqest);
                }
                break;
            case R.id.bt_discount:
                clickEdit = editDiscount;
                if(btDiscount.getText().toString().equals("+")){
                    btDiscount.setText("-");
                    EditWatcher(clickEdit);
                }else {
                    btDiscount.setText("+");
                    EditWatcher(clickEdit);

                }
                break;
            case R.id.bt_back:
                clickEdit = editBack;
                if(btBack.getText().toString().equals("-")){
                    btBack.setText("+");
                    EditWatcher(clickEdit);
                }else {
                    btBack.setText("-");
                    EditWatcher(clickEdit);
                }
                break;
        }
    }

    void EditWatcher(EditText clickEdit){
        if (clickEdit != null) {
            switch (clickEdit.getId()) {
                case R.id.edit_carat:
                    String carat = editCarat.getText().toString();
                    if (carat != null && StringUtil.isValidString(carat)) {
                        onlinepriceReqest.setS_carat(carat);
                        queryOnlineprice(onlinepriceReqest);
                    }else{
                        onlinepriceReqest.setS_carat("0");
                        queryOnlineprice(onlinepriceReqest);
                    }
                    break;
                case R.id.edit_rate:
                    String rate=editRate.getText().toString();
                    if(rate != null && StringUtil.isValidString(rate)){
                        calculatorPrice();
                    }
                    break;
                case R.id.edit_discount:
                    String discount = editDiscount.getText().toString();
                    if (StringUtil.isValidString(discount)) {
                        Float floatDiscount = Float.parseFloat(discount);

                        if(btDiscount.getText().toString().equals("-")){
                            floatDiscount= floatDiscount*(-1);
                        }
                        BigDecimal big_bdiscount = new BigDecimal(floatDiscount);
                        BigDecimal big_back = big_bdiscount.subtract(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        int back=big_back.compareTo(BigDecimal.ZERO);
                        if(back < 0){
                            big_back=big_back.abs();
                            btBack.setText("-");
                        }else {
                            btBack.setText("+");
                        }
                        editBack.setText(String.valueOf(big_back));
                        calculatorPrice();
                    } else {
                        editBack.setText(discount);
                    }
                    break;
                case R.id.edit_back:
                    String back = editBack.getText().toString();
                    if (StringUtil.isValidString(back)) {
                        Float floatBack =Float.parseFloat(back);
                        if(btBack.getText().toString().equals("-")){
                            floatBack= floatBack*(-1);
                        }
                        BigDecimal big_back = new BigDecimal(floatBack);
                        BigDecimal big_discount = big_back.add(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        int Intdiscount=big_discount.compareTo(BigDecimal.ZERO);
                        if(Intdiscount < 0){
                            big_discount=big_discount.abs();
                            btDiscount.setText("-");
                        }
                        editDiscount.setText(String.valueOf(big_discount));
                        calculatorPrice();
                    } else {
                        editDiscount.setText(back);
                    }
                    break;
            }
        }

    }


    @OnTextChanged({R.id.edit_carat, R.id.edit_rate, R.id.edit_discount, R.id.edit_back})
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

    @OnTextChanged({R.id.edit_carat, R.id.edit_rate, R.id.edit_discount, R.id.edit_back})
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("输入前的状态", "i:" + s + "+" + start + "+" + count + "+" + after);
    }

    @OnTextChanged({R.id.edit_carat, R.id.edit_rate, R.id.edit_discount, R.id.edit_back})
    public void afterTextChanged(Editable s) {

    }

//    当折扣输入为负数时， 折扣=100-文本框值，如输入-5，折扣=105.
//    当折扣输入为正数时，折扣=文本框值，如输入5，折扣=5.
//    美元：USD/ct=折扣/100*国际报价;
//    USD/颗=USD/ct *重量
//    人民币：RMB/ct=折扣/100*国际报价*汇率= USD/ct*汇率;
//    RMB/颗= RMB/ct*重量;
    private void calculatorPrice() {

        String carat = editCarat.getText().toString();
        BigDecimal bigCarat =null;
        if (!StringUtil.isValidString(carat)) {
            bigCarat = new BigDecimal(0);
        } else {
            bigCarat=new BigDecimal(carat);
        }
        String discount = editDiscount.getText().toString();
        BigDecimal bigDiscount = null;
        if (!StringUtil.isValidString(discount)) {
            bigDiscount = new BigDecimal(100);
        } else {
            bigDiscount = new BigDecimal(discount);
            if (btDiscount.getText().toString().equals("-")) {
                bigDiscount = bigDiscount.add(new BigDecimal(100));
            }
        }

        bigDiscount = new BigDecimal(String.valueOf(bigDiscount));
        BigDecimal bigOnlineprice = new BigDecimal(0);
        if (StringUtil.isValidString(editOnlineprice.getText().toString())) {
            bigOnlineprice = new BigDecimal(editOnlineprice.getText().toString());
        }
        BigDecimal bigRate = null;
        if (StringUtils.isEmpty(editRate.getText().toString())) {
            bigRate = new BigDecimal(0);
        } else {
            bigRate = new BigDecimal(editRate.getText().toString());
        }
        BigDecimal dollorpriceCt = bigDiscount.divide(new BigDecimal(100)).multiply(bigOnlineprice);

        dollorPriceCt.setText(dollorpriceCt.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "/Ct");
        BigDecimal dollorprice = dollorpriceCt.multiply(bigCarat).setScale(2, BigDecimal.ROUND_HALF_UP);
        dollorPrice.setText(dollorprice.toString());
        BigDecimal rmbpriceCt = dollorpriceCt.multiply(bigRate);
        rmbPriceCt.setText(rmbpriceCt.setScale(0, BigDecimal.ROUND_HALF_UP).toString() + "/Ct");
        BigDecimal rmbprice = rmbpriceCt.multiply(bigCarat).setScale(0, BigDecimal.ROUND_HALF_UP);
        rmbPrice.setText(rmbprice.toString());
    }
}
