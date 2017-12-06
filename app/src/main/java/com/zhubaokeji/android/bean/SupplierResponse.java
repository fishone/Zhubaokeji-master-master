package com.zhubaokeji.android.bean;

/**
 * Created by fisho on 2017/7/18.
 */

public class SupplierResponse {
    private String Abbreviation;
    private String FullName;
    private String RapNetId;
    private String Skype;
    private String Email;
    private String HkAddress;

    public String getAbbreviation() {
        return Abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        Abbreviation = abbreviation;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getRapNetId() {
        return RapNetId;
    }

    public void setRapNetId(String rapNetId) {
        RapNetId = rapNetId;
    }

    public String getSkype() {
        return Skype;
    }

    public void setSkype(String skype) {
        Skype = skype;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHkAddress() {
        return HkAddress;
    }

    public void setHkAddress(String hkAddress) {
        HkAddress = hkAddress;
    }
}
