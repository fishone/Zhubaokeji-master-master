package com.zhubaokeji.android.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * Created by fisho on 2017/7/5.
 */

public class SettingUtil {
    public static View.OnClickListener getSettingNetwork() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                v.getContext().startActivity(intent);
                }catch (Exception e){
                    Logger.e("设置跳转",e);
                }
            }
        };
    }
}
