package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by Yuizhi on 2016/12/21.
 */

public class ZhubaoDiamondRequest extends Page implements Parcelable {
    /**
     * 证书编号
     */
    private String reportNo;

    /**
     * 货号，多个货号支持需英文逗号隔开
     */
    private String stoneid;

    /**
     * 设置退点
     */
    private String discountPoint;

    /**
     * 设置倍率
     */
    private String percentage;

    /**
     * 汇率
     */
    private String rate;

    /**
     * 克拉开始
     */
    private String caratStart;

    /**
     * 克拉结束
     */
    private String caratEnd;

    /**
     * 证书机构
     */
    private String reportType;

    /**
     * 形状
     */
    private String shape;


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
     * 彩钻标识 1：彩钻 默认白钻
     */
    private String protype;

    /**
     * 净度
     */
    private String clarity;

    /**
     * 3EX、3VG
     */
    private String threeEx;

    /**
     * 切工
     */
    private String cut;

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
    private String fluore;

    /**
     * 供应商名称
     */
    private String supname;

    private String sortSc = "asc";

    private String sortName = "salediscount";

    private String loginName;

    /**
     * 货所在地
     */
    private String location;

    /**
     * 查询时间，格式为“yyyy-MM-dd HH:mm:ss” 24小时制
     */
    private String timeStart;

    private String timeEnd;

    /**
     * 调用.net的token
     */
    private String token;

    /**
     * 现货与非现货，1是现货，0是非现货
     *
     * @return
     */
    private String spot;



    public String getCaratEnd() {
        return caratEnd;
    }

    public void setCaratEnd(String caratEnd) {
        this.caratEnd = caratEnd;
    }

    public String getCaratStart() {
        return caratStart;
    }

