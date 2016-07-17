// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package in.eweblabs.careeradvance.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import in.eweblabs.careeradvance.Utils.Logger;

public class BaseNetwork
{

    public static final String DEVICE_TYPE = "device_type";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String AUTH_ID = "auth_id";


    private static BaseNetwork obj = null;
    public static final String LOGIN_METHOD = "/login";
    public static final String FORGET_PASSWORD_METHOD = "/forgetpass";
    public static final String CHANGE_PASSWORD_METHOD = "/changepassword";
    public static final String UPDATEPROFILE1 = "updateprofile1";
    public static final String UPDATEPROFILE2 = "updateprofile2";
    public static final String UPDATEPROFILE3 = "updateprofile3";
    public static final String APPLYJOB = "applyJob";
    public static final String MY_APPLIED_JOBS = "myApliedJobs";

    public static final String JOBKEYWORDS = "jobKeywords";
    public static final String JOBLOCATIONS = "jobLocations";
    public static final String SEARCHJOBBYKEYWORD= "searchJobBykeyword";
    public static final String URL_HOST = "http://n2nsoftwares.com/api/";
    public static final String USER_EMAIL_PARAMETER="userEmail";
    public static final String USER_PASSWORD_PARAMETER="userPassword";
    public static final String OLD_PASSWORD="oldPassword";
    public static final String NEW_PASSWORD="newPassword";
    public static final String CONFIRM_PASSWORD="confirmPassword";
    public static final String USER_NAME_PARAMETER="userName";
    public static final String USER_F_NAME_PARAMETER="userFName";
    public static final String USER_M_NAME_PARAMETER="userMName";
    public static final String USER_L_NAME_PARAMETER="userLName";
    public static final String USER_ID_PARAMETER="userId";
    public static final String USER_OBJECTIVE_PARAMETER="userObjective";
    public static final String USER_COUNTRY_PARAMETER="userCountry";
    public static final String USER_STATE_PARAMETER="userState";
    public static final String USER_ADDRESS_PARAMETER="userAddress";
    public static final String USER_CITY_PARAMETER="userCity";
    public static final String USER_POSTAL_CODE_PARAMETER="userPostalCode";
    public static final String USER_CONTACT_NUM_PARAMETER="userContactNum";
    public static final String USER_WORK_EXP_YEAR_PARAMETER="userWorkExpYear";
    public static final String USER_WORK_EXP_MONTH_PARAMETER="userWorkExpMonth";
    public static final String USER_CURRENCY_PARAMETER="userCurrency";
    public static final String USER_CTC_PARAMETER="userCTC";
    public static final String USER_KEY_SKILLS_PARAMETER="userKeySkills";
    public static final String USER_RESUME_HEADLINE_PARAMETER="userResumeHeadline";
    public static final String USER_INDUSTRY_PARAMETER="userIndustry";
    public static final String USER_FUNCTION_AREA_PARAMETER="userFunctionalArea";
    public static final String USER_BASIC_EDUCATION_PARAMETER="userBasicEducation";
    public static final String USER_BASIC_EDUCATION_OTHER_PARAMETER="userBasicEducationOther";
    public static final String USER_MASTER_EDUCATION_PARAMETER="userMasterEducation";
    public static final String USER_MASTER_EDUCATION_PARAMETER_OTHER="userMasterEducationOther";
    public static final String USER_DOCTRATE_EDUCATION_PARAMETER="userDoctrateEducation";
    public static final String USER_DOCTRATE_EDUCATION_PARAMETER_OTHER="userDoctrateEducationOther";
    public static final String USER_DIPLOMA_PARAMETER="userDiploma";
    public static final String USER_RESUME_PATH_PARAMETER="userResumePath";
    public static final String USER_RESUME_TEXT_PARAMETER="userResumeText";
    public static final String USER_JOB_ALERT_PARAMETER="userJobAlert";
    public static final String USER_FAST_FORWARD_EMAILS_PARAMETER="userFastForwordEmails";
    public static final String USER_FAST_FORWARD_CALLS_PARAMETER="userFastForwordCalls";
    public static final String USER_COMMUNICATION_PARAMETER="userCommunication";
    public static final String USER_NOTIFICATION_PARAMETER="userNotification";
    public static final String USER_SPECIAL_OFFER_PARAMETER="userSpecialOffer";
    public static final String KEYWORD="keyword";
    public static final String LOCATION="location";
    public static final String JOBID= "jobId";
    public static final String USERID= "userId";
    public static final String EMPID= "EmpId";
    public static final String DATETIME= "dateTime";
    public static final String ADD_USERS = "/addusers";



    public int TimeOut = 100000;
    public BaseNetwork()
    {
        TimeOut = 100000;
    }

    public static BaseNetwork obj()
    {
        BaseNetwork basenetwork;
        if (obj == null)
        {
            obj = new BaseNetwork();
        }
        basenetwork = obj;
        return basenetwork;
    }

    HttpURLConnection urlConnection = null;
   public  String PostMethodWay(Context context,String URL_HOST, String KeyWord, HashMap<Object,Object> hashMap, int timeout) {
        String response = "";

        try {
            if(urlConnection!=null)
                urlConnection.disconnect();
            String URLPaTH = URL_HOST+KeyWord;
            Logger.d("URLPaTH","::"+URLPaTH);
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(URLPaTH);
            JSONObject holder = new JSONObject();
            for(Map.Entry entry: hashMap.entrySet()){
                holder.put((String) entry.getKey(), entry.getValue());
            }
            Logger.d("JSONObject",holder.toString());
            StringEntity se = new StringEntity(holder.toString());
            httpost.setEntity(se);
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");
            HttpResponse result = httpclient.execute(httpost);
            response = EntityUtils.toString(result.getEntity());
            Logger.d("STATUS", "::" +response);
            if(TextUtils.isEmpty(response))
            {
                response = "404";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response = "404";
         } catch (SocketTimeoutException e) {
            e.printStackTrace();
            response = "500";
        }catch (EOFException e){
            e.printStackTrace();
            PostMethodWay(context,URL_HOST,KeyWord,hashMap,timeout);
        }catch (IOException e) {
            e.printStackTrace();
            response = "404";
        } catch (JSONException e) {
            e.printStackTrace();
            response = "404";
        } finally {
            if (urlConnection != null) {
                try{
                    urlConnection.disconnect();
                }
                catch (Exception e){
                    e.printStackTrace();
                    response = "500";
                }

            }
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    public static String createQueryStringForParameters(Map<Object,Object> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (Object parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }

                parametersAsQueryString.append((String)parameterName)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode(
                                (String) parameters.get(parameterName)));

                firstParameter = false;
            }
        }
        return parametersAsQueryString.toString();
    }



    public boolean checkConnOnline(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public String LoadXmlFromAssets(Context context,int mAssetsFileID){
        StringBuilder stringBuilder =  new StringBuilder();
        BufferedReader bufferedReader;
        InputStream inputStream = context.getResources().openRawResource(mAssetsFileID);
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        String line;
        try {
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
}
