package org.Theomachy.Handler.Module;

import org.Theomachy.Utility.Hangul;

public class HangulModule {
    public static String getJosa(String jongSung){
        char josa = '가';
        try {
            josa = Hangul.getJosa(jongSung.charAt(jongSung.toCharArray().length - 1), '이', '가');
        } catch (Exception ignored) {
        }
        return String.valueOf(josa);
    }
}
