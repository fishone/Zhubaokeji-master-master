package com.zhubaokeji.android.bean;

/**
 * Created by fisho on 2017/7/12.
 */

public class LzyListResponse<T>{
    public  int status;
    public String message;
    public ServerModle msgdata;

    public class  ServerModle{
        public  String  total;
        public T rows;
    }

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

    public ServerModle getMsgdata() {
        return msgdata;
    }

    public void setMsgdata(ServerModle msgdata) {
        this.msgdata = msgdata;
    }
}
