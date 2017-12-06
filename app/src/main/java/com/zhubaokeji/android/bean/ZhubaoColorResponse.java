package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

import java.math.BigDecimal;

/**
 * Created by fisho on 2017/4/1.
 */

public class ZhubaoColorResponse implements Parcelable {
    /**
     * 钻石编号
     */
    private String stoneid;

    /**
     * 形状
     */
    private String shape;

    /**
     * 克拉
     */
    private String carat;

    /**
     * 颜色
     */
    private String color;

    /**
     * 强度
     */
    private String intensity;

    /**
     * 光彩
     */
    private String gloss;

    /**
     * 分布
     */
    private String colordistribution;

    /**
     * 净度
     */
    private String clarity;

    /**
     * 抛光
     */
    private String polish;

    /**
     * 对称
     */
    private String symmetry;

    /**
     * 荧光
     */
    private String fluorescence;

    /**
     * 尺寸
     */
    private String measurement;

    /**
     * 证书机构
     */
    private String report;

    /**
     * 证书编号
     */
    private String reportno;

    /**
     * 原货号
     */
    private String supstoneid;

    /**
     * 供应商名称
     */
    private String supname;

    /**
     * 最后更新时间
     */
    private String lasttime;

    /**
     * 彩钻出售美金
     */
    private String dollorprice;

    /**
     * 彩钻出售每ct的美元价格
     */
    private String saledollorctprice;

    /**
     * 卖出人民币
     */
    private String rmbprice;

    /**
     * 每克拉人民币
     */
    private String rmbPerCt;

    //倍率
    private String percentage;

    //汇率
    private String rate;

    //国际报价
    private String onlineprice;
    /**
     * 实物图地址
     */

    private String imgurl;

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

    public String getStoneid() {
        return stoneid;
    }

