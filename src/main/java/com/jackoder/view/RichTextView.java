package com.jackoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.jackoder.util.HtmlParser;
import com.jackoder.view.base.ImageNonViewAware;
import com.jackoder.view.base.URLDrawable;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by Jackoder on 2015/1/15.
 */
public class RichTextView extends TextView{

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRichText(String text) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(getContext());
            ImageLoader.getInstance().init(config);
        }

        SpannableString ss = new SpannableString(text);
        if (HtmlParser.containsImgTag(text)) {
            List<String> cutTextList = HtmlParser.cutStringByImgTag(text);
            int index = 0;
            for(String temp : cutTextList) {
                if (HtmlParser.containsImgTag(temp)) {
                    final URLDrawable drawable = new URLDrawable(this);

                    String url = HtmlParser.getSrcFromImgTag(temp);
                    if (null != url && url.trim().length() != 0) {
                        ImageSize imageSize = new ImageSize(100, 100);
                        ImageNonViewAware imageAware = new ImageNonViewAware(imageSize, ViewScaleType.CROP);
                        ImageLoader.getInstance().displayImage(url, imageAware, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                if (loadedImage != null) {
                                    Drawable loadedImageDrawable = new BitmapDrawable(getResources(), loadedImage);
                                    float multiplier = 1;
                                    float width = 1.5f * loadedImageDrawable.getIntrinsicWidth();
                                    float height = 1.5f * loadedImageDrawable.getIntrinsicHeight();
                                    float containerWidth = getMeasuredWidth() - getTotalPaddingLeft() - getTotalPaddingRight();
                                    if (containerWidth < width) {
                                        multiplier = containerWidth / width;
                                    }
                                    width = width * multiplier;
                                    height = height * multiplier;
                                    loadedImageDrawable.setBounds(0, 0, (int) width, (int) height);
                                    drawable.setBounds(0, 0, (int) width, (int) height);
                                    drawable.setDrawable(loadedImageDrawable);
                                    setEllipsize(null);
                                    setHeight(getHeight() + (int) height);
                                    Log.d("RichTextView", "get image " + imageUri);
                                } else {
                                    Log.d("URLImageParser", "load image error ---- " + imageUri);
                                }
                            }
                        });
                    }
                    //必须用bottom,否则第一行图片会显示不全
                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                    ss.setSpan(span, index, index + temp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                index += temp.length();
            }
        }
        setText(ss);
    }

    @Override
    public boolean onPreDraw() {
        return super.onPreDraw();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setPlainText(String text) {
        if (HtmlParser.containsImgTag(text)) {
            setText(HtmlParser.convertToCustomString(text));
        } else {
            setText(text);
        }
    }

}
