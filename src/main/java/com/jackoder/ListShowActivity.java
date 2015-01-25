package com.jackoder;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jackoder.view.RichTextView2;

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
            RichTextView2 richTextView = new RichTextView2(ListShowActivity.this);
            String html = "这是一个图片"
                    + "<img src=\"http://jmzsoftware.com/wp-content/uploads/2014/05/jmzsoftware1-e1400169340524.png\" >"
                    + "，看看显示怎么样"
                    + "<img src=\"http://jmzsoftware.com/wp-content/uploads/2014/05/jmzsoftware1-e1400169340524.png\" >"
                    + "<p><b>粗体</b></p>";
            richTextView.setRichText(html);
            return richTextView;
        }
    };
}
