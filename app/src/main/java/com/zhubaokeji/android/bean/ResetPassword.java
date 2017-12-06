package com.zhubaokeji.android.bean;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/6/26.
 */

public class ResetPassword {
    //账号
    private String vipaccount;
    //登录密码
    private String vippsd;
    //验证码
    private String checkcode;

    public String getVipaccount() {
        return vipaccount;
    }

    public void setVipaccount(String vipaccount) {
        this.vipaccount = vipaccount;
    }

    public String getVippsd() {
        return vippsd;
    }

    public void setVippsd(String vippsd) {
        this.vippsd = vippsd;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    public String toJson(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&");
        if (StringUtil.isValidString(this.vipaccount)) {
            stringBuffer.append("vipaccount=").append(this.vipaccount).append("&");
        }
        if (StringUtil.isValidString(this.vippsd)) {
            stringBuffer.append("vippsd=").append(this.vippsd).append("&");
        }
        if(StringUtil.isValidString(this.checkcode)){
            stringBuffer.append("checkcode=").append(this.checkcode);
        }
        return stringBuffer.toString();
    }
}
