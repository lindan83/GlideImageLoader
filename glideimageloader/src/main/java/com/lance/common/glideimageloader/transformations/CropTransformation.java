package com.lance.common.glideimageloader.transformations;

/**
 * Copyright (C) 2017 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class CropTransformation implements Transformation<Bitmap> {

    public enum CropType {
        TOP,
        CENTER,
        BOTTOM
    }

    private BitmapPool bitmapPool;
    private int width;
    private int height;

    private CropType cropType = CropType.CENTER;

    public CropTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public CropTransformation(BitmapPool pool) {
        this(pool, 0, 0);
    }

    public CropTransformation(Context context, int width, int height) {
        this(Glide.get(context).getBitmapPool(), width, height);
    }

    public CropTransformation(BitmapPool pool, int width, int height) {
        this(pool, width, height, CropType.CENTER);
    }

    public CropTransformation(Context context, int width, int height, CropType cropType) {
        this(Glide.get(context).getBitmapPool(), width, height, cropType);
    }

    public CropTransformation(BitmapPool pool, int width, int height, CropType cropType) {
        bitmapPool = pool;
        this.width = width;
        this.height = height;
        this.cropType = cropType;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        width = width == 0 ? source.getWidth() : width;
        height = height == 0 ? source.getHeight() : height;

        Bitmap.Config config =
                source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = bitmapPool.get(width, height, config);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, config);
        }

        float scaleX = (float) width / source.getWidth();
        float scaleY = (float) height / source.getHeight();
        float scale = Math.max(scaleX, scaleY);

        float scaledWidth = scale * source.getWidth();
        float scaledHeight = scale * source.getHeight();
        float left = (width - scaledWidth) / 2;
        float top = getTop(scaledHeight);
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(source, null, targetRect, null);

        return BitmapResource.obtain(bitmap, bitmapPool);
    }

    @Override
    public String getId() {
        return "CropTransformation(width=" + width + ", height=" + height + ", cropType=" + cropType
                + ")";
    }

    private float getTop(float scaledHeight) {
        switch (cropType) {
            case TOP:
                return 0;
            case CENTER:
                return (height - scaledHeight) / 2;
            case BOTTOM:
                return height - scaledHeight;
            default:
                return 0;
        }
    }
}
