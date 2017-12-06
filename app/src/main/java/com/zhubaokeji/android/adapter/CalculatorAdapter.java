package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhubaokeji.android.R;
import java.util.List;


/**
 * Created by fisho on 2017/2/16.
 */

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private List<String> mDatas;
    private  int clickPosition;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int tag,String data);
    }

    public CalculatorAdapter(Context context, List<String> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.calculaor_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        if (position == clickPosition) {
            holder.tv.setSelected(true);
        } else {
            holder.tv.setSelected(false);

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            clickPosition= (Integer) v.getTag();
            mOnItemClickListener.onItemClick(v,clickPosition,mDatas.get(clickPosition));
            notifyDataSetChanged();

        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.calculator_item);
        }
    }
}
