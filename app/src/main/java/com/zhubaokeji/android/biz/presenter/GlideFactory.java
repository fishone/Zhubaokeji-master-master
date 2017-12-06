package com.zhubaokeji.android.biz.presenter;

import com.zhubaokeji.android.loader.GlideImageLoader;

/**
 * 作者：YUIZHI
 * 项目名称：Zhubaokeji-master
 * 包名：com.zhubaokeji.android.biz.presenter
 * 创建时间: 2017/8/18  10:43
 * 描述：
 */

public class GlideFactory {
    private static volatile ImageWrapper sInstance;

    private GlideFactory() {

    }

    /**
     * 获取图片加载器
     *
     * @return
     */
    public static ImageWrapper getLoader() {
        if (sInstance == null) {
            synchronized (GlideFactory.class) {
                if (sInstance == null) {
                    sInstance = new GlideImageLoader();
                }
            }
        }
        return sInstance;
    }
}
