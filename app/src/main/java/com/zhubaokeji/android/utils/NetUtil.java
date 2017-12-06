package com.zhubaokeji.android.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.orhanobut.logger.Logger;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.view.JpLoginActivity;
import com.zhubaokeji.android.view.ZhubaoLoginActivity;

import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

public class NetUtil {
	/**
	 * 没有连接网络
	 */
	public static final int NETWORK_NONE = -1;
	/**
	 * 移动网络
	 */
	public static final int NETWORK_MOBILE = 0;
	/**
	 * 无线网络
	 */
	public static final int NETWORK_WIFI = 1;


	private NetUtil(){}

	public static int getNetWorkState(Context context) {
		// 得到连接管理器对象
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

			if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
				return NETWORK_WIFI;
			} else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
				return NETWORK_MOBILE;
			}
		} else {
			return NETWORK_NONE;
		}
		return NETWORK_NONE;
	}

	/**
	 * 判断网络是否连接
	 *
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivity) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null || cm.getActiveNetworkInfo() == null)
			return false;
		else
			return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**
	 * 判断是否是手机连接
	 */
	public static boolean isMobile(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null || cm.getActiveNetworkInfo() == null)
			return false;
		else
			return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;

	}

	/**
	 * 获取网络类型 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO<br/>
	 * NETWORK_TYPE_CDMA 网络类型为CDMA<br/>
	 * NETWORK_TYPE_EDGE 网络类型为EDGE<br/>
	 * NETWORK_TYPE_EVDO_0 网络类型为EVDO0<br/>
	 * NETWORK_TYPE_EVDO_A 网络类型为EVDOA<br/>
	 * NETWORK_TYPE_GPRS 网络类型为GPRS<br/>
	 * NETWORK_TYPE_HSDPA 网络类型为HSDPA<br/>
	 * NETWORK_TYPE_HSPA 网络类型为HSPA<br/>
	 * NETWORK_TYPE_HSUPA 网络类型为HSUPA<br/>
	 * NETWORK_TYPE_UMTS 网络类型为UMTS<br/>
	 *
	 * @param context
	 * @return
	 */
	public static int getNetType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null || cm.getActiveNetworkInfo() == null)
			return -1;
		else
			return cm.getActiveNetworkInfo().getType();

	}

	/**
	 * 获取手机ip
	 *
	 * @return
	 */
	public static String getPhoneIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Activity activity) {
//		Intent intent = new Intent("/");
//		ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
//		intent.setComponent(cm);
//		intent.setAction("android.intent.action.VIEW");
//		activity.startActivityForResult(intent, 0);
		try {
			Intent intent;

			if (android.os.Build.MANUFACTURER.equals("Meizu")) {
				intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
			} else if (android.os.Build.VERSION.SDK_INT > 10) {
				intent = new Intent(Settings.ACTION_SETTINGS);
			} else {
				intent = new Intent();
				ComponentName component = new ComponentName(
						"com.android.settings",
						"com.android.settings.WirelessSettings");
				intent.setComponent(component);
				intent.setAction("android.intent.action.VIEW");
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(intent);
		}catch (Exception e){
			Logger.e("设置跳转",e);
		}
	}

	public static boolean  isZhubaoQuery(Context context){
		if(NetUtil.isConnected(context) != true){
            zhubao_Login_boolean=false;
			return false;
		}
		return true;
	}

	public static boolean isJpQuery(Context context){
		if(NetUtil.isConnected(context) != true){
            jp_Login_Boolean=false;
			return false;
		}
		return true;
	}

	public static void  zbException(Context context,Throwable throwable){
		if(throwable !=null)throwable.printStackTrace();
		if(throwable instanceof UnknownHostException ||throwable instanceof ConnectException){
			ToastUtil.show(context,"网络未连接,请连接网络");
		}else if(throwable instanceof SocketTimeoutException){
			ToastUtil.show(context,"网络请求超时");
		}else if(throwable instanceof HttpException){
			Logger.e("exception", "网络端响应码404或者505了，请联系服务器开发人员");
		}else if(throwable instanceof StorageException){
			Logger.e("exception", "SD卡不存在或者没有权限");
		}
		else {
			ToastUtil.show(context,"查询出错，请重试");
		}
		if(NetUtil.isZhubaoQuery(context) ==false){
			context.startActivity(new Intent(context, ZhubaoLoginActivity.class));
			return;
		}
	}

	public static void  jpException(Context context,Throwable throwable){
		if(throwable !=null)throwable.printStackTrace();
		if(throwable instanceof UnknownHostException ||throwable instanceof ConnectException){
			ToastUtil.show(context,"网络未连接,请连接网络");
		}else if(throwable instanceof SocketTimeoutException){
			ToastUtil.show(context,"网络请求超时");
		}else {
			ToastUtil.show(context,"操作错误，请重试");
		}
		if(NetUtil.isJpQuery(context) ==false){
			context.startActivity(new Intent(context, JpLoginActivity.class));
			return;
		}
	}
}
