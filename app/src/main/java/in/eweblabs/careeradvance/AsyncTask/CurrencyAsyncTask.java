package in.eweblabs.careeradvance.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import in.eweblabs.careeradvance.Data.DataModel;
import in.eweblabs.careeradvance.Data.HJSONParsing;
import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.Storage.CurrencyStorage;

/**
 * Created by Akash.Singh on 12/4/2015.
 * This process run on background for save counties server xml string to local xml string file
 */
public class CurrencyAsyncTask extends AsyncTask{

    CurrencyStorage currencyStorage;
    Context context;
    DataModel dataModel;
    public CurrencyAsyncTask(DataModel dataModel){
        this.context = dataModel.getContext();
        this.dataModel = dataModel;
        currencyStorage =  new CurrencyStorage(context);
        currencyStorage.setFileName();
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
        String XmlResponse = currencyStorage.LoadXmlFromFile();
        if(!TextUtils.isEmpty(XmlResponse)){
            HJSONParsing xmlParsing =  new HJSONParsing();
            dataModel.SetCurrencyList(xmlParsing.parseCurrencyData(XmlResponse));
        }
        else
            return;
    }

    void LoadCountryListFormServer(){
        if(PreHelper.getStoredBoolean(context, StaticConstant.FIRSTTIMECOMPILECURRENYLIST))
        {
            String XmlResponse = currencyStorage.LoadXmlFromAssets();
            if(!TextUtils.isEmpty(XmlResponse)){
                HJSONParsing xmlParsing =  new HJSONParsing();
                if(currencyStorage.SaveXmlToFile(XmlResponse)) {
                    PreHelper.storeBoolean(context, StaticConstant.FIRSTTIMECOMPILECURRENYLIST, false);
                }
                dataModel.SetCurrencyList(xmlParsing.parseCurrencyData(XmlResponse));
            }
        }
        else
            LoadCountryListFormFile();
    }

}
