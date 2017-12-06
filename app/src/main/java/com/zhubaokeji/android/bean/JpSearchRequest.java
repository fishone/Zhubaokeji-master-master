package com.zhubaokeji.android.bean;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.zhubaokeji.android.utils.StringUtil;

/**
 * Created by fisho on 2017/4/20.
 */

public class  JpSearchRequest implements Parcelable {
    //钻石种类
    private  String s_protype;
    //正/反序
    private  String s_qorderdir;
    //排序字段
    private  String s_qorder;
    //每页大小
    private int size;
    //当前页数
    private int page;
    //形状
    private  String s_shape;
    //颜色
    private  String s_color;
    //强度
    private String s_intensity;
    //光彩
    private String s_gloss;
    //净度
    private  String s_clarity;
    //三项
    private  String s_threepara;
    //切工
    private  String s_cut;
    //抛光
    private  String s_polish;
    //对称
    private  String s_symmetry;
    //荧光
    private  String s_fluorescence;
    //证书
    private  String s_report;
    //奶色
    private  String s_milky;
    //咖色
    private  String s_colsh;
    //绿色
    private  String s_green;
    //黑点
    private  String s_black;
    //现货
    private  String s_spot;
    //价格段开始
    private  String s_rmb1;
    //价格段结束
    private  String s_rmb2;
    //克拉段开始
    private  String s_carat1;
    //克拉段结束
    private  String s_carat2;
    //证书号
    private  String s_reportno;
    //原货号/库存号
    private  String s_stone;
    //供应商
    private  String s_sup;
    //是否分页
    private  String ispageed;
    //token
    private  String token;
    //aap标识符
    private  String appVersion;
    //助宝分页
    private int  s_page;
    //助宝分页数量
    private int s_size;
    /**
     * 汇率
     */
    private String rate;
    /**
     * 设置退点
     */
    private String discountPoint;
    /**
     * 设置倍率
     */
    private String percentage;


    public String getS_protype() {
        return s_protype;
    }

    public void setS_protype(String s_protype) {
        this.s_protype = s_protype;
    }

    public String getS_qorderdir() {
        return s_qorderdir;
    }

    public void setS_qorderdir(String s_qorderdir) {
        this.s_qorderdir = s_qorderdir;
    }

    public String getS_qorder() {
        return s_qorder;
    }

    public void setS_qorder(String s_qorder) {
        this.s_qorder = s_qorder;
    }

