package com.zhubaokeji.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.DateUtils;

import java.util.Date;

/**
 * Created by fisho on 2017/5/11.
 */

public class JpBulletinResponse implements Parcelable {
    private  String Recid;
    private  String Issettop;
    private String Lasttime;
    private String Chtitle;
    private String Entitle;
    private String Chcontent;
    private String Encontent;
    private String Title;

    public String getRecid() {
        return Recid;
    }

    public void setRecid(String recid) {
        Recid = recid;
    }

    public String getIssettop() {
        return Issettop;
    }

    public void setIssettop(String issettop) {
        Issettop = issettop;
    }

    public String getLasttime() {
        if(Lasttime !=null){
            Lasttime = Lasttime.substring(0,10);
        }
        return Lasttime;
    }

    public void setLasttime(String lasttime) {
        Lasttime = lasttime;
    }

    public String getChtitle() {
        return Chtitle;
    }

    public void setChtitle(String chtitle) {
        Chtitle = chtitle;
    }

    public String getEntitle() {
        return Entitle;
    }

    public void setEntitle(String entitle) {
        Entitle = entitle;
    }

    public String getChcontent() {

        return Chcontent.replace("/n","\n");
    }

    public void setChcontent(String chcontent) {
        Chcontent = chcontent;
    }

    public String getEncontent() {
        return Encontent.replace("/n","\n");
    }

    public void setEncontent(String encontent) {
        Encontent = encontent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Recid);
        dest.writeString(this.Issettop);
        dest.writeString(this.Lasttime);
        dest.writeString(this.Chtitle);
        dest.writeString(this.Entitle);
        dest.writeString(this.Chcontent);
        dest.writeString(this.Encontent);
        dest.writeString(this.Title);
    }

    public JpBulletinResponse() {
    }

    protected JpBulletinResponse(Parcel in) {
        this.Recid = in.readString();
        this.Issettop = in.readString();
        this.Lasttime = in.readString();
        this.Chtitle = in.readString();
        this.Entitle = in.readString();
        this.Chcontent = in.readString();
        this.Encontent = in.readString();
        this.Title = in.readString();
    }

    public static final Creator<JpBulletinResponse> CREATOR = new Creator<JpBulletinResponse>() {
        @Override
        public JpBulletinResponse createFromParcel(Parcel source) {
            return new JpBulletinResponse(source);
        }

        @Override
        public JpBulletinResponse[] newArray(int size) {
            return new JpBulletinResponse[size];
        }
    };
}
