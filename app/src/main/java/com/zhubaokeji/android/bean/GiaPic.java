package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yuizhi on 2017/1/9.
 */

public class GiaPic implements Parcelable {

    /**
     * GIA证书大图片
     */
    private String reportpicLg;

    /**
     * GIA证书小图片
     */
    private String reportpicSm;

    /**
     * GIA净度小图
     */
    private String plotSmPic;

    /**
     * GIA净度大图
     */
    private String plotLgPic;

    /**
     * GIA台宽小图
     */
    private String propSmPic;

    /**
     * GIA台宽大图
     */
    private String propLgPic;

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

    public String getPropLgPic() {
        return propLgPic;
    }

    public void setPropLgPic(String propLgPic) {
        this.propLgPic = propLgPic;
    }

    public String getPropSmPic() {
        return propSmPic;
    }

    public void setPropSmPic(String propSmPic) {
        this.propSmPic = propSmPic;
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
        dest.writeString(this.propSmPic);
        dest.writeString(this.propLgPic);
    }

    public GiaPic() {
    }

    protected GiaPic(Parcel in) {
        this.reportpicLg = in.readString();
        this.reportpicSm = in.readString();
        this.plotSmPic = in.readString();
        this.plotLgPic = in.readString();
        this.propSmPic = in.readString();
        this.propLgPic = in.readString();
    }

    public static final Creator<GiaPic> CREATOR = new Creator<GiaPic>() {
        @Override
        public GiaPic createFromParcel(Parcel source) {
            return new GiaPic(source);
        }

        @Override
        public GiaPic[] newArray(int size) {
            return new GiaPic[size];
        }
    };
}
