package com.jackoder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.jackoder.view.RichEditText;
import com.jackoder.view.RichTextView;

public class HelloAndroidActivity extends Activity {

    RichTextView richTextView;
    RichEditText richEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        richTextView = (RichTextView)findViewById(R.id.rtv_content);
        richEditText = (RichEditText)findViewById(R.id.ret_content);

        String html = "这是一个图片"
                + "<img src=\"http://jmzsoftware.com/wp-content/uploads/2014/05/jmzsoftware1-e1400169340524.png\" >"
                + "，看看显示怎么样"
                + "<img src=\"http://jmzsoftware.com/wp-content/uploads/2014/05/jmzsoftware1-e1400169340524.png\" >"
                + "<br>这里展现的是一个公式：<img src=\"http://v8.huayu.nd/s/p/167/cc457053c91547c0b3f8175fd480b583.png\" title=\"公式\">出来没？"
                + "<p><b>粗体</b></p>";

        richTextView.setRichText(html);
        Log.d("TEST", richTextView.getText().toString());
        richEditText.setRichText(html);
        Log.d("TEST", richEditText.getText().toString());
    }
}

