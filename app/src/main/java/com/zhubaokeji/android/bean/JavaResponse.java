package com.zhubaokeji.android.bean;

import java.io.Serializable;

/**
 * Created by fisho on 2017/7/10.
 */

public class JavaResponse<T> implements Serializable{
    private static final long serialVersionUID = 5213230387175987834L;

    public int status;

    public String reason;

    public T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