    public void setCaratStart(String caratStart) {
        this.caratStart = caratStart;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getDiscountPoint() {
        return discountPoint;
    }

    public void setDiscountPoint(String discountPoint) {
        this.discountPoint = discountPoint;
    }

    public String getFluore() {
        return fluore;
    }

    public void setFluore(String fluore) {
        this.fluore = fluore;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getProtype() {
        return protype;
    }

    public void setProtype(String protype) {
        this.protype = protype;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getReportType() {
        if(StringUtil.isValidString(getReportType())){
            String report="GIA";
            reportType=report;
            return  reportType;
        }
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getShape() {
        if (StringUtil.isValidString(getShape())) {
            String round="round";
            shape=round;
            return shape;
        }
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortSc() {
        return sortSc;
    }

    public void setSortSc(String sortSc) {
        this.sortSc = sortSc;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getStoneid() {
        return stoneid;
    }

    public void setStoneid(String stoneid) {
        this.stoneid = stoneid;
    }

    public String getSupname() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public String getSymmetry() {
        return symmetry;
    }

    public void setSymmetry(String symmetry) {
        this.symmetry = symmetry;
    }

    public String getThreeEx() {
        return threeEx;
    }

    public void setThreeEx(String threeEx) {
        this.threeEx = threeEx;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtil.isValidString(this.reportNo)) {
            stringBuffer.append("证书号:").append(this.reportNo).append(";");
        }
        if (StringUtil.isValidString(this.percentage)) {
            stringBuffer.append("倍率:").append(this.percentage).append(";");
        }
        if(StringUtil.isValidString(this.discountPoint)){
            stringBuffer.append("加点:").append(this.discountPoint).append(";");
        }
        if(StringUtil.isValidString(this.rate)){
            stringBuffer.append("汇率:").append(this.rate).append(";");
        }
        if (StringUtil.isValidString(this.caratStart) && StringUtil.isValidString(this.caratEnd)) {
            stringBuffer.append("克拉:").append(this.caratStart).append("-").append(this.caratEnd).append(";");
        }
        if(StringUtil.isValidString(this.reportType)){
            stringBuffer.append("证书类型:").append(this.reportType).append(";");
        }
        if(StringUtil.isValidString(this.shape)){
            String shapeStr = shape.replace("round", "圆形").replace("pear", "梨形").replace("princess", "公主方").replace("heart", "心形")
                    .replace("marquise", "马眼形").replace("oval", "椭圆形").replace("radiant", "雷迪恩").replace("emerald", "祖母绿")
                    .replace("cushion", "垫形").replace("triangle", "三角形");
            stringBuffer.append("形状:").append(shapeStr).append(";");
        }
        if(StringUtil.isValidString(this.intensity)){
            stringBuffer.append("强度:").append(this.intensity).append(";");
        }
        if(StringUtil.isValidString(this.gloss)){
            stringBuffer.append("光彩:").append(this.gloss).append(";");
        }
        if(StringUtil.isValidString(this.color)){
            stringBuffer.append("颜色:").append(this.color).append(";");
        }
        if(StringUtil.isValidString(this.clarity)){
            stringBuffer.append("净度:").append(this.clarity).append(";");
        }
        if (StringUtil.isValidString(this.threeEx)) {
            stringBuffer.append("三项:").append(this.threeEx).append(";");
        }
        if (StringUtil.isValidString(this.cut)) {
            stringBuffer.append("切工:").append(this.cut).append(";");
        }
        if (StringUtil.isValidString(this.polish)) {
            stringBuffer.append("抛光:").append(this.polish).append(";");
        }
        if (StringUtil.isValidString(this.symmetry)) {
            stringBuffer.append("对称:").append(this.symmetry).append(";");
        }
        if (StringUtil.isValidString(this.fluore)) {
            stringBuffer.append("荧光:").append(this.fluore).append(";");
        }
        return stringBuffer.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reportNo);
        dest.writeString(this.stoneid);
        dest.writeString(this.discountPoint);
        dest.writeString(this.percentage);
        dest.writeString(this.rate);
        dest.writeString(this.caratStart);
        dest.writeString(this.caratEnd);
        dest.writeString(this.reportType);
        dest.writeString(this.shape);
        dest.writeString(this.color);
        dest.writeString(this.intensity);
        dest.writeString(this.gloss);
        dest.writeString(this.protype);
        dest.writeString(this.clarity);
        dest.writeString(this.threeEx);
        dest.writeString(this.cut);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluore);
        dest.writeString(this.supname);
        dest.writeString(this.sortSc);
        dest.writeString(this.sortName);
        dest.writeString(this.loginName);
        dest.writeString(this.location);
        dest.writeString(this.timeStart);
        dest.writeString(this.timeEnd);
        dest.writeString(this.token);
        dest.writeString(this.spot);
    }

    public ZhubaoDiamondRequest() {
    }

    protected ZhubaoDiamondRequest(Parcel in) {
        this.reportNo = in.readString();
        this.stoneid = in.readString();
        this.discountPoint = in.readString();
        this.percentage = in.readString();
        this.rate = in.readString();
        this.caratStart = in.readString();
        this.caratEnd = in.readString();
        this.reportType = in.readString();
        this.shape = in.readString();
        this.color = in.readString();
        this.intensity = in.readString();
        this.gloss = in.readString();
        this.protype = in.readString();
        this.clarity = in.readString();
        this.threeEx = in.readString();
        this.cut = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluore = in.readString();
        this.supname = in.readString();
        this.sortSc = in.readString();
        this.sortName = in.readString();
        this.loginName = in.readString();
        this.location = in.readString();
        this.timeStart = in.readString();
        this.timeEnd = in.readString();
        this.token = in.readString();
        this.spot = in.readString();
    }

    public static final Creator<ZhubaoDiamondRequest> CREATOR = new Creator<ZhubaoDiamondRequest>() {
        @Override
        public ZhubaoDiamondRequest createFromParcel(Parcel source) {
            return new ZhubaoDiamondRequest(source);
        }

        @Override
        public ZhubaoDiamondRequest[] newArray(int size) {
            return new ZhubaoDiamondRequest[size];
        }
    };
}
