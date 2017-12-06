package com.zhubaokeji.android.biz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.adapter.CaratSectionAdapter;
import com.zhubaokeji.library.WheelView;

import java.util.ArrayList;

/**
 * 创建人:YUIZHI
 * 包名：com.zhubaokeji.android.biz
 * 创建时间：2017/8/29
 * 描述：
*/
public class PopupWheel {
    RecyclerView recyclerCaratSpread;
    public static PopupWindow popupWindow;



    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClick(View view, int wv_optionCurrentItem, int item, int currentItem, int postion);
    }

    public void setSelectItems(int option1, int option2, int option3,int option4) {

    }
    /**
     * 弹出底部滚轮选择
     *
     * @param context
     * @param list
     * @param click
     */
    public static void alertBottomWheelOption(Context context, ArrayList<?> list, ArrayList<?> list2, ArrayList<?> list3, ArrayList<?> list4, final OnWheelViewClick click) {

        popupWindow  = new PopupWindow();

        View view = LayoutInflater.from(context).inflate(R.layout.popup_wheel_option, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.btnSubmit);
        final WheelView wv_shape = (WheelView) view.findViewById(R.id.wv_shape);
        final WheelView wv_carat = (WheelView) view.findViewById(R.id.wv_carat);
        final WheelView wv_color = (WheelView) view.findViewById(R.id.wv_color);
        final WheelView wv_clarity = (WheelView) view.findViewById(R.id.wv_clarity);

        wv_shape.setAdapter(new ArrayWheelAdapter(list));
        wv_carat.setAdapter(new ArrayWheelAdapter(list2));
        wv_color.setAdapter(new ArrayWheelAdapter(list3));
        wv_clarity.setAdapter(new ArrayWheelAdapter(list4));
        wv_shape.setCurrentItem(0);
        wv_carat.setCurrentItem(0);
        wv_color.setCurrentItem(0);
        wv_clarity.setCurrentItem(0);
        wv_shape.setCyclic(false);
        wv_carat.setCyclic(false);
        wv_color.setCyclic(false);
        wv_clarity.setCyclic(false);
        wv_shape.setTextSize(16);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                click.onClick(view, wv_shape.getCurrentItem(), wv_carat.getCurrentItem(), wv_color.getCurrentItem(), wv_clarity.getCurrentItem());
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016/8/11 0011 取消
                popupWindow.dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int top = view.findViewById(R.id.ll_container).getTop();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int y = (int) motionEvent.getY();
                    if (y < top) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }

    public static void caratSpreadPopupWindow(Context context,ArrayList<String> caratSpreadList){
        popupWindow=new PopupWindow();
        View popupView = LayoutInflater.from(context).inflate(R.layout.caratsection_popupwindow, null);
        RecyclerView  recyclerCarat = (RecyclerView) popupView.findViewById(R.id.recycler_caratSection);
        //设置布局管理器
        recyclerCarat.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        CaratSectionAdapter caratSectionAdapter = new CaratSectionAdapter(context, caratSpreadList);
        recyclerCarat.setAdapter(caratSectionAdapter);
        caratSectionAdapter.setOnItemClickListener(new CaratSectionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int tag, String data) {
            }
        });
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
    }
}
