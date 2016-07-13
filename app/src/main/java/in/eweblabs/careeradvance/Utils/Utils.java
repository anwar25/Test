package in.eweblabs.careeradvance.Utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anil on 11/21/2015.
 */
public class Utils {

    private static String currentDateTime;

    public static void hideSoftKeyboard(Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getCurrentDateTime() {
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }
}
