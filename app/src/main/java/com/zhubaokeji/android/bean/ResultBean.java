package com.zhubaokeji.android.bean;

import java.util.ArrayList;

/**
 * Created by Yuizhi on 2016/12/26.
 */


public class ResultBean {
    private int status;
    private int total;
    private String message;

    private ArrayList<?> rows;
    private ArrayList<?> result;
    private ArrayList<?> tab;
    private ArrayList<GiaCertificateResponse> giaCertificateResponses;
    private ArrayList<IgiCertificateResponse> igiCertificateResponses;
    private ArrayList<goldResponse> goldRequestArrayList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<?> getResult() {
        return result;
    }

    public void setResult(ArrayList<?> result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<?> getRows() {
        return rows;
    }

    public void setRows(ArrayList<?> rows) {
        this.rows = rows;
    }

    public ArrayList<?> getTab() {
        return tab;
    }

    public void setTab(ArrayList<?> tab) {
        this.tab = tab;
    }

    public ArrayList<GiaCertificateResponse> getGiaCertificateResponses() {
        return giaCertificateResponses;
    }

    public void setGiaCertificateResponses(ArrayList<GiaCertificateResponse> giaCertificateResponses) {
        this.giaCertificateResponses = giaCertificateResponses;
    }

    public ArrayList<IgiCertificateResponse> getIgiCertificateResponses() {
        return igiCertificateResponses;
    }

    public void setIgiCertificateResponses(ArrayList<IgiCertificateResponse> igiCertificateResponses) {
        this.igiCertificateResponses = igiCertificateResponses;
    }

    public ArrayList<goldResponse> getGoldRequestArrayList() {
        return goldRequestArrayList;
    }

    public void setGoldRequestArrayList(ArrayList<goldResponse> goldRequestArrayList) {
        this.goldRequestArrayList = goldRequestArrayList;
    }
}
