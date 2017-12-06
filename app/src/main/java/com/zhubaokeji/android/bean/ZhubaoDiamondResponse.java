package com.zhubaokeji.android.bean;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

import java.math.BigDecimal;

/**
 * Created by Yuizhi on 2016/12/22.
 */

public class ZhubaoDiamondResponse extends BaseObservable implements Parcelable {
    /**
     * 钻石主键
     */
    private String diano;

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
     * 咖色
     */
    private String colsh;

    /**
     * 奶色
     */
    private String milky;

    /**
     * 绿
     */
    private String green;

    /**
     * 黑点
     */
    private String black;

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
     * 售折扣
     */
    private String salediscount;

    /**
     * 原货号
     */
    private String supstoneid;

    /**
     * 供应商名称
     */
    private String supname;

    /**
     * 钻石地址
     */
    private String address;

    /**
     * 最后更新时间
     */
    private String lasttime;

    /**
     * 助宝折扣
     */
    private String zbdiscount;

    /**
     * 国际报价
     */
    private String onlineprice;

    /**
     * rap退点
     */
    private String back;

    /**
     * 售退点
     */
    private String saleback;

    /**
     * 买入美金
     */
    private String dollorprice;

    /**
     *买入美元Ct
     */
    private String dollorpriceCt;

    /**
     * 买入人民币
     */
    private String rmbPurchase;

    /**
     * 卖出人民币
     */
    private String rmbprice;

    /**
     * jp卖出的美金价
     */
    private String saledollorprice;

    /**
     * 每克拉人民币
     */
    private String rmbPerCt;

    /**
     * 助宝退点
     */
    private String zbBack;

    /**
     * 预售退点
     */
    private String presellBack;

    /**
     * 原退点
     */
    private String presaleback;

    /**
     * 肉眼干净
     */
    private String  eyeclean;

    /**
     * 预计到货
     */
    private String purrecivetime;

    /**
     * 更新时间点，如5分钟前，1小时前
     */
    private String updateHour;

    /***
     * 实物图地址
     */
    private String imgurl;

    //是否被锁
    private String ishold;

    //用户锁id
    private String holdvipno;

    //是否下单
    private boolean placeOrder;

    /**
     * rapnetid
     */
    private String rapnetid;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getCarat() {
        return carat;
    }

