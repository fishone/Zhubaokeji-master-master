package com.zhubaokeji.android.utils;

import android.content.Context;

/**
 * Created by fisho on 2017/3/27.
 */

public class ScreenTools {
    //将dp 转px
    public int dip2px(Context context, float dipValue)
    {
        float m=context.getResources().getDisplayMetrics().density ;
        return (int)(dipValue * m + 0.5f) ;
    }
    //px转dp
    public static int px2dip(Context context, float pxValue)
    {
        float m=context.getResources().getDisplayMetrics().density ;
        return (int)(pxValue / m + 0.5f) ;
    }
}
