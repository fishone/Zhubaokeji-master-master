package com.zhubaokeji.android.bean;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/2/18.
 */

public class CalculatorRequest {
    private String s_color;

    private String s_clarity;

    private String s_carat;

    private String s_shape;

    public String getS_color() {
        return s_color;
    }

    public void setS_color(String s_color) {
        this.s_color = s_color;
    }

    public String getS_clarity() {
        return s_clarity;
    }

    public void setS_clarity(String s_clarity) {
        this.s_clarity = s_clarity;
    }

    public String getS_carat() {
        return s_carat;
    }

    public void setS_carat(String s_carat) {
        this.s_carat = s_carat;
    }

    public String getS_shape() {
        return s_shape;
    }

    public void setS_shape(String s_shape) {
        this.s_shape = s_shape;
    }

    public String toJson(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&");
        if(StringUtil.isValidString(this.s_carat)){
            stringBuffer.append("s_carat=").append(this.s_carat).append("&");
        }
        if(StringUtil.isValidString(this.s_shape)){
            stringBuffer.append("s_shape=").append(this.s_shape).append("&");
        }
        if (StringUtil.isValidString(this.s_clarity)) {
            stringBuffer.append("s_clarity=").append(this.s_clarity).append("&");
        }
        if (StringUtil.isValidString(this.s_color)) {
            stringBuffer.append("s_color=").append(this.s_color);
        }
        return stringBuffer.toString();
    }
}
