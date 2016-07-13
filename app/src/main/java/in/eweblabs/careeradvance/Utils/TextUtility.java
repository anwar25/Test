package in.eweblabs.careeradvance.Utils;

import android.text.TextUtils;

/**
 * Created by Tanuj.Sareen on 9/3/2015.
 */
public class TextUtility {

    public static String checkIsStringEmpty(String string) {

        if (TextUtils.isEmpty(string))
            return "";
        else if (string.equals("null"))
            return "";
        else return string;
    }

    public static String checkIsStringDoubleEmpty(String string) {

        if (TextUtils.isEmpty(string))
            return "0.00";
        else if (string.equals("null"))
            return "0.00";
        else return string;
    }

    public static String checkIsStringIntegerEmpty(String string) {

        if (TextUtils.isEmpty(string))
            return "0";
        else if (string.equals("null"))
            return "0";
        else return string;
    }

}
