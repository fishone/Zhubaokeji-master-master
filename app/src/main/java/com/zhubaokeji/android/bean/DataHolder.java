package com.zhubaokeji.android.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fisho on 2018/1/9.
 */

public class DataHolder{
    static Map<String, WeakReference<Object>> data = new HashMap<String, WeakReference<Object>>();

    public static void save(String id, Object object) {
        data.put(id, new WeakReference<Object>(object));
    }

    public static Object retrieve(String id) {
        WeakReference<Object> objectWeakReference = data.get(id);
        return objectWeakReference.get();
    }
}
