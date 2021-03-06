package com.lance.common.glideimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.lance.common.glideimageloader.transformations.BlurTransformation;
import com.lance.common.glideimageloader.transformations.GlideCircleTransformation;
import com.lance.common.glideimageloader.transformations.GlideRoundTransformation;
import com.lance.common.glideimageloader.transformations.GrayScaleTransformation;
import com.lance.common.glideimageloader.transformations.RotateTransformation;

import java.io.File;

import static com.bumptech.glide.Glide.with;

/**
 * Created by lindan on 16-10-20.
 * Glide 图片加载类
 */

public class GlideImageLoader {
    private GlideImageLoader() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //默认配置
    private static GlideImageConfig defConfig = new GlideImageConfig.Builder().
            setCropType(GlideImageConfig.CENTER_CROP).
            setAsBitmap(true).
            setPlaceHolderResId(android.R.color.white).
            setErrorResId(R.drawable.icon_error).
            setDiskCacheStrategy(GlideImageConfig.DiskCache.ALL).
            setPriority(GlideImageConfig.LoadPriority.NORMAL).build();

    /**
     * 加载String类型的资源
     * SD卡资源："file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg"
     * assets资源："file:///android_asset/f003.gif"
     * raw资源："android.resource://com.lance.app/raw/raw_1"或"android.resource://com.lance.app/raw/"+R.raw.raw_1
     * drawable资源："android.resource://com.lance.app/drawable/news"或"android.resource://com.lance.app/drawable/"+R.drawable.news
     * ContentProvider资源："content://media/external/images/media/139469"
     * http资源："http://www.xxx.com/1438760757_3588.jpg"
     * https资源："https://www.xxx.com/XXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp"
     *
     * @param view     ImageView to show the image
     * @param imageUrl the url of the image to show
     * @param config   GlideImageConfig
     * @param listener GlideLoadingListener
     */
    public static void loadStringResource(ImageView view, String imageUrl, GlideImageConfig config, GlideLoadingListener listener) {
        load(view.getContext(), view, imageUrl, config, listener);
    }

    /**
     * 加载String类型的资源
     * SD卡资源："file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg"
     * assets资源："file:///android_asset/f003.gif"
     * raw资源："android.resource://com.lance.app/raw/raw_1"或"android.resource://com.lance.app/raw/"+R.raw.raw_1
     * drawable资源："android.resource://com.lance.app/drawable/news"或"android.resource://com.lance.app/drawable/"+R.drawable.news
     * ContentProvider资源："content://media/external/images/media/139469"
     * http资源："http://www.xxx.com/1438760757_3588.jpg"
     * https资源："https://www.xxx.com/XXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp"
     *
     * @param view     ImageView to show the image
     * @param imageUrl the url of the image to show
     * @param config   GlideImageConfig
     */
    public static void loadStringResource(ImageView view, String imageUrl, GlideImageConfig config) {
        load(view.getContext(), view, imageUrl, config, null);
    }

    /**
     * 加载String类型的资源
     * SD卡资源："file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg"
     * assets资源："file:///android_asset/f003.gif"
     * raw资源："android.resource://com.lance.app/raw/raw_1"或"android.resource://com.lance.app/raw/"+R.raw.raw_1
     * drawable资源："android.resource://com.lance.app/drawable/news"或"android.resource://com.lance.app/drawable/"+R.drawable.news
     * ContentProvider资源："content://media/external/images/media/139469"
     * http资源："http://www.xxx.com/1438760757_3588.jpg"
     * https资源："https://www.xxx.com/XXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp"
     *
     * @param view     ImageView to show the image
     * @param imageUrl the url of the image to show
     */
    public static void loadStringResource(ImageView view, String imageUrl) {
        load(view.getContext(), view, imageUrl, null, null);
    }

    /**
     * 加载本地文件
     *
     * @param view     ImageView to show the image
     * @param file     the file of the image to show
     * @param config   GlideImageConfig
     * @param listener GlideLoadingListener
     */
    public static void loadFile(ImageView view, File file, GlideImageConfig config, GlideLoadingListener listener) {
        load(view.getContext(), view, file, config, listener);
    }

    /**
     * 加载本地文件
     *
     * @param view   ImageView to show the image
     * @param file   the file of the image to show
     * @param config GlideImageConfig
     */
    public static void loadFile(ImageView view, File file, GlideImageConfig config) {
        load(view.getContext(), view, file, config, null);
    }

    /**
     * 加载本地文件
     *
     * @param view ImageView to show the image
     * @param file the file of the image to show
     */
    public static void loadFile(ImageView view, File file) {
        load(view.getContext(), view, file, null, null);
    }

