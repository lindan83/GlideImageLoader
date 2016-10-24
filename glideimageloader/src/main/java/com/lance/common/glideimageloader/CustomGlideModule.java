package com.lance.common.glideimageloader;

/**
 * Created by lindan on 16-10-21.
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * 自定义 GlideModule
 */
public class CustomGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

//        new GlideBuilder(getApplicationContext()).setDiskCache(new DiskCache.Factory() {
//            @Override
//            public DiskCache build() {
//                File cacheLocation = getApplicationContext().getExternalCacheDir();
//                cacheLocation.mkdirs();
//                return DiskLruCacheWrapper.get(cacheLocation, 250 * 1024 * 1024);
//            }
//        });
        String cacheDir = "imgcache";
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheDir, 250 * 1024 * 1024));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheDir, 250 * 1024 * 1024));

//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//
//        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
//        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
//
//        builder.setMemoryCache( new LruResourceCache( customMemoryCacheSize ));
//        builder.setBitmapPool( new LruBitmapPool( customBitmapPoolSize ));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}