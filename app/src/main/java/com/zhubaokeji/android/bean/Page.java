package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yuizhi on 2016/12/27.
 */

public class Page implements Parcelable{

    /**分页起始（当前页数）*/
    private Integer pageNum;

    /**每页数量*/
    private Integer pageSize;


    public Integer getPageSize(int pageSize) {
        if(this.pageSize == null || this.pageSize<=0){
            this.pageSize = 25;
        }

        if(this.pageSize > 500){
            this.pageSize = 500;
        }

        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum(int i) {
        if(this.pageNum == null || this.pageNum<=0){
            this.pageNum = 1;
        }

        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.pageNum);
        dest.writeValue(this.pageSize);
    }

    public Page() {
    }

    protected Page(Parcel in) {
        this.pageNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pageSize = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel source) {
            return new Page(source);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };
}
