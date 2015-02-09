package com.jackoder;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jackoder.view.RichTextView;

/**
 * Created by Jackoder on 2015/1/25.
 */
public class ListShowActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(adapter);
    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RichTextView richTextView = new RichTextView(ListShowActivity.this);
            String html = "这是一个图片"
                    + "<img src=\"http://v8.huayu.nd/s/p/167/f69efa84a2f84086a2e3912d069c43cb.png\" id=\"7667f1b5-57fa-45d1-955d-ea642ba0b2c9\" title=\"公式\" imgtype=\"2\">"
                    + "，看看显示怎么样"
                    + "<img src=\"http://jmzsoftware.com/wp-content/uploads/2014/05/jmzsoftware1-e1400169340524.png\" >"
                    + "<br>这里展现的是一个公式：<img src=\"http://v8.huayu.nd/s/p/167/cc457053c91547c0b3f8175fd480b583.png\" title=\"公式\">出来没？"
                    + "<p><b>粗体</b></p>";
            richTextView.setRichText(html);
            return richTextView;
        }
    };
}
