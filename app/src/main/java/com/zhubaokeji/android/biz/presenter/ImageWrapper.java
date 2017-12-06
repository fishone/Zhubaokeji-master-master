package com.zhubaokeji.android.biz.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.File;

/**
 * 作者：YUIZHI
 * 项目名称：Zhubaokeji-master
 * 包名：com.zhubaokeji.android.biz.presenter
 * 创建时间: 2017/8/17  17:35
 * 描述：
 */

public interface ImageWrapper {
    /**
     * 显示 图片
     *
     * @param imageView 显示图片的ImageView
     * @param imageBitmap 图片
     */
    public void displayImage(ImageView imageView, Bitmap imageBitmap);

}
