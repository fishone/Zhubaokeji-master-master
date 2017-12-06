package com.zhubaokeji.android.bean;

import java.math.BigDecimal;

/**
 * Created by fisho on 2017/7/11.
 */

public class CalculatorResponse {
    public  String color;

    public String  shape;

    public String clarity;

    public String carat;

    public String price;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getCarat() {
        return carat;
    }

    public void setCarat(String carat) {
        this.carat = carat;
    }

    public String getPrice() {
        BigDecimal bigPrice=new BigDecimal(price);
        bigPrice=bigPrice.multiply(new BigDecimal(100));
        return bigPrice.toString();
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
