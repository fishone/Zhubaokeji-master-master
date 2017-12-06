package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/4/25.
 */

public class JpOrderRequest implements Parcelable {
    //起始页
    private String s_pageindex;
    //每页条数
    private String s_pagesize;
    //订单状态
    private String s_state;
    //输入查询参数
    private String s_num;
    //分类查询
    private String s_numtype;
    //结束日期
    private String s_date_big;
    //开始日期
    private String s_date_small;
    //支付状态
    private String s_isprepayed;
    //token
    private String token;

    public String getS_pageindex() {
        return s_pageindex;
    }

    public void setS_pageindex(String s_pageindex) {
        this.s_pageindex = s_pageindex;
    }

    public String getS_pagesize() {
        return s_pagesize;
    }

    public void setS_pagesize(String s_pagesize) {
        this.s_pagesize = s_pagesize;
    }

    public String getS_state() {
        return s_state;
    }

    public void setS_state(String s_state) {
        this.s_state = s_state;
    }

    public String getS_num() {
        return s_num;
    }

    public void setS_num(String s_num) {
        this.s_num = s_num;
    }

    public String getS_numtype() {
        return s_numtype;
    }

    public void setS_numtype(String s_numtype) {
        this.s_numtype = s_numtype;
    }

    public String getS_date_big() {
        return s_date_big;
    }

    public void setS_date_big(String s_date_big) {
        this.s_date_big = s_date_big;
    }

    public String getS_date_small() {
        return s_date_small;
    }

    public void setS_date_small(String s_date_small) {
        this.s_date_small = s_date_small;
    }

    public String getS_isprepayed() {
        return s_isprepayed;
    }

    public void setS_isprepayed(String s_isprepayed) {
        this.s_isprepayed = s_isprepayed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toJson(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&");
        if (StringUtil.isValidString(this.s_pageindex)) {
            stringBuffer.append("s_pageindex=").append(this.s_pageindex).append("&");
        }
        if (StringUtil.isValidString(this.s_pagesize)) {
            stringBuffer.append("s_pagesize=").append(this.s_pagesize).append("&");
        }
        if (StringUtil.isValidString(this.s_state)) {
            stringBuffer.append("s_state=").append(this.s_state).append("&");
        }
        if (StringUtil.isValidString(this.s_num)) {
            stringBuffer.append("s_num=").append(this.s_num).append("&");
        }
        if (StringUtil.isValidString(this.s_numtype)) {
            stringBuffer.append("s_numtype=").append(this.s_numtype).append("&");
        }
        if (StringUtil.isValidString(this.token)) {
            stringBuffer.append("token=").append(this.token).append("&");
        }
        return stringBuffer.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.s_pageindex);
        dest.writeString(this.s_pagesize);
        dest.writeString(this.s_state);
        dest.writeString(this.s_num);
        dest.writeString(this.s_numtype);
        dest.writeString(this.s_date_big);
        dest.writeString(this.s_date_small);
        dest.writeString(this.s_isprepayed);
        dest.writeString(this.token);
    }

    public JpOrderRequest() {
    }

    protected JpOrderRequest(Parcel in) {
        this.s_pageindex = in.readString();
        this.s_pagesize = in.readString();
        this.s_state = in.readString();
        this.s_num = in.readString();
        this.s_numtype = in.readString();
        this.s_date_big = in.readString();
        this.s_date_small = in.readString();
        this.s_isprepayed = in.readString();
        this.token = in.readString();
    }

    public static final Creator<JpOrderRequest> CREATOR = new Creator<JpOrderRequest>() {
        @Override
        public JpOrderRequest createFromParcel(Parcel source) {
            return new JpOrderRequest(source);
        }

        @Override
        public JpOrderRequest[] newArray(int size) {
            return new JpOrderRequest[size];
        }
    };
}
