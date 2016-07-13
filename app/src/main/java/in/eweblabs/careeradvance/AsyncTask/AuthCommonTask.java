// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package in.eweblabs.careeradvance.AsyncTask;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ProgressBar;
import java.util.HashMap;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.Data.HJSONParsing;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.Utils.Logger;

public class AuthCommonTask extends AsyncTaskRunner
{


    public AuthCommonTask(Context context, IAsyncTaskRunner iasynctaskrunner, String s)
    {
        super(context, s, iasynctaskrunner);
    }

    public AuthCommonTask(Context context,String s,  IAsyncTaskRunner iasynctaskrunner,LoadingDialog progressdialog)
    {
        super(context,s,progressdialog,iasynctaskrunner);
    }

    public AuthCommonTask(Context context, IAsyncTaskRunner iasynctaskrunner, String s, ProgressBar progressbar)
    {
        super(context, s, progressbar, iasynctaskrunner);
    }



    protected Object doInBackground(Object obj[])
    {
        ResultMessage resultmessage = new ResultMessage();
        resultmessage.STATUS = 0;
        if (BaseNetwork.obj().checkConnOnline(context)) {
            publishProgress(new Integer[]{
                    Integer.valueOf(1)
            });
            resultmessage.TYPE = urlString;
            HJSONParsing jsonParser = new HJSONParsing();
            resultmessage.RESPONSE = "";

            resultmessage.RESPONSE = BaseNetwork.obj().PostMethodWay(context,BaseNetwork.URL_HOST,urlString,(HashMap < Object, Object >) obj[0], BaseNetwork.obj().TimeOut);

            Logger.d("AuthLoginTask", (new StringBuilder("::")).append(resultmessage.RESPONSE).toString());
            if (!isTerminationRequest() && !TextUtils.isEmpty(resultmessage.RESPONSE)) {
                String s = urlString;
                BaseNetwork.obj().getClass();
                if (!resultmessage.RESPONSE.equalsIgnoreCase("404")) {
                    if (!resultmessage.RESPONSE.equalsIgnoreCase("500")) {
                        if (!isTerminationRequest()) {
                            if(s.equalsIgnoreCase(BaseNetwork.LOGIN_METHOD)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseLoginDetail(context, resultmessage.RESPONSE);
                            }
                            else if(s.equalsIgnoreCase(BaseNetwork.JOBKEYWORDS)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseJobKeyword(context, resultmessage.RESPONSE);
                            }else if(s.equalsIgnoreCase(BaseNetwork.SEARCHJOBBYKEYWORD)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseJobSearch(context, resultmessage.RESPONSE);
                            }
                            else if(s.equalsIgnoreCase(BaseNetwork.FORGET_PASSWORD_METHOD)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseForgetPassword(context, resultmessage.RESPONSE);
                            }else if(s.equalsIgnoreCase(BaseNetwork.CHANGE_PASSWORD_METHOD)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseForgetPassword(context, resultmessage.RESPONSE);
                            }
                            else if(s.equalsIgnoreCase(BaseNetwork.JOBLOCATIONS)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseJobLocation(context, resultmessage.RESPONSE);
                            }
                            else if(s.equalsIgnoreCase(BaseNetwork.APPLYJOB)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseApplyJob(context, resultmessage.RESPONSE);
                            }
                            else  if(s.equalsIgnoreCase(BaseNetwork.ADD_USERS)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseLoginDetail(context, resultmessage.RESPONSE);
                            }
                            else if(s.equalsIgnoreCase(BaseNetwork.UPDATEPROFILE1)||s.equalsIgnoreCase(BaseNetwork.UPDATEPROFILE2)||s.equalsIgnoreCase(BaseNetwork.UPDATEPROFILE3)){
                                resultmessage.RESULT_OBJECT = jsonParser.ParseUpdateDetail(context,resultmessage.RESPONSE);
                            }


                            resultmessage.STATUS = 200;
                        }

                    } else
                        resultmessage.STATUS = 500;
                } else
                    resultmessage.STATUS = 400;

            }
            }
            else
                resultmessage.STATUS = 500;

        return resultmessage;
    }

    protected void onPostExecute(Object obj)
    {
        super.onPostExecute(obj);
    }

    protected void onProgressUpdate(Integer ainteger[])
    {
        super.onProgressUpdate(ainteger);
    }

    protected void onProgressUpdate(Object aobj[])
    {
        onProgressUpdate((Integer[])aobj);
    }
}
