package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhubaokeji.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fisho on 2017/3/13.
 */

public class CaratSectionAdapter extends RecyclerView.Adapter<CaratSectionAdapter.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private List<String> CaratSectionData;
    private  int clickPosition;
    private CaratSectionAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int tag,String data);
    }

    public CaratSectionAdapter(Context context, List<String> CaratSectionData)
    {
        mInflater = LayoutInflater.from(context);
        this.CaratSectionData = CaratSectionData;
    }

    @Override
    public CaratSectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.caratsection_item, parent, false);
        CaratSectionAdapter.ViewHolder holder = new CaratSectionAdapter.ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CaratSectionAdapter.ViewHolder holder, int position) {
        holder.tv.setText(CaratSectionData.get(position));
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return CaratSectionData == null ? 0 : CaratSectionData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            clickPosition= (Integer) v.getTag();
            mOnItemClickListener.onItemClick(v,clickPosition,CaratSectionData.get(clickPosition));
            Log.i("点击id","点击次数"+clickPosition);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.CaratSection_item);
        }
    }

    public void setOnItemClickListener(CaratSectionAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
