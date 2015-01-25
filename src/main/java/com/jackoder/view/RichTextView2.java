package com.jackoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.jackoder.view.base.ImageNonViewAware;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

/**
 * Created by Jackoder on 2015/1/25.
 */
public class RichTextView2 extends TextView {

    String originalHtmlText;

    public RichTextView2(Context context) {
        super(context);
    }

    public RichTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRichText(String html) {
        this.originalHtmlText = html;
        setText(Html.fromHtml(originalHtmlText, imageGetter, null));
    }

    final Html.ImageGetter imageGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            if (!ImageLoader.getInstance().isInited()) {
                ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(getContext());
                ImageLoader.getInstance().init(config);
            }
            Drawable drawable = null;
            File tempFile = ImageLoader.getInstance().getDiskCache().get(source);
            //判断SD卡里面是否存在图片文件
            if (tempFile != null) {
                //获取本地文件返回Drawable
                drawable=Drawable.createFromPath(tempFile.getAbsolutePath());
                //设置图片边界
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }else {
                //启动新线程下载
                ImageSize imageSize = new ImageSize(200, 200);
                ImageNonViewAware imageAware = new ImageNonViewAware(imageSize, ViewScaleType.CROP);
                ImageLoader.getInstance().displayImage(source, imageAware, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        setText(Html.fromHtml(originalHtmlText, imageGetter, null));
                    }
                });
                return drawable;
            }

        };

    };
}
