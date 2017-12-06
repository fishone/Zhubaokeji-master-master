package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fisho on 2017/3/9.
 */

public class RapDetailsResponse implements Parcelable {
    /**
     * 证书机构
     */
    private String reportType;

    /**
     * 证书编号
     */
    private String reportNo;

    /**
     * 加点
     */
    private String discountPoint;

    /**
     * 汇率
     */
    private String rate;

    /**
     * 报告日期
     */
    private String reportDate;

    /**
     * 原货号
     */
    private String stockid;

    /**
     * 形状
     */
    private String shape;

    /**
     * 克拉重量
     */
    private String carat;

    /**
     * 颜色
     */
    private String color;

    /**
     * 净度
     */
    private String clarity;

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
    private String fluorescence;

    /**
     * 深度
     */
    private String depth;

    /**
     * 表
     */
    private String table;

    /**
     * 测量尺寸
     */
    private String measurements;

    /**
     * 底面
     */
    private String culet;

    /**
     * 腰围
     */
    private String girdle;

    /**
     * 顶点
     */
    private String crown;

    /**
     * 展位
     */
    private String pavilion;

    /**
     * 处理
     */
    private String treatment;

    /**
     * 标题，腰码
     */
    private String inscription;

    /**
     * 比率
     */
    private String ratio;

    /**
     * 星型刻面长
     */
    private String starLength;

    /**
     * 证书上形状
     */
    private String reportShape;

    /**
     * 认证评论
     */
    private String reportComments;

    /**
     * 主要特征
     */
    private String keyToSymbols;

    /**
     * 可用性
     */
    private String availability;

    /**
     * 区域位置
     */
    private String lotLocation;

    /**
     * 每克拉价格，美元
     */
    private String pricePerCt;

    /**
     * 每克拉价格，整数，美元
     */
    private String pricePerCtInt;

    /**
     * rap退点
     */
    private String rapBack;

    /**
     * rap退点，整数
     */
    private String rapBackInt;

    /**
     * 总价，美元
     */
    private String priceTotal;

    /**
     * 总价，整数，美元
     */
    private String priceTotalInt;

    /**
     * 人民币每粒
     */
    private String rmbprice;

    /**
     * 预售退点
     */
    private String preSellBack;

    /**
     * rap国际报价
     */
    private String rapOnlinePrice;

    /**
     * 品牌
     */
    private String brand;

    private String shade;

    private String labLocation;

    /**
     * 更新时间点
     */
    private String updated;

    private String inclusions;

    /**
     * 会员评论
     */
    private String memberComment;

    /**
     * rap货号
     */
    private String lotId;

    /**
     * 供应商rapid
     */
    private String rapId;

    /**
     * 供应商简称
     */
    private String rapCode;

    /**
     * 联系人名称
     */
    private String name;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司电话
     */
    private String telephone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 公司地点
     */
    private String location;

    /**
     * 评级会员
     */
    private String rateMember;


    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getDiscountPoint() {
        return discountPoint;
    }

