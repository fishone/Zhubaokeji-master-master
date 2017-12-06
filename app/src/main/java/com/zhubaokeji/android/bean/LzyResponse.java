package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by fisho on 2017/7/8.
 */

public class LzyResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;
    public  int status;
    public String message;
    public T msgdata;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getMsgdata() {
        return msgdata;
    }

    public void setMsgdata(T msgdata) {
        this.msgdata = msgdata;
    }
}