    public int getSize() {
        if(this.size<=0){
            this.size = 25;
        }

        if(this.size > 500){
            this.size = 500;
        }

        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        if(this.page<=0){
            this.page = 1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getS_color() {
        return s_color;
    }

    public void setS_color(String s_color) {
        this.s_color = s_color;
    }

    public String getS_clarity() {
        return s_clarity;
    }

    public void setS_clarity(String s_clarity) {
        this.s_clarity = s_clarity;
    }

    public String getS_threepara() {
        return s_threepara;
    }

    public void setS_threepara(String s_threepara) {
        this.s_threepara = s_threepara;
    }

    public String getS_cut() {
        return s_cut;
    }

    public void setS_cut(String s_cut) {
        this.s_cut = s_cut;
    }

    public String getS_polish() {
        return s_polish;
    }

    public void setS_polish(String s_polish) {
        this.s_polish = s_polish;
    }

    public String getS_symmetry() {
        return s_symmetry;
    }

    public void setS_symmetry(String s_symmetry) {
        this.s_symmetry = s_symmetry;
    }

    public String getS_fluorescence() {
        return s_fluorescence;
    }

    public void setS_fluorescence(String s_fluorescence) {
        this.s_fluorescence = s_fluorescence;
    }

    public String getS_report() {
        return s_report;
    }

    public void setS_report(String s_report) {
        this.s_report = s_report;
    }

    public String getS_milky() {
        return s_milky;
    }

    public void setS_milky(String s_milky) {
        this.s_milky = s_milky;
    }

    public String getS_colsh() {
        return s_colsh;
    }

    public void setS_colsh(String s_colsh) {
        this.s_colsh = s_colsh;
    }

    public String getS_green() {
        return s_green;
    }

    public void setS_green(String s_green) {
        this.s_green = s_green;
    }

    public String getS_black() {
        return s_black;
    }

    public void setS_black(String s_black) {
        this.s_black = s_black;
    }

    public String getS_spot() {
        return s_spot;
    }

    public void setS_spot(String s_spot) {
        this.s_spot = s_spot;
    }

    public String getS_rmb1() {
        return s_rmb1;
    }

    public void setS_rmb1(String s_rmb1) {
        this.s_rmb1 = s_rmb1;
    }

    public String getS_rmb2() {
        return s_rmb2;
    }

    public void setS_rmb2(String s_rmb2) {
        this.s_rmb2 = s_rmb2;
    }

    public String getS_carat1() {
        return s_carat1;
    }

    public void setS_carat1(String s_carat1) {
        this.s_carat1 = s_carat1;
    }

    public String getS_carat2() {
        return s_carat2;
    }

    public void setS_carat2(String s_carat2) {
        this.s_carat2 = s_carat2;
    }

    public String getS_reportno() {
        return s_reportno;
    }

    public void setS_reportno(String s_reportno) {
        this.s_reportno = s_reportno;
    }

    public String getS_stone() {
        return s_stone;
    }

    public void setS_stone(String s_stone) {
        this.s_stone = s_stone;
    }

    public String getS_sup() {
        return s_sup;
    }

    public void setS_sup(String s_sup) {
        this.s_sup = s_sup;
    }

    public String getIspageed() {
        if(this.ispageed ==null){
            ispageed="1";
        }
        return ispageed;
    }

    public void setIspageed(String ispageed) {
        this.ispageed = ispageed;
    }

    public String getS_shape() {
        return s_shape;
    }

    public void setS_shape(String s_shape) {
        this.s_shape = s_shape;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getS_page() {
        return s_page;
    }

    public void setS_page(int s_page) {
        this.s_page = s_page;
    }

    public int getS_size() {
        return s_size;
    }

    public void setS_size(int s_size) {
        this.s_size = s_size;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDiscountPoint() {
        return discountPoint;
    }

    public void setDiscountPoint(String discountPoint) {
        this.discountPoint = discountPoint;
    }

    public String getS_intensity() {
        return s_intensity;
    }

    public void setS_intensity(String s_intensity) {
        this.s_intensity = s_intensity;
    }

    public String getS_gloss() {
        return s_gloss;
    }

    public void setS_gloss(String s_gloss) {
        this.s_gloss = s_gloss;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }


    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtil.isValidString(this.s_stone)) {
            stringBuffer.append("货号:").append(this.s_stone).append(";");
        }
        if (StringUtil.isValidString(this.s_spot)) {
            String shopStr=s_spot.replace("1", "HK").replace("0", "IND");
            stringBuffer.append("地址:").append(shopStr).append(";");
        }
        if(StringUtil.isValidString(this.discountPoint)){
            stringBuffer.append("加点:").append(this.discountPoint).append(";");
        }
        if(StringUtil.isValidString(this.getPercentage())){
            stringBuffer.append("倍率:").append(this.percentage).append(";");
        }
        if(StringUtil.isValidString(this.rate)){
            stringBuffer.append("汇率:").append(this.rate).append(";");
        }
        if(StringUtil.isValidString(this.s_report)){
            stringBuffer.append("证书:").append(this.s_report).append(";");
        }
        if(StringUtil.isValidString(this.s_reportno)){
            stringBuffer.append("证书号:").append(this.s_reportno).append(";");
        }
        if (StringUtil.isValidString(this.s_carat1) && StringUtil.isValidString(this.s_carat2)) {
            stringBuffer.append("克拉:").append(this.s_carat1).append("-").append(this.s_carat2).append(";");
        }
        if(StringUtil.isValidString(this.s_shape)){
            String shapeStr = s_shape.replace("1024", "三角形").replace("512", "雷迪恩").replace("256", "梨形").replace("128", "马眼形")
                    .replace("64", "祖母绿").replace("32", "垫形").replace("16", "心形").replace("8", "公主方").replace("4", "椭圆形")
                    .replace("2", "圆形");
            stringBuffer.append("形状:").append(shapeStr).append(";");
        }
        if (StringUtil.isValidString(this.s_intensity)){
            stringBuffer.append("强度:").append(this.s_intensity).append(";");
        }
        if(StringUtil.isValidString(this.s_gloss)){
            stringBuffer.append("光彩:").append(this.s_gloss).append(";");
        }
        if(StringUtil.isValidString(this.s_color)){
            stringBuffer.append("颜色:").append(this.s_color).append(";");
        }
        if(StringUtil.isValidString(this.s_clarity)){
            stringBuffer.append("净度:").append(this.s_clarity).append(";");
        }
        if (StringUtil.isValidString(this.s_threepara)) {
            stringBuffer.append("三项:").append(s_threepara).append(";");
        }
        if (StringUtil.isValidString(this.s_cut)) {
            stringBuffer.append("切工:").append(this.s_cut).append(";");
        }
        if (StringUtil.isValidString(this.s_polish)) {
            stringBuffer.append("抛光:").append(this.s_polish).append(";");
        }
        if (StringUtil.isValidString(this.s_symmetry)) {
            stringBuffer.append("对称:").append(this.s_symmetry).append(";");
        }
        if (StringUtil.isValidString(this.s_fluorescence)) {
            stringBuffer.append("荧光:").append(this.s_fluorescence).append(";");
        }
        return stringBuffer.toString();
    }

    public String toJson(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&");
        if (StringUtil.isValidString(this.s_protype)) {
            stringBuffer.append("s_protype=").append(this.s_protype).append("&");
        }
        if (StringUtil.isValidString(this.s_qorder)) {
            stringBuffer.append("s_qorder=").append(this.s_qorder).append("&");
        }
        if(StringUtil.isValidString(this.s_qorderdir)){
            stringBuffer.append("s_qorderdir=").append(this.s_qorderdir).append("&");
        }
        if(StringUtil.isValidString(this.s_report)){
            stringBuffer.append("s_report=").append(this.s_report).append("&");
        }
        if(StringUtil.isValidString(this.s_reportno)){
            stringBuffer.append("s_reportno=").append(this.s_reportno).append("&");
        }
        if(this.page >0){
            stringBuffer.append("page=").append(this.page).append("&");
        }
        if(this.size >0){
            stringBuffer.append("size=").append(this.size).append("&");
        }
        if(this.s_page >0){
            stringBuffer.append("s_page=").append(this.s_page).append("&");
        }
        if(this.s_size >0){
            stringBuffer.append("s_size=").append(this.s_size).append("&");
        }
        if (StringUtil.isValidString(this.s_carat1)) {
            stringBuffer.append("s_carat1=").append(this.s_carat1).append("&");
        }
        if(StringUtil.isValidString(this.s_carat2)){
            stringBuffer.append("s_carat2=").append(this.s_carat2).append("&");
        }
        if(StringUtil.isValidString(this.s_shape)){
            stringBuffer.append("s_shape=").append(this.s_shape).append("&");
        }
        if(StringUtil.isValidString(this.s_intensity)){
            stringBuffer.append("s_intensity=").append(this.s_intensity).append("&");
        }
        if(StringUtil.isValidString(this.s_gloss)){
            stringBuffer.append("s_gloss=").append(this.s_gloss).append("&");
        }
        if(StringUtil.isValidString(this.s_color)){
            stringBuffer.append("s_color=").append(this.s_color).append("&");
        }
        if(StringUtil.isValidString(this.s_clarity)){
            stringBuffer.append("s_clarity=").append(this.s_clarity).append("&");
        }
        if(StringUtil.isValidString(this.s_threepara)){
            String threeStr = s_threepara.replace("3EX", "4").replace("3VG", "3").replace("3GD", "2").replace("3FR", "1");
            stringBuffer.append("s_threepara=").append(threeStr).append("&");
        }
        if(StringUtil.isValidString(this.s_cut)){
            stringBuffer.append("s_cut=").append(this.s_cut).append("&");
        }
        if (StringUtil.isValidString(this.s_polish)) {
            stringBuffer.append("s_polish=").append(this.s_polish).append("&");
        }
        if (StringUtil.isValidString(this.s_symmetry)) {
            stringBuffer.append("s_symmetry=").append(this.s_symmetry).append("&");
        }
        if (StringUtil.isValidString(this.s_fluorescence)) {
            stringBuffer.append("s_fluorescence=").append(this.s_fluorescence).append("&");
        }
        if (StringUtil.isValidString(this.s_milky)) {
            stringBuffer.append("s_milky=").append(this.s_milky).append("&");
        }
        if (StringUtil.isValidString(this.s_colsh)) {
            stringBuffer.append("s_colsh=").append(this.s_colsh).append("&");
        }
        if (StringUtil.isValidString(this.s_green)) {
            stringBuffer.append("s_green=").append(this.s_green).append("&");
        }
        if (StringUtil.isValidString(this.s_black)) {
            stringBuffer.append("s_black=").append(this.s_black).append("&");
        }
        if (StringUtil.isValidString(this.s_spot)) {
            stringBuffer.append("s_spot=").append(this.s_spot).append("&");
        }
        if (StringUtil.isValidString(this.s_rmb1)) {
            stringBuffer.append("s_rmb1=").append(this.s_rmb1).append("&");
        }
        if (StringUtil.isValidString(this.s_rmb2)) {
            stringBuffer.append("s_rmb2=").append(this.s_rmb2).append("&");
        }
        if (StringUtil.isValidString(this.s_stone)) {
            stringBuffer.append("s_stone=").append(this.s_stone).append("&");
        }
        if (StringUtil.isValidString(this.s_sup)) {
            stringBuffer.append("s_sup=").append(this.s_sup).append("&");
        }
        if (StringUtil.isValidString(this.token)) {
            stringBuffer.append("token=").append(this.token).append("&");
        }
        if (StringUtil.isValidString(this.appVersion)) {
            stringBuffer.append("appVersion=").append(this.appVersion).append("&");
        }
        if (StringUtil.isValidString(this.ispageed)) {
            stringBuffer.append("ispageed=").append(this.ispageed);
        }
        return stringBuffer.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.s_protype);
        dest.writeString(this.s_qorderdir);
        dest.writeString(this.s_qorder);
        dest.writeInt(this.size);
        dest.writeInt(this.page);
        dest.writeString(this.s_shape);
        dest.writeString(this.s_color);
        dest.writeString(this.s_intensity);
        dest.writeString(this.s_gloss);
        dest.writeString(this.s_clarity);
        dest.writeString(this.s_threepara);
        dest.writeString(this.s_cut);
        dest.writeString(this.s_polish);
        dest.writeString(this.s_symmetry);
        dest.writeString(this.s_fluorescence);
        dest.writeString(this.s_report);
        dest.writeString(this.s_milky);
        dest.writeString(this.s_colsh);
        dest.writeString(this.s_green);
        dest.writeString(this.s_black);
        dest.writeString(this.s_spot);
        dest.writeString(this.s_rmb1);
        dest.writeString(this.s_rmb2);
        dest.writeString(this.s_carat1);
        dest.writeString(this.s_carat2);
        dest.writeString(this.s_reportno);
        dest.writeString(this.s_stone);
        dest.writeString(this.s_sup);
        dest.writeString(this.ispageed);
        dest.writeString(this.token);
        dest.writeString(this.appVersion);
        dest.writeInt(this.s_page);
        dest.writeInt(this.s_size);
        dest.writeString(this.rate);
        dest.writeString(this.discountPoint);
        dest.writeString(this.percentage);
    }

    public JpSearchRequest() {
    }

    protected JpSearchRequest(Parcel in) {
        this.s_protype = in.readString();
        this.s_qorderdir = in.readString();
        this.s_qorder = in.readString();
        this.size = in.readInt();
        this.page = in.readInt();
        this.s_shape = in.readString();
        this.s_color = in.readString();
        this.s_intensity = in.readString();
        this.s_gloss = in.readString();
        this.s_clarity = in.readString();
        this.s_threepara = in.readString();
        this.s_cut = in.readString();
        this.s_polish = in.readString();
        this.s_symmetry = in.readString();
        this.s_fluorescence = in.readString();
        this.s_report = in.readString();
        this.s_milky = in.readString();
        this.s_colsh = in.readString();
        this.s_green = in.readString();
        this.s_black = in.readString();
        this.s_spot = in.readString();
        this.s_rmb1 = in.readString();
        this.s_rmb2 = in.readString();
        this.s_carat1 = in.readString();
        this.s_carat2 = in.readString();
        this.s_reportno = in.readString();
        this.s_stone = in.readString();
        this.s_sup = in.readString();
        this.ispageed = in.readString();
        this.token = in.readString();
        this.appVersion = in.readString();
        this.s_page = in.readInt();
        this.s_size = in.readInt();
        this.rate = in.readString();
        this.discountPoint = in.readString();
        this.percentage = in.readString();
    }

    public static final Creator<JpSearchRequest> CREATOR = new Creator<JpSearchRequest>() {
        @Override
        public JpSearchRequest createFromParcel(Parcel source) {
            return new JpSearchRequest(source);
        }

        @Override
        public JpSearchRequest[] newArray(int size) {
            return new JpSearchRequest[size];
        }
    };
}