    public void setDiscountPoint(String discountPoint) {
        this.discountPoint = discountPoint;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getStockid() {
        return stockid;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid;
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

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
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

    public String getFluorescence() {
        return fluorescence;
    }

    public void setFluorescence(String fluorescence) {
        this.fluorescence = fluorescence;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    public String getCulet() {
        return culet;
    }

    public void setCulet(String culet) {
        this.culet = culet;
    }

    public String getGirdle() {
        return girdle;
    }

    public void setGirdle(String girdle) {
        this.girdle = girdle;
    }

    public String getCrown() {
        return crown;
    }

    public void setCrown(String crown) {
        this.crown = crown;
    }

    public String getPavilion() {
        return pavilion;
    }

    public void setPavilion(String pavilion) {
        this.pavilion = pavilion;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getInscription() {
        return inscription;
    }

    public void setInscription(String inscription) {
        this.inscription = inscription;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getStarLength() {
        return starLength;
    }

    public void setStarLength(String starLength) {
        this.starLength = starLength;
    }

    public String getReportShape() {
        return reportShape;
    }

    public void setReportShape(String reportShape) {
        this.reportShape = reportShape;
    }

    public String getReportComments() {
        return reportComments;
    }

    public void setReportComments(String reportComments) {
        this.reportComments = reportComments;
    }

    public String getKeyToSymbols() {
        return keyToSymbols;
    }

    public void setKeyToSymbols(String keyToSymbols) {
        this.keyToSymbols = keyToSymbols;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getLotLocation() {
        return lotLocation;
    }

    public void setLotLocation(String lotLocation) {
        this.lotLocation = lotLocation;
    }

    public String getPricePerCt() {
        return pricePerCt;
    }

    public void setPricePerCt(String pricePerCt) {
        this.pricePerCt = pricePerCt;
    }

    public String getPricePerCtInt() {
        return pricePerCtInt;
    }

    public void setPricePerCtInt(String pricePerCtInt) {
        this.pricePerCtInt = pricePerCtInt;
    }

    public String getRapBack() {
        return rapBack;
    }

    public void setRapBack(String rapBack) {
        this.rapBack = rapBack;
    }

    public String getRapBackInt() {
        return rapBackInt;
    }

    public void setRapBackInt(String rapBackInt) {
        this.rapBackInt = rapBackInt;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getPriceTotalInt() {
        return priceTotalInt;
    }

    public void setPriceTotalInt(String priceTotalInt) {
        this.priceTotalInt = priceTotalInt;
    }

    public String getRmbprice() {
        return rmbprice;
    }

    public void setRmbprice(String rmbprice) {
        this.rmbprice = rmbprice;
    }

    public String getPreSellBack() {
        return preSellBack;
    }

    public void setPreSellBack(String preSellBack) {
        this.preSellBack = preSellBack;
    }

    public String getRapOnlinePrice() {
        return rapOnlinePrice;
    }

    public void setRapOnlinePrice(String rapOnlinePrice) {
        this.rapOnlinePrice = rapOnlinePrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public String getLabLocation() {
        return labLocation;
    }

    public void setLabLocation(String labLocation) {
        this.labLocation = labLocation;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }

    public String getMemberComment() {
        return memberComment;
    }

    public void setMemberComment(String memberComment) {
        this.memberComment = memberComment;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getRapId() {
        return rapId;
    }

    public void setRapId(String rapId) {
        this.rapId = rapId;
    }

    public String getRapCode() {
        return rapCode;
    }

    public void setRapCode(String rapCode) {
        this.rapCode = rapCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRateMember() {
        return rateMember;
    }

    public void setRateMember(String rateMember) {
        this.rateMember = rateMember;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reportType);
        dest.writeString(this.reportNo);
        dest.writeString(this.discountPoint);
        dest.writeString(this.rate);
        dest.writeString(this.reportDate);
        dest.writeString(this.stockid);
        dest.writeString(this.shape);
        dest.writeString(this.carat);
        dest.writeString(this.color);
        dest.writeString(this.clarity);
        dest.writeString(this.cut);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluorescence);
        dest.writeString(this.depth);
        dest.writeString(this.table);
        dest.writeString(this.measurements);
        dest.writeString(this.culet);
        dest.writeString(this.girdle);
        dest.writeString(this.crown);
        dest.writeString(this.pavilion);
        dest.writeString(this.treatment);
        dest.writeString(this.inscription);
        dest.writeString(this.ratio);
        dest.writeString(this.starLength);
        dest.writeString(this.reportShape);
        dest.writeString(this.reportComments);
        dest.writeString(this.keyToSymbols);
        dest.writeString(this.availability);
        dest.writeString(this.lotLocation);
        dest.writeString(this.pricePerCt);
        dest.writeString(this.pricePerCtInt);
        dest.writeString(this.rapBack);
        dest.writeString(this.rapBackInt);
        dest.writeString(this.priceTotal);
        dest.writeString(this.priceTotalInt);
        dest.writeString(this.rmbprice);
        dest.writeString(this.preSellBack);
        dest.writeString(this.rapOnlinePrice);
        dest.writeString(this.brand);
        dest.writeString(this.shade);
        dest.writeString(this.labLocation);
        dest.writeString(this.updated);
        dest.writeString(this.inclusions);
        dest.writeString(this.memberComment);
        dest.writeString(this.lotId);
        dest.writeString(this.rapId);
        dest.writeString(this.rapCode);
        dest.writeString(this.name);
        dest.writeString(this.companyName);
        dest.writeString(this.telephone);
        dest.writeString(this.fax);
        dest.writeString(this.location);
        dest.writeString(this.rateMember);
    }

    public RapDetailsResponse() {
    }

    protected RapDetailsResponse(Parcel in) {
        this.reportType = in.readString();
        this.reportNo = in.readString();
        this.discountPoint = in.readString();
        this.rate = in.readString();
        this.reportDate = in.readString();
        this.stockid = in.readString();
        this.shape = in.readString();
        this.carat = in.readString();
        this.color = in.readString();
        this.clarity = in.readString();
        this.cut = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluorescence = in.readString();
        this.depth = in.readString();
        this.table = in.readString();
        this.measurements = in.readString();
        this.culet = in.readString();
        this.girdle = in.readString();
        this.crown = in.readString();
        this.pavilion = in.readString();
        this.treatment = in.readString();
        this.inscription = in.readString();
        this.ratio = in.readString();
        this.starLength = in.readString();
        this.reportShape = in.readString();
        this.reportComments = in.readString();
        this.keyToSymbols = in.readString();
        this.availability = in.readString();
        this.lotLocation = in.readString();
        this.pricePerCt = in.readString();
        this.pricePerCtInt = in.readString();
        this.rapBack = in.readString();
        this.rapBackInt = in.readString();
        this.priceTotal = in.readString();
        this.priceTotalInt = in.readString();
        this.rmbprice = in.readString();
        this.preSellBack = in.readString();
        this.rapOnlinePrice = in.readString();
        this.brand = in.readString();
        this.shade = in.readString();
        this.labLocation = in.readString();
        this.updated = in.readString();
        this.inclusions = in.readString();
        this.memberComment = in.readString();
        this.lotId = in.readString();
        this.rapId = in.readString();
        this.rapCode = in.readString();
        this.name = in.readString();
        this.companyName = in.readString();
        this.telephone = in.readString();
        this.fax = in.readString();
        this.location = in.readString();
        this.rateMember = in.readString();
    }

    public static final Creator<RapDetailsResponse> CREATOR = new Creator<RapDetailsResponse>() {
        @Override
        public RapDetailsResponse createFromParcel(Parcel source) {
            return new RapDetailsResponse(source);
        }

        @Override
        public RapDetailsResponse[] newArray(int size) {
            return new RapDetailsResponse[size];
        }
    };
}
