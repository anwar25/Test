package in.eweblabs.careeradvance.Data;

import android.content.Context;

import java.util.HashMap;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.AsyncTask.AsyncTaskTools;
import in.eweblabs.careeradvance.AsyncTask.CityAsyncTask;
import in.eweblabs.careeradvance.AsyncTask.CountryAsyncTask;
import in.eweblabs.careeradvance.AsyncTask.CurrencyAsyncTask;
import in.eweblabs.careeradvance.Interface.IDataModel;

/**
 * Created by Akash.Singh on 6/4/2015.
 * This class manage application content to local data.
 */
public class DataModel implements IDataModel {

    HashMap<Object,Object> countrylist =  new HashMap<Object,Object>();
    HashMap<Object,Object> citylist =  new HashMap<Object,Object>();
    HashMap<Object,Object> currencylist =  new HashMap<Object,Object>();

    Context context;

    public DataModel(Context context){
        this.context = context;

    }


    @Override
    public void PopulateCountryList() {
        CountryAsyncTask compInfoAsyncTask =  new CountryAsyncTask(ApplicationController.getInstance().getDataModel());
        AsyncTaskTools.execute(compInfoAsyncTask);
    }

    @Override
    public HashMap<Object, Object> GetCountryList() {
        return countrylist;
    }

    @Override
    public void PopulateCityList() {
        CityAsyncTask compInfoAsyncTask =  new CityAsyncTask(ApplicationController.getInstance().getDataModel());
        AsyncTaskTools.execute(compInfoAsyncTask);
    }

    @Override
    public HashMap<Object, Object> GetCityList() {
        return citylist;
    }



    @Override
    public void PopulateCurrencyList() {
        CurrencyAsyncTask compInfoAsyncTask =  new CurrencyAsyncTask(ApplicationController.getInstance().getDataModel());
        AsyncTaskTools.execute(compInfoAsyncTask);
    }

    @Override
    public HashMap<Object, Object> GetCurrencyList() {
        return currencylist;
    }

    public void SetCurrencyList(HashMap<Object,Object> currencylist){
        this.currencylist = currencylist;
    }

    public void SetCityList(HashMap<Object,Object> citylist){
        this.citylist = citylist;
    }

    public void SetCountryList(HashMap<Object,Object> countrylist){
        this.countrylist = countrylist;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
