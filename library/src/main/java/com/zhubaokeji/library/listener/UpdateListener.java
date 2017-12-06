package com.zhubaokeji.library.listener;

import com.zhubaokeji.library.model.UpdateModel;

/**
 * 更新监听接口
 * @author JebySun
 *
 */
public interface UpdateListener {
	void onFoundNewVersion(UpdateModel updateInfo);
	void onNoFoundNewVersion();
	void onCheckError(String errorMsg);

	void onDownloading(Integer... values);
	void onDownloadFinish();
	void onDownloadError(String errorMsg);
}
