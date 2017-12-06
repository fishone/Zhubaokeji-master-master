package com.zhubaokeji.android.bean;

public class VersionBean {
	private String url;// apkurl
	private int versionCode;//最新版本号
	private String desc;//版本描述

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	@Override
	public String toString() {
		return "UrlBean [url=" + url + ", versionCode=" + versionCode
				+ ", desc=" + desc + "]";
	}

}
