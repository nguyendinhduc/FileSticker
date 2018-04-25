package com.t3h.filesticker.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducnd on 4/26/18.
 */

public class ConvertUtils {
    public static String convertListStringToStringQuery(List<String> strs) {
        List<String> newStrs = new ArrayList<>();
        for (String str : strs) {
            newStrs.add("'" + str + "'");
        }
        String content = newStrs.toString();
        content = content.substring(1);
        content = content.substring(0, content.length() - 1);
        return content;
    }
}
