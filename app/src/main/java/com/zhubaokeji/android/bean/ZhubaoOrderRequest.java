package com.zhubaokeji.android.bean;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/7/13.
 */

public class ZhubaoOrderRequest {

    public String s_page;

    public String s_size;

    public String s_state;

    public String token;

    public String getS_state() {
        return s_state;
    }

    public void setS_state(String s_state) {
        this.s_state = s_state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getS_page() {
        return s_page;
    }

    public void setS_page(String s_page) {
        this.s_page = s_page;
    }

    public String getS_size() {
        return s_size;
    }

    public void setS_size(String s_size) {
        this.s_size = s_size;
    }

    public String toJson(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&");

        if (StringUtil.isValidString(this.s_page)) {
            stringBuffer.append("s_page=").append(this.s_page).append("&");
        }
        if (StringUtil.isValidString(this.s_size)) {
            stringBuffer.append("s_size=").append(this.s_size).append("&");
        }
        if (StringUtil.isValidString(this.s_state)) {
            stringBuffer.append("s_state=").append(this.s_state).append("&");
        }
        if (StringUtil.isValidString(this.token)) {
            stringBuffer.append("token=").append(this.token).append("&");
        }
        return stringBuffer.toString();
    }
}
