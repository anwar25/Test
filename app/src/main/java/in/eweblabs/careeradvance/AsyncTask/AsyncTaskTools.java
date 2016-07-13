package in.eweblabs.careeradvance.AsyncTask;

import android.os.AsyncTask;

/**
 * Created by Akash.Singh on 6/4/2015.
 */
public class AsyncTaskTools {

    public AsyncTaskTools() {}

    public static void execute(AsyncTask asynctask) {
        execute(asynctask,null);
    }

    public static void execute(AsyncTask asynctask, Object aobj[]) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            asynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, aobj);
            return;
        } else {
            asynctask.execute(aobj);
            return;
        }
    }

   /* public static void execute(AsyncTask asynctask,HashMap<Object,Object> aobj) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            asynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, aobj);
            return;
        } else {
            asynctask.execute(aobj);
            return;
        }
    }*/
}


