package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;


/**
 * Created by Yuizhi on 2017/1/9.
 */

public class GiaCertificateResponse extends GiaPic implements Parcelable {

    private String reportno;

    private String reportdate;

    private String shape;

    private String caratweight;

    private String colorgrade;

    private String claritygrade;

    private String cutgrade;

    private String polish;

    private String symmetry;

    private String fluorescence;

    private String colsh;

    private String milky;

    // private String lengthInc;

    // private String widthInc;

    // private String depthInc;

    private String measurements;

    private String depthPct;

    private String tablePct;

    private String crownangle;

    private String crownheight;

    private String pavilionangle;

    private String paviliondepth;

    private String starlength;

    private String lowerhalf;

    private String girdle;

    private String culet;

    private String clarityKeys;

    private String reportcomments;

    private String inscription;

    private String distribution;

    private String origin;

    private String grade;

    public String getCaratweight() {
        if(!StringUtil.isValidString(caratweight)){
            caratweight=null;
        }
        return caratweight;
    }

    public void setCaratweight(String caratweight) {
        this.caratweight = caratweight;
    }

    public String getClaritygrade() {
        if(!StringUtil.isValidString(claritygrade)){
            claritygrade=null;
        }
        return claritygrade;
    }

    public void setClaritygrade(String claritygrade) {
        this.claritygrade = claritygrade;
    }

    public String getClarityKeys() {
        if(!StringUtil.isValidString(clarityKeys)){
            clarityKeys=null;
        }
        return clarityKeys;
    }

    public void setClarityKeys(String clarityKeys) {
        this.clarityKeys = clarityKeys;
    }

    public String getColorgrade() {
        if(!StringUtil.isValidString(colorgrade)){
            colorgrade=null;
        }
        return colorgrade;
    }

    public void setColorgrade(String colorgrade) {
        this.colorgrade = colorgrade;
    }

    public String getColsh() {
        if(!StringUtil.isValidString(colsh)){
            colsh=null;
        }
        return colsh;
    }

    public void setColsh(String colsh) {
        this.colsh = colsh;
    }

    public String getCrownangle() {
        if(!StringUtil.isValidString(crownangle)){
            crownangle=null;
        }
        return crownangle;
    }

    public void setCrownangle(String crownangle) {
        this.crownangle = crownangle;
    }

    public String getCrownheight() {
        if(!StringUtil.isValidString(crownheight)){
            crownheight=null;
        }
        return crownheight;
    }

    public void setCrownheight(String crownheight) {
        this.crownheight = crownheight;
    }

    public String getCulet() {
        if(!StringUtil.isValidString(culet)){
            culet=null;
        }
        return culet;
    }

    public void setCulet(String culet) {
        this.culet = culet;
    }

    public String getCutgrade() {
        if(!StringUtil.isValidString(cutgrade)){
            cutgrade=null;
        }
        return cutgrade;
    }

    public void setCutgrade(String cutgrade) {
        this.cutgrade = cutgrade;
    }

    public String getDepthPct() {
        if(!StringUtil.isValidString(depthPct)){
            depthPct=null;
        }
        return depthPct;
    }

    public void setDepthPct(String depthPct) {
        this.depthPct = depthPct;
    }

