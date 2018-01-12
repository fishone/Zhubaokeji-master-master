package com.zhubaokeji.android.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
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
import com.zhubaokeji.android.bean.ZhubaoDiamondResponse;
import com.zhubaokeji.android.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fisho on 2017/6/7.
 */

public class ZhuaboResultAdapter extends PanelAdapter {
    private static final int TITLE_TYPE = 4;
    private static final int LEFT_TYPE = 0;
    private static final int HEADER_TYPE = 1;
    private static final int CONTENT_TYPE = 2;
    private List<String> TitleList = new ArrayList<>();
    private HashMap<String ,Integer> widthMap=new HashMap<>();
    private ArrayList<ZhubaoDiamondResponse> contentList =new ArrayList<>();
    private ZhuaboResultAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private SharedPreferencesUtil preferencesUtil; //haredPreferences 读写
    private JpUserInfo jpUserInfo;
    private JpSearchRequest jpSearchRequest;
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
                setTitleView(column, (ZhuaboResultAdapter.HeaderViewHolder) holder);
                break;
//            case LEFT_TYPE:
//                setLeftView(row, (ZhuaboResultAdapter.LeftViewHolder) holder);
//                break;
            case CONTENT_TYPE:
                if(holder instanceof ZhuaboResultAdapter.ContentViewHolder){
                    setContentView(row, column, (ZhuaboResultAdapter.ContentViewHolder) holder);
                }
                break;
            case TITLE_TYPE:
                break;
            default:
                if(holder instanceof ZhuaboResultAdapter.ContentViewHolder){
                    setContentView(row, column, (ZhuaboResultAdapter.ContentViewHolder) holder);
                }
        }
    }

    public int getItemViewType(int row, int column) {
        if (column == 0 && row == 0) {
            return TITLE_TYPE;
        }
        if (column == 0) {
            return LEFT_TYPE;
        }
        if (row == 0) {
            return HEADER_TYPE;
        }
        return CONTENT_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_TYPE:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_date_info, parent, false));
//            case LEFT_TYPE:
//                return new LeftViewHolder(LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.listitem_room_info, parent, false));
            case CONTENT_TYPE:
                return new ContentViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_order_info, parent, false));
