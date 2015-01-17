package com.jackoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.Html;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import com.jackoder.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * Created by Jackoder on 2015/1/15.
 */
public class RichTextView extends TextView{

    private final String TAG = RichTextView.class.getSimpleName();

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHtmlText(String text) {
        setText(Html.fromHtml(text, new URLImageParser(), new CustomTagHandler()));
    }

    class URLDrawable extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if(drawable != null) {
                drawable.draw(canvas);
            } else {
                //display the default image
                drawable = getResources().getDrawable(R.drawable.unknown_image);
                int width = drawable.getIntrinsicWidth();
                int height = drawable.getIntrinsicHeight();
                drawable.setBounds(0, 0, width, height);
                setBounds(0, 0, width, height);
                draw(canvas);
                RichTextView.this.invalidate();
            }
        }
    }

    class URLImageParser implements Html.ImageGetter {

        URLDrawable urlDrawable;

        public Drawable getDrawable(String source) {
            urlDrawable = new URLDrawable();

            ImageGetterAsyncTask asyncTask =
                    new ImageGetterAsyncTask(urlDrawable);

            asyncTask.execute(source);

            return urlDrawable;
        }

        public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
            URLDrawable urlDrawable;

            public ImageGetterAsyncTask(URLDrawable d) {
                this.urlDrawable = d;
            }

            @Override
            protected Drawable doInBackground(String... params) {
                String source = params[0];
                return fetchDrawable(source);
            }

            @Override
            protected void onPostExecute(Drawable result) {
                if (null != result) {
                    //the image's size may larger than the container, set the max width equal to the parent's width
                    float multiplier = 1;
                    if (getMeasuredWidth() < result.getIntrinsicWidth()) {
                        multiplier = (float) getMeasuredWidth() / (float) result.getIntrinsicWidth();
                    }
                    int width = (int) (result.getIntrinsicWidth() * multiplier);
                    int height = (int) (result.getIntrinsicHeight() * multiplier);
                    result.setBounds(0, 0, width, height);
                    urlDrawable.setBounds(0, 0, width, height);
                    urlDrawable.drawable = result;
                    RichTextView.this.invalidate();
                    RichTextView.this.setHeight(RichTextView.this.getHeight() + result.getIntrinsicHeight());
                    RichTextView.this.setEllipsize(null);
                }
            }

            public Drawable fetchDrawable(String urlString) {
                if (!ImageLoader.getInstance().isInited()) {
                    ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(getContext());
                    ImageLoader.getInstance().init(config);
                }
                Bitmap bitmap = ImageLoader.getInstance().loadImageSync(urlString);
                if (bitmap != null) {
                    return new BitmapDrawable(getResources(), bitmap);
                } else {
                    Log.d(TAG, "load image error ---- " + urlString);
                    return null;
                }
            }
        }
    }

    public class CustomTagHandler implements Html.TagHandler {

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if(tag.equalsIgnoreCase("img")) {
                processImg(opening, output, xmlReader);
            }
        }

        public void processImg(boolean opening, Editable output, XMLReader xmlReader) {
            Log.d(TAG, output.toString());
            Log.d(TAG, output.length() + "");
            Object[] objects = output.getSpans(0, output.length(), ImageSpan.class);
            if (objects != null && objects.length != 0) {
                try {
                    Log.d(TAG, "imagespan " + xmlReader.getProperty("src"));
                } catch (SAXNotRecognizedException e) {
                    e.printStackTrace();
                } catch (SAXNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
