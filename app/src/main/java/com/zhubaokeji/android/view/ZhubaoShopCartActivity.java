//package com.zhubaokeji.android.view;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.model.Response;
//import com.orhanobut.logger.Logger;
//import com.yanzhenjie.recyclerview.swipe.Closeable;
//import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
//import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
//import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
//import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
//import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
//import com.zhubaokeji.android.R;
//import com.zhubaokeji.android.adapter.ZhubaoShopCartAdapter;
//import com.zhubaokeji.android.bean.JpUserInfo;
//import com.zhubaokeji.android.bean.LzyResponse;
//import com.zhubaokeji.android.bean.LzyListResponse;
//import com.zhubaokeji.android.bean.ZhubaoShopCartResponse;
//import com.zhubaokeji.android.callback.DialogCallback;
//import com.zhubaokeji.android.callback.JsonCallback;
//import com.zhubaokeji.android.fragment.MyStatusView;
//import com.zhubaokeji.android.listener.OnItemListener;
//import com.zhubaokeji.android.base.BaseActivity;
//import com.zhubaokeji.android.utils.GsonUtil;
//import com.zhubaokeji.android.utils.SharedPreferencesUtil;
//import com.zhubaokeji.android.utils.ToastUtil;
//import com.zhubaokeji.android.utils.Urls;
//import com.zhubaokeji.library.TitleBar;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
//
///**
// * Created by fisho on 2017/6/30.
// */
//
//public class ZhubaoShopCartActivity extends BaseActivity {
//    @BindView(R.id.zhubao_shopCart_checkAll)
//    CheckBox zhubaoShopCartCheckAll;
//    @BindView(R.id.zhubao_shopCart_totalPrice)
//    TextView zhubaoShopCartTotalPrice;
//    @BindView(R.id.zhubao_shopCart_placeOrder)
//    TextView zhubaoShopCartPlaceOrder;
//    @BindView(R.id.zhubao_shopCart_delete)
//    TextView zhubaoShopCartDelete;
//    @BindView(R.id.zhubao_shopCart_explain)
//    TextView zhubaoShopCartExplain;
//    @BindView(R.id.zhubao_ShopCart)
//    SwipeMenuRecyclerView zhubaoShopCart;
//    private TitleBar titleBar;
//    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
//    private Activity mContext; //类Context
//    private JpUserInfo userInfo;
//    private ArrayList<ZhubaoShopCartResponse> zhubaoShopCartList;
//    private ZhubaoShopCartAdapter zhubaoShopCartAdapter;
//    MyStatusView statusView;
//    //记录选择的Item
//    private HashSet<Integer> positionSet;
//    private BigDecimal totalPrice;// 购买的商品总价
//    private int totalCount = 0;// 购买的商品总数量
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.zhubao_shopcart);
//        ButterKnife.bind(this);
//               /*设置标题*/
//        boolean isImmersive = true;
//        initTranslucentStatusBar();
//        titleBar = (TitleBar) findViewById(R.id.zhubao_shopcart_title);
//        titleBar.setImmersive(isImmersive);
//        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
//        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
//        titleBar.setTitle("购物车");
//        titleBar.setTitleColor(Color.WHITE);
//        titleBar.setSubTitleColor(Color.WHITE);
//        titleBar.setDividerColor(Color.GRAY);
//        titleBar.setActionTextColor(Color.WHITE);
//        titleBar.setLeftTextColor(Color.WHITE);
//        titleBar.setLeftClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
////                setResult(responseList.size(), intent);
//                finish();
//                overridePendingTransition(R.anim.in_right, R.anim.out_right);
//            }
//        });
//        titleBar.addAction(new TitleBar.TextAction("删除") {
//            @Override
//            public void performAction(View view) {
//            }
//        });
//        mContext = this;
//
//        zhubaoShopCart.setLayoutManager(new LinearLayoutManager(this));
//        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
//        // 设置菜单创建器。
//        zhubaoShopCart.setSwipeMenuCreator(swipeMenuCreator);
//        // 设置菜单Item点击监听。
//        zhubaoShopCart.setSwipeMenuItemClickListener(menuItemClickListener);
////        statusView = new MyStatusView(getApplicationContext());
////        statusLayout.setStatusView(statusView);
//        preferencesUtil = new SharedPreferencesUtil(getApplicationContext());
//        try {
//            userInfo = preferencesUtil.getInfo();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        initData();
//    }
//
//    /**
//     * 菜单创建器。在Item要创建菜单的时候调用。
//     */
//    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
//        @Override
//        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
//            int width = getResources().getDimensionPixelSize(R.dimen.item_width);
//
//            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//
//            // 添加左侧的，如果不添加，则左侧不会出现菜单。
//            // 添加右侧的，如果不添加，则右侧不会出现菜单。
//            {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
//                        .setBackgroundDrawable(R.drawable.selector_red)
//                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
//                        .setTextColor(Color.BLACK)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
//            }
//        }
//    };
//
//    private void initData() {
//        final String path = Urls.ZHUBAOURL + Urls.ZHUBAOSHOPCAT + "&token=" + userInfo.getToken();
//        OkGo.<LzyListResponse<ArrayList<ZhubaoShopCartResponse>>>get(path)     // 请求方式和请求url
//                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                .execute(new DialogCallback<LzyListResponse<ArrayList<ZhubaoShopCartResponse>>>(this) {
//                    @Override
//                    public void onSuccess(Response<LzyListResponse<ArrayList<ZhubaoShopCartResponse>>> response) {
//                        try {
//                            if (null != response && response.body() != null) {
//                                LzyListResponse<ArrayList<ZhubaoShopCartResponse>> lzyListResponse = response.body();
//                                String message = lzyListResponse.message;
//                                switch (lzyListResponse.status) {
//                                    case 1:
//                                        LzyListResponse<ArrayList<ZhubaoShopCartResponse>>.ServerModle serverModle = lzyListResponse.getMsgdata();
//                                        zhubaoShopCartList = GsonUtil.FromJson(serverModle.rows,ZhubaoShopCartResponse.class);
//                                        if (zhubaoShopCartList != null && zhubaoShopCartList.size() != 0) {
//                                            zhubaoShopCartAdapter = new ZhubaoShopCartAdapter(mContext, zhubaoShopCartList);
//                                            // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
//                                            // 设置菜单创建器。
//                                            zhubaoShopCart.setSwipeMenuCreator(swipeMenuCreator);
//                                            // 设置菜单Item点击监听。
//                                            zhubaoShopCart.setSwipeMenuItemClickListener(menuItemClickListener);
//                                            zhubaoShopCart.setAdapter(zhubaoShopCartAdapter);
//                                            titleBar.setTitle("购物车" + "(" + zhubaoShopCartList.size() + ")");
//                                            setListener();
//                                        }
//                                        break;
//                                    case -404:
//                                    case 404:
//                                        jp_Login_Boolean = false;
//                                        ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
//                                        Intent login_intent = new Intent();
//                                        login_intent.setClass(mContext, JpLoginActivity.class);
//                                        startActivity(login_intent);
//                                        break;
//                                    default:
//                                        ToastUtil.show(getApplicationContext(), message);
//                                        break;
//                                }
//                            }
//                        } catch (Exception e) {
//                            Logger.e(e, "JP购物车查询");
//                        }
//                    }
//                });
//    }
//    private void setListener() {
//        positionSet = new HashSet<>();
//        if (positionSet.size() == 0) {
//            zhubaoShopCartCheckAll.setChecked(false);
//        }
//        zhubaoShopCartAdapter.setOnItemListener(new OnItemListener() {
//            @Override
//            public void onItemClick(int position, boolean isChecked) {
//                zhubaoShopCartList.get(position).setSelect(isChecked);
//                for (int i = 0; i < zhubaoShopCartList.size(); i++) {
//                    if (true == zhubaoShopCartList.get(i).isSelect()) {
//                        positionSet.add(i);
//                    } else {
//                        positionSet.remove(i);
//                    }
//                }
//                if (positionSet.size() == zhubaoShopCartList.size()) {
//                    if (zhubaoShopCartList.size() == 0) {
//                        zhubaoShopCartCheckAll.setChecked(false);
//                    } else {
//                        zhubaoShopCartCheckAll.setChecked(true);
//                    }
//                } else {
//                    zhubaoShopCartCheckAll.setChecked(false);
//                }
//                zhubaoShopCartAdapter.notifyDataSetChanged();
//                calculate();
//            }
//
//            @Override
//            public void onItemyOfferClick(int position, boolean isChecked) {
//            }
//        });
//    }
//
//    @OnClick({R.id.zhubao_shopCart_placeOrder, R.id.zhubao_shopCart_delete, R.id.zhubao_shopCart_checkAll})
//    public void onClick(final View view) {
//        switch (view.getId()){
//            case R.id.zhubao_shopCart_placeOrder:
//                break;
//            case R.id.zhubao_shopCart_delete:
//                break;
//            case R.id.zhubao_shopCart_checkAll:
//                for (int i = 0; i < zhubaoShopCartList.size(); i++) {
//                    zhubaoShopCartList.get(i).setSelect(zhubaoShopCartCheckAll.isChecked());
//                }
//                zhubaoShopCartAdapter.notifyDataSetChanged();
//                calculate();
//                break;
//        }
//    }
//    /**
//     * 统计操作<br>
//     * 1.先清空全局计数器<br>
//     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
//     * 3.给底部的textView进行数据填充
//     */
//
//    private void calculate() {
//        totalCount = 0;
//        totalPrice = new BigDecimal("0.00");
//        for (int i = 0; i < zhubaoShopCartList.size(); i++) {
//            ZhubaoShopCartResponse zhubaoShopCartResponse = zhubaoShopCartList.get(i);
//            if (zhubaoShopCartResponse.isSelect()) {
//                totalCount++;
//                BigDecimal bigSaleprice = new BigDecimal(zhubaoShopCartResponse.getRmbprice());
//                totalPrice = totalPrice.add(bigSaleprice).setScale(0, BigDecimal.ROUND_HALF_UP);
//            }
//
//        }
//        zhubaoShopCartTotalPrice.setText("￥" + totalPrice);
//        zhubaoShopCartPlaceOrder.setText("确认下单(" + totalCount + ")");
//        //计算购物车的金额为0时候清空购物车的视图
//        if (totalCount == 0) {
//            setCartNum();
//        } else {
//
//        }
//    }
//
//    /**
//     * 设置购物车产品数量
//     */
//    private void setCartNum() {
//        int count = 0;
//        for (int i = 0; i < zhubaoShopCartList.size(); i++) {
////            responseList.get(i).setSelect(jpShoppingCheckAll.isChecked());
////            JpShoppingCartResponse jpShoppingCartResponse = responseList.get(i);
//            for (ZhubaoShopCartResponse zhubaoShopCartResponse : zhubaoShopCartList) {
//                count += 1;
//            }
//        }
//
//        //购物车已清空
//        if (count == 0) {
//            clearCart();
//        } else {
//            titleBar.setTitle("购物车" + "(" + zhubaoShopCartList.size() + ")");
//        }
//    }
//
//    private void clearCart() {
//        titleBar.setTitle("购物车" + "(" + 0 + ")");
//    }
//
//
//    /**
//     * 删除操作<br>
//     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
//     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
//     */
//    protected void doDelete() {
//        List<ZhubaoShopCartResponse> toBeDeleteGroups = new ArrayList<ZhubaoShopCartResponse>();// 待删除的组元素列表
//        StringBuffer idsBuffer = new StringBuffer();
//        for (int i = 0; i < zhubaoShopCartList.size(); i++) {
//            ZhubaoShopCartResponse zhubaoShopCartResponse = zhubaoShopCartList.get(i);
//            if (zhubaoShopCartResponse.isSelect()) {
//                toBeDeleteGroups.add(zhubaoShopCartResponse);
//                idsBuffer.append(zhubaoShopCartResponse.getRecno());
//                idsBuffer.append(",");
//            }
//        }
//        zhubaoShopCartList.removeAll(toBeDeleteGroups);
//        if (zhubaoShopCartList.size() == 0) {
//            zhubaoShopCartCheckAll.setChecked(false);
//        }
//        final String path = Urls.ZHUBAOURL + Urls.ZHUBAODELETESHOPCAT + "&token=" + userInfo.getToken() + "&stoneno=" + idsBuffer.toString();
//        OkGo.<LzyResponse>get(path)     // 请求方式和请求url
//                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
//                    @Override
//                    public void onSuccess(Response<LzyResponse> response) {
//                        try {
//                            if (null != response && null != response.body()) {
//                                LzyResponse lzyResponse=response.body();
//                                String message =lzyResponse.message;
//                                switch (lzyResponse.status) {
//                                    case 1:
//                                    case -1:
//                                    case 0:
//                                        //GSON直接解析成对象
//                                        ToastUtil.show(getApplicationContext(), message);
//                                        initData();
//                                        break;
//                                    case 404:
//                                    case -404:
//                                        ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
//                                        Intent login_intent = new Intent();
//                                        login_intent.setClass(ZhubaoShopCartActivity.this, JpLoginActivity.class);
//                                        startActivity(login_intent);
//                                        break;
//                                    default:
//                                        ToastUtil.show(getApplicationContext(), message);
//                                        break;
//                                }
//                            }
//                        } catch (Exception e) {
//                            Logger.e(e, "jp购物车删除");
//                        }
//                    }
//                });
//        //记得重新设置购物车
//        calculate();
//        zhubaoShopCartAdapter.notifyDataSetChanged();
//
//    }
//    /**
//     * 菜单点击监听。
//     */
//    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
//        /**
//         * Item的菜单被点击的时候调用。
//         * @param closeable       closeable. 用来关闭菜单。
//         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
//         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
//         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
//         *                        #RIGHT_DIRECTION.
//         */
//        @Override
//        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
//            closeable.smoothCloseMenu();// 关闭被点击的菜单。
//            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
//            if (menuPosition == 0) {// 删除按钮被点击。
//                zhubaoShopCartList.remove(adapterPosition);
//                zhubaoShopCartAdapter.notifyItemRemoved(adapterPosition);
//                final String recnoStr = zhubaoShopCartList.get(adapterPosition).getRecno();
//                final String path = Urls.ZHUBAOURL + Urls.JPSHOPPINGCARTDELECT + "&token=" + userInfo.getToken() + "&ids=" + recnoStr;
//                OkGo.<LzyResponse>get(path)     // 请求方式和请求url
//                        .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                        .execute(new JsonCallback<LzyResponse>(LzyResponse.class) {
//                            @Override
//                            public void onSuccess(Response<LzyResponse> response) {
//                                try {
//                                    if (null != response && null !=response.body()) {
//                                        LzyResponse lzyResponse=response.body();
//                                        String message = lzyResponse.message;
//                                        switch (lzyResponse.status) {
//                                            case 1:
//                                                break;
//                                            case -1:
//                                            case 0:
//                                                //GSON直接解析成对象
//                                                ToastUtil.show(getApplicationContext(), message);
//                                                initData();
//                                                break;
//                                            case 404:
//                                            case -404:
//                                                ToastUtil.show(getApplicationContext(), "登录超时,请重新登录");
//                                                Intent login_intent = new Intent();
//                                                login_intent.setClass(mContext, JpLoginActivity.class);
//                                                startActivity(login_intent);
//                                                break;
//                                            default:
//                                                ToastUtil.show(getApplicationContext(), message);
//                                                break;
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    Logger.e(e, "jp购物车删除");
//                                }
//                            }
//                        });
//            }
//        }
//    };
//}
