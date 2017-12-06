package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fisho on 2017/4/19.
 */

public class JpUserInfo implements Parcelable {
    //用户名
    private  String vipaccount;
    //性别
    private  String sexname;
    //电话
    private  String tellphone;
    //联系人
    private  String name;
    //用户id
    private String id;
    //邮箱
    private  String email;
    //公司名称
    private  String companyname;
    //总金额
    private String netmoney;
    //卡上总余额
    private String rechargemoney;
    //优惠点
    private String offpoint;
    //公司地址
    private  String address;
    //token
    private  String token;

    public String getVipaccount() {
        return vipaccount;
    }

    public void setVipaccount(String vipaccount) {
        this.vipaccount = vipaccount;
    }

    public String getSexname() {
        return sexname;
    }

    public void setSexname(String sexname) {
        this.sexname = sexname;
    }

    public String getTellphone() {
        return tellphone;
    }

    public void setTellphone(String tellphone) {
        this.tellphone = tellphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNetmoney() {
        return netmoney;
    }

    public void setNetmoney(String netmoney) {
        this.netmoney = netmoney;
    }

    public String getRechargemoney() {
        if(this.rechargemoney == null || this.rechargemoney.equals("")){
            rechargemoney="0";
        }
        return rechargemoney;
    }

    public void setRechargemoney(String rechargemoney) {
        this.rechargemoney = rechargemoney;
    }

    public String getOffpoint() {
        return offpoint;
    }

    public void setOffpoint(String offpoint) {
        this.offpoint = offpoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vipaccount);
        dest.writeString(this.sexname);
        dest.writeString(this.tellphone);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.email);
        dest.writeString(this.companyname);
        dest.writeString(this.netmoney);
        dest.writeString(this.rechargemoney);
        dest.writeString(this.offpoint);
        dest.writeString(this.address);
        dest.writeString(this.token);
    }

    public JpUserInfo() {
    }

    protected JpUserInfo(Parcel in) {
        this.vipaccount = in.readString();
        this.sexname = in.readString();
        this.tellphone = in.readString();
        this.name = in.readString();
        this.id = in.readString();
        this.email = in.readString();
        this.companyname = in.readString();
        this.netmoney = in.readString();
        this.rechargemoney = in.readString();
        this.offpoint = in.readString();
        this.address = in.readString();
        this.token = in.readString();
    }

    public static final Creator<JpUserInfo> CREATOR = new Creator<JpUserInfo>() {
        @Override
        public JpUserInfo createFromParcel(Parcel source) {
            return new JpUserInfo(source);
        }

        @Override
        public JpUserInfo[] newArray(int size) {
            return new JpUserInfo[size];
        }
    };
}
