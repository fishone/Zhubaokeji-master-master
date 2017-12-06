package com.zhubaokeji.android.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelin.scrollablepanel.library.PanelAdapter;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.ZhubaoOrderResponse;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fisho on 2017/7/13.
 */

public class ZhubaoOrderAdapter extends PanelAdapter {
    private static final int HEADER_TYPE = 1;
    private static final int CONTENT_TYPE = 2;
    private List<String> TitleList = new ArrayList<>();
    private HashMap<String ,Integer> widthMap=new HashMap<>();
    private ArrayList<ZhubaoOrderResponse> contentList =new ArrayList<>();
    private ZhubaoOrderAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    private JpUserInfo userInfo;
    private int tableRow=0;
    @Override
    public int getRowCount() {
        return contentList.size()+1;
    }

    @Override
    public int getColumnCount() {
        return TitleList.size()+1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        int viewType = getItemViewType(row, column);
        switch (viewType) {
            case HEADER_TYPE:
                setTitleView(column, (ZhubaoOrderAdapter.HeaderViewHolder) holder);
                break;
            case CONTENT_TYPE:
                if(holder instanceof ZhubaoOrderAdapter.ContentViewHolder){
                    setContentView(row, column, (ZhubaoOrderAdapter.ContentViewHolder) holder);
                }
                break;
            default:
                if(holder instanceof ZhubaoOrderAdapter.ContentViewHolder){
                    setContentView(row, column, (ZhubaoOrderAdapter.ContentViewHolder) holder);
                }
        }
    }


