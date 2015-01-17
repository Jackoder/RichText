package com.jackoder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于处理带图片标签的文本的工具类
 *
 * Created by Jackoder on 2015/1/17.
 */
public class HtmlParser {

    private final static String PATTERN_IMG_TAG = "<img(\\s+[\\w]+=\"[^\"']*\")*>";
    private final static String PATTERN_IMG_TAG_FORMULA = "<img(\\s+[\\w]+=\"(?!(pic))[^\"']*\")*>";
    private final static String PATTERN_IMG_TAG_PIC = "<img(\\s+[\\w]+=\"(?!(formula))[^\"']*\")*>";

    final static Pattern patternImgTag = Pattern.compile(PATTERN_IMG_TAG);
    final static Pattern patternImgTagFormula = Pattern.compile(PATTERN_IMG_TAG_FORMULA);
    final static Pattern patternImgTagPic = Pattern.compile(PATTERN_IMG_TAG_PIC);

    final static String  IMAGE_TAG_TEXT = "[图片]";
    final static String  FORMULA_TAG_TEXT = "[公式]";

    public static void main(String args[]) {
        String testStr = "haha<img title=\"\" src=\"www.baidu.com\" tag=\"公式\">so good<img src=\"www.google.com\">\n<img>";
        System.out.println(testStr);

        System.out.println("是否包含img标签=" + containsImgTag(testStr));

        //切割字符串
        System.out.println("the string is split to these string");
        for(String temp : cutStringByImgTag(testStr)) {
            System.out.println(temp);
        }

        //不显示图片或公式，以字符串代替的方案
        String testStr2 = "haha<img title=\"\" src=\"www.baidu.com\" tag=\"formula\">so good<img src=\"www.google.com\" tag=\"pic\">\n<img>";
        System.out.println("转换为简单文本：" + convertToString(testStr2));
        System.out.println("转换为特定文本：" + convertToCustomString(testStr2));
    }

    //测试group方法
    public static void testGroup(String targetStr) {
        List<String> splitTextList = new ArrayList<String>();
        Matcher matcher = patternImgTag.matcher(targetStr);
        int lastIndex = 0;
        while (matcher.find()) {
//            System.out.println(String.format("regionStart=%d regionEnd=%d", matcher.regionStart(), matcher.regionEnd()));
//            System.out.println(String.format("start=%d end=%d", matcher.start(), matcher.end()));
            int groupCount = matcher.groupCount();
            System.out.println("pattern.matcher(testStr).groupCount()=" + groupCount);
            for(int i = 0; i < groupCount ; i++) {
                System.out.println(String.format("group[%d]=%s", i, matcher.group(i)));
            }
        }
        if (lastIndex != targetStr.length()) {
            splitTextList.add(targetStr.substring(lastIndex, targetStr.length()));
        }
    }

    /**
     * @param targetStr 要处理的字符串
     * @description 是否包含img标签
     */
    public static boolean containsImgTag(String targetStr) {
        Matcher matcher = patternImgTag.matcher(targetStr);
        return matcher.find();
    }

    /**
     * @param targetStr 要处理的字符串
     * @description 切割字符串，将文本和img标签碎片化，如"ab<img>cd"转换为"ab"、"<img>"、"cd"
     */
    public static List<String> cutStringByImgTag(String targetStr) {
        List<String> splitTextList = new ArrayList<String>();
        Matcher matcher = patternImgTag.matcher(targetStr);
        int lastIndex = 0;
        while (matcher.find()) {
            if (matcher.start() > lastIndex) {
                splitTextList.add(targetStr.substring(lastIndex, matcher.start()));
            }
            splitTextList.add(targetStr.substring(matcher.start(), matcher.end()));
            lastIndex = matcher.end();
        }
        if (lastIndex != targetStr.length()) {
            splitTextList.add(targetStr.substring(lastIndex, targetStr.length()));
        }
        return splitTextList;
    }

    /**
     * @param targetStr 要处理的字符串
     * @description 转化字符串，将公式和图片显示为替代文本
     */
    public static String convertToString(String targetStr) {
        return patternImgTag.matcher(targetStr).replaceAll(IMAGE_TAG_TEXT);
    }

    /**
     * @param targetStr 要处理的字符串
     * @description 转化字符串，将公式和图片显示为替代文本
     */
    public static String convertToCustomString(String targetStr) {
        return patternImgTagFormula.matcher(patternImgTagPic.matcher(targetStr).replaceAll(IMAGE_TAG_TEXT)).replaceAll(FORMULA_TAG_TEXT);
    }
}
