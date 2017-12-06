package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.ZhubaoShopCartResponse;
import com.zhubaokeji.android.listener.OnItemListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fisho on 2017/7/12.
 */

public class ZhubaoShopCartAdapter extends SwipeMenuAdapter<ZhubaoShopCartAdapter.DefaultViewHolder> {
    private Context mContext;
    private ArrayList<ZhubaoShopCartResponse> mDataList;
    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public ZhubaoShopCartAdapter(Context context, ArrayList<ZhubaoShopCartResponse> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.zhubao_shopcart_item, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {
        final ZhubaoShopCartResponse zhubaoShopCartResponse = mDataList.get(position);
        holder.zhubaoShopCartStoneId.setText(zhubaoShopCartResponse.getStoneid());
        if(zhubaoShopCartResponse.getProtype().equals("0")){
            holder.zhubaoShopCartProtype.setText("白钻");
        }else {
            holder.zhubaoShopCartProtype.setText("彩钻");
        }
        holder.zhubaoShopCartSaleback.setText(zhubaoShopCartResponse.getSaleback());
        holder.zhubaoShopCartAddtiome.setText(zhubaoShopCartResponse.getGroupbytime());
        holder.zhubaoShopCartSaleprice.setText(zhubaoShopCartResponse.getSaledollorctprice());
//        if (zhubaoShopCartResponse.getMilky().equals("无奶") && zhubaoShopCartResponse.getColsh().equals("无咖")) {
//            holder.zhubaoShopCartBgm.setText("NO BGM");
//        }
        holder.select.setChecked(zhubaoShopCartResponse.isSelect());
        holder.zhubaoShopCartBgm.setText(zhubaoShopCartResponse.getMilky()+" "+ zhubaoShopCartResponse.getColsh());
        holder.zhubaoShopCartRmbprice.setText(zhubaoShopCartResponse.getRmbprice());
        holder.zhubaoShopCartIsokname.setText(zhubaoShopCartResponse.getIsokname());
        holder.zhubaoShopCartInfo.setText(zhubaoShopCartResponse.toString());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelect = holder.select.isChecked();
                mOnItemListener.onItemClick(position, isSelect);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
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
        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
