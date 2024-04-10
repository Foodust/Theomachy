package org.Theomachy.Handler.Module.source;

// Josa 를 붙이기 위함
public class HangulModule {
    public enum Josa{
        EI_GA, // 이 , 가
        EL_LL, // 을 , 를
        EN_NN; // 은 , 는
    }
    public String getJosa(String str,Josa josa){
        String one = "";
        String two = "";
        switch (josa){
            case EI_GA -> {
                one = "이";
                two="가";
            }
            case EL_LL -> {
                one = "을";
                two="를";
            }
            case EN_NN -> {
                one = "은";
                two="는";
            }
        }
            try {
            return getJosa(str, one, two);
        } catch (Exception ignored) {
        }
        return String.valueOf(str);
    }

    public String getJosa(String str, String firstVal, String secondVal) {
        try {
            char lastStr = str.charAt(str.length() - 1);
            // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
            if (lastStr < 0xAC00 || lastStr > 0xD7A3) {
                return str;
            }
            int lastCharIndex = (lastStr - 0xAC00) % 28;
            // 종성인덱스가 0이상일 경우는 받침이 있는경우이며 그렇지 않은경우는 받침이 없는 경우
            if (lastCharIndex > 0) {
                // 받침이 있는경우
                // 조사가 '로'인경우 'ㄹ'받침으로 끝나는 경우는 '로' 나머지 경우는 '으로'
                if (firstVal.equals("으로") && lastCharIndex == 8) {
                    str += secondVal;
                } else {
                    str += firstVal;
                }
            } else {
                // 받침이 없는 경우
                str += secondVal;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return str;
    }
}
