/**
 *
 */
package in.eweblabs.careeradvance.loader;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.StaticData.StaticConstant;

/**
 * @author ppphuc
 *
 */
public class Utils {

	public static void copyStream(InputStream in, OutputStream out) {
        final int buffer_size = 1024;
        byte[] bytes = new byte[buffer_size];
        for (;;) {
            int count = 0;
            try {
                count = in.read(bytes, 0, buffer_size);
                if (count == -1) {
                    break;
                }
                out.write(bytes, 0, count);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getRatingValueString(String ratingName, double ratingValue) {
        String valueString = "";

        Double star_50 = new Double("5.0");
        Double star_45 = new Double("4.5");
        Double star_40 = new Double("4.0");
        Double star_35 = new Double("3.5");
        Double star_30 = new Double("3.0");
        Double star_25 = new Double("2.5");
        Double star_20 = new Double("2.0");
        Double star_15 = new Double("1.5");
        Double star_10 = new Double("1.0");
        Double star_05 = new Double("0.5");

        if (ratingName.equalsIgnoreCase("AAA")) {
            if (Double.compare(ratingValue, star_50) == 0) {
                valueString = "★★★★★";
            } else if (Double.compare(ratingValue, star_45) == 0) {
                valueString = "★★★★☆";
            } else if (Double.compare(ratingValue, star_40) == 0) {
                valueString = "★★★★";
            } else if (Double.compare(ratingValue, star_35) == 0) {
                valueString = "★★★☆";
            } else if (Double.compare(ratingValue, star_30) == 0) {
                valueString = "★★★";
            } else if (Double.compare(ratingValue, star_25) == 0) {
                valueString = "★★☆";
            } else if (Double.compare(ratingValue, star_20) == 0) {
                valueString = "★★";
            } else if (Double.compare(ratingValue, star_15) == 0) {
                valueString = "★☆";
            } else if (Double.compare(ratingValue, star_10) == 0) {
                valueString = "★";
            } else if (Double.compare(ratingValue, star_05) == 0) {
                valueString = "☆";
            }
        } else {
            if (Double.compare(ratingValue, star_50) == 0) {
                valueString = "�?�?�?�?�?";
            } else if (Double.compare(ratingValue, star_45) == 0) {
                valueString = "�?�?�?�?○";
            } else if (Double.compare(ratingValue, star_40) == 0) {
                valueString = "�?�?�?�?";
            } else if (Double.compare(ratingValue, star_35) == 0) {
                valueString = "�?�?�?○";
            } else if (Double.compare(ratingValue, star_30) == 0) {
                valueString = "�?�?�?";
            } else if (Double.compare(ratingValue, star_25) == 0) {
                valueString = "�?�?○";
            } else if (Double.compare(ratingValue, star_20) == 0) {
                valueString = "�?�?";
            } else if (Double.compare(ratingValue, star_15) == 0) {
                valueString = "�?○";
            } else if (Double.compare(ratingValue, star_10) == 0) {
                valueString = "�?";
            } else if (Double.compare(ratingValue, star_05) == 0) {
                valueString = "○";
            }
        }
        return valueString;
    }


}
