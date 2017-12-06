package com.zhubaokeji.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.ThreeEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fisho on 2017/3/14.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private List<String> searchRequirement;
    private ArrayList<String> isClickList=new ArrayList<>();;
    private  int clickPosition=-1;
    private ArrayList<Boolean> isClicks=new ArrayList<Boolean>();;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    private SearchAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public ArrayList<ThreeEx> threeExList;
    public int mSelectedItem=-1;


    public SearchAdapter(Context context, List<String> searchRequirement)
    {
        mInflater = LayoutInflater.from(context);
        this.searchRequirement  = searchRequirement;
    }
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.jp_search_item, parent, false);
        SearchAdapter.ViewHolder holder = new SearchAdapter.ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        holder.tv.setText(searchRequirement.get(position));
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        isClicks.add(position,false);
        if(searchRequirement.get(0).equals("3EX") && position==mSelectedItem){
            holder.itemView.setSelected(true);
        }else{
            holder.itemView.setSelected(false);
        }
        if (searchRequirement.get(position).equals("圆形") && position==0) {
            isClicks.set(position,true);
            holder.itemView.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return searchRequirement ==null ? 0:searchRequirement.size();
    }

    @Override
    public void onClick(View v) {
        clickPosition= (Integer) v.getTag();
        if(searchRequirement.get(0).equals("3EX")){
            notifyDataSetChanged();
            if(v.isSelected()){
                v.setSelected(false);
            }else{
                v.setSelected(true);
            }
            mSelectedItem=clickPosition;
            StringBuilder threeEx= new StringBuilder();
            threeEx.append(searchRequirement.get(clickPosition));
            mOnItemClickListener.onItemClick(v,clickPosition,threeEx);
            return;
        }
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            if(v.isSelected()){
                isClicks.set(clickPosition,false);
                v.setSelected(false);
            }else{
                isClicks.set(clickPosition,true);
                v.setSelected(true);
            }
            List<Integer> list = new ArrayList<Integer>();
            for (int k = 0; k < searchRequirement.size(); k++) {
                if (isClicks.get(k)) {
                    list.add(k);
                }
            }

            StringBuilder stringBuffer = null;
            if (list != null && list.size() > -1) {
                stringBuffer= new StringBuilder();
                for (int i = 1; i <= list.size(); i++) {
                    int isSelect = 0;
                    int selected = list.size();
                    if (selected - i != 0 && list.size() != 1) {
                        isSelect = list.get(i - 1);
                        stringBuffer.append(searchRequirement.get(isSelect));
                        stringBuffer.append(",");
                    } else {
                        isSelect = list.get(i - 1);
                        stringBuffer.append(searchRequirement.get(isSelect));
                    }
                }
            }
            mOnItemClickListener.onItemClick(v,clickPosition,stringBuffer);
            Log.i("点击id","点击次数"+clickPosition);
//            notifyDataSetChanged();
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.search_item);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int tag,StringBuilder sb);
    }

    public void setOnItemClickListener(SearchAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
