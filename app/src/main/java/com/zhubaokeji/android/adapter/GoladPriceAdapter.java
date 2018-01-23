package com.zhubaokeji.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelin.scrollablepanel.library.PanelAdapter;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.goldResponse;

import java.util.List;

/**
 * Created by fisho on 2017/6/19.
 */

public class GoladPriceAdapter extends PanelAdapter {
    private List<goldResponse> itemList;
    private int width;


    @Override
    public int getRowCount() {
        return null == itemList ? 0 : itemList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        setContentView(row, column, (HeaderViewHolder) holder);
    }

    private void setContentView(int row, int column, HeaderViewHolder holder) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.item.getLayoutParams();
        params.width=width;
        params.height=250;
        holder.item.setLayoutParams(params);
        holder.item.setGravity(Gravity.CENTER);
        final goldResponse orderInfo = itemList.get(row);
        switch (column){
            case 0:
                holder.item.setText(orderInfo.getName());
                break;
            case 1:
                holder.item.setText(orderInfo.getBuy());
                break;
            case 2:
                holder.item.setText(orderInfo.getSell());
                break;
            case 3:
                holder.item.setText(orderInfo.getMaxPrice()+ "\n" +orderInfo.getMinPrice());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoladPriceAdapter.HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gold_item, parent, false));
    }


    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.gold_item);
        }
    }
    public void setTitleList(List<goldResponse> itemList,int width) {
        this.width=width;
        this.itemList = itemList;
    }
}
