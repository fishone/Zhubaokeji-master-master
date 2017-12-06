package com.zhubaokeji.android.bean;

/**
 * Created by fisho on 2017/3/23.
 */

public class Item {
    private String title;
    private int resId;
    private String englishTitle;

    public Item(String title, int resId) {
        this.title = title;
        this.resId = resId;

    }
    public Item(String title, int resId, String englishTitle){
        this.title = title;
        this.resId = resId;
        this.englishTitle=englishTitle;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
