package com.jackoder.view.base;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.jackoder.R;

/**
 * Created by Jackoder on 2015/1/20.
 */
public class URLDrawable extends BitmapDrawable {

    protected View      view;
    protected Drawable  drawable;

    public URLDrawable(View view) {
        this.view = view;
    }

    @Override
    public void draw(Canvas canvas) {
        if(drawable != null) {
            drawable.draw(canvas);
        } else {
            //display the default image
            drawable = view.getResources().getDrawable(R.drawable.unknown_image);
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            drawable.setBounds(0, 0, width, height);
            setBounds(0, 0, width, height);
            draw(canvas);
            view.invalidate();
            ((TextView)view).setHeight(view.getHeight() + drawable.getIntrinsicHeight());
        }
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
