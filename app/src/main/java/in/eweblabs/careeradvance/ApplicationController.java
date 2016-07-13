package in.eweblabs.careeradvance;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;

import in.eweblabs.careeradvance.DB.CareerAdvanceDBData;
import in.eweblabs.careeradvance.DB.DatabaseManager;
import in.eweblabs.careeradvance.Data.DataModel;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.Utils.Logger;


/**
 * Created by Akash.Singh on 15/7/2015.
 * This is global singleton class.its maintain global application state
 */
public class ApplicationController extends Application{

    static ApplicationController appController;
    GoogleCloudMessaging gcm;
    CareerAdvanceDBData careerAdvanceDBData;
    UserInfo userInfo;
    DataModel dataModel;
    public static ApplicationController getInstance()
    {
        return  appController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
        dataModel =  new DataModel(this);
        DatabaseManager databaseManager = new DatabaseManager(this);
        try {
            databaseManager.createDataBase();
        }
        catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }
        careerAdvanceDBData =  new CareerAdvanceDBData(this);
        registerGCMService();
        LocalDataLoad();
    }

    private void LocalDataLoad(){
        dataModel.PopulateCurrencyList();
        dataModel.PopulateCountryList();
        dataModel.PopulateCityList();
    }

    public void registerGCMService()
    {
        try{
            gcm = GoogleCloudMessaging.getInstance(this);
            String regId = getRegistrationId(this);


            if (TextUtils.isEmpty(regId))
            {
                registerInBackground();
            } else
            {
                storeRegistrationId(this, regId);
            }
            Logger.d("registerGCMService", (new StringBuilder("::")).append(regId).toString());
        }catch (Exception e){

        }

    }

    private void storeRegistrationId(Context context, String regId) {
    }

    private String getRegistrationId(Context context) {
        return PreHelper.getStoredString(context, StaticConstant.GCM_REG_KEY);
    }



    private void registerInBackground() {
        (new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    String regId = gcm.register(StaticConstant.GCM_APP_ID);


                    if(regId!=null)
                        storeRegistrationId(appController, regId);
                }
                catch (Exception e)
                {
                    String s = (new StringBuilder("Error :")).append(e.getMessage()).toString();
                    Logger.d("GCNException", "::" + s);
                    e.printStackTrace();
                    return s;
                }
                return "";
            }

            protected  void onPostExecute(Object obj)
            {
                onPostExecute((String)obj);
            }

            protected void onPostExecute(String s)
            {
            }

        }).execute();


    }

    public CareerAdvanceDBData getCareerAdvanceDBData() {
        return careerAdvanceDBData;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

}