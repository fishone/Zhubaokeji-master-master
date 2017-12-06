package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/4/19.
 */

public class JpUser  implements Parcelable {
    //用户名
    private String vipid;
    //密码
    private String vippsd;

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

    public String toJson() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&");
        if (StringUtil.isValidString(this.vipid)) {
            stringBuffer.append("vipid=").append(this.vipid).append("&");
        }
        if (StringUtil.isValidString(this.vippsd)) {
            stringBuffer.append("vippsd=").append(this.vippsd);
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
    }

    public JpUser() {
    }

    protected JpUser(Parcel in) {
        this.vipid = in.readString();
        this.vippsd = in.readString();
    }

    public static final Creator<JpUser> CREATOR = new Creator<JpUser>() {
        @Override
        public JpUser createFromParcel(Parcel source) {
            return new JpUser(source);
        }

        @Override
        public JpUser[] newArray(int size) {
            return new JpUser[size];
        }
    };
}
