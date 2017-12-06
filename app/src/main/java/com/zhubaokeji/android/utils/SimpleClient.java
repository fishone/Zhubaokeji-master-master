package com.zhubaokeji.android.utils;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fisho on 2016/12/5.
 */

public class SimpleClient{
    private static HttpURLConnection urlConnection;
    private static InputStream inputStream;

    public static String getStr(String url) {

        try {
            URL httpget = new URL(url);
            urlConnection = (HttpURLConnection) httpget.openConnection();

            // 设置编码格式
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            //请求的标题头
            urlConnection.setRequestProperty("Accept", "application/json");

            // 设置请求方式
            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();

        /* 200 represents HTTP OK */
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response =StreamTools.readStream(inputStream);
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    public static String postRemoteAPI(String url,String params) {
        try {
            URL httppost = new URL(url);
            urlConnection = (HttpURLConnection) httppost.openConnection();
            // 设置连接主机超时时间
            urlConnection.setConnectTimeout(3 * 10000);
            //设置从主机读取数据超时
            urlConnection.setReadTimeout(3 * 10000);
            // Post请求必须设置允许输出 默认false
            urlConnection.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConnection.setDoInput(true);
            // Post请求不能使用缓存
            urlConnection.setUseCaches(false);;
            // 设置编码格式
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            //请求的标题头
            urlConnection.setRequestProperty("Accept", "application/json");

            // 设置请求方式
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);  // 从连接中读取数据
            urlConnection.setDoInput(true); // 向连接中写入数据
            urlConnection.setInstanceFollowRedirects(true);   //自动执行HTTP重定向

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();
            // try to get response
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = StreamTools.readStream(inputStream);
              return response;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

}
