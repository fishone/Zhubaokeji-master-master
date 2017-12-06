package com.zhubaokeji.android.bean;


public class BasicSetting {
    /**
     * 汇率
     */
    private String rate;

    /**
     * 加点
     */
    private String DiscountPoint;

    /**
     * 倍率
     */
    private String percentage;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDiscountPoint() {
        return DiscountPoint;
    }

    public void setDiscountPoint(String discountPoint) {
        DiscountPoint = discountPoint;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