    public int getItemViewType(int row, int column) {
        if (row == 0) {
            return HEADER_TYPE;
        }
        return CONTENT_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_TYPE:
                return new ZhubaoOrderAdapter.HeaderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_date_info, parent, false));
            case CONTENT_TYPE:
                return new ZhubaoOrderAdapter.ContentViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_order_info, parent, false));
            default:
                break;
        }
        return new ZhubaoOrderAdapter.ContentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_order_info, parent, false));
    }

    //标题行设置
    private void setTitleView(int pos, ZhubaoOrderAdapter.HeaderViewHolder viewHolder) {
        if (TitleList != null && pos > 0) {
            viewHolder.HeaderTextView.setTag(pos);
            viewHolder.HeaderImageView.setTag(pos);
            viewHolder.HeaderImageView.setVisibility(View.GONE);
            viewHolder.HeaderTextView.setTextColor(Color.parseColor("#000000"));
            switch (pos){
                case 2: case 3: case 18: case 20:
//                    viewHolder.dateTextView.setPadding(70, 20, 70, 20);
                    LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    linearParams.width =250;
                    linearParams.height=90;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(linearParams);
                    break;
                default:
                    LinearLayout.LayoutParams linearParams2 =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    linearParams2.width =140;
                    linearParams2.height=90;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(linearParams2);
//                    viewHolder.dateTextView.setPadding(30, 20, 30, 20);
            }
            viewHolder.HeaderTextView.setText(TitleList.get(pos-1));
//            viewHolder.dateTextView.measure(0, 0); // 计算子项View 的宽高
//            int list_child_item_height = viewHolder.dateTextView.getMeasuredWidth();
//            widthMap.put(pos+"",list_child_item_height);
        }
    }


    //内容设置
    private void setContentView(final int row, final int column, ZhubaoOrderAdapter.ContentViewHolder viewHolder) {
        final ZhubaoOrderResponse orderInfo = contentList.get(row - 1);
//        LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)viewHolder.nameTextView.getLayoutParams();
//        linearParams.width = widthMap.get(column+"");
//        linearParams.height=90;
//        viewHolder.nameTextView.setGravity(Gravity.CENTER);
//        viewHolder.nameTextView.setLayoutParams(linearParams);
        viewHolder.contentTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        viewHolder.contentTextView.setTextSize(12);
        viewHolder.contentTextView.setTextColor(Color.parseColor("#000000"));
        if(row==tableRow+1) {
            viewHolder.contentTextView.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
        viewHolder.contentTextView.setTag(row-1);
        if (TitleList != null && column > 0) {
            switch (column){
                case 2: case 3: case 18: case 20:
//                    viewHolder.dateTextView.setPadding(70, 20, 70, 20);
                    LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    linearParams.width =250;
                    linearParams.height=90;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams);
                    break;
                default:
                    LinearLayout.LayoutParams linearParams2 =  (LinearLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    linearParams2.width =140;
                    linearParams2.height=90;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams2);
//                    viewHolder.dateTextView.setPadding(30, 20, 30, 20);
            }
        }
        if (orderInfo != null) {
            switch (column){
                case 1:
                    viewHolder.contentTextView.setText(orderInfo.getStateName());
                    break;
                case 2:
                    viewHolder.contentTextView.setText(orderInfo.getOrderid());
                    break;
                case 3:
                    viewHolder.contentTextView.setText(orderInfo.getOrderdate());
                    break;
                case 4:
                    if(orderInfo.getProtype().equals("0")){
                        viewHolder.contentTextView.setText("白钻");
                    }else {
                        viewHolder.contentTextView.setText("彩钻");
                    }
                    break;
                case 5:
                    viewHolder.contentTextView.setText(orderInfo.getShape());
                    break;
                case 6:
                    viewHolder.contentTextView.setText(orderInfo.getCarat());
                    break;
                case 7:
                    if(orderInfo.getIntensity().equals("")){
                        viewHolder.contentTextView.setText("-");
                    }else {
                        viewHolder.contentTextView.setText(orderInfo.getIntensity());
                    }
                    break;
                case 8:
                    if(orderInfo.getIntensity().equals("")){
                        viewHolder.contentTextView.setText("-");
                    }else {
                        viewHolder.contentTextView.setText(orderInfo.getGloss());
                    }
                    break;
                case 9:
                    viewHolder.contentTextView.setText(orderInfo.getColor());
                    break;
                case 10:
                    viewHolder.contentTextView.setText(orderInfo.getClarity());
                    break;
                case 11:
                    viewHolder.contentTextView.setText(orderInfo.getCut());
                    break;
                case 12:
                    viewHolder.contentTextView.setText(orderInfo.getPolish());
                    break;
                case 13:
                    viewHolder.contentTextView.setText(orderInfo.getSymmetry());
                    break;
                case 14:
                    viewHolder.contentTextView.setText(orderInfo.getFluorescence());
                    break;
                case 15:
                    viewHolder.contentTextView.setText(orderInfo.getMilky());
                    break;
                case 16:
                    viewHolder.contentTextView.setText(orderInfo.getColsh());
                    break;
                case 17:
                    viewHolder.contentTextView.setText(orderInfo.getReport());
                    break;
                case 18:
                    viewHolder.contentTextView.setText(orderInfo.getReportno());
                    break;
                case 19:
                    viewHolder.contentTextView.setText(orderInfo.getSaleback());
                    break;
                case 20:
                    viewHolder.contentTextView.setText(orderInfo.getD_rmbprice());
                    break;
                case 21:
                    viewHolder.contentTextView.setText(orderInfo.getStateName());
                    break;
            }
        }
        viewHolder.contentTextView.setClickable(true);
        viewHolder.contentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                mOnItemClickListener.onRowClick(v,position);

            }
        });
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView HeaderTextView;
        public ImageView HeaderImageView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.HeaderTextView = (TextView) itemView.findViewById(R.id.header_item);
            this.HeaderImageView= (ImageView) itemView.findViewById(R.id.header_image_item);
        }

    }

    private static class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTextView;
        public View view;

        public ContentViewHolder(View view) {
            super(view);
            this.view = view;
            this.contentTextView = (TextView) view.findViewById(R.id.content_item);
        }
    }

    public void setTitleList(List<String> TitleList) {
        this.TitleList = TitleList;
    }

    public void setContentList(ArrayList<ZhubaoOrderResponse> contentList, int row) {
        this.contentList = contentList;
        this.tableRow=row;
    }
    public void setJpUserInfo(JpUserInfo jpUserInfo) {
        this.userInfo = jpUserInfo;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onRowClick(View view, int tag);

    }

    public void setOnItemClickListener(ZhubaoOrderAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