//            case TITLE_TYPE:
//                return new TitleViewHolder(LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.listitem_title, parent, false));
            default:
                break;
        }
        return new ContentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_order_info, parent, false));
    }

    //标题行设置
    private void setTitleView(int pos, ZhuaboResultAdapter.HeaderViewHolder viewHolder) {
        if (TitleList != null && pos > 0) {
            viewHolder.HeaderTextView.setTag(pos);
            viewHolder.HeaderImageView.setTag(pos);
            viewHolder.HeaderImageView.setVisibility(View.GONE);
            viewHolder.HeaderTextView.setTextColor(Color.parseColor("#000000"));
            switch (pos){
                case 1:
//                    viewHolder.dateTextView.setPadding(70, 20, 70, 20);
                    LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    linearParams.width =300;
                    linearParams.height=120;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(linearParams);
                    break;
                case 2:  case 8:
                    viewHolder.HeaderImageView.setImageResource(R.drawable.sort);
                    viewHolder.HeaderImageView.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams HeaderImageViewLayoutParams =  (LinearLayout.LayoutParams)viewHolder.HeaderImageView.getLayoutParams();
                    HeaderImageViewLayoutParams.width =40;
                    HeaderImageViewLayoutParams.height=40;
                    viewHolder.HeaderImageView.setLayoutParams(HeaderImageViewLayoutParams);
                    LinearLayout.LayoutParams HeaderTextViewLayoutParams =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    HeaderTextViewLayoutParams.width =140;
                    HeaderTextViewLayoutParams.height=120;
                    viewHolder.HeaderTextView.setGravity(Gravity.CENTER);
                    viewHolder.HeaderTextView.setLayoutParams(HeaderTextViewLayoutParams);
                    if(pos ==2){
                        if(jpSearchRequest.getS_qorder().equals("salediscount")){
                            if(jpSearchRequest.getS_qorderdir().equals("asc")){
                                viewHolder.HeaderImageView.setImageResource(R.drawable.sort_up);
                            }else {
                                viewHolder.HeaderImageView.setImageResource(R.drawable.sort_down);
                            }
                        }
                    }
                    if(pos==8){
                        if(jpSearchRequest.getS_qorder().equals("carat")){
                            if(jpSearchRequest.getS_qorderdir().equals("asc")){
                                viewHolder.HeaderImageView.setImageResource(R.drawable.sort_up);
                            }else {
                                viewHolder.HeaderImageView.setImageResource(R.drawable.sort_down);
                            }
                        }
                    }
                    break;
                case 3:
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
                    if(jpSearchRequest.getS_qorder().equals("rmbprice")){
                        if(jpSearchRequest.getS_qorderdir().equals("asc")){
                            viewHolder.HeaderImageView.setImageResource(R.drawable.sort_up);
                        }else {
                            viewHolder.HeaderImageView.setImageResource(R.drawable.sort_down);
                        }
                    }

                    break;
                default:
                    LinearLayout.LayoutParams linearParams2 =  (LinearLayout.LayoutParams)viewHolder.HeaderTextView.getLayoutParams();
                    linearParams2.width =180;
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

    //左边设置
//    private void setLeftView(int pos, final LeftViewHolder viewHolder) {
//        final ZhubaoDiamondResponse zhubaoDiamondResponse = contentList.get(pos - 1);
//        if (zhubaoDiamondResponse != null && pos > 0) {
//            viewHolder.LeftTextView.setTag(pos-1);
//            if (zhubaoDiamondResponse.getIshold() !=null && zhubaoDiamondResponse.getIshold().equals("1")) {
//                viewHolder.LeftTextView.setImageResource(R.drawable.lock);
//                contentList.get(pos-1).setPlaceOrder(false);
//            }else {
//                viewHolder.LeftTextView.setImageResource(R.drawable.cart);
//                contentList.get(pos-1).setPlaceOrder(true);
//            }
//            viewHolder.LeftTextView.setClickable(true);
//            viewHolder.LeftTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = (Integer) v.getTag();
//                    ZhubaoDiamondResponse zhubaoDiamondResponse = contentList.get(position);
//                    mOnItemClickListener.onItemClick(v,position,zhubaoDiamondResponse);
//                }
//            });
//        }
//    }

    //内容设置
    private void setContentView(final int row, final int column, ZhuaboResultAdapter.ContentViewHolder viewHolder) {
        final ZhubaoDiamondResponse orderInfo = contentList.get(row - 1);
//        LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)viewHolder.nameTextView.getLayoutParams();
//        linearParams.width = widthMap.get(column+"");
//        linearParams.height=90;
//        viewHolder.nameTextView.setGravity(Gravity.CENTER);
//        viewHolder.nameTextView.setLayoutParams(linearParams);
        viewHolder.contentImage.setImageBitmap(null);
        viewHolder.contentTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        viewHolder.contentTextView.setTextSize(12);
        viewHolder.contentTextView.setTextColor(Color.parseColor("#000000"));
        if(row==tableRow+1) {
            viewHolder.contentTextView.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
        viewHolder.contentTextView.setTag(row-1);
        viewHolder.contentImage.setTag(row-1);
        if (TitleList != null && column > 0) {
            switch (column){
                case 1:
//                    viewHolder.dateTextView.setPadding(70, 20, 70, 20);
                    RelativeLayout.LayoutParams linearParams =  (RelativeLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    RelativeLayout.LayoutParams contentImageLayoutParams =  (RelativeLayout.LayoutParams)viewHolder.contentImage.getLayoutParams();
                    linearParams.width =265;
                    linearParams.height=120;
                    contentImageLayoutParams.width =35;
                    contentImageLayoutParams.height=30;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams);
                    viewHolder.contentImage.setLayoutParams(contentImageLayoutParams);
                    break;
                case 3:
                    RelativeLayout.LayoutParams linearParams3 =  (RelativeLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    linearParams3.width =240;
                    linearParams3.height=120;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams3);
                    break;
                default:
                    RelativeLayout.LayoutParams linearParams2 =  (RelativeLayout.LayoutParams)viewHolder.contentTextView.getLayoutParams();
                    linearParams2.width =180;
                    linearParams2.height=120;
                    viewHolder.contentTextView.setGravity(Gravity.CENTER);
                    viewHolder.contentTextView.setLayoutParams(linearParams2);
//                    viewHolder.dateTextView.setPadding(30, 20, 30, 20);
            }
        }
        if (orderInfo != null) {
            switch (column){
                case 1:
                    if (!"".equals(orderInfo.getImgurl()) && Patterns.WEB_URL.matcher(orderInfo.getImgurl()).matches()) {
                        viewHolder.contentImage.setImageResource(R.drawable.tupian);
                        viewHolder.contentImage.setClickable(true);
                        viewHolder.contentTextView.setClickable(true);
                        viewHolder.contentImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position = (Integer) v.getTag();
                                ZhubaoDiamondResponse zhubaoDiamondResponse = contentList.get(position);
                                mOnItemClickListener.onImageClick(v,position,zhubaoDiamondResponse);

                            }
                        });
                        viewHolder.contentTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position = (Integer) v.getTag();
                                ZhubaoDiamondResponse zhubaoDiamondResponse = contentList.get(position);
                                mOnItemClickListener.onImageClick(v,position,zhubaoDiamondResponse);

                            }
                        });
                    }
                    viewHolder.contentTextView.setText(orderInfo.getSupname());
                    break;
                case 2:
                    viewHolder.contentTextView.setText(orderInfo.getSaleback());
                    break;
                case 3:
                    viewHolder.contentTextView.setText(orderInfo.getDollorprice());
                    break;
                case 4:
                    viewHolder.contentTextView.setText(orderInfo.getMilky());
                    break;
                case 5:
                    viewHolder.contentTextView.setText(orderInfo.getColsh());
                    break;
                case 6:
                    viewHolder.contentTextView.setText(orderInfo.getGreen());
                    break;
                case 7:
                    viewHolder.contentTextView.setText(orderInfo.getShape());
                    break;
                case 8:
                    viewHolder.contentTextView.setText(orderInfo.getCarat());
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
                    viewHolder.contentTextView.setText(orderInfo.getReport());
                    break;
            }
        }
        if(column ==1 &&!orderInfo.getImgurl().equals("")){
            return;
        }
        viewHolder.contentTextView.setClickable(true);
        viewHolder.contentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                ZhubaoDiamondResponse zhubaoDiamondResponse = contentList.get(position);
                mOnItemClickListener.onRowClick(v,position,zhubaoDiamondResponse);

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
    private static class LeftViewHolder extends RecyclerView.ViewHolder {
        public ImageView LeftTextView;

        public LeftViewHolder(View view) {
            super(view);
            this.LeftTextView = (ImageView) view.findViewById(R.id.left_item);
        }
    }

