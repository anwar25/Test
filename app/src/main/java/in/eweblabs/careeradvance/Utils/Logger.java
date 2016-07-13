package in.eweblabs.careeradvance.Utils;

import android.util.Log;

public class Logger
{

    public static boolean printLogs = true;

    public Logger()
    {
    }

    public static void d(String s, String s1)
    {
        if (printLogs)
        {
              Log.d(s, s1);
        }
    }


    public static void d(String s, String s1, Throwable throwable)
    {
        if (printLogs)
        {
           Log.d(s, s1,throwable);
        }
    }

    public static void e(String s, String s1)
    {
        if (printLogs)
        {
            Log.e(s, s1);
        }
    }

    public static void e(String s, String s1, Throwable throwable)
    {
        if (printLogs)
        {
             Log.w(s, s1,throwable);
        }
    }

    public static void i(String s, String s1)
    {
        if (printLogs)
        {
            Log.w(s, s1);
        }
    }

    public static void i(String s, String s1, Throwable throwable)
    {
        if (printLogs)
        {
            Log.i(s, s1, throwable);
        }
    }

    public static void v(String s, String s1)
    {
        if (printLogs)
        {
            Log.v(s, s1);
        }
    }

    public static void v(String s, String s1, Throwable throwable)
    {
        if (printLogs)
        {
            Log.v(s, s1, throwable);
        }
    }

    public static void w(String s, String s1)
    {
        if (printLogs)
        {
            Log.w(s, s1);
        }
    }

    public static void w(String s, String s1, Throwable throwable)
    {
        if (printLogs)
        {
            Log.w(s, s1, throwable);
        }
    }

    public static void w(String s, Throwable throwable)
    {
        if (printLogs)
        {
            Log.w(s, throwable);
        }
    }

    public static void wtf(String s, String s1)
    {
        if (printLogs)
        {
            Log.wtf(s, s1);
        }
    }

    public static void wtf(String s, String s1, Throwable throwable)
    {
        if (printLogs)
        {
            Log.wtf(s, s1, throwable);
        }
    }

}
