package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.ZhubaoShopCartResponse;
import com.zhubaokeji.android.listener.OnItemListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fisho on 2017/7/12.
 */

public class ZhubaoShopCartAdapter extends  BaseQuickAdapter<ZhubaoShopCartResponse,ZhubaoShopCartAdapter.ShoppingHolder> {
    private Context mContext;
    private List<ZhubaoShopCartResponse> mDataList;
    private OnItemListener mOnItemListener;

    public ZhubaoShopCartAdapter(int layoutResId, @Nullable List<ZhubaoShopCartResponse> data) {
        super(layoutResId, data);
        this.mDataList = data;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

//    public ZhubaoShopCartAdapter(Context context, ArrayList<ZhubaoShopCartResponse> dataList) {
//        this.mContext = context;
//        this.mDataList = dataList;
//    }



    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    protected void convert(final ShoppingHolder helper, ZhubaoShopCartResponse item) {
        final int position=helper.getLayoutPosition();
        helper.zhubaoShopCartStoneId.setText(item.getStoneid());
        if(item.getProtype().equals("0")){
            helper.zhubaoShopCartProtype.setText("白钻");
        }else {
            helper.zhubaoShopCartProtype.setText("彩钻");
        }
        helper.zhubaoShopCartSaleback.setText(item.getSaleback());
        helper.zhubaoShopCartAddtiome.setText(item.getGroupbytime());
        helper.zhubaoShopCartSaleprice.setText(item.getSaledollorctprice());
//        if (zhubaoShopCartResponse.getMilky().equals("无奶") && zhubaoShopCartResponse.getColsh().equals("无咖")) {
//            holder.zhubaoShopCartBgm.setText("NO BGM");
//        }
        helper.select.setChecked(item.isSelect());
        helper.zhubaoShopCartBgm.setText(item.getMilky()+" "+ item.getColsh());
        helper.zhubaoShopCartRmbprice.setText(item.getRmbprice());
        helper.zhubaoShopCartIsokname.setText(item.getIsokname());
        helper.zhubaoShopCartInfo.setText(item.toString());
        helper.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelect = helper.select.isChecked();
                mOnItemListener.onItemClick(position, isSelect);
            }
        });
    }

    public class ShoppingHolder extends BaseViewHolder {
        @BindView(R.id.select)
        CheckBox select;
        @BindView(R.id.zhubao_shopCart_stoneId)
        TextView zhubaoShopCartStoneId;
        @BindView(R.id.zhubao_shopCart_saleback)
        TextView zhubaoShopCartSaleback;
        @BindView(R.id.zhubao_shopCart_saleprice)
        TextView zhubaoShopCartSaleprice;
        @BindView(R.id.zhubao_shopCart_rmbprice)
        TextView zhubaoShopCartRmbprice;
        @BindView(R.id.zhubao_shopCart_protype)
        TextView zhubaoShopCartProtype;
        @BindView(R.id.zhubao_shopCart_addtiome)
        TextView zhubaoShopCartAddtiome;
        @BindView(R.id.zhubao_shopCart_bgm)
        TextView zhubaoShopCartBgm;
        @BindView(R.id.zhubao_shopCart_isokname)
        TextView zhubaoShopCartIsokname;
        @BindView(R.id.zhubao_shopCart_info)
        TextView zhubaoShopCartInfo;
        public ShoppingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