    /**
     * 加载资源文件
     *
     * @param view       ImageView to show the image
     * @param resourceId the resource id of the image to show
     * @param config     GlideImageConfig
     * @param listener   GlideLoadingListener
     */
    public static void loadResourceId(ImageView view, Integer resourceId, GlideImageConfig config, GlideLoadingListener listener) {
        load(view.getContext(), view, resourceId, config, listener);
    }

    /**
     * 加载资源文件
     *
     * @param view       ImageView to show the image
     * @param resourceId the resource id of the image to show
     * @param config     GlideImageConfig
     */
    public static void loadResourceId(ImageView view, Integer resourceId, GlideImageConfig config) {
        load(view.getContext(), view, resourceId, config, null);
    }

    /**
     * 加载资源文件
     *
     * @param view       ImageView to show the image
     * @param resourceId the resource id of the image to show
     */
    public static void loadResourceId(ImageView view, Integer resourceId) {
        load(view.getContext(), view, resourceId, null, null);
    }

    /**
     * 加载指定URL的图片
     *
     * @param view     ImageView to show the image
     * @param uri      the uri of the image to show
     * @param config   GlideImageConfig
     * @param listener GlideLoadingListener
     */
    public static void loadUri(ImageView view, Uri uri, GlideImageConfig config, GlideLoadingListener listener) {
        load(view.getContext(), view, uri, config, listener);
    }

    /**
     * 加载指定URL的图片
     *
     * @param view   ImageView to show the image
     * @param uri    the uri of the image to show
     * @param config GlideImageConfig
     */
    public static void loadUri(ImageView view, Uri uri, GlideImageConfig config) {
        load(view.getContext(), view, uri, config, null);
    }

    /**
     * 加载指定URL的图片
     *
     * @param view ImageView to show the image
     * @param uri  the uri of the image to show
     */
    public static void loadUri(ImageView view, Uri uri) {
        load(view.getContext(), view, uri, null, null);
    }

    /**
     * 加载GIF动图
     *
     * @param view   ImageView to show the image
     * @param gifUrl the url of the gif image to show
     * @param config GlideImageConfig
     */
    public static void loadGif(ImageView view, String gifUrl, GlideImageConfig config) {
        load(view.getContext(), view, gifUrl, GlideImageConfig.parseBuilder(config).setAsGif(true).build(), null);
    }

    /**
     * 加载GIF动图
     *
     * @param view   ImageView to show the image
     * @param gifUrl the url of the gif image to show
     */
    public static void loadGif(ImageView view, String gifUrl) {
        load(view.getContext(), view, gifUrl, GlideImageConfig.parseBuilder(defConfig).setAsGif(true).build(), null);
    }

    /**
     * 加载指定url图片
     *
     * @param context  Context
     * @param objUrl   the url object of the image to show
     * @param config   GlideImageConfig
     * @param listener GlideLoadingListener
     */
    public static void loadTarget(Context context, Object objUrl, GlideImageConfig config, final GlideLoadingListener listener) {
        load(context, null, objUrl, config, listener);
    }

