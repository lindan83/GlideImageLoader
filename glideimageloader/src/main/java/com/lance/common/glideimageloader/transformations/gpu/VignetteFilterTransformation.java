package com.lance.common.glideimageloader.transformations.gpu;

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
import android.graphics.PointF;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;

/**
 * Performs a vignetting effect, fading out the image at the edges
 * The directional intensity of the vignetting,
 * with a default of x = 0.5, y = 0.5, start = 0, end = 0.75
 */
public class VignetteFilterTransformation extends GPUFilterTransformation {

    private PointF center;
    private float[] vignetteColor;
    private float vignetteStart;
    private float vignetteEnd;

    public VignetteFilterTransformation(Context context) {
        this(context, Glide.get(context).getBitmapPool());
    }

    public VignetteFilterTransformation(Context context, BitmapPool pool) {
        this(context, pool, new PointF(0.5f, 0.5f), new float[]{0.0f, 0.0f, 0.0f}, 0.0f, 0.75f);
    }

    public VignetteFilterTransformation(Context context, PointF center, float[] color, float start,
                                        float end) {
        this(context, Glide.get(context).getBitmapPool(), center, color, start, end);
    }

    public VignetteFilterTransformation(Context context, BitmapPool pool, PointF center,
                                        float[] color, float start, float end) {
        super(context, pool, new GPUImageVignetteFilter());
        this.center = center;
        vignetteColor = color;
        vignetteStart = start;
        vignetteEnd = end;
        GPUImageVignetteFilter filter = getFilter();
        filter.setVignetteCenter(this.center);
        filter.setVignetteColor(vignetteColor);
        filter.setVignetteStart(vignetteStart);
        filter.setVignetteEnd(vignetteEnd);
    }

    @Override
    public String getId() {
        return "VignetteFilterTransformation(center=" + center.toString() +
                ",color=" + Arrays.toString(vignetteColor) +
                ",start=" + vignetteStart + ",end=" + vignetteEnd + ")";
    }
}
