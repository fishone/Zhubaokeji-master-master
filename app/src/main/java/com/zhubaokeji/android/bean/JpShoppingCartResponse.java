package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

import java.math.BigDecimal;

/**
 * Created by fisho on 2017/5/15.
 */

public class JpShoppingCartResponse  implements Parcelable{
    //订单号
    private String recno;
    //销售价格
    private String saleprice;
    //货号
    private String vipstoneid;
    //添加购物车的时间
    private String addtiome;
    //国际报价
    private String onlineprice;
    //彩钻Rap的价格
    private String fancyonlineprice;
    //最终售价
    private String endsaleback;
    //销售退点
    private String saleback;
    //类别
    private String protype;
    //钻石的状态
    private String isokname;
    //形状
    private String shape;
    //克拉
    private String carat;
    //颜色
    private String color;
    //彩钻颜色
    private String fancycolor;
    //彩钻强度
    private String intensity;
    //彩钻光彩
    private String gloss;
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
    //证书号
    private String reportno;
    //证书类型
    private String report;
    //咖色
    private String colsh;
    //奶色
    private String milky;
    //绿色调
    private String green;
    //黑点
    private String black;
    //肉眼干净
    private String eyeclean;
    //是否被选中
    private boolean isSelect;
    //优惠是否被选中
    private boolean offerSelect;
    //地址
    private String address;

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno;
    }

    public String getSaleprice() {
        BigDecimal bigSaleprice=new BigDecimal(this.saleprice).setScale(0, BigDecimal.ROUND_HALF_UP);
        saleprice=bigSaleprice.toString();
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getVipstoneid() {
        return vipstoneid;
    }

    public void setVipstoneid(String vipstoneid) {
        this.vipstoneid = vipstoneid;
    }

    public String getAddtiome() {
        return addtiome;
    }

    public void setAddtiome(String addtiome) {
        this.addtiome = addtiome;
    }

    public String getOnlineprice() {
        return onlineprice;
    }

    public void setOnlineprice(String onlineprice) {
        this.onlineprice = onlineprice;
    }

    public String getFancyonlineprice() {
        return fancyonlineprice;
    }

    public void setFancyonlineprice(String fancyonlineprice) {
        this.fancyonlineprice = fancyonlineprice;
    }

    public String getEndsaleback() {
        return endsaleback;
    }

    public void setEndsaleback(String endsaleback) {
        this.endsaleback = endsaleback;
    }

    public String getSaleback() {
        return saleback;
    }

    public void setSaleback(String saleback) {
        this.saleback = saleback;
    }

    public String getProtype() {
        return protype;
    }

    public void setProtype(String protype) {
        this.protype = protype;
    }

    public String getIsokname() {
        return isokname;
    }

    public void setIsokname(String isokname) {
        this.isokname = isokname;
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

    public String getFancycolor() {
        return fancycolor;
    }

    public void setFancycolor(String fancycolor) {
        this.fancycolor = fancycolor;
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

    public String getReportno() {
        return reportno;
    }

    public void setReportno(String reportno) {
        this.reportno = reportno;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getColsh() {
        return colsh;
    }

    public void setColsh(String colsh) {
        this.colsh = colsh;
    }

    public String getMilky() {
        return milky;
    }

    public void setMilky(String milky) {
        this.milky = milky;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getEyeclean() {
        return eyeclean;
    }


    public void setEyeclean(String eyeclean) {
        this.eyeclean = eyeclean;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isOfferSelect() {
        return offerSelect;
    }

    public void setOfferSelect(boolean offerSelect) {
        this.offerSelect = offerSelect;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtil.isValidString(this.shape)) {
            stringBuffer.append(this.shape).append(" ");
        }
        if (StringUtil.isValidString(this.carat)) {
            stringBuffer.append(this.carat).append(" ");
        }
        if(StringUtil.isValidString(this.color)){
            stringBuffer.append(this.color).append(" ");
        }
        if(StringUtil.isValidString(this.clarity)){
            stringBuffer.append(this.clarity).append(" ");
        }
        if (StringUtil.isValidString(this.cut)) {
            stringBuffer.append(this.cut).append(" ");
        }
        if(StringUtil.isValidString(this.polish)){
            stringBuffer.append(this.polish).append(" ");
        }
        if(StringUtil.isValidString(this.symmetry)){
            stringBuffer.append(this.symmetry).append(" ");
        }
        if(StringUtil.isValidString(this.fluorescence)){
            stringBuffer.append(this.fluorescence).append(" ");
        }
        if(StringUtil.isValidString(this.report)){
            stringBuffer.append(this.report);
        }
        return stringBuffer.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recno);
        dest.writeString(this.saleprice);
        dest.writeString(this.vipstoneid);
        dest.writeString(this.addtiome);
        dest.writeString(this.onlineprice);
        dest.writeString(this.fancyonlineprice);
        dest.writeString(this.endsaleback);
        dest.writeString(this.saleback);
        dest.writeString(this.protype);
        dest.writeString(this.isokname);
        dest.writeString(this.shape);
        dest.writeString(this.carat);
        dest.writeString(this.color);
        dest.writeString(this.fancycolor);
        dest.writeString(this.intensity);
        dest.writeString(this.gloss);
        dest.writeString(this.clarity);
        dest.writeString(this.cut);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluorescence);
        dest.writeString(this.reportno);
        dest.writeString(this.report);
        dest.writeString(this.colsh);
        dest.writeString(this.milky);
        dest.writeString(this.green);
        dest.writeString(this.black);
        dest.writeString(this.eyeclean);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public JpShoppingCartResponse() {
    }

    protected JpShoppingCartResponse(Parcel in) {
        this.recno = in.readString();
        this.saleprice = in.readString();
        this.vipstoneid = in.readString();
        this.addtiome = in.readString();
        this.onlineprice = in.readString();
        this.fancyonlineprice = in.readString();
        this.endsaleback = in.readString();
        this.saleback = in.readString();
        this.protype = in.readString();
        this.isokname = in.readString();
        this.shape = in.readString();
        this.carat = in.readString();
        this.color = in.readString();
        this.fancycolor = in.readString();
        this.intensity = in.readString();
        this.gloss = in.readString();
        this.clarity = in.readString();
        this.cut = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluorescence = in.readString();
        this.reportno = in.readString();
        this.report = in.readString();
        this.colsh = in.readString();
        this.milky = in.readString();
        this.green = in.readString();
        this.black = in.readString();
        this.eyeclean = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<JpShoppingCartResponse> CREATOR = new Creator<JpShoppingCartResponse>() {
        @Override
        public JpShoppingCartResponse createFromParcel(Parcel source) {
            return new JpShoppingCartResponse(source);
        }

        @Override
        public JpShoppingCartResponse[] newArray(int size) {
            return new JpShoppingCartResponse[size];
        }
    };
}
