package com.zhubaokeji.android.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.RapDetailsResponse;
import com.zhubaokeji.android.bean.RapDiamondResponse;
import com.zhubaokeji.android.bean.ZhubaoColorResponse;
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.library.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建人:YUIZHI
 * 包名：com.zhubaokeji.android.view
 * 创建时间：2017/8/8
 * 描述：
*/

public class OfferActivity extends BaseActivity {
    @BindView(R.id.offerinfo_credentials)
    TextView offerinfoCredentials;
    @BindView(R.id.offerinfo_no_credentials)
    TextView offerinfoNoCredentials;
    @BindView(R.id.offerinfo_presell_back)
    TextView offerinfoPresellBack;
    @BindView(R.id.copy_offer_zhengshu)
    Button copyOfferZhengshu;
    @BindView(R.id.copy_offer_zhengshu_no)
    Button copyOfferZhengshuNo;
    @BindView(R.id.copy_offer_presellBack)
    Button copyOfferPresellBack;
    RapDiamondResponse rapDiamondResponse;
    ZhubaoColorResponse zhubaoColorResponse;
    @BindView(R.id.round)
    TextView round;
    @BindView(R.id.noRound)
    TextView noRound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerinfo);
        ButterKnife.bind(this);

        /*设置标题*/
        boolean isImmersive = true;
        initTranslucentStatusBar();
        final TitleBar titleBar = (TitleBar) findViewById(R.id.offoer_title);
        titleBar.setImmersive(isImmersive);
        titleBar.setLeftImageResource(R.drawable.ic_arrow_back_black_24dp);
        titleBar.setLeftTextColor(Color.WHITE);
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


        ZhubaoDiamondResponse diamondResponse = getIntent().getParcelableExtra("offerInfo"); //接收助宝点击详情的数据
        RapDetailsResponse rapDetailsResponse = getIntent().getParcelableExtra("RapofferInfo");//接收RAPNET点击详情的数据
        zhubaoColorResponse = getIntent().getParcelableExtra("colorOfferInfo");//接收彩钻点击详情的数据
        rapDiamondResponse = getIntent().getParcelableExtra("RapDiamondInfo");//接收RAPNET点击详情的数据
        if (diamondResponse != null) {
            titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
            titleBar.setTitle("助宝白钻报价");
            offerinfoCredentials.setText(offerInfo(diamondResponse, "zhengshu_yes"));  //带证书号
            offerinfoNoCredentials.setText(offerInfo(diamondResponse, ""));            //不带证书号
            offerinfoPresellBack.setText(offerInfo(diamondResponse, "PresellBack_yes")); //带预售退点
        }
        if (rapDetailsResponse != null) {
            titleBar.setBackgroundColor(Color.parseColor("#D31A2B"));
            titleBar.setTitle("Rap白钻报价");
            offerinfoCredentials.setText(RapOfferInfo(rapDetailsResponse, "zhengshu_yes"));  //带证书号
            offerinfoNoCredentials.setText(RapOfferInfo(rapDetailsResponse, ""));            //不带证书号
            offerinfoPresellBack.setText(RapOfferInfo(rapDetailsResponse, "PresellBack_yes")); //带预售退点
        }
        if (zhubaoColorResponse != null) {
            titleBar.setBackgroundColor(Color.parseColor("#02a8f3"));
            titleBar.setTitle("助宝彩钻报价");
            offerinfoCredentials.setText(ColorOfferInfo(zhubaoColorResponse, "zhengshu_yes"));  //带证书号
            offerinfoNoCredentials.setText(ColorOfferInfo(zhubaoColorResponse, ""));            //不带证书号
            offerinfoPresellBack.setText(ColorOfferInfo(zhubaoColorResponse, "rmb_yes")); //   rmbCt
            round.setText("");
            noRound.setText("形状 克拉 颜色-强度-光彩 均衡 净度 抛光 对称 荧光 RMB/粒 证书类型");
        }

    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {

    }

    //rap报价
    StringBuilder RapOfferInfo(RapDetailsResponse rapDetailsResponse, String s) {
        StringBuilder RapOfferinfo = new StringBuilder();
        if (rapDetailsResponse.getShape().toUpperCase().equals("ROUND")) {
            RapOfferinfo.append(rapDetailsResponse.getShape());
            RapOfferinfo.append(" ");
        }
        RapOfferinfo.append(rapDetailsResponse.getCarat());
        RapOfferinfo.append("ct");
        RapOfferinfo.append(" ");
        RapOfferinfo.append(rapDiamondResponse.getColor());
        RapOfferinfo.append(" ");
        RapOfferinfo.append(rapDiamondResponse.getClarity());
        RapOfferinfo.append(" ");
        if (rapDetailsResponse.getShape().toUpperCase().equals("ROUND")) {
            RapOfferinfo.append(rapDiamondResponse.getCut());
            RapOfferinfo.append(" ");
        } else {
            RapOfferinfo.append(rapDetailsResponse.getShape());
            RapOfferinfo.append(" ");
        }
        RapOfferinfo.append(rapDiamondResponse.getPolish());
        RapOfferinfo.append(" ");
        RapOfferinfo.append(rapDiamondResponse.getSymmetry());
        RapOfferinfo.append(" ");
        RapOfferinfo.append(rapDiamondResponse.getFloursence());
        RapOfferinfo.append(" ");
        if (s.equals("PresellBack_yes")) {
            RapOfferinfo.append(rapDetailsResponse.getPreSellBack());
            RapOfferinfo.append(" ");
        }
        RapOfferinfo.append("RMB/粒:");
        RapOfferinfo.append(rapDetailsResponse.getRmbprice());
        RapOfferinfo.append(" ");
        RapOfferinfo.append(rapDetailsResponse.getReportType());
        RapOfferinfo.append(" ");
        if (s.equals("zhengshu_yes") || s.equals("PresellBack_yes")) {
            RapOfferinfo.append(rapDetailsResponse.getReportNo());
            RapOfferinfo.append(" ");
        }
        return RapOfferinfo;
    }

    //白钻报价
    StringBuilder offerInfo(ZhubaoDiamondResponse diamondResponse, String info_estimate) {
        StringBuilder offerinfo = new StringBuilder();
        if (diamondResponse.getShape().equals("圆形")) {
            offerinfo.append(diamondResponse.getShape());
            offerinfo.append(" ");
        }
        offerinfo.append(diamondResponse.getCarat());
        offerinfo.append("ct");
        offerinfo.append(" ");
        offerinfo.append(diamondResponse.getColor());
        offerinfo.append(" ");
        offerinfo.append(diamondResponse.getClarity());
        offerinfo.append(" ");
        if (diamondResponse.getShape().equals("圆形")) {
            offerinfo.append(diamondResponse.getCut());
            offerinfo.append(" ");
        } else {
            offerinfo.append(diamondResponse.getShape());
            offerinfo.append(" ");
        }
        offerinfo.append(diamondResponse.getPolish());
        offerinfo.append(" ");
        offerinfo.append(diamondResponse.getSymmetry());
        offerinfo.append(" ");
        offerinfo.append(diamondResponse.getFluorescence());
        offerinfo.append(" ");
        if (info_estimate.equals("PresellBack_yes")) {
            offerinfo.append(diamondResponse.getPresellBack());
            offerinfo.append(" ");
        }
        offerinfo.append("RMB/粒:");
        offerinfo.append(diamondResponse.getRmbprice());
        offerinfo.append(" ");
        offerinfo.append(diamondResponse.getReport());
        offerinfo.append(" ");
        if (info_estimate.equals("zhengshu_yes") || info_estimate.equals("PresellBack_yes")) {
            offerinfo.append(diamondResponse.getReportno());
            offerinfo.append(" ");
        }
        return offerinfo;
    }

    //彩钻报价
    StringBuilder ColorOfferInfo(ZhubaoColorResponse diamondResponse, String info_estimate) {
        StringBuilder ColorOfferinfo = new StringBuilder();
        ColorOfferinfo.append(diamondResponse.getShape());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getCarat());
        ColorOfferinfo.append("ct");
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getColor());
        ColorOfferinfo.append(" ");

        ColorOfferinfo.append(diamondResponse.getIntensity());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getGloss());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getColordistribution());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getClarity());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getPolish());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getSymmetry());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append(diamondResponse.getFluorescence());
        ColorOfferinfo.append(" ");
        ColorOfferinfo.append("RMB/粒:");
        ColorOfferinfo.append(diamondResponse.getRmbprice());
        ColorOfferinfo.append(" ");
        if (info_estimate.equals("rmb_yes")) {
            ColorOfferinfo.append("RMB/粒:");
            ColorOfferinfo.append(diamondResponse.getSaledollorctprice());
            ColorOfferinfo.append(" ");
        }
        ColorOfferinfo.append(diamondResponse.getReport());
        ColorOfferinfo.append(" ");
        if (info_estimate.equals("zhengshu_yes") || info_estimate.equals("rmb_yes")) {
            ColorOfferinfo.append(diamondResponse.getReportno());
            ColorOfferinfo.append(" ");
        }
        return ColorOfferinfo;
    }

    @OnClick({R.id.copy_offer_zhengshu, R.id.copy_offer_zhengshu_no, R.id.copy_offer_presellBack})
    public void onClick(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "复制成功，可以任意粘贴了。", Toast.LENGTH_SHORT);
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData;
        switch (view.getId()) {
            case R.id.copy_offer_zhengshu:
                mClipData = ClipData.newPlainText("Label", offerinfoCredentials.getText().toString());
                cm.setPrimaryClip(mClipData);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case R.id.copy_offer_zhengshu_no:
                mClipData = ClipData.newPlainText("Label", offerinfoNoCredentials.getText().toString());
                cm.setPrimaryClip(mClipData);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case R.id.copy_offer_presellBack:
                mClipData = ClipData.newPlainText("Label", offerinfoPresellBack.getText().toString());
                cm.setPrimaryClip(mClipData);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            default:

        }
    }
}
