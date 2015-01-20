package com.jackoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
 * Created by Jackoder on 2015/1/20.
 */
public class RichEditText extends EditText {

    public RichEditText(Context context) {
        super(context);
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRichText(String text) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(getContext());
            ImageLoader.getInstance().init(config);
        }

        SpannableStringBuilder ss = new SpannableStringBuilder(text);
        if (HtmlParser.containsImgTag(text)) {
            List<String> cutTextList = HtmlParser.cutStringByImgTag(text);
            int index = 0;
            for(String temp : cutTextList) {
                if (HtmlParser.containsImgTag(temp)) {
                    final URLDrawable drawable = new URLDrawable(this);

                    String url = HtmlParser.getSrcFromImgTag(temp);
                    if (null != url && url.trim().length() != 0) {
                        ImageSize imageSize = new ImageSize(200, 200);
                        ImageNonViewAware imageAware = new ImageNonViewAware(imageSize, ViewScaleType.CROP);
                        ImageLoader.getInstance().displayImage(url, imageAware, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                if (loadedImage != null) {
                                    Drawable loadedImageDrawable = new BitmapDrawable(getResources(), loadedImage);
                                    float multiplier = 1;
                                    if (getMeasuredWidth() < loadedImageDrawable.getIntrinsicWidth()) {
                                        multiplier = (float) getMeasuredWidth() / (float) loadedImageDrawable.getIntrinsicWidth();
                                    }
                                    int width = (int) (loadedImageDrawable.getIntrinsicWidth() * multiplier) - getTotalPaddingLeft() - getTotalPaddingRight();
                                    int height = (int) (loadedImageDrawable.getIntrinsicHeight() * multiplier);
                                    loadedImageDrawable.setBounds(0, 0, width, height);
                                    drawable.setBounds(0, 0, width, height);
                                    drawable.setDrawable(loadedImageDrawable);
                                    invalidate();
                                    setHeight(getHeight() + loadedImageDrawable.getIntrinsicHeight());
                                    setEllipsize(null);
                                } else {
                                    Log.d("URLImageParser", "load image error ---- " + imageUri);
                                }
                            }
                        });
                    }
                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    ss.setSpan(span, index, index + temp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                index += temp.length();
            }
        }
        setText(ss);
    }

    public void setPlainText(String text) {
        if (HtmlParser.containsImgTag(text)) {
            setText(HtmlParser.convertToCustomString(text));
        } else {
            setText(text);
        }
    }
}
