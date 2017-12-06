package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fisho on 2017/3/8.
 */

public class RapDiamondResponse implements Parcelable {
    private String rapDiamondId;

    private String seller;

    private String location;

    private String shape;

    private String carat;

    private String color;

    private String clarity;

    private String cut;

    private String polish;

    private String symmetry;

    private String pricePerCt;

    private String rapBack;

    private String priceTotal;

    private String reportType;

    private String floursence;

    private String depth;

    private String tableth;

    private String measurement;

    public String getRapDiamondId() {
        return rapDiamondId;
    }

    public void setRapDiamondId(String rapDiamondId) {
        this.rapDiamondId = rapDiamondId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPricePerCt() {
        return pricePerCt;
    }

    public void setPricePerCt(String pricePerCt) {
        this.pricePerCt = pricePerCt;
    }

    public String getRapBack() {
        return rapBack;
    }

    public void setRapBack(String rapBack) {
        this.rapBack = rapBack;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFloursence() {
        return floursence;
    }

    public void setFloursence(String floursence) {
        this.floursence = floursence;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getTableth() {
        return tableth;
    }

    public void setTableth(String tableth) {
        this.tableth = tableth;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rapDiamondId);
        dest.writeString(this.seller);
        dest.writeString(this.location);
        dest.writeString(this.shape);
        dest.writeString(this.carat);
        dest.writeString(this.color);
        dest.writeString(this.clarity);
        dest.writeString(this.cut);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.pricePerCt);
        dest.writeString(this.rapBack);
        dest.writeString(this.priceTotal);
        dest.writeString(this.reportType);
        dest.writeString(this.floursence);
        dest.writeString(this.depth);
        dest.writeString(this.tableth);
        dest.writeString(this.measurement);
    }

    public RapDiamondResponse() {
    }

    protected RapDiamondResponse(Parcel in) {
        this.rapDiamondId = in.readString();
        this.seller = in.readString();
        this.location = in.readString();
        this.shape = in.readString();
        this.carat = in.readString();
        this.color = in.readString();
        this.clarity = in.readString();
        this.cut = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.pricePerCt = in.readString();
        this.rapBack = in.readString();
        this.priceTotal = in.readString();
        this.reportType = in.readString();
        this.floursence = in.readString();
        this.depth = in.readString();
        this.tableth = in.readString();
        this.measurement = in.readString();
    }

    public static final Creator<RapDiamondResponse> CREATOR = new Creator<RapDiamondResponse>() {
        @Override
        public RapDiamondResponse createFromParcel(Parcel source) {
            return new RapDiamondResponse(source);
        }

        @Override
        public RapDiamondResponse[] newArray(int size) {
            return new RapDiamondResponse[size];
        }
    };
}
