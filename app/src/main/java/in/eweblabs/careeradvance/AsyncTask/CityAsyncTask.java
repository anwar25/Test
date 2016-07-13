package in.eweblabs.careeradvance.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import in.eweblabs.careeradvance.Data.DataModel;
import in.eweblabs.careeradvance.Data.HJSONParsing;
import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.Storage.CityStorage;
import in.eweblabs.careeradvance.Storage.CountryStorage;

/**
 * Created by Akash.Singh on 6/4/2015.
 * This process run on background for save counties server xml string to local xml string file
  */
public class CityAsyncTask extends AsyncTask{

    CityStorage cityStorage;
    Context context;
    DataModel dataModel;
    public CityAsyncTask(DataModel dataModel){
        this.context = dataModel.getContext();
        this.dataModel = dataModel;
        cityStorage =  new CityStorage(context);
        cityStorage.setFileName();
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
        String XmlResponse = cityStorage.LoadXmlFromFile();
        if(!TextUtils.isEmpty(XmlResponse)){
            HJSONParsing xmlParsing =  new HJSONParsing();
            dataModel.SetCityList(xmlParsing.parseCityData(XmlResponse));
        }
        else
            return;
    }

    void LoadCountryListFormServer(){
        if(PreHelper.getStoredBoolean(context, StaticConstant.FIRSTTIMECOMPILECITYLIST))
        {
            String XmlResponse = cityStorage.LoadXmlFromAssets();
            if(!TextUtils.isEmpty(XmlResponse)){
                HJSONParsing xmlParsing =  new HJSONParsing();
                if(cityStorage.SaveXmlToFile(XmlResponse)) {
                    PreHelper.storeBoolean(context, StaticConstant.FIRSTTIMECOMPILECITYLIST, false);
                }
                dataModel.SetCityList(xmlParsing.parseCityData(XmlResponse));
            }
        }
        else
            LoadCountryListFormFile();


    }

}