    public void setStoneid(String stoneid) {
        this.stoneid = stoneid;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getCarat() {
        return carat;
    }

    public void setCarat(String carat) {
        this.carat = carat;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public String getColordistribution() {
        return colordistribution;
    }

    public void setColordistribution(String colordistribution) {
        this.colordistribution = colordistribution;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getSymmetry() {
        return symmetry;
    }

    public void setSymmetry(String symmetry) {
        this.symmetry = symmetry;
    }

    public String getFluorescence() {
        return fluorescence;
    }

    public void setFluorescence(String fluorescence) {
        this.fluorescence = fluorescence;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getReportno() {
        return reportno;
    }

    public void setReportno(String reportno) {
        this.reportno = reportno;
    }

    public String getSupstoneid() {
        return supstoneid;
    }

    public void setSupstoneid(String supstoneid) {
        this.supstoneid = supstoneid;
    }

    public String getSupname() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getDollorprice() {
        if(saledollorctprice != null && !saledollorctprice.equals("")){
            BigDecimal bigSaledollorctprice=new BigDecimal(saledollorctprice);
            BigDecimal bigCarat=new BigDecimal(carat);
            BigDecimal bigDollorprice=bigSaledollorctprice.multiply(bigCarat).setScale(2,BigDecimal.ROUND_HALF_UP);
            return  bigDollorprice.toString();
        }
        return dollorprice;
    }

    public void setDollorprice(String dollorprice) {
        this.dollorprice = dollorprice;
    }

//    public String getSaledollorprice() {
//        if(saledollorctprice != null && !saledollorctprice.equals("")){
//            BigDecimal bigSaledollorctprice=new BigDecimal(saledollorctprice);
//            BigDecimal bigCarat=new BigDecimal(carat);
//            BigDecimal bigSaledollorprice=bigSaledollorctprice.multiply(bigCarat).setScale(2,BigDecimal.ROUND_HALF_UP);
//            return  bigSaledollorprice.toString();
//        }
//        return saledollorprice;
//    }
//
//    public void setSaledollorprice(String saledollorprice) {
//        this.saledollorprice = saledollorprice;
//    }

    public String getSaledollorctprice() {
        return saledollorctprice;
    }

    public void setSaledollorctprice(String saledollorctprice) {
        this.saledollorctprice = saledollorctprice;
    }

    public String getRmbprice() {
        BigDecimal bigRmbprice=new BigDecimal(this.rmbprice).setScale(0, BigDecimal.ROUND_HALF_UP);
        rmbprice=bigRmbprice.toString();
        return rmbprice;
    }

    public void setRmbprice(String rmbprice) {
        this.rmbprice = rmbprice;
    }

    public String getRmbPerCt() {
        BigDecimal bigCarat = new BigDecimal(carat);
        BigDecimal bigSaledollorCt = new BigDecimal(saledollorctprice);
        BigDecimal bigRate = new BigDecimal(rate);
        BigDecimal bigPercentage = new BigDecimal(percentage);
        if (bigRate.equals("") && bigRate == null) {
            bigRate = new BigDecimal(0.00);
        }
        if (bigPercentage.equals("") && bigPercentage == null) {
            bigPercentage = new BigDecimal(0.00);
        }
        BigDecimal bigRmbPerCt = bigSaledollorCt.multiply(bigRate).multiply(bigPercentage).setScale(0, BigDecimal.ROUND_HALF_UP);
        return bigRmbPerCt.toString();
    }

    public void setRmbPerCt(String rmbPerCt) {
        this.rmbPerCt = rmbPerCt;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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

    public String getPlotSmPic() {
        return plotSmPic;
    }

    public void setPlotSmPic(String plotSmPic) {
        this.plotSmPic = plotSmPic;
    }

    public String getPlotLgPic() {
        return plotLgPic;
    }

    public void setPlotLgPic(String plotLgPic) {
        this.plotLgPic = plotLgPic;
    }

    public String getPropSmPic() {
        return propSmPic;
    }

    public void setPropSmPic(String propSmPic) {
        this.propSmPic = propSmPic;
    }

    public String getPropLgPic() {
        return propLgPic;
    }

    public void setPropLgPic(String propLgPic) {
        this.propLgPic = propLgPic;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOnlineprice() {
        return onlineprice;
    }

    public void setOnlineprice(String onlineprice) {
        this.onlineprice = onlineprice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stoneid);
        dest.writeString(this.shape);
        dest.writeString(this.carat);
        dest.writeString(this.color);
        dest.writeString(this.intensity);
        dest.writeString(this.gloss);
        dest.writeString(this.colordistribution);
        dest.writeString(this.clarity);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluorescence);
        dest.writeString(this.measurement);
        dest.writeString(this.report);
        dest.writeString(this.reportno);
        dest.writeString(this.supstoneid);
        dest.writeString(this.supname);
        dest.writeString(this.lasttime);
        dest.writeString(this.dollorprice);
        dest.writeString(this.saledollorctprice);
        dest.writeString(this.rmbprice);
        dest.writeString(this.rmbPerCt);
        dest.writeString(this.percentage);
        dest.writeString(this.rate);
        dest.writeString(this.onlineprice);
        dest.writeString(this.imgurl);
        dest.writeString(this.reportpicLg);
        dest.writeString(this.reportpicSm);
        dest.writeString(this.plotSmPic);
        dest.writeString(this.plotLgPic);
        dest.writeString(this.propSmPic);
        dest.writeString(this.propLgPic);
    }

    public ZhubaoColorResponse() {
    }

    protected ZhubaoColorResponse(Parcel in) {
        this.stoneid = in.readString();
        this.shape = in.readString();
        this.carat = in.readString();
        this.color = in.readString();
        this.intensity = in.readString();
        this.gloss = in.readString();
        this.colordistribution = in.readString();
        this.clarity = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluorescence = in.readString();
        this.measurement = in.readString();
        this.report = in.readString();
        this.reportno = in.readString();
        this.supstoneid = in.readString();
        this.supname = in.readString();
        this.lasttime = in.readString();
        this.dollorprice = in.readString();
        this.saledollorctprice = in.readString();
        this.rmbprice = in.readString();
        this.rmbPerCt = in.readString();
        this.percentage = in.readString();
        this.rate = in.readString();
        this.onlineprice = in.readString();
        this.imgurl = in.readString();
        this.reportpicLg = in.readString();
        this.reportpicSm = in.readString();
        this.plotSmPic = in.readString();
        this.plotLgPic = in.readString();
        this.propSmPic = in.readString();
        this.propLgPic = in.readString();
    }

    public static final Creator<ZhubaoColorResponse> CREATOR = new Creator<ZhubaoColorResponse>() {
        @Override
        public ZhubaoColorResponse createFromParcel(Parcel source) {
            return new ZhubaoColorResponse(source);
        }

        @Override
        public ZhubaoColorResponse[] newArray(int size) {
            return new ZhubaoColorResponse[size];
        }
    };
}
