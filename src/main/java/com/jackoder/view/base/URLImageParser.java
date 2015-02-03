package com.jackoder.view.base;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Jackoder on 2015/1/20.
 */
public class URLImageParser implements Html.ImageGetter {

    TextView        view;
    URLDrawable urlDrawable;

    public URLImageParser(TextView view) {
        this.view = view;
    }

    public Drawable getDrawable(String source) {
        urlDrawable = new URLDrawable(view);

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
                if (view.getMeasuredWidth() < result.getIntrinsicWidth()) {
                    multiplier = (float) view.getMeasuredWidth() / (float) result.getIntrinsicWidth();
                }
                int width = (int) (result.getIntrinsicWidth() * multiplier);
                int height = (int) (result.getIntrinsicHeight() * multiplier);
                result.setBounds(0, 0, width, height);
                urlDrawable.setBounds(0, 0, width, height);
                urlDrawable.drawable = result;
                view.invalidate();
                view.setHeight(view.getHeight() + result.getIntrinsicHeight());
                view.setEllipsize(null);
            }
        }

        public Drawable fetchDrawable(String urlString) {
            if (!ImageLoader.getInstance().isInited()) {
                ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(view.getContext());
                ImageLoader.getInstance().init(config);
            }
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(urlString);
            if (bitmap != null) {
                return new BitmapDrawable(view.getResources(), bitmap);
            } else {
                Log.d("URLImageParser", "load image error ---- " + urlString);
                return null;
            }
        }
    }
}
