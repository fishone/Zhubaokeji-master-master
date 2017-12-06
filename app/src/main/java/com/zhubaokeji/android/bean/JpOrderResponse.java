package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

import java.math.BigDecimal;

/**
 * Created by fisho on 2017/4/25.
 */

public class JpOrderResponse implements Parcelable {
    //序号
    private  String EntryNo;
    //库存号
    private  String vipstoneid;
    //售美金/粒
    private  String endsaleprice;
    //实售退点
    private  String endsaleback;
    //已付金额
    private  String payedmoney;
    //下单日期
    private  String orderdate;
    //取货日期
    private  String senddate;
    //证书号
    private  String reportno;
    //预约状态
    private String isreserved;
    //形状
    private String shape;
    //克拉
    private String carat;
    //颜色
    private String color;
    //净度
    private String clarity;
    //切工
    private String cut;
    //抛光
    private String polish;
    //对称
    private String symmetry;
    //荧光
    private String fluorescence;
    //证书
    private String report;
    //退点
    private String saleback;
    //国际报价/原克拉单价
    private String onlineprice;
    //售克拉单价
    private String saleonlineprice;
    //地点
    private String address;
    //预计到货
    private String prerecivetime;
    //状态
    private String stateName;
    //数据返回url
    private String tarurl;
    //肉眼干净
    private String eyeclean;

    public String getEntryNo() {
        return EntryNo;
    }

    public void setEntryNo(String entryNo) {
        EntryNo = entryNo;
    }

    public String getVipstoneid() {
        return vipstoneid;
    }

    public void setVipstoneid(String vipstoneid) {
        this.vipstoneid = vipstoneid;
    }

    public String getEndsaleprice() {
        if(StringUtil.isValidString(this.endsaleprice)){
            BigDecimal bigEndsalePrice=new BigDecimal(this.endsaleprice);
            return bigEndsalePrice.setScale(0).toString();
        }
        return endsaleprice;
    }

    public void setEndsaleprice(String endsaleprice) {
        this.endsaleprice = endsaleprice;
    }

    public String getEndsaleback() {
        return endsaleback;
    }

    public void setEndsaleback(String endsaleback) {
        this.endsaleback = endsaleback;
    }

    public String getPayedmoney() {
        return payedmoney;
    }

    public void setPayedmoney(String payedmoney) {
        this.payedmoney = payedmoney;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getReportno() {
        return reportno;
    }

    public void setReportno(String reportno) {
        this.reportno = reportno;
    }

    public String getIsreserved() {
        return isreserved;
    }

    public void setIsreserved(String isreserved) {
        this.isreserved = isreserved;
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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getSaleback() {
        return saleback;
    }

    public void setSaleback(String saleback) {
        this.saleback = saleback;
    }

    public String getOnlineprice() {
        return onlineprice;
    }

    public void setOnlineprice(String onlineprice) {
        this.onlineprice = onlineprice;
    }

    public String getSaleonlineprice() {
        return saleonlineprice;
    }

    public void setSaleonlineprice(String saleonlineprice) {
        this.saleonlineprice = saleonlineprice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrerecivetime() {
        if(prerecivetime != null && !prerecivetime.equals("")){
            prerecivetime = prerecivetime.substring(0,10);
        }
        return prerecivetime;
    }

    public void setPrerecivetime(String prerecivetime) {
        this.prerecivetime = prerecivetime;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTarurl() {
        return tarurl;
    }

    public void setTarurl(String tarurl) {
        this.tarurl = tarurl;
    }
    public String getEyeclean() {
        return eyeclean;
    }

    public void setEyeclean(String eyeclean) {
        this.eyeclean = eyeclean;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.EntryNo);
        dest.writeString(this.vipstoneid);
        dest.writeString(this.endsaleprice);
        dest.writeString(this.endsaleback);
        dest.writeString(this.payedmoney);
        dest.writeString(this.orderdate);
        dest.writeString(this.senddate);
        dest.writeString(this.reportno);
        dest.writeString(this.isreserved);
        dest.writeString(this.shape);
        dest.writeString(this.carat);
        dest.writeString(this.color);
        dest.writeString(this.clarity);
        dest.writeString(this.cut);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluorescence);
        dest.writeString(this.report);
        dest.writeString(this.saleback);
        dest.writeString(this.onlineprice);
        dest.writeString(this.saleonlineprice);
        dest.writeString(this.address);
        dest.writeString(this.prerecivetime);
        dest.writeString(this.stateName);
        dest.writeString(this.tarurl);
        dest.writeString(this.eyeclean);
    }

    public JpOrderResponse() {
    }

    protected JpOrderResponse(Parcel in) {
        this.EntryNo = in.readString();
        this.vipstoneid = in.readString();
        this.endsaleprice = in.readString();
        this.endsaleback = in.readString();
        this.payedmoney = in.readString();
        this.orderdate = in.readString();
        this.senddate = in.readString();
        this.reportno = in.readString();
        this.isreserved = in.readString();
        this.shape = in.readString();
        this.carat = in.readString();
        this.color = in.readString();
        this.clarity = in.readString();
        this.cut = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluorescence = in.readString();
        this.report = in.readString();
        this.saleback = in.readString();
        this.onlineprice = in.readString();
        this.saleonlineprice = in.readString();
        this.address = in.readString();
        this.prerecivetime = in.readString();
        this.stateName = in.readString();
        this.tarurl = in.readString();
        this.eyeclean = in.readString();
    }

    public static final Parcelable.Creator<JpOrderResponse> CREATOR = new Creator<JpOrderResponse>() {
        @Override
        public JpOrderResponse createFromParcel(Parcel source) {
            return new JpOrderResponse(source);
        }

        @Override
        public JpOrderResponse[] newArray(int size) {
            return new JpOrderResponse[size];
        }
    };
}
