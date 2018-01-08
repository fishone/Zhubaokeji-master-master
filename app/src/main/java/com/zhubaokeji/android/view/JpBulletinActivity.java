package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.JpBulletinAdapter;
import com.zhubaokeji.android.bean.LzyResponse;
import com.zhubaokeji.android.callback.DialogCallback;
import com.zhubaokeji.android.utils.FlagUtil;
import com.zhubaokeji.android.utils.ToastUtil;
import com.zhubaokeji.android.utils.Urls;
import com.zhubaokeji.android.bean.JpBulletinResponse;
import com.zhubaokeji.android.fragment.MyStatusView;
import com.zhubaokeji.android.fragment.StatusLayoutFragment;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.DividerItemDecoration;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.library.TitleBar;

import java.util.ArrayList;
import com.lzy.okgo.model.Response;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;


/**
 * 创建人:YUIZHI
 * 包名：com.zhubaokeji.android.view
 * 创建时间：2017/8/7
 * 描述：公告显示
*/

public class JpBulletinActivity extends BaseActivity {
    @BindView(R.id.jp_recycler_bulletin)
    RecyclerView jpRecyclerBulletin;
    JpBulletinAdapter jpBulletinAdapter;
    ArrayList<JpBulletinResponse> jpBulletinResponseList;
    MyStatusView statusView;
    @BindView(R.id.status_layout)
    StatusLayoutFragment statusLayout;
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp_bulletin);
        ButterKnife.bind(this);
     /*设置标题*/
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.jp_bulletin_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitle("公告栏");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
        mContext=this;
        statusView = new MyStatusView(getApplicationContext());
        statusLayout.setStatusView(statusView);
        if(NetUtil.isConnected(this) != true) {
            statusLayout.showSetting();
            return;
        }
        initialise();
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
    private void initialise() {
        String path = Urls.URL + Urls.JPBULLETINURL + "&appVersion=1.1";
        OkGo.<LzyResponse<ArrayList<JpBulletinResponse>>>get(path)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new DialogCallback<LzyResponse<ArrayList<JpBulletinResponse>>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ArrayList<JpBulletinResponse>>> response) {
                        try {
                            if (null != response && null !=response.body()) {
                                LzyResponse<ArrayList<JpBulletinResponse>> lzyResponse =response.body();
                                String message = lzyResponse.message;
                                switch (lzyResponse.status) {
                                    case 1:
                                        jpBulletinResponseList=lzyResponse.getMsgdata();
//                                        JSONArray msgdata = jsonObject.getJSONArray("msgdata"); //取出用户信息
//                                        jpBulletinResponseList = new ArrayList<JpBulletinResponse>();
//                                        for (int i = 0; i < msgdata.length(); i++) {
//                                            JSONObject job = msgdata.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//                                            jpBulletinResponseList.add(new Gson().fromJson(job.toString(), JpBulletinResponse.class));
//                                        }
                                        jpRecyclerBulletin.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        jpBulletinAdapter = new JpBulletinAdapter(getApplicationContext(), jpBulletinResponseList);
                                        jpRecyclerBulletin.setAdapter(jpBulletinAdapter);
                                        jpRecyclerBulletin.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                                                DividerItemDecoration.VERTICAL_LIST, 1, R.color.grey_400));
                                        jpBulletinAdapter.setOnItemClickListener(new JpBulletinAdapter.OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int tag) {
                                                JpBulletinResponse jpBulletinResponse = new JpBulletinResponse();
                                                jpBulletinResponse = jpBulletinResponseList.get(tag);
                                                Intent Intent_Result = new Intent();
                                                Intent_Result.setClass(JpBulletinActivity.this, JpBulletinDetailActivity.class);
                                                Intent_Result.putExtra("jpBulletinResponse", jpBulletinResponse);
                                                JpBulletinActivity.this.startActivity(Intent_Result);
                                            }
                                        });
                                        break;
                                    default:
                                        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Logger.e(e, "message");
                        }

                    }
                    @Override
                    public void onError(Response<LzyResponse<ArrayList<JpBulletinResponse>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        NetUtil.myException(mContext,response.getException(), FlagUtil.JP);
                    }
                });
    }
}