    private static void load(Context context, ImageView imageView, Object objUrl, GlideImageConfig config, final GlideLoadingListener listener) {
        if (null == objUrl) {
            throw new IllegalArgumentException("objUrl is null");
        } else if (null == config) {
            config = defConfig;
        }
        try {
            GenericRequestBuilder builder = null;
            if (config.isAsGif()) {//gif类型
                GifRequestBuilder request = Glide.with(context).load(objUrl).asGif();
                if (config.getCropType() == GlideImageConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            } else if (config.isAsBitmap()) {  //bitmap类型
                BitmapRequestBuilder request = Glide.with(context).load(objUrl).asBitmap();
                if (config.getCropType() == GlideImageConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                //transform bitmap
                if (config.isRoundedCorners()) {
                    //如果需要边框且边框大小大于0
                    if (config.isRoundedWithBorder() && config.getRoundedBorderWidth() > 0) {
                        request.transform(new GlideRoundTransformation(context,
                                config.getRoundedRadius(),
                                config.getRoundedBorderColor(),
                                config.getRoundedBorderWidth(),
                                config.getRoundedBorderAlpha()));
                    } else {
                        //不需要边框或没有设置边框大小
                        request.transform(new GlideRoundTransformation(context,
                                config.getRoundedRadius()));
                    }
                } else if (config.isCropCircle()) {
                    //如果需要边框且边框大小大于0
                    if (config.isCircleWithBorder() && config.getCircleBorderWidth() > 0) {
                        request.transform(new GlideCircleTransformation(context,
                                config.getCircleBorderColor(),
                                config.getCircleBorderWidth(),
                                config.getCircleBorderAlpha()));
                    } else {
                        request.transform(new GlideCircleTransformation(context));
                    }
                } else if (config.isGrayScale()) {
                    request.transform(new GrayScaleTransformation(context));
                } else if (config.isBlur()) {
                    request.transform(new BlurTransformation(context, 8, 8));
                } else if (config.isRotate()) {
                    request.transform(new RotateTransformation(context, config.getRotateDegree()));
                }
                builder = request;
            } else if (config.isCrossFade()) { //渐入渐出动画
                DrawableRequestBuilder request = with(context).load(objUrl).crossFade();
                if (config.getCropType() == GlideImageConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            }
            //缓存设置
            builder.diskCacheStrategy(config.getDiskCacheStrategy().getStrategy()).
                    skipMemoryCache(config.isSkipMemoryCache()).
                    priority(config.getPriority().getPriority());
            builder.dontAnimate();
            if (null != config.getTag()) {
                builder.signature(new StringSignature(config.getTag()));
            } else {
                builder.signature(new StringSignature(objUrl.toString()));
            }
            if (null != config.getAnimator()) {
                builder.animate(config.getAnimator());
            } else if (null != config.getAnimResId()) {
                builder.animate(config.getAnimResId());
            }
            if (config.getThumbnail() > 0.0f) {
                builder.thumbnail(config.getThumbnail());
            }
            if (null != config.getErrorResId()) {
                builder.error(config.getErrorResId());
            }
            if (null != config.getPlaceHolderResId()) {
                builder.placeholder(config.getPlaceHolderResId());
            }
            if (null != config.getSize()) {
                builder.override(config.getSize().getWidth(), config.getSize().getHeight());
            }
            if (null != listener) {
                setListener(builder, listener);
            }
            if (null != config.getThumbnailUrl()) {
                BitmapRequestBuilder thumbnailRequest = with(context).load(config.getThumbnailUrl()).asBitmap();
                builder.thumbnail(thumbnailRequest).into(imageView);
            } else {
                setTargetView(builder, config, imageView);
            }
        } catch (Exception e) {
            imageView.setImageResource(config.getErrorResId());
        }
    }

    private static void setListener(GenericRequestBuilder request, final GlideLoadingListener listener) {
        request.listener(new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                listener.onError();
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                listener.onSuccess();
                return false;
            }
        });
    }

    private static void setTargetView(GenericRequestBuilder request, GlideImageConfig config, ImageView view) {
        //set targetView
        if (null != config.getSimpleTarget()) {
            request.into(config.getSimpleTarget());
        } else if (null != config.getViewTarget()) {
            request.into(config.getViewTarget());
        } else if (null != config.getNotificationTarget()) {
            request.into(config.getNotificationTarget());
        } else if (null != config.getAppWidgetTarget()) {
            request.into(config.getAppWidgetTarget());
        } else {
            request.into(view);
        }
    }

    /**
     * 加载bitmap
     *
     * @param context  Context
     * @param url      the url of the bitmap to load
     * @param listener GlideBitmapLoadingListener
     */
    public static void loadBitmap(Context context, Object url, final GlideBitmapLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            with(context).
                    load(url).
                    asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    dontAnimate().
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (listener != null) {
                                listener.onSuccess(resource);
                            }
                        }
                    });
        }
    }

    /**
     * 高优先级加载
     *
     * @param url       the url of the image to load
     * @param imageView the ImageView to show the image
     * @param listener  GlideLoadingListener
     */
    public static void loadImageWithHighPriority(Object url, ImageView imageView, final GlideLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            with(imageView.getContext()).
                    load(url).
                    asBitmap().
                    priority(Priority.HIGH).
                    dontAnimate().
                    listener(new RequestListener<Object, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            if (null != listener) {
                                listener.onError();
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (null != listener) {
                                listener.onSuccess();
                            }
                            return false;
                        }
                    }).into(imageView);
        }
    }

    /**
     * 暂停所有正在下载或等待下载的任务
     *
     * @param context Context
     */
    public static void pauseAllTasks(Context context) {
        with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     *
     * @param context Context
     */
    public static void resumeAllTasks(Context context) {
        with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context Context
     */
    public static void clearDiskCache(final Context context) {
        new Thread() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }.start();
    }

    /**
     * 清除所有缓存
     *
     * @param context Context
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }

    /**
     * 清除目标View
     *
     * @param view the target view
     */
    public static void clearTarget(View view) {
        Glide.clear(view);
    }
}