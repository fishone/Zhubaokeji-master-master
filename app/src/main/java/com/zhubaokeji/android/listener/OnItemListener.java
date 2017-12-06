package com.zhubaokeji.android.listener;

import android.view.View;

import com.zhubaokeji.android.bean.JpShoppingCartResponse;

/**
 * Created by Song on 2016/9/7.
 */
public interface OnItemListener {
    void onItemClick(int position,boolean isChecked);
    void onItemReportClick(JpShoppingCartResponse jpShoppingCartResponse);
    void onItemyOfferClick(int position,boolean isChecked);
}
