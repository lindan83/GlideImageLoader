package com.lance.common.glideimageloader.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.lance.common.glideimageloader.GlideImageLoader;
import com.lance.common.glideimageloader.GlideLoadingListener;

public class MainActivity extends AppCompatActivity {
    private ImageView mIvDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvDemo = (ImageView) findViewById(R.id.iv_demo);

        GlideImageLoader.loadStringResource(mIvDemo,
                "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1477359525&di=0d8d90a43e76b860a0c78ef3c18286dc&src=http://e.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg", null, new GlideLoadingListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "图片加载完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(MainActivity.this, "图片加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
