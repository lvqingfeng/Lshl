package com.lshl.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 *
 * Created by Administrator on 2016/11/17.
 */

public class PinYinUtils {
    /**
     * 纯粹通过映射的方式进行转换查找,会有一个效率问题,所以不应该多次被调用
     *
     * @param hanzi
     * @return
     */

    public static String GetPinyin(String hanzi) {

        String result = "";
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();//输出方式的格式化参数
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//设置返回的字母为大写
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置没有音标
        char[] chars = hanzi.toCharArray();//把字符串变成字符集合
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (Character.isWhitespace(aChar)) continue;//如果是空格 就直接跳出本次循环

            //如果不是空格 就应该判断是不是汉字
            if (aChar > 127) {//理论上可以判断是一个汉字
                try {
                    String[] strings = PinyinHelper.toHanyuPinyinStringArray(aChar, hanyuPinyinOutputFormat);//是数组的原因是因为多音字
                    if (strings == null) {//全角字符的话就是 null
                        result += aChar;
                    } else {
                        result += strings[0];//为什么直接写0,是多音字的话,因为我们也不知道到底要取谁,干脆直接取0
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                    result += aChar;//若果出现异常 就将转换的数据直接追加到结果
                }
            } else {
                result += aChar;//如果不是汉字 就直接追加结果
            }
        }

        return result;
    }
}