    public String getDistribution() {
        if(!StringUtil.isValidString(distribution)){
            distribution=null;
        }
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getFluorescence() {
        if(!StringUtil.isValidString(fluorescence)){
            fluorescence=null;
        }
        return fluorescence;
    }

    public void setFluorescence(String fluorescence) {
        this.fluorescence = fluorescence;
    }

    public String getGirdle() {
        if(!StringUtil.isValidString(girdle)){
            girdle=null;
        }
        return girdle;
    }

    public void setGirdle(String girdle) {
        this.girdle = girdle;
    }

    public String getGrade() {
        if(!StringUtil.isValidString(grade)){
            grade=null;
        }
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getInscription() {
        if(!StringUtil.isValidString(inscription)){
            inscription=null;
        }
        return inscription;
    }

    public void setInscription(String inscription) {
        this.inscription = inscription;
    }

    public String getLowerhalf() {
        if(!StringUtil.isValidString(lowerhalf)){
            lowerhalf=null;
        }
        return lowerhalf;
    }

    public void setLowerhalf(String lowerhalf) {
        this.lowerhalf = lowerhalf;
    }

    public String getMeasurements() {
        if(!StringUtil.isValidString(measurements)){
            measurements=null;
        }
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    public String getMilky() {
        if(!StringUtil.isValidString(milky)){
            milky=null;
        }
        return milky;
    }

    public void setMilky(String milky) {
        this.milky = milky;
    }

    public String getOrigin() {
        if(!StringUtil.isValidString(origin)){
            origin=null;
        }
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPavilionangle() {
        if(!StringUtil.isValidString(pavilionangle)){
            pavilionangle=null;
        }
        return pavilionangle;
    }

    public void setPavilionangle(String pavilionangle) {
        this.pavilionangle = pavilionangle;
    }

    public String getPaviliondepth() {
        if(!StringUtil.isValidString(paviliondepth)){
            paviliondepth=null;
        }
        return paviliondepth;
    }

    public void setPaviliondepth(String paviliondepth) {
        this.paviliondepth = paviliondepth;
    }

    public String getPolish() {
        if(!StringUtil.isValidString(polish)){
            polish=null;
        }
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getReportcomments() {
        if(!StringUtil.isValidString(reportcomments)){
            reportcomments=null;
        }
        return reportcomments;
    }

    public void setReportcomments(String reportcomments) {
        this.reportcomments = reportcomments;
    }

    public String getReportdate() {
        if(!StringUtil.isValidString(reportdate)){
            reportdate=null;
        }
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getReportno() {
        if(!StringUtil.isValidString(reportno)){
            reportno=null;
        }
        return reportno;
    }

    public void setReportno(String reportno) {
        this.reportno = reportno;
    }

    public String getShape() {
        if(!StringUtil.isValidString(shape)){
            shape=null;
        }
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getStarlength() {
        if(!StringUtil.isValidString(starlength)){
            starlength=null;
        }
        return starlength;
    }

    public void setStarlength(String starlength) {
        this.starlength = starlength;
    }

    public String getSymmetry() {
        if(!StringUtil.isValidString(symmetry)){
            symmetry=null;
        }
        return symmetry;
    }

    public void setSymmetry(String symmetry) {
        this.symmetry = symmetry;
    }

    public String getTablePct() {
        if(!StringUtil.isValidString(tablePct)){
            tablePct=null;
        }
        return tablePct;
    }

    public void setTablePct(String tablePct) {
        this.tablePct = tablePct;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reportno);
        dest.writeString(this.reportdate);
        dest.writeString(this.shape);
        dest.writeString(this.caratweight);
        dest.writeString(this.colorgrade);
        dest.writeString(this.claritygrade);
        dest.writeString(this.cutgrade);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.fluorescence);
        dest.writeString(this.colsh);
        dest.writeString(this.milky);
        dest.writeString(this.measurements);
        dest.writeString(this.depthPct);
        dest.writeString(this.tablePct);
        dest.writeString(this.crownangle);
        dest.writeString(this.crownheight);
        dest.writeString(this.pavilionangle);
        dest.writeString(this.paviliondepth);
        dest.writeString(this.starlength);
        dest.writeString(this.lowerhalf);
        dest.writeString(this.girdle);
        dest.writeString(this.culet);
        dest.writeString(this.clarityKeys);
        dest.writeString(this.reportcomments);
        dest.writeString(this.inscription);
        dest.writeString(this.distribution);
        dest.writeString(this.origin);
        dest.writeString(this.grade);
    }

    public GiaCertificateResponse() {
    }

    protected GiaCertificateResponse(Parcel in) {
        this.reportno = in.readString();
        this.reportdate = in.readString();
        this.shape = in.readString();
        this.caratweight = in.readString();
        this.colorgrade = in.readString();
        this.claritygrade = in.readString();
        this.cutgrade = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.fluorescence = in.readString();
        this.colsh = in.readString();
        this.milky = in.readString();
        this.measurements = in.readString();
        this.depthPct = in.readString();
        this.tablePct = in.readString();
        this.crownangle = in.readString();
        this.crownheight = in.readString();
        this.pavilionangle = in.readString();
        this.paviliondepth = in.readString();
        this.starlength = in.readString();
        this.lowerhalf = in.readString();
        this.girdle = in.readString();
        this.culet = in.readString();
        this.clarityKeys = in.readString();
        this.reportcomments = in.readString();
        this.inscription = in.readString();
        this.distribution = in.readString();
        this.origin = in.readString();
        this.grade = in.readString();
    }

    public static final Creator<GiaCertificateResponse> CREATOR = new Creator<GiaCertificateResponse>() {
        @Override
        public GiaCertificateResponse createFromParcel(Parcel source) {
            return new GiaCertificateResponse(source);
        }

        @Override
        public GiaCertificateResponse[] newArray(int size) {
            return new GiaCertificateResponse[size];
        }
    };
}
