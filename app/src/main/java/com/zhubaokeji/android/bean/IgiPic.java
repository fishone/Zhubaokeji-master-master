package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yuizhi on 2017/1/9.
 */

public class IgiPic  implements Parcelable {
    /**
     * IGI证书大图片
     */
    private String reportpicLg;
    /**
     * IGI证书小图片
     */
    private String reportpicSm;
    /**
     * IGI净度小图
     */
    private String plotSmPic;
    /**
     * IGI净度大图
     */
    private String plotLgPic;

    public String getPlotLgPic() {
        return plotLgPic;
    }

    public void setPlotLgPic(String plotLgPic) {
        this.plotLgPic = plotLgPic;
    }

    public String getPlotSmPic() {
        return plotSmPic;
    }

    public void setPlotSmPic(String plotSmPic) {
        this.plotSmPic = plotSmPic;
    }

    public String getReportpicLg() {
        return reportpicLg;
    }

    public void setReportpicLg(String reportpicLg) {
        this.reportpicLg = reportpicLg;
    }

    public String getReportpicSm() {
        return reportpicSm;
    }

    public void setReportpicSm(String reportpicSm) {
        this.reportpicSm = reportpicSm;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reportpicLg);
        dest.writeString(this.reportpicSm);
        dest.writeString(this.plotSmPic);
        dest.writeString(this.plotLgPic);
    }

    public IgiPic() {
    }

    protected IgiPic(Parcel in) {
        this.reportpicLg = in.readString();
        this.reportpicSm = in.readString();
        this.plotSmPic = in.readString();
        this.plotLgPic = in.readString();
    }

    public static final Creator<IgiPic> CREATOR = new Creator<IgiPic>() {
        @Override
        public IgiPic createFromParcel(Parcel source) {
            return new IgiPic(source);
        }

        @Override
        public IgiPic[] newArray(int size) {
            return new IgiPic[size];
        }
    };
}