//    private static class TitleViewHolder extends RecyclerView.ViewHolder {
//        public TextView titleTextView;
//        public TitleViewHolder(View view) {
//            super(view);
//            this.titleTextView = (TextView) view.findViewById(R.id.title);
//        }
//    }

    private static class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTextView;
        public View view;
        public ImageView contentImage;


        public ContentViewHolder(View view) {
            super(view);
            this.view = view;
            this.contentTextView = (TextView) view.findViewById(R.id.content_item);
            this.contentImage= (ImageView) view.findViewById(R.id.content_image);
        }
    }

    public void setTitleList(List<String> TitleList) {
        this.TitleList = TitleList;
    }

    public void setContentList(ArrayList<ZhubaoDiamondResponse> contentList, int row, JpSearchRequest jpSearchRequest) {
        this.contentList = contentList;
        this.tableRow=row;
        this.jpSearchRequest=jpSearchRequest;
    }
    public void setJpUserInfo(JpUserInfo jpUserInfo) {
        this.jpUserInfo = jpUserInfo;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int tag,ZhubaoDiamondResponse zhubaoDiamondResponse);
        void onRowClick(View view, int tag,ZhubaoDiamondResponse zhubaoDiamondResponse);
        void onSortClick(View view,int tag);
        void onImageClick(View view,int tag,ZhubaoDiamondResponse zhubaoDiamondResponse);
    }

    public void setOnItemClickListener(ZhuaboResultAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
