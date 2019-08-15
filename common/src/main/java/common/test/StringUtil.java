package common.test;

/**
 *
 */
public class StringUtil {
    public static String trim(String string){
        if(string==null||"".equals(string)){
            return  "";
        }
        return string.trim();
    }
}
