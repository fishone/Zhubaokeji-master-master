package com.zhubaokeji.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhubaokeji.android.bean.BasicSetting;
import com.zhubaokeji.android.bean.JpUser;
import com.zhubaokeji.android.bean.JpUserInfo;
import com.zhubaokeji.android.bean.User;
import com.zhubaokeji.android.bean.Userinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Yuizhi on 2016/12/8.
 */

public class SharedPreferencesUtil {
    private Context context;
    public SharedPreferencesUtil(Context context)
    {
        this.context = context;
    }
    /**
     * 实现应用参数保存 zhubao 信息
     * @throws Exception
     */
    public void saveInfo(JpUserInfo userinfo) throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("setInfo",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("vipaccount", userinfo.getVipaccount());
        editor.putString("tellphone", userinfo.getTellphone());
        editor.putString("name", userinfo.getName());
        editor.putString("token", userinfo.getToken());
        editor.putString("email", userinfo.getEmail());
        editor.putString("address", userinfo.getAddress());
        editor.putString("companyname", userinfo.getCompanyname());
        editor.commit();
    }

    /**
     * 实现应用参数提取 zhubao 信息
     * @return
     * @throws Exception
     */
    public JpUserInfo getInfo() throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("setInfo",  Context.MODE_PRIVATE);
        JpUserInfo info= new JpUserInfo();
        info.setVipaccount(preferences.getString("vipaccount",""));
        info.setName(preferences.getString("name",""));
        info.setTellphone(preferences.getString("tellphone",""));
        info.setToken(preferences.getString("token",""));
        info.setId((preferences.getString("id","")));
        info.setEmail(preferences.getString("email",""));
        info.setCompanyname(preferences.getString("companyname",""));
        info.setAddress(preferences.getString("address",""));
        return info;
    }
    /**
     * 实现应用参数保存 jp 信息
     * @throws Exception
     */
    public void saveJpInfo(JpUserInfo userinfo) throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("setJpInfo",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("vipaccount", userinfo.getVipaccount());
        editor.putString("tellphone", userinfo.getTellphone());
        editor.putString("id",userinfo.getId());
        editor.putString("name", userinfo.getName());
        editor.putString("token", userinfo.getToken());
        editor.putString("email", userinfo.getEmail());
        editor.putString("address", userinfo.getAddress());
        editor.putString("companyname", userinfo.getCompanyname());
        editor.apply();
    }

    /**
     * 实现应用参数提取 jp 信息
     * @return
     * @throws Exception
     */
    public JpUserInfo getJpInfo() throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("setJpInfo",  Context.MODE_PRIVATE);
        JpUserInfo info= new JpUserInfo();
        info.setVipaccount(preferences.getString("vipaccount",""));
        info.setName(preferences.getString("name",""));
        info.setTellphone(preferences.getString("tellphone",""));
        info.setToken(preferences.getString("token",""));
        info.setId((preferences.getString("id","")));
        info.setEmail(preferences.getString("email",""));
        info.setCompanyname(preferences.getString("companyname",""));
        info.setAddress(preferences.getString("address",""));
        return info;
    }
    /**
     * 保存登录信息
     * 用户名
     * 密码
     */
    public void saveUserInfo(User user) {
        //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
        SharedPreferences preferences=context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //取到编辑器
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("loginName", user.getLoginName());
        editor.putString("password",user.getPassword());
        //把数据提交给文件中
        editor.commit();
    }

    /**
     * 获取登录信息
     * 用户名
     * 密码
     */
    public User getUsertInfo() throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("userinfo",  Context.MODE_PRIVATE);
        User userinfo= new User();
        userinfo.setLoginName(preferences.getString("loginName",""));
        userinfo.setPassword(preferences.getString("password",""));
        return userinfo;
    }

    /**
     * 保存zhubao登录信息
     * 用户名
     * 密码
     */
    public void saveZhubaoUserInfo(JpUser user) {
        //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
        SharedPreferences preferences=context.getSharedPreferences("zhubaoUserInfo", Context.MODE_PRIVATE);
        //取到编辑器
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("loginName", user.getVipid());
        editor.putString("password",user.getVippsd());
        //把数据提交给文件中
        editor.commit();
    }

    /**
     * 获取zhubao登录信息
     * 用户名
     * 密码
     */
    public JpUser getZhubaoUsertInfo() throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("zhubaoUserInfo",  Context.MODE_PRIVATE);
        JpUser userinfo= new JpUser();
        userinfo.setVipid(preferences.getString("loginName",""));
        userinfo.setVippsd(preferences.getString("password",""));
        return userinfo;
    }


    /**
     * 保存jp登录信息
     * 用户名
     * 密码
     */
    public void saveJpUserInfo(JpUser user) {
        //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
        SharedPreferences preferences=context.getSharedPreferences("JpUserInfo", Context.MODE_PRIVATE);
        //取到编辑器
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("vipid", user.getVipid());
        editor.putString("vipsd",user.getVippsd());
        //把数据提交给文件中
        editor.commit();
    }

    /**
     * 获取jp登录信息
     * 用户名
     * 密码
     */
    public JpUser getJpUsertInfo() throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("JpUserInfo",  Context.MODE_PRIVATE);
        JpUser userinfo= new JpUser();
        userinfo.setVipid(preferences.getString("vipid",""));
        userinfo.setVippsd(preferences.getString("vipsd",""));
        return userinfo;
    }

    /**
     * 保存基础设置
     * 汇率, 加点 ,倍率
     */
    public void saveBasicSetting(BasicSetting basicSetting) {
        //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
        SharedPreferences preferences=context.getSharedPreferences("basicSetting", Context.MODE_PRIVATE);
        //取到编辑器
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("rate", basicSetting.getRate());
        editor.putString("percentage",basicSetting.getPercentage());
        editor.putString("discountPoint",basicSetting.getDiscountPoint());
        //把数据提交给文件中
        editor.commit();
    }

    /**
     * 获取基础设置
     * 汇率, 加点 ,倍率
     */
    public BasicSetting getBasicSetting() throws Exception
    {
        android.content.SharedPreferences preferences=context.getSharedPreferences("basicSetting",  Context.MODE_PRIVATE);
        BasicSetting basicSetting=new BasicSetting();
        basicSetting.setRate(preferences.getString("rate",""));
        basicSetting.setPercentage(preferences.getString("percentage",""));
        basicSetting.setDiscountPoint(preferences.getString("discountPoint",""));
        return basicSetting;
    }


    /*
    保存助宝滚动图片的地址
     */
    public void saveZbImageAddress(List<String> imgList){
         //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
        SharedPreferences.Editor editor =context.getSharedPreferences("zbImgList", Context.MODE_PRIVATE).edit();
        editor.putInt("zbImgList", imgList.size());
        for (int i = 0; i < imgList.size(); i++)
        {
            editor.putString("item_"+i, imgList.get(i));
        }
        //把数据提交给文件中
        editor.commit();
    }

    /*
     * 获取助宝滚动图片的地址
     */
    public List<String> getZbImageAddress() throws Exception{
        List<String> imgList = new ArrayList<String>();
        SharedPreferences preferDataList = context.getSharedPreferences("zbImgList", Context.MODE_PRIVATE);
        int environNums = preferDataList.getInt("zbImgList", 0);
        for (int i = 0; i < environNums; i++)
        {
            String environItem = preferDataList.getString("item_"+i, null);
            imgList.add(environItem);
        }
        return  imgList;
    }

    /*
     *保存JP滚动图片的地址
     */
    public void saveJpImageAddress(List<String> imgList){
        //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
        SharedPreferences.Editor editor =context.getSharedPreferences("jpImgList", Context.MODE_PRIVATE).edit();
        editor.putInt("jpImgList", imgList.size());
        for (int i = 0; i < imgList.size(); i++)
        {
            editor.putString("item_"+i, imgList.get(i));
        }
        //把数据提交给文件中
        editor.commit();
    }
    /*
    * 获取JP滚动图片的地址
    */
    public List<String> getJpImageAddress() throws Exception{
        List<String> imgList = new ArrayList<String>();
        SharedPreferences preferDataList = context.getSharedPreferences("jpImgList", Context.MODE_PRIVATE);
        int environNums = preferDataList.getInt("jpImgList", 0);
        for (int i = 0; i < environNums; i++)
        {
            String environItem = preferDataList.getString("item_"+i, null);
            imgList.add(environItem);
        }
        return  imgList;
    }
}

