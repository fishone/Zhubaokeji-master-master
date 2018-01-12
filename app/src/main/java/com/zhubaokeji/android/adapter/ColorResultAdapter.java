package com.zhubaokeji.android.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelin.scrollablepanel.library.PanelAdapter;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpSearchRequest;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.ZhubaoColorResponse;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fisho on 2017/6/8.
 */

public class ColorResultAdapter  extends PanelAdapter {
    private static final int HEADER_TYPE = 1;
    private static final int CONTENT_TYPE = 2;
    private List<String> TitleList = new ArrayList<>();
    private HashMap<String ,Integer> widthMap=new HashMap<>();
    private ArrayList<ZhubaoColorResponse> contentList =new ArrayList<>();
    private ColorResultAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    private JpUserInfo jpUserInfo;
    private JpSearchRequest searchRequest;
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
                setTitleView(column, (ColorResultAdapter.HeaderViewHolder) holder);
                break;
            case CONTENT_TYPE:
                if(holder instanceof ColorResultAdapter.ContentViewHolder){
                    setContentView(row, column, (ColorResultAdapter.ContentViewHolder) holder);
                }
                break;
            default:
                if(holder instanceof ColorResultAdapter.ContentViewHolder){
                    setContentView(row, column, (ColorResultAdapter.ContentViewHolder) holder);
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
                return new ColorResultAdapter.HeaderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_date_info, parent, false));
            case CONTENT_TYPE:
                return new ColorResultAdapter.ContentViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_order_info, parent, false));
            default:
                break;
        }
        return new ColorResultAdapter.ContentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_order_info, parent, false));
    }

    //标题行设置
    private void setTitleView(int pos, ColorResultAdapter.HeaderViewHolder viewHolder) {
        if (TitleList != null && pos > 0) {
            viewHolder.HeaderTextView.setTag(pos);
            viewHolder.HeaderImageView.setTag(pos);
            viewHolder.HeaderImageView.setVisibility(View.GONE);
            viewHolder.HeaderTextView.setTextColor(Color.parseColor("#000000"));
            switch (pos){
                case 1:case 7:case 8:case 9:case 3:
//                    viewHolder.dateTextView.setPadding(70, 20, 70, 20);
                    LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    linearParams.width =300;
                    linearParams.height=120;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(linearParams);
                    break;
                case 2:
                    viewHolder.HeaderImageView.setImageResource(R.drawable.sort);
                    viewHolder.HeaderImageView.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams HeaderImageViewLayoutParams =  (LinearLayout.LayoutParams)viewHolder.HeaderImageView.getLayoutParams();
                    HeaderImageViewLayoutParams.width =40;
                    HeaderImageViewLayoutParams.height=40;
                    viewHolder.HeaderImageView.setLayoutParams(HeaderImageViewLayoutParams);
                    LinearLayout.LayoutParams HeaderTextViewLayoutParams =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    HeaderTextViewLayoutParams.width =200;
                    HeaderTextViewLayoutParams.height=120;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(HeaderTextViewLayoutParams);
                    if(pos ==2){
                        if(searchRequest.getS_qorder().equals("saledollorctprice")){
                            if(searchRequest.getS_qorderdir().equals("asc")){
                                viewHolder.HeaderImageView.setImageResource(R.drawable.sort_up);
                            }else {
                                viewHolder.HeaderImageView.setImageResource(R.drawable.sort_down);
                            }
                        }
                    }
                    break;
                case 5:
                    LinearLayout.LayoutParams linearParams3 =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    linearParams3.width =200;
                    linearParams3.height=120;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(linearParams3);
                    LinearLayout.LayoutParams HeaderImageViewLayoutParams2 =  (LinearLayout.LayoutParams)viewHolder.HeaderImageView.getLayoutParams();
                    HeaderImageViewLayoutParams2.width =40;
                    HeaderImageViewLayoutParams2.height=40;
                    viewHolder.HeaderImageView.setLayoutParams(HeaderImageViewLayoutParams2);
                    viewHolder.HeaderImageView.setImageResource(R.drawable.sort);
                    viewHolder.HeaderImageView.setVisibility(View.VISIBLE);
                    if(searchRequest.getS_qorder().equals("carat")){
                        if(searchRequest.getS_qorderdir().equals("asc")){
                            viewHolder.HeaderImageView.setImageResource(R.drawable.sort_up);
                        }else {
                            viewHolder.HeaderImageView.setImageResource(R.drawable.sort_down);
                        }
                    }

                    break;
                default:
                    LinearLayout.LayoutParams linearParams2 =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    linearParams2.width =200;
                    linearParams2.height=120;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(linearParams2);
//                    viewHolder.dateTextView.setPadding(30, 20, 30, 20);
            }
            viewHolder.HeaderTextView.setClickable(true);
            viewHolder.HeaderImageView.setClickable(true);
            viewHolder.HeaderTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    mOnItemClickListener.onSortClick(v,position);
                }
            });
            viewHolder.HeaderImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    mOnItemClickListener.onSortClick(v,position);
                }
            });
            viewHolder.HeaderTextView.setText(TitleList.get(pos-1));
