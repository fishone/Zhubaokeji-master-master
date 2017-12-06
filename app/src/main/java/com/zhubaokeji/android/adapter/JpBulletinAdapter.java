package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.JpBulletinResponse;

import java.util.ArrayList;

/**
 * Created by fisho on 2017/5/11.
 */

public class JpBulletinAdapter extends RecyclerView.Adapter<JpBulletinAdapter.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private ArrayList<JpBulletinResponse> jpBulletinResponseList;
    private JpBulletinAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public JpBulletinAdapter(Context context, ArrayList<JpBulletinResponse> jpBulletinResponseList)
    {
        mInflater = LayoutInflater.from(context);
        this.jpBulletinResponseList = jpBulletinResponseList;
    }
    @Override
    public JpBulletinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.jp_bulletin_item, parent, false);
        JpBulletinAdapter.ViewHolder holder = new JpBulletinAdapter.ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(JpBulletinAdapter.ViewHolder holder, int position) {
        holder.jp_bulletin_title.setTag(position);
        holder.Chtitle.setText(jpBulletinResponseList.get(position).getChtitle());
        holder.Lasttime.setText(jpBulletinResponseList.get(position).getLasttime());
        holder.Entitle.setText(jpBulletinResponseList.get(position).getEntitle());
    }

    @Override
    public int getItemCount() {
        return jpBulletinResponseList ==null ? 0:jpBulletinResponseList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            int clickPosition= (int) v.getTag();
            mOnItemClickListener.onItemClick(v,clickPosition);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Chtitle;
        TextView Lasttime;
        TextView Entitle;
        LinearLayout jp_bulletin_title;
        public ViewHolder(View itemView) {
            super(itemView);
            Chtitle = (TextView) itemView.findViewById(R.id.jp_bulletin_Chtitle);
            Lasttime = (TextView) itemView.findViewById(R.id.jp_bulletin_Lasttime);
            Entitle = (TextView) itemView.findViewById(R.id.jp_bulletin_Entitle);
            jp_bulletin_title= (LinearLayout) itemView.findViewById(R.id.jp_bulletin_title);

        }
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int tag);
    }

    public void setOnItemClickListener(JpBulletinAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
