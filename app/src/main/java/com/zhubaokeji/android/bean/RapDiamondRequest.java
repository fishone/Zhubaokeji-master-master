package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/3/7.
 */

public class RapDiamondRequest  implements Parcelable {
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
     * 地点
     */
    private String location;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * rapid
     */
    private String rapId;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 选择分页数据  0：默认1页  1：全部
     *
     */
    private int choicePage;

    /**
     * 设置退点
     */
    private String discountPoint;

    /**
     * 汇率
     */
    private String rate;

    public String getCaratStart() {
        return caratStart;
    }

    public void setCaratStart(String caratStart) {
        this.caratStart = caratStart;
    }

    public String getCaratEnd() {
        return caratEnd;
    }

    public void setCaratEnd(String caratEnd) {
        this.caratEnd = caratEnd;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getThreeEx() {
        return threeEx;
    }

    public void setThreeEx(String threeEx) {
        this.threeEx = threeEx;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
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

    public String getFluore() {
        return fluore;
    }

    public void setFluore(String fluore) {
        this.fluore = fluore;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRapId() {
        return rapId;
    }

    public void setRapId(String rapId) {
        this.rapId = rapId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getChoicePage() {
        return choicePage;
    }

    public void setChoicePage(int choicePage) {
        this.choicePage = choicePage;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDiscountPoint() {
        return discountPoint;
    }

    public void setDiscountPoint(String discountPoint) {
        this.discountPoint = discountPoint;
    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
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
        if(StringUtil.isValidString(this.location)){
            stringBuffer.append("地点:").append(this.location).append(";");
        }
        if(StringUtil.isValidString(this.shape)){
            String shapeStr = shape.replace("round", "圆形").replace("pear", "梨形").replace("princess", "公主方").replace("heart", "心形")
                    .replace("marquise", "马眼形").replace("oval", "椭圆形").replace("radiant", "雷迪恩").replace("emerald", "祖母绿")
                    .replace("cushion", "垫形").replace("triangle", "三角形");
            stringBuffer.append("形状:").append(shapeStr).append(";");
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
        dest.writeString(this.caratStart);
        dest.writeString(this.caratEnd);
        dest.writeString(this.reportType);
        dest.writeString(this.shape);
        dest.writeString(this.color);
        dest.writeString(this.clarity);
        dest.writeString(this.threeEx);
        dest.writeString(this.cut);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluore);
        dest.writeString(this.location);
        dest.writeString(this.loginName);
        dest.writeString(this.password);
        dest.writeString(this.rapId);
        dest.writeValue(this.pageSize);
        dest.writeInt(this.choicePage);
        dest.writeString(this.discountPoint);
        dest.writeString(this.rate);
    }

    public RapDiamondRequest() {
    }

    protected RapDiamondRequest(Parcel in) {
        this.caratStart = in.readString();
        this.caratEnd = in.readString();
        this.reportType = in.readString();
        this.shape = in.readString();
        this.color = in.readString();
        this.clarity = in.readString();
        this.threeEx = in.readString();
        this.cut = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluore = in.readString();
        this.location = in.readString();
        this.loginName = in.readString();
        this.password = in.readString();
        this.rapId = in.readString();
        this.pageSize = (Integer) in.readValue(Integer.class.getClassLoader());
        this.choicePage = in.readInt();
        this.discountPoint = in.readString();
        this.rate = in.readString();
    }

    public static final Creator<RapDiamondRequest> CREATOR = new Creator<RapDiamondRequest>() {
        @Override
        public RapDiamondRequest createFromParcel(Parcel source) {
            return new RapDiamondRequest(source);
        }

        @Override
        public RapDiamondRequest[] newArray(int size) {
            return new RapDiamondRequest[size];
        }
    };
}
