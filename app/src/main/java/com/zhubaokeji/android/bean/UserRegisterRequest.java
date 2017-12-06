package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by Yuizhi on 2017/1/17.
 */

public class UserRegisterRequest implements Parcelable {

    private String vipid;

    private String vippsd;

    private String vipname;

    private String tell;

    private String company;

    private String address;

    /**
     * 1:是注册   2：是找回密码
     */
    private String code;

    /**
     * 验证码
     */
    private String verfCode;

    public String getVipid() {
        return vipid;
    }

    public void setVipid(String vipid) {
        this.vipid = vipid;
    }

    public String getVippsd() {
        return vippsd;
    }

    public void setVippsd(String vippsd) {
        this.vippsd = vippsd;
    }

    public String getVipname() {
        return vipname;
    }

    public void setVipname(String vipname) {
        this.vipname = vipname;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVerfCode() {
        return verfCode;
    }

    public void setVerfCode(String verfCode) {
        this.verfCode = verfCode;
    }

    public String toJson() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&");
        if (StringUtil.isValidString(this.vipid)) {
            stringBuffer.append("vipid=").append(this.vipid).append("&");
        }
        if (StringUtil.isValidString(this.vippsd)) {
            stringBuffer.append("vippsd=").append(this.vippsd).append("&");
        }
        if (StringUtil.isValidString(this.vipname)) {
            stringBuffer.append("vipname=").append(this.vipname).append("&");
        }
        if (StringUtil.isValidString(this.tell)) {
            stringBuffer.append("tell=").append(this.tell).append("&");
        }
        if (StringUtil.isValidString(this.company)) {
            stringBuffer.append("company=").append(this.company).append("&");
        }
        if (StringUtil.isValidString(this.address)) {
            stringBuffer.append("address=").append(this.address).append("&");
        }
        return stringBuffer.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vipid);
        dest.writeString(this.vippsd);
        dest.writeString(this.vipname);
        dest.writeString(this.tell);
        dest.writeString(this.company);
        dest.writeString(this.address);
        dest.writeString(this.code);
        dest.writeString(this.verfCode);
    }

    public UserRegisterRequest() {
    }

    protected UserRegisterRequest(Parcel in) {
        this.vipid = in.readString();
        this.vippsd = in.readString();
        this.vipname = in.readString();
        this.tell = in.readString();
        this.company = in.readString();
        this.address = in.readString();
        this.code = in.readString();
        this.verfCode = in.readString();
    }

    public static final Creator<UserRegisterRequest> CREATOR = new Creator<UserRegisterRequest>() {
        @Override
        public UserRegisterRequest createFromParcel(Parcel source) {
            return new UserRegisterRequest(source);
        }

        @Override
        public UserRegisterRequest[] newArray(int size) {
            return new UserRegisterRequest[size];
        }
    };
}
