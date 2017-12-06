/*
 * Copyright © Yan Zhenjie. All Rights Reserved
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
package com.zhubaokeji.android.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhubaokeji.android.biz.presenter.ImageWrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Yan Zhenjie on 2017/3/31.
 */
public class GlideImageLoader implements ImageWrapper {
    @Override
    public void displayImage(ImageView imageView, Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(imageView.getContext())
                .load(bytes)
                .into(imageView);
    }
}
