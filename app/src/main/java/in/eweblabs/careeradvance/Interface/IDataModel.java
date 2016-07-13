package in.eweblabs.careeradvance.Interface;

import java.util.HashMap;


/**
 * Created by Akash.Singh on 6/4/2015.
 */
public interface IDataModel {
    void PopulateCountryList();
    HashMap<Object,Object> GetCountryList();
    void PopulateCityList();
    HashMap<Object,Object> GetCityList();
    void PopulateCurrencyList();
    HashMap<Object,Object> GetCurrencyList();
}
