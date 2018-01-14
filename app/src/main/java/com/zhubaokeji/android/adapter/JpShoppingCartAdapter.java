package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhubaokeji.android.BR;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpShoppingCartResponse;
import com.zhubaokeji.android.listener.OnItemListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fisho on 2017/5/15.
 */

public class JpShoppingCartAdapter extends BaseQuickAdapter<JpShoppingCartResponse,JpShoppingCartAdapter.ShoppingHolder> {

    private List<JpShoppingCartResponse> mDataList;
    private LayoutInflater mInflater;
    private OnItemListener mOnItemListener;

    public JpShoppingCartAdapter(final int layoutResId, @Nullable List<JpShoppingCartResponse> data) {
        super(layoutResId, data);

        this.mDataList=data;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    @Override
    protected void convert( final ShoppingHolder helper, JpShoppingCartResponse item) {
        final int position=helper.getLayoutPosition();
        helper.jpShoppingSaleback.setText(item.getSaleback());
        helper.jpShoppingSaleprice.setText(item.getSaleprice());
        helper.jpShoppingEyeclean.setText(item.getEyeclean());
        helper.jpShoppingReportno.setText(item.getReportno());


        helper.jpShoppingEndsaleback.setText(item.getEndsaleback());
        helper.jpShoppingBgm.setText(item.getMilky() +" "+item.getColsh()+ " "+ item.getGreen());
        helper.jpShoppingIsokname.setText(item.getIsokname());
        helper.jpShoppingInfo.setText(item.toString());
        helper.select.setChecked(item.isSelect());
        if(item.getAddress().equals("ind")){
            helper.jpShoppingAddress.setText("ind");
            helper.jpShoppingAddress.setTextColor(mContext.getResources().getColor(R.color.red_500));
        }else {
            helper.jpShoppingAddress.setText(item.getAddress());
        }
        helper.offerSelect.setChecked(item.isOfferSelect());
        if (item.isOfferSelect()) {
            helper.jpShoppingSaleprice.setTextColor(mContext.getResources().getColor(R.color.red_500));
            helper.jpShoppingEndsaleback.setTextColor(mContext.getResources().getColor(R.color.red_500));
        } else {
            helper.jpShoppingSaleprice.setTextColor(mContext.getResources().getColor(R.color.grey_900));
            helper.jpShoppingEndsaleback.setTextColor(mContext.getResources().getColor(R.color.grey_900));
        }
        if (item.isSelect()) {
            helper.layoutOffer.setVisibility(View.VISIBLE);
        } else {
            helper.layoutOffer.setVisibility(View.GONE);
            helper.offerSelect.setChecked(false);
        }



        helper.jpShoppingReportno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.onItemReportClick(mDataList.get(position));
            }
        });

        helper.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelect = helper.select.isChecked();
                if (isSelect) {
                    helper.layoutOffer.setVisibility(View.VISIBLE);
                }
                mOnItemListener.onItemClick(position, isSelect);
            }
        });

        helper.offerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelect = helper.offerSelect.isChecked();
                mOnItemListener.onItemyOfferClick(position, isSelect);
            }
        });
    }

    @Override
    protected ShoppingHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            return new ShoppingHolder(getItemView(R.layout.jp_shoppingcart_item, parent));
    }

//    @Override
//    protected View getItemView(int layoutResId, ViewGroup parent) {
//       View D= LayoutInflater.from(parent.getContext()).inflate(R.layout.jp_shoppingcart_item, parent, false);
//
////        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.jp_shoppingcart_item, parent, false);
////        if (binding == null) {
////            return super.getItemView(layoutResId, parent);
////        }
////        View view = binding.getRoot();
//        return D;
//    }



//    public void remove(int itemModel) {
//        mDataList.remove(itemModel);
//    }

    public static class ShoppingHolder extends BaseViewHolder {
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

        public ShoppingHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
