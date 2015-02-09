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
                + "<img src=\"http://v8.huayu.nd/s/p/167/f69efa84a2f84086a2e3912d069c43cb.png\" id=\"7667f1b5-57fa-45d1-955d-ea642ba0b2c9\" title=\"公式\" imgtype=\"2\">"
                + "，看看显示怎么样"
                + "<img src=\"http://jmzsoftware.com/wp-content/uploads/2014/05/jmzsoftware1-e1400169340524.png\" >"
                + "<br>这里展现的是一个公式：<img src=\"http://v8.huayu.nd/s/p/167/cc457053c91547c0b3f8175fd480b583.png\" title=\"公式\">出来没？"
                + "<p><b>粗体</b></p>";
        String html2 = "<img src=\"http://v8.huayu.nd/s/p/167/36911cee14c8460fb7de426c6445846d.png\" id=\"a3177096-7ac4-40cd-aa6f-16da9a21edf6\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/fa4516d3d22640feadaa0a8a2859ffbb.png\" id=\"fae50614-0733-471e-a1b2-a6ebdb80a348\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/4a653b8fb3ca44368f37d0ad5049e17c.png\" id=\"52b6889f-4fbc-4e33-a791-272f136dc5bf\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/6e99b76ca2844ce08aed467d489f6dcb.png\" id=\"abedfc98-c3ef-49b6-af71-3c1762694762\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/bd5cbbb487da420e8a6a33165f7f9ac7.png\" id=\"b803c688-9138-444d-84d1-69ba879efac9\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/01f2f16725244827a4853e658068d90c.png\" id=\"2527196c-7e93-48f9-87ef-9ab20fbf70aa\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/dec390656aed4972ad3f62487012b088.png\" id=\"35fbc657-28ba-4363-b94b-febec155068a\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/0c7864de14ba423f85efcd74b3800da1.png\" id=\"cb2bd0bc-697f-42d1-9776-4f56abbc1826\" title=\"公式\" imgtype=\"2\" /><img src=\"http://v8.huayu.nd/s/p/167/49e5c06122c641128330838b871460b8.png\" id=\"244a6d86-36d0-4714-8d95-a64d38d04580\" title=\"公式\" imgtype=\"2\" />";

        richTextView.setRichText(html2);
        Log.d("TEST", richTextView.getText().toString());
        richEditText.setRichText(html2);
        Log.d("TEST", richEditText.getText().toString());
    }
}

