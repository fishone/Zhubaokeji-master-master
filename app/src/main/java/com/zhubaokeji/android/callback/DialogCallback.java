/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhubaokeji.android.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.request.base.Request;
import com.zhubaokeji.android.utils.LoadingDialog;
/**
 * 创建人:YUIZHI
 * 包名：com.zhubaokeji.android.callback
 * 创建时间：2017/8/29
 * 描述：
*/
public abstract class DialogCallback<T> extends JsonCallback<T> {

    private LoadingDialog dialog;

    private void initDialog(Activity activity) {
        dialog=new LoadingDialog(activity,"玩命加载中...");
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (dialog != null) {
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null) {
            dialog.close();
        }
    }
}
