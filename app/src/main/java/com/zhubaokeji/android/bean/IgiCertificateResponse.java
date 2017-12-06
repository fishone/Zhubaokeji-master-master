package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by Yuizhi on 2017/1/9.
 */

public class IgiCertificateResponse extends  IgiPic implements Parcelable {

    private String reportnumber;
    private String reportcitydate;
    private String description;
    private String shapeandcut;
    private String caratweight;
    private String colorgrade;
    private String claritygrade;
    private String cutgrade;
    private String proportions;
    private String polishsymmetry;
    private String polish;
    private String symmetry;
    private String measurements;
    private String tablesize;
    private String crownheightA;
    private String paviliondepthA;
    private String girdlethickness;
    private String culet;
    private String tobaldepth;
    private String fluorescence;
    private String laserscribe;

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

    public String getColorgrade() {
        if(!StringUtil.isValidString(colorgrade)){
            colorgrade=null;
        }
        return colorgrade;
    }

    public void setColorgrade(String colorgrade) {
        this.colorgrade = colorgrade;
    }

    public String getCrownheightA() {
        if(!StringUtil.isValidString(crownheightA)){
            crownheightA=null;
        }
        return crownheightA;
    }

    public void setCrownheightA(String crownheightA) {
        this.crownheightA = crownheightA;
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

    public String getDescription() {
        if(!StringUtil.isValidString(description)){
            description=null;
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getGirdlethickness() {
        if(!StringUtil.isValidString(girdlethickness)){
            girdlethickness=null;
        }
        return girdlethickness;
    }

    public void setGirdlethickness(String girdlethickness) {
        this.girdlethickness = girdlethickness;
    }

    public String getLaserscribe() {
        if(!StringUtil.isValidString(laserscribe)){
            laserscribe=null;
        }
        return laserscribe;
    }

    public void setLaserscribe(String laserscribe) {
        this.laserscribe = laserscribe;
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

    public String getPaviliondepthA() {
        if(!StringUtil.isValidString(paviliondepthA)){
            paviliondepthA=null;
        }
        return paviliondepthA;
    }

    public void setPaviliondepthA(String paviliondepthA) {
        this.paviliondepthA = paviliondepthA;
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

    public String getPolishsymmetry() {
        if(!StringUtil.isValidString(polishsymmetry)){
            polishsymmetry=null;
        }
        return polishsymmetry;
    }

    public void setPolishsymmetry(String polishsymmetry) {
        this.polishsymmetry = polishsymmetry;
    }

    public String getProportions() {
        if(!StringUtil.isValidString(proportions)){
            proportions=null;
        }
        return proportions;
    }

    public void setProportions(String proportions) {
        this.proportions = proportions;
    }

    public String getReportcitydate() {
        if(!StringUtil.isValidString(reportcitydate)){
            reportcitydate=null;
        }
        return reportcitydate;
    }

    public void setReportcitydate(String reportcitydate) {
        this.reportcitydate = reportcitydate;
    }

    public String getReportnumber() {
        if(!StringUtil.isValidString(reportnumber)){
            reportnumber=null;
        }
        return reportnumber;
    }

    public void setReportnumber(String reportnumber) {
        this.reportnumber = reportnumber;
    }

    public String getShapeandcut() {
        if(!StringUtil.isValidString(shapeandcut)){
            shapeandcut=null;
        }
        return shapeandcut;
    }

    public void setShapeandcut(String shapeandcut) {
        this.shapeandcut = shapeandcut;
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

    public String getTablesize() {
        if(!StringUtil.isValidString(tablesize)){
            tablesize=null;
        }
        return tablesize;
    }

    public void setTablesize(String tablesize) {
        this.tablesize = tablesize;
    }

    public String getTobaldepth() {
        if(!StringUtil.isValidString(tobaldepth)){
            tobaldepth=null;
        }
        return tobaldepth;
    }

    public void setTobaldepth(String tobaldepth) {
        this.tobaldepth = tobaldepth;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reportnumber);
        dest.writeString(this.reportcitydate);
        dest.writeString(this.description);
        dest.writeString(this.shapeandcut);
        dest.writeString(this.caratweight);
        dest.writeString(this.colorgrade);
        dest.writeString(this.claritygrade);
        dest.writeString(this.cutgrade);
        dest.writeString(this.proportions);
        dest.writeString(this.polishsymmetry);
        dest.writeString(this.polish);
        dest.writeString(this.symmetry);
        dest.writeString(this.measurements);
        dest.writeString(this.tablesize);
        dest.writeString(this.crownheightA);
        dest.writeString(this.paviliondepthA);
        dest.writeString(this.girdlethickness);
        dest.writeString(this.culet);
        dest.writeString(this.tobaldepth);
        dest.writeString(this.fluorescence);
        dest.writeString(this.laserscribe);
    }

    public IgiCertificateResponse() {
    }

    protected IgiCertificateResponse(Parcel in) {
        this.reportnumber = in.readString();
        this.reportcitydate = in.readString();
        this.description = in.readString();
        this.shapeandcut = in.readString();
        this.caratweight = in.readString();
        this.colorgrade = in.readString();
        this.claritygrade = in.readString();
        this.cutgrade = in.readString();
        this.proportions = in.readString();
        this.polishsymmetry = in.readString();
        this.polish = in.readString();
        this.symmetry = in.readString();
        this.measurements = in.readString();
        this.tablesize = in.readString();
        this.crownheightA = in.readString();
        this.paviliondepthA = in.readString();
        this.girdlethickness = in.readString();
        this.culet = in.readString();
        this.tobaldepth = in.readString();
        this.fluorescence = in.readString();
        this.laserscribe = in.readString();
    }

    public static final Creator<IgiCertificateResponse> CREATOR = new Creator<IgiCertificateResponse>() {
        @Override
        public IgiCertificateResponse createFromParcel(Parcel source) {
            return new IgiCertificateResponse(source);
        }

        @Override
        public IgiCertificateResponse[] newArray(int size) {
            return new IgiCertificateResponse[size];
        }
    };
}
