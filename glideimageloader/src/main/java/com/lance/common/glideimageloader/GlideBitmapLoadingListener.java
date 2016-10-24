package com.lance.common.glideimageloader;

import android.graphics.Bitmap;

/**
 * Created by lindan on 16-10-20.
 * 加载监听接口
 */

public interface GlideBitmapLoadingListener {
    void onSuccess(Bitmap bitmap);

    void onError();
}
