package com.zhubaokeji.android.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：YUIZHI
 * 项目名称：Zhubaokeji-master
 * 包名：com.zhubaokeji.android.utils
 * 创建时间: 2017/8/19  11:10
 * 描述：把图片保存在手机上
 */

public class ImageSaveUtils {
    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp,String imgName) {
        // 首先保存图片
//        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        String storePath = context.getFilesDir() + "/";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = imgName;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
