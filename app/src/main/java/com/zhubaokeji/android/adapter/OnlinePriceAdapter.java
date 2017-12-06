package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhubaokeji.android.R;

import java.util.ArrayList;

/**
 * Created by fisho on 2017/2/28.
 */

public class OnlinePriceAdapter  extends RecyclerView.Adapter<OnlinePriceAdapter.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private ArrayList<String> itemOnlierPriceData;
    private  int clickPosition;
    private  int itemHeight;
    private  int selectIndex=0;

    private OnlinePriceAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;



    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int tag,String data);
    }

    public OnlinePriceAdapter(Context context, ArrayList<String> itemOnlierPriceData ,int height,int selectIndex)
    {
        mInflater = LayoutInflater.from(context);
        this.itemOnlierPriceData = itemOnlierPriceData;
        this.selectIndex=selectIndex;
        itemHeight=height;
    }

    @Override
    public OnlinePriceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.onlineprice_item, parent, false);
        OnlinePriceAdapter.ViewHolder holder = new OnlinePriceAdapter.ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(OnlinePriceAdapter.ViewHolder holder, int position) {
        if(itemOnlierPriceData.size() >=20){
            holder.tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            if(position ==selectIndex){
                holder.tv.setBackgroundColor(Color.parseColor("#FFEE58"));
            }else if(position >=selectIndex/11*11 &&position <=selectIndex/11*11+10) {
                holder.tv.setBackgroundColor(Color.parseColor("#E0E0E0"));
            }else if(position % 11 == selectIndex %11){
                holder.tv.setBackgroundColor(Color.parseColor("#E0E0E0"));
            }
        }
        if(itemHeight >0){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tv.getLayoutParams();
        params.height=itemHeight;
        holder.tv.setLayoutParams(params);
        }
        holder.tv.setText(itemOnlierPriceData.get(position));
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return itemOnlierPriceData == null ? 0 : itemOnlierPriceData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            clickPosition= (Integer) v.getTag();
            mOnItemClickListener.onItemClick(v,clickPosition,itemOnlierPriceData.get(clickPosition));
            selectIndex=clickPosition;
            Log.i("点击id","点击次数"+clickPosition);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.onlineprice_item);
        }
    }

    public void setOnItemClickListener(OnlinePriceAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
