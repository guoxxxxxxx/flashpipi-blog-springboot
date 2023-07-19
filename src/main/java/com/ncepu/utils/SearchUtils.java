package com.ncepu.utils;

public class SearchUtils {
    /**
     * 给搜索关键字添加高亮显示
     * @param content 内容
     * @param keyword 关键字
     * @return 处理后的信息
     */
    public static String highLight(String content, String keyword){
        content = content.replaceAll("(?i)"+keyword, "<span style=\"color:#f47466\">" + keyword + "</span>");
        return content;
    }
}
