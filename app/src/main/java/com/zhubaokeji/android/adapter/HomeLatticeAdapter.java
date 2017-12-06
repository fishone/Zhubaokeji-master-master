package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhubaokeji.android.bean.Item;
import com.zhubaokeji.android.R;

import java.util.List;

/**
 * Created by fisho on 2017/3/22.
 */

public class HomeLatticeAdapter extends RecyclerView.Adapter<HomeLatticeAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Item> itemList;
    private LayoutInflater inflater;
    private  int clickPosition;
    private int width;
    private int badg_num=0;
    private HomeLatticeAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int tag);
    }

    public HomeLatticeAdapter(Context context, List<Item> itemList, int width) {
        this.context = context;
        this.itemList = itemList;
        inflater = LayoutInflater.from(context);
        this.width=width;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.jp_home_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
        linearParams.width = width;
        holder.relativeLayout.setLayoutParams(linearParams);
        Item item=itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.englishTitle.setText(item.getEnglishTitle());
        holder.image.setImageResource(item.getResId());
        holder.badgeView.setVisibility(View.GONE);
        if(position ==2){
            if(badg_num !=0){
                holder.badgeView.setVisibility(View.VISIBLE);
                holder.badgeView.setText(String.valueOf(badg_num));
            }
        }
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == itemList ? 0 : itemList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            clickPosition= (Integer) v.getTag();
            mOnItemClickListener.onItemClick(v,clickPosition);
            Log.i("点击id","点击次数"+clickPosition);
//            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView englishTitle;
        public RelativeLayout relativeLayout;
        public LinearLayout  linearLayout;
        public TextView badgeView;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.jp_home_titles);
            englishTitle= (TextView) itemView.findViewById(R.id.jp_home_englishTitle);
            image = (ImageView) itemView.findViewById(R.id.jp_home_image);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.jp_home_item);
            badgeView= (TextView) itemView.findViewById(R.id.bar_num);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.relative_home_item);
        }
    }
    public void setOnItemClickListener(HomeLatticeAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setBadg(int num) {
            this.badg_num=num;
    }
}
