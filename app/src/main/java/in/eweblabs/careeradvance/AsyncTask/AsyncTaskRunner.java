// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package in.eweblabs.careeradvance.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

import in.eweblabs.careeradvance.Data.HJSONParsing;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.Utils.Logger;

public abstract class AsyncTaskRunner extends AsyncTask {

    private IAsyncTaskRunner _asyncTaskRunner;
    protected Context context;
    private WeakReference contextWeakRef;
    protected ProgressBar pBar;
    protected LoadingDialog loadingDialog;
    protected ProgressDialog pDialog;
    protected String urlString;
    HJSONParsing jsonParsing;
    boolean TerminationRequest = false;


    public AsyncTaskRunner(Context context1,String s,LoadingDialog progressdialog, IAsyncTaskRunner iasynctaskrunner) {
        _asyncTaskRunner = iasynctaskrunner;
        contextWeakRef = new WeakReference(iasynctaskrunner.getContext());
        context = context1;
        urlString = s;
    }

    public AsyncTaskRunner(Context context1,String s,ProgressDialog progressdialog,  IAsyncTaskRunner iasynctaskrunner) {
        _asyncTaskRunner = iasynctaskrunner;
        context = context1;
        urlString = s;
        pDialog = progressdialog;
        contextWeakRef = new WeakReference(context1);
    }


    public AsyncTaskRunner(Context context1, String s, ProgressBar progressbar, IAsyncTaskRunner iasynctaskrunner) {
        _asyncTaskRunner = iasynctaskrunner;
        context = context1;
        urlString = s;
        pBar = progressbar;
        contextWeakRef = new WeakReference(context1);
    }

    public AsyncTaskRunner(Context context1, String s, IAsyncTaskRunner iasynctaskrunner) {
        _asyncTaskRunner = iasynctaskrunner;
        urlString = s;
        context = context1;
        contextWeakRef = new WeakReference(iasynctaskrunner.getContext());
    }

    protected Object doInBackground(Object aobj[]) {
        return new ResultMessage();
    }

    protected void onPostExecute(Object obj) {
        if (!isTerminationRequest() && contextWeakRef.get() != null) {
            Logger.d("onPostExecute", "onPostExecute");
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }else if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }else if (pBar != null) {
                pBar.setVisibility(View.GONE);
            }
            ResultMessage resultmessage = (ResultMessage) obj;
            if (resultmessage.STATUS != 0) {

                if (resultmessage.STATUS > 299 && resultmessage.STATUS < 420) {
                    resultmessage.ERRORMESSAGE = context.getResources().getString(R.string.error_dialog_error_unknown);
                    _asyncTaskRunner.taskErrorMessage(resultmessage);

                //    Utils.ShowErrorMessageDialog(context, resultmessage.ERRORMESSAGE);
                } else if (resultmessage.STATUS > 499 && resultmessage.STATUS < 510) {
                    resultmessage.ERRORMESSAGE = context.getResources().getString(R.string.error_message_network_to_connect);
                    _asyncTaskRunner.taskErrorMessage(resultmessage);
                //    Utils.ShowErrorMessageDialog(context, resultmessage.ERRORMESSAGE);
                } else if (resultmessage.STATUS > 199 && resultmessage.STATUS < 210)
                    _asyncTaskRunner.taskCompleted(obj);
                else if (resultmessage.STATUS == 4001) {
                    _asyncTaskRunner.taskErrorMessage(resultmessage);
                } else {
                    resultmessage.ERRORMESSAGE = context.getResources().getString(R.string.error_dialog_error_unknown);
                    _asyncTaskRunner.taskErrorMessage(resultmessage);
                 //   Utils.ShowErrorMessageDialog(context, resultmessage.ERRORMESSAGE);
                }
                return;
            } else {
                resultmessage.ERRORMESSAGE = context.getResources().getString(R.string.error_dialog_error);
                _asyncTaskRunner.taskErrorMessage(resultmessage);
               // Utils.ShowErrorMessageDialog(context, resultmessage.ERRORMESSAGE);
            }

        } else {
            Logger.d("onPostExecute", "onPostExecute_Problem");
        }
    }

    protected void onPreExecute() {
        _asyncTaskRunner.taskStarting();
    }

    protected void onProgressUpdate(Object aobj[]) {

        _asyncTaskRunner.taskProgress(aobj[0]);

        if (aobj[0].equals(Integer.valueOf(1))) {

            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.show();
            }
            else if (pBar != null) {
                pBar.setVisibility(View.VISIBLE);
            }
           else if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }

        }
    }

    public boolean isTerminationRequest() {
        return TerminationRequest;
    }

    public void setTerminationRequest(boolean terminationRequest) {
        TerminationRequest = terminationRequest;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        onCancelledRequest();
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
        onCancelledRequest();

    }

    void onCancelledRequest() {
        setTerminationRequest(true);
        _asyncTaskRunner.onCanceled();

    }
}
