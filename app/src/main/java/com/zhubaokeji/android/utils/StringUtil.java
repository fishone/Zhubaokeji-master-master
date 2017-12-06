package com.zhubaokeji.android.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yuizhi on 2016/12/8.
 */

public class StringUtil {
    /**
     * 默认分隔符英文逗号 ,
     */
    public static final String defaultSeparator = ",";

    /**
     * 验证字符串是否有效 空为无效
     *
     * @param str
     * @return 有效为ture 无效为false
     */
    public static boolean isValidString(String str) {
        return null != str && 0 < str.trim().length() && !str.equals("null") && !str.equals("");
    }

    /**
     * 去除字符串的空格
     * @param str
     * @return
     */
    public static String trimString(String str) {
        return StringUtil.isValidString(str) == true ? str.trim() : "";
    }

    /**
     * 将集合中的字符串按照指定分隔符划分返回字符串
     *
     * @param list
     * @param separator
     *            分隔符
     * @return
     */
    public static String listToString(List<String> list, String separator) {
        if (!isValidString(separator))
            separator = defaultSeparator;
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append(separator);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // 去除最后的分隔符
        }
        return sb.toString();
    }

    /**
     * 将集合中的字符串按照指定分隔符划分返回字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<Long> list) {
        StringBuilder sb = new StringBuilder();
        for (Long str : list) {
            sb.append(str).append(defaultSeparator);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // 去除最后的分隔符
        }
        return sb.toString();
    }

    /**
     * 将list转为String
     * 格式如： 'a','b','c','d'
     * @param sqlList
     * @return
     */
    public static String listToSQLString(Collection<String> sqlList){
        if(null!=sqlList&&sqlList.size()>0){
            StringBuffer idLst = new StringBuffer("'");
            for(String obj : sqlList){
                idLst.append(obj).append("','");
            }
            if(idLst.length()>0) {
                idLst=idLst.deleteCharAt(idLst.length()-1);
                idLst=idLst.deleteCharAt(idLst.length()-1);
            }
            return idLst.toString();
        }
        return null;
    }

    public static String stringToSQLString(String str,String separator){
        List<String> list = stringToList(str,separator);
        if(list.size()>0){
            return listToSQLString(list);
        }
        return null;
    }

    /**
     * 将string转换为List<Long>
     * @param str
     * @return
     */
    public static List<Long> stringToLongList(String str){
        List<Long> ids = new ArrayList<>();
        String[] idsStr = str.split(defaultSeparator);
        for(String idStr:idsStr){
            ids.add(Long.parseLong(idStr));
        }
        return ids;
    }

    /**
     * 将string转换为List<String>
     * @param str
     * @return
     */
    public static List<String> stringToList(String str, String separator){
        if(!isValidString(str))return Collections.EMPTY_LIST;
        if (!isValidString(separator)){
            separator = defaultSeparator;
        }
        List<String> list = new ArrayList<String>();
        String[] strArray = str.split(separator);
        for(String s:strArray){
            list.add(s.trim());
        }
        return list;
    }

    /**
     * 比较字段值是否修改
     * @param oldStr
     * @param newStr
     * @return
     */
    public static boolean compareContentIsChange(String oldStr, String newStr){
        return (oldStr == null && StringUtil.isValidString(newStr))
                || (oldStr != null && !newStr.equals(oldStr));
    }

    /**
     * 字符串替换，从头到尾查询一次，替换后的字符串不检查
     * @param str     源字符串
     * @param oldStr  目标字符串
     * @param newStr  替换字符串
     * @return        替换后的字符串
     */
    static public String replaceAll(String str, String oldStr, String newStr)
    {
        int i = str.indexOf(oldStr);
        int n = 0;
        while(i != -1)
        {
            str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
            i = str.indexOf(oldStr, i + newStr.length());
            n++;
        }
        return str;
    }

    /**
     * 字符串替换，左边第一个。
     * @param str     源字符串
     * @param oldStr  目标字符串
     * @param newStr  替换字符串
     * @return        替换后的字符串
     */
    static public String replaceFirst(String str, String oldStr, String newStr)
    {
        int i = str.indexOf(oldStr);
        if (i == -1) return str;
        str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
        return str;
    }
}
