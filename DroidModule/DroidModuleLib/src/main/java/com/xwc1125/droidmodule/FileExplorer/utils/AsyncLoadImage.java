package com.xwc1125.droidmodule.FileExplorer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.xwc1125.droidmodule.FileExplorer.entity.ImgCache;
import com.xwc1125.droidmodule.R;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 图片异步加载
 *
 * @author xwc1125
 */
public class AsyncLoadImage {

    private static final String TAG = AsyncLoadImage.class.getSimpleName();

    private Context context;
    private Handler handler;

    private Object lock = new Object();
    private boolean isAllow = true;

    private ConcurrentLinkedQueue<ImgCache> imageCache;

    public AsyncLoadImage(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        imageCache = new ConcurrentLinkedQueue<ImgCache>();
    }

    public void loadImage(ImageView imageView) {
        String path = (String) imageView.getTag();
        imageView.setImageResource(R.drawable.file_explorer_format_picture);
        // 1.根据路径从内存中取已经加载过的图片，如果有，直接从内存中返回
        for (ImgCache img : imageCache) {
            if (path.equals(img.getPath())) {
                Bitmap bitmap = img.getBitmap();
                imageView.setImageBitmap(bitmap);
                return;
            }
        }
        // 2.如果没有，再去子线程中去取
        new LoadImageThread(imageView).start();
    }

    class LoadImageThread extends Thread {
        private ImageView imageView;
        private String path;

        public LoadImageThread(ImageView imageView) {
            this.imageView = imageView;
            this.path = (String) imageView.getTag();
        }

        @Override
        public void run() {
            // 加载图片
            if (!isAllow) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "", e);
                    }
                }
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            final Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            ImgCache img = new ImgCache();
            img.setPath(path);
            img.setBitmap(bitmap);

            // 把得到bitmap和路径的CacheImage对象存入到内存里边，如果大于要求的缓存数量在，就把缓存当中的第一个元素删掉。
            if (imageCache.size() >= 100) {
                imageCache.poll();
            }
            imageCache.add(img);

            // 2.完毕之后通过handler主线程更新
            handler.post(new Runnable() {

                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

        }
    }

    public void lock() {
        isAllow = false;
    }

    public void unLock() {
        isAllow = true;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

}
