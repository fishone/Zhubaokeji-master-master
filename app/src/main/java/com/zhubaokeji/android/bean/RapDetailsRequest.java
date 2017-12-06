package com.zhubaokeji.android.bean;

/**
 * Created by fisho on 2017/3/9.
 */

public class RapDetailsRequest {
    /**
     * rap的货号
     */
    private String rapDiamondId;

    /**
     * 加点
     */
    private String discountPoint;

    /**
     * 汇率
     */
    private String rate;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    public String getRapDiamondId() {
        return rapDiamondId;
    }

    public void setRapDiamondId(String rapDiamondId) {
        this.rapDiamondId = rapDiamondId;
    }

    public String getDiscountPoint() {
        return discountPoint;
    }

    public void setDiscountPoint(String discountPoint) {
        this.discountPoint = discountPoint;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
