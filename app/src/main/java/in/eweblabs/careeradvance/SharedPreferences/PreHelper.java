package in.eweblabs.careeradvance.SharedPreferences;


import android.content.Context;
import android.content.SharedPreferences;

public class PreHelper
{


        public static final String GCM_REG_KEY = "gmcRegKey";
        public static String PREF_FILE_NAME = "CareerAdvance";

        public PreHelper()
        {
        }

    public static boolean getStoredBoolean(Context context,String s1)
    {
        return context.getSharedPreferences(PREF_FILE_NAME, 0).getBoolean(s1, true);
    }

    public static float getStoredFloat(Context context,String s1)
    {
        return context.getSharedPreferences(PREF_FILE_NAME, 0).getFloat(s1, 0.0F);
    }

    public static int getStoredInt(Context context,String s1)
    {
        return context.getSharedPreferences(PREF_FILE_NAME, 0).getInt(s1, 0);
    }

    public static String getStoredString(Context context, String s1)
    {
        return context.getSharedPreferences(PREF_FILE_NAME, 0).getString(s1, "");
    }

    public static void storeBoolean(Context context,String s1, boolean flag)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE_NAME, 0).edit();
        editor.putBoolean(s1, flag);
        editor.commit();
    }

    public static void storeDouble(Context context,String s1, double d)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE_NAME, 0).edit();
        editor.putFloat(s1, (float)d);
        editor.commit();
    }

    public static void storeInt(Context context,String s1, int i)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE_NAME, 0).edit();
        editor.putInt(s1, i);
        editor.commit();
    }

    public static void storeString(Context context,String s1, String s2)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE_NAME, 0).edit();
        editor.putString(s1, s2);
        editor.commit();
    }

}
