package com.zhubaokeji.android.bean;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/7/12.
 */

public class ZhubaoShopCartResponse {

    public String recno;

    public String stoneid;

    public String protype;

    public String addtiome;

    public String groupbytime;

    public String shape;

    public String carat;

    public String intensity;

    public String gloss;

    public String color;

    public String clarity;

    public String cut;

    public String polish;

    public String symmetry;

    public String fluorescence;

    public String milky;

    public String black;

    public String colsh;

    public String report;

    public String onlineprice;

    public String saledollorctprice;

    public String saleback;

    public String rmbprice;

    public String isokname;

    //是否被选中
    private boolean isSelect;

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno;
    }

    public String getStoneid() {
        return stoneid;
    }

    public void setStoneid(String stoneid) {
        this.stoneid = stoneid;
    }

    public String getProtype() {
        return protype;
    }

    public void setProtype(String protype) {
        this.protype = protype;
    }

    public String getAddtiome() {
        return addtiome;
    }

    public void setAddtiome(String addtiome) {
        this.addtiome = addtiome;
    }

    public String getGroupbytime() {
        return groupbytime;
    }

    public void setGroupbytime(String groupbytime) {
        this.groupbytime = groupbytime;
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

    public String getMilky() {
        return milky;
    }

    public void setMilky(String milky) {
        this.milky = milky;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getColsh() {
        return colsh;
    }

    public void setColsh(String colsh) {
        this.colsh = colsh;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getOnlineprice() {
        return onlineprice;
    }

    public void setOnlineprice(String onlineprice) {
        this.onlineprice = onlineprice;
    }

    public String getSaledollorctprice() {
        return saledollorctprice;
    }

    public void setSaledollorctprice(String saledollorctprice) {
        this.saledollorctprice = saledollorctprice;
    }

    public String getSaleback() {
        return saleback;
    }

    public void setSaleback(String saleback) {
        this.saleback = saleback;
    }

    public String getRmbprice() {
        return rmbprice;
    }

    public void setRmbprice(String rmbprice) {
        this.rmbprice = rmbprice;
    }

    public String getIsokname() {
        return isokname;
    }

    public void setIsokname(String isokname) {
        this.isokname = isokname;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtil.isValidString(this.shape)) {
            stringBuffer.append(this.shape).append(" ");
        }
        if (StringUtil.isValidString(this.carat)) {
            stringBuffer.append(this.carat).append(" ");
        }
        if(StringUtil.isValidString(this.intensity)){
            stringBuffer.append(this.intensity).append(" ");
        }
        if(StringUtil.isValidString(this.gloss)){
            stringBuffer.append(this.gloss).append(" ");
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
}
