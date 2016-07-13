package in.eweblabs.careeradvance.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import in.eweblabs.careeradvance.Data.DataModel;
import in.eweblabs.careeradvance.Data.HJSONParsing;
import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.Storage.CountryStorage;

/**
 * Created by Akash.Singh on 6/4/2015.
 * This process run on background for save counties server xml string to local xml string file
  */
public class CountryAsyncTask extends AsyncTask{

    CountryStorage countryStorage;
    Context context;
    DataModel dataModel;
    public CountryAsyncTask(DataModel dataModel){
        this.context = dataModel.getContext();
        this.dataModel = dataModel;
        countryStorage =  new CountryStorage(context);
        countryStorage.setFileName();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        LoadCountryListFormServer();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }

    void LoadCountryListFormFile(){
        String XmlResponse = countryStorage.LoadXmlFromFile();
        if(!TextUtils.isEmpty(XmlResponse)){
            HJSONParsing xmlParsing =  new HJSONParsing();
            dataModel.SetCountryList(xmlParsing.parseCountryData(XmlResponse));
        }
        else
            return;
    }

    void LoadCountryListFormServer(){
        if(PreHelper.getStoredBoolean(context, StaticConstant.FIRSTTIMECOMPILECOUNTRYLIST))
        {
            String XmlResponse = countryStorage.LoadXmlFromAssets();
            if(!TextUtils.isEmpty(XmlResponse)){
                HJSONParsing xmlParsing =  new HJSONParsing();
                if(countryStorage.SaveXmlToFile(XmlResponse)) {
                    PreHelper.storeBoolean(context, StaticConstant.FIRSTTIMECOMPILECOUNTRYLIST, false);
                }
                dataModel.SetCountryList(xmlParsing.parseCountryData(XmlResponse));
            }
        }
        else
            LoadCountryListFormFile();


    }

}