//            viewHolder.dateTextView.measure(0, 0); // 计算子项View 的宽高
//            int list_child_item_height = viewHolder.dateTextView.getMeasuredWidth();
//            widthMap.put(pos+"",list_child_item_height);
        }
    }


    //内容设置
    private void setContentView(final int row, final int column, ColorResultAdapter.ContentViewHolder viewHolder) {
        final ZhubaoColorResponse orderInfo = contentList.get(row - 1);
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
                case 1:case 7:case 8:case 9:case 3:
//                    viewHolder.dateTextView.setPadding(70, 20, 70, 20);
                    RelativeLayout.LayoutParams linearParams =  (RelativeLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    linearParams.width =300;
                    linearParams.height=120;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams);
                    break;
                case 2 : case 5:
                    RelativeLayout.LayoutParams linearParams3 =  (RelativeLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    linearParams3.width =240;
                    linearParams3.height=120;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams3);
                    break;
                default:
                    RelativeLayout.LayoutParams linearParams2 =  (RelativeLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    linearParams2.width =200;
                    linearParams2.height=120;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams2);
                    break;
//                    viewHolder.dateTextView.setPadding(30, 20, 30, 20);
            }
        }
        if (orderInfo != null) {
            switch (column){
                case 1:
                    viewHolder.contentTextView.setText(orderInfo.getSupname());
                    break;
                case 2:
                    viewHolder.contentTextView.setText(orderInfo.getSaledollorctprice());
                    break;
                case 3:
                    viewHolder.contentTextView.setText(orderInfo.getDollorprice());
                    break;
                case 4:
                    viewHolder.contentTextView.setText(orderInfo.getShape());
                    break;
                case 5:
                    viewHolder.contentTextView.setText(orderInfo.getCarat());
                    break;
                case 6:
                    viewHolder.contentTextView.setText(orderInfo.getColor());
                    break;
                case 7:
                    viewHolder.contentTextView.setText(orderInfo.getIntensity());
                    break;
                case 8:
                    viewHolder.contentTextView.setText(orderInfo.getGloss());
                    break;
                case 9:
                    viewHolder.contentTextView.setText(orderInfo.getColordistribution());
                    break;
                case 10:
                    viewHolder.contentTextView.setText(orderInfo.getClarity());
                    break;
                case 11:
                    viewHolder.contentTextView.setText(orderInfo.getPolish());
                    break;
                case 12:
                    viewHolder.contentTextView.setText(orderInfo.getSymmetry());
                    break;
                case 13:
                    viewHolder.contentTextView.setText(orderInfo.getFluorescence());
                    break;
                case 14:
                    viewHolder.contentTextView.setText(orderInfo.getReport());
                    break;
            }
        }
        viewHolder.contentTextView.setClickable(true);
        viewHolder.contentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                ZhubaoColorResponse zhubaoColorResponse = contentList.get(position);
                mOnItemClickListener.onRowClick(v,position,zhubaoColorResponse);

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

    public void setContentList(ArrayList<ZhubaoColorResponse> contentList, int row, JpSearchRequest searchRequest) {
        this.contentList = contentList;
        this.tableRow=row;
        this.searchRequest=searchRequest;
    }
    public void setJpUserInfo(JpUserInfo jpUserInfo) {
        this.jpUserInfo = jpUserInfo;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onRowClick(View view, int tag,ZhubaoColorResponse zhubaoColorResponse);
        void onSortClick(View view,int tag);
    }

    public void setOnItemClickListener(ColorResultAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
