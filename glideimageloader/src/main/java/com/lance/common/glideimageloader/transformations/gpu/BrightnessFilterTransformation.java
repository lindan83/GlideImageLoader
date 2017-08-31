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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;

/**
 * brightness value ranges from -1.0 to 1.0, with 0.0 as the normal level
 */
public class BrightnessFilterTransformation extends GPUFilterTransformation {

    private float brightness;

    public BrightnessFilterTransformation(Context context) {
        this(context, Glide.get(context).getBitmapPool());
    }

    public BrightnessFilterTransformation(Context context, BitmapPool pool) {
        this(context, pool, 0.0f);
    }

    public BrightnessFilterTransformation(Context context, float brightness) {
        this(context, Glide.get(context).getBitmapPool(), brightness);
    }

    public BrightnessFilterTransformation(Context context, BitmapPool pool, float brightness) {
        super(context, pool, new GPUImageBrightnessFilter());
        this.brightness = brightness;
        GPUImageBrightnessFilter filter = getFilter();
        filter.setBrightness(this.brightness);
    }

    @Override
    public String getId() {
        return "BrightnessFilterTransformation(brightness=" + brightness + ")";
    }
}
