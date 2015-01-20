package com.jackoder.view.base;

import android.text.Editable;
import android.text.Html;
import org.xml.sax.XMLReader;

/**
 * Created by Jackoder on 2015/1/20.
 */
public class RichTextTagHandler implements Html.TagHandler {

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if(tag.equalsIgnoreCase("img")) {
            processImg(opening, output, xmlReader);
        }
    }

    public void processImg(boolean opening, Editable output, XMLReader xmlReader) {

    }
}