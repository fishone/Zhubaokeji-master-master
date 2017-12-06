package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpShoppingCartResponse;
import com.zhubaokeji.android.listener.OnItemListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fisho on 2017/5/15.
 */

public class JpShoppingCartAdapter extends SwipeMenuAdapter<JpShoppingCartAdapter.DefaultViewHolder> {

    private Context mContext;
    private ArrayList<JpShoppingCartResponse> mDataList;
    private LayoutInflater mInflater;
    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public JpShoppingCartAdapter(Context context, ArrayList<JpShoppingCartResponse> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {
        final JpShoppingCartResponse jpShoppingCartResponse = mDataList.get(position);
        holder.jpShoppingSaleback.setText(jpShoppingCartResponse.getSaleback());
        holder.jpShoppingSaleprice.setText(jpShoppingCartResponse.getSaleprice());
        holder.jpShoppingEyeclean.setText(jpShoppingCartResponse.getEyeclean());
        holder.jpShoppingReportno.setText(jpShoppingCartResponse.getReportno());
        holder.jpShoppingEndsaleback.setText(jpShoppingCartResponse.getEndsaleback());
        holder.jpShoppingBgm.setText(jpShoppingCartResponse.getMilky() +" "+jpShoppingCartResponse.getColsh()+ " "+ jpShoppingCartResponse.getGreen());
        holder.jpShoppingIsokname.setText(jpShoppingCartResponse.getIsokname());
        holder.jpShoppingInfo.setText(jpShoppingCartResponse.toString());
        holder.select.setChecked(jpShoppingCartResponse.isSelect());
        if(jpShoppingCartResponse.getAddress().equals("ind")){
            holder.jpShoppingAddress.setText("ind");
            holder.jpShoppingAddress.setTextColor(mContext.getResources().getColor(R.color.red_500));
        }else {
            holder.jpShoppingAddress.setText(jpShoppingCartResponse.getAddress());
        }
        holder.offerSelect.setChecked(jpShoppingCartResponse.isOfferSelect());
        if (jpShoppingCartResponse.isOfferSelect()) {
            holder.jpShoppingSaleprice.setTextColor(mContext.getResources().getColor(R.color.red_500));
            holder.jpShoppingEndsaleback.setTextColor(mContext.getResources().getColor(R.color.red_500));
        } else {
            holder.jpShoppingSaleprice.setTextColor(mContext.getResources().getColor(R.color.grey_900));
            holder.jpShoppingEndsaleback.setTextColor(mContext.getResources().getColor(R.color.grey_900));
        }
        if (jpShoppingCartResponse.isSelect()) {
            holder.layoutOffer.setVisibility(View.VISIBLE);
        } else {
            holder.layoutOffer.setVisibility(View.GONE);
            holder.offerSelect.setChecked(false);
        }
        holder.jpShoppingReportno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.onItemReportClick(mDataList.get(position));
            }
        });

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelect = holder.select.isChecked();
                if (isSelect) {
                    holder.layoutOffer.setVisibility(View.VISIBLE);
                }
                mOnItemListener.onItemClick(position, isSelect);
            }
        });

        holder.offerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelect = holder.offerSelect.isChecked();
                mOnItemListener.onItemyOfferClick(position, isSelect);
            }
        });
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.jp_shoppingcart_item, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void remove(int itemModel) {
        mDataList.remove(itemModel);
    }

    public int getItem(int pos) {
        return pos;
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select)
        CheckBox select;
        @BindView(R.id.offerSelect)
        CheckBox offerSelect;
        @BindView(R.id.jp_shopping_saleprice)
        TextView jpShoppingSaleprice;
        @BindView(R.id.jp_shopping_eyeclean)
        TextView jpShoppingEyeclean;
        @BindView(R.id.jp_shopping_endsaleback)
        TextView jpShoppingEndsaleback;
        @BindView(R.id.jp_shopping_Reportno)
        TextView jpShoppingReportno;
        @BindView(R.id.jp_shopping_saleback)
        TextView jpShoppingSaleback;
        @BindView(R.id.jp_shopping_bgm)
        TextView jpShoppingBgm;
        @BindView(R.id.jp_shopping_isokname)
        TextView jpShoppingIsokname;
        @BindView(R.id.jp_shopping_info)
        TextView jpShoppingInfo;
        @BindView(R.id.jp_shopping_address)
        TextView jpShoppingAddress;
        @BindView(R.id.layout_offer)
        LinearLayout layoutOffer;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
