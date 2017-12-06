package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fisho on 2016/12/5.
 */

public class User implements Parcelable {
    /**
     * 用户名
     */
    private String loginName;

    /**
     * 登陆密码
     */
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.loginName);
        dest.writeString(this.password);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.loginName = in.readString();
        this.password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