    public void setCarat(String carat) {
        this.carat = carat;
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

    public String getColsh() {
        return colsh;
    }

    public void setColsh(String colsh) {
        this.colsh = colsh;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getDiano() {
        return diano;
    }

    public void setDiano(String diano) {
        this.diano = diano;
    }

    public String getDollorprice() {
        return dollorprice;
    }

    public void setDollorprice(String dollorprice) {
        this.dollorprice = dollorprice;
    }

    public String getFluorescence() {
        return fluorescence;
    }

    public void setFluorescence(String fluorescence) {
        this.fluorescence = fluorescence;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getMilky() {
        return milky;
    }

    public void setMilky(String milky) {
        this.milky = milky;
    }

    public String getOnlineprice() {
        return onlineprice;
    }

    public void setOnlineprice(String onlineprice) {
        this.onlineprice = onlineprice;
    }

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

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getPresellBack() {
        return presellBack;
    }

    public void setPresellBack(String presellBack) {
        this.presellBack = presellBack;
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

    public String getRapnetid() {
        return rapnetid;
    }

    public void setRapnetid(String rapnetid) {
        this.rapnetid = rapnetid;
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

    public String getRmbPerCt() {
        return rmbPerCt;
    }

    public void setRmbPerCt(String rmbPerCt) {
        this.rmbPerCt = rmbPerCt;
    }

    public String getRmbprice() {
        BigDecimal bigRmbprice=new BigDecimal(this.rmbprice).setScale(0, BigDecimal.ROUND_HALF_UP);
        rmbprice=bigRmbprice.toString();
        return rmbprice;
    }

    public void setRmbprice(String rmbprice) {
        this.rmbprice = rmbprice;
    }

    public String getRmbPurchase() {
        return rmbPurchase;
    }

    public void setRmbPurchase(String rmbPurchase) {
        this.rmbPurchase = rmbPurchase;
    }

    public String getSaleback() {
        return saleback;
    }

    public void setSaleback(String saleback) {
        this.saleback = saleback;
    }

    public String getSalediscount() {
        return salediscount;
    }

    public void setSalediscount(String salediscount) {
        this.salediscount = salediscount;
    }

    public String getSaledollorprice() {
        return saledollorprice;
    }

    public void setSaledollorprice(String saledollorprice) {
        this.saledollorprice = saledollorprice;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
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

    public String getSupstoneid() {
        return supstoneid;
    }

    public void setSupstoneid(String supstoneid) {
        this.supstoneid = supstoneid;
    }

    public String getSymmetry() {
        return symmetry;
    }

    public void setSymmetry(String symmetry) {
        this.symmetry = symmetry;
    }

    public String getUpdateHour() {
        return updateHour;
    }

    public void setUpdateHour(String updateHour) {
        this.updateHour = updateHour;
    }

    public String getZbBack() {
        return zbBack;
    }

    public void setZbBack(String zbBack) {
        this.zbBack = zbBack;
    }

    public String getZbdiscount() {
        return zbdiscount;
    }

    public void setZbdiscount(String zbdiscount) {
        this.zbdiscount = zbdiscount;
    }

    public String getPresaleback() {
        return presaleback;
    }

    public void setPresaleback(String presaleback) {
        this.presaleback = presaleback;
    }

    public String getDollorpriceCt() {
        if(dollorpriceCt ==null &&!carat.equals("0") && dollorprice!=null){
            BigDecimal dollorprice= null;
            dollorprice = new BigDecimal(this.dollorprice);
            BigDecimal carat = new BigDecimal(this.carat);
            dollorpriceCt= String.valueOf(dollorprice.divide(carat,2,BigDecimal.ROUND_HALF_UP));
        }else if(dollorpriceCt==null && !carat.equals("0") && saledollorprice !=null){
            BigDecimal saledollorprice= null;
            saledollorprice = new BigDecimal(this.saledollorprice);
            BigDecimal carat = new BigDecimal(this.carat);
            dollorpriceCt= String.valueOf(saledollorprice.divide(carat,2,BigDecimal.ROUND_HALF_UP));
        }
        return dollorpriceCt;
    }
    public void setDollorpriceCt(String dollorpriceCt) {
        this.dollorpriceCt = dollorpriceCt;
    }

    public String getEyeclean() {
        return eyeclean;
    }

    public void setEyeclean(String eyeclean) {
        this.eyeclean = eyeclean;
    }

    public String getPurrecivetime() {
        return purrecivetime;
    }

    public void setPurrecivetime(String purrecivetime) {
        this.purrecivetime = purrecivetime;
    }

    public String getIshold() {
        return ishold;
    }

    public void setIshold(String ishold) {
        this.ishold = ishold;
    }

    public String getHoldvipno() {
        return holdvipno;
    }

    public void setHoldvipno(String holdvipno) {
        this.holdvipno = holdvipno;
    }

    public boolean isPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(boolean placeOrder) {
        this.placeOrder = placeOrder;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.diano);
        dest.writeString(this.stoneid);
        dest.writeString(this.shape);
        dest.writeString(this.carat);
        dest.writeString(this.color);
        dest.writeString(this.clarity);
        dest.writeString(this.cut);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluorescence);
        dest.writeString(this.colsh);
        dest.writeString(this.milky);
        dest.writeString(this.green);
        dest.writeString(this.black);
        dest.writeString(this.measurement);
        dest.writeString(this.report);
        dest.writeString(this.reportno);
        dest.writeString(this.salediscount);
        dest.writeString(this.supstoneid);
        dest.writeString(this.supname);
        dest.writeString(this.address);
        dest.writeString(this.lasttime);
        dest.writeString(this.zbdiscount);
        dest.writeString(this.onlineprice);
        dest.writeString(this.back);
        dest.writeString(this.saleback);
        dest.writeString(this.dollorprice);
        dest.writeString(this.dollorpriceCt);
        dest.writeString(this.rmbPurchase);
        dest.writeString(this.rmbprice);
        dest.writeString(this.saledollorprice);
        dest.writeString(this.rmbPerCt);
        dest.writeString(this.zbBack);
        dest.writeString(this.presellBack);
        dest.writeString(this.presaleback);
        dest.writeString(this.eyeclean);
        dest.writeString(this.purrecivetime);
        dest.writeString(this.updateHour);
        dest.writeString(this.imgurl);
        dest.writeString(this.ishold);
        dest.writeString(this.holdvipno);
        dest.writeByte(this.placeOrder ? (byte) 1 : (byte) 0);
        dest.writeString(this.rapnetid);
        dest.writeString(this.reportpicLg);
        dest.writeString(this.reportpicSm);
        dest.writeString(this.plotSmPic);
        dest.writeString(this.plotLgPic);
        dest.writeString(this.propSmPic);
        dest.writeString(this.propLgPic);
    }

    public ZhubaoDiamondResponse() {
    }

    protected ZhubaoDiamondResponse(Parcel in) {
        this.diano = in.readString();
        this.stoneid = in.readString();
        this.shape = in.readString();
        this.carat = in.readString();
        this.color = in.readString();
        this.clarity = in.readString();
        this.cut = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluorescence = in.readString();
        this.colsh = in.readString();
        this.milky = in.readString();
        this.green = in.readString();
        this.black = in.readString();
        this.measurement = in.readString();
        this.report = in.readString();
        this.reportno = in.readString();
        this.salediscount = in.readString();
        this.supstoneid = in.readString();
        this.supname = in.readString();
        this.address = in.readString();
        this.lasttime = in.readString();
        this.zbdiscount = in.readString();
        this.onlineprice = in.readString();
        this.back = in.readString();
        this.saleback = in.readString();
        this.dollorprice = in.readString();
        this.dollorpriceCt = in.readString();
        this.rmbPurchase = in.readString();
        this.rmbprice = in.readString();
        this.saledollorprice = in.readString();
        this.rmbPerCt = in.readString();
        this.zbBack = in.readString();
        this.presellBack = in.readString();
        this.presaleback = in.readString();
        this.eyeclean = in.readString();
        this.purrecivetime = in.readString();
        this.updateHour = in.readString();
        this.imgurl = in.readString();
        this.ishold = in.readString();
        this.holdvipno = in.readString();
        this.placeOrder = in.readByte() != 0;
        this.rapnetid = in.readString();
        this.reportpicLg = in.readString();
        this.reportpicSm = in.readString();
        this.plotSmPic = in.readString();
        this.plotLgPic = in.readString();
        this.propSmPic = in.readString();
        this.propLgPic = in.readString();
    }

    public static final Creator<ZhubaoDiamondResponse> CREATOR = new Creator<ZhubaoDiamondResponse>() {
        @Override
        public ZhubaoDiamondResponse createFromParcel(Parcel source) {
            return new ZhubaoDiamondResponse(source);
        }

        @Override
        public ZhubaoDiamondResponse[] newArray(int size) {
            return new ZhubaoDiamondResponse[size];
        }
    };
}
