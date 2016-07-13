package in.eweblabs.careeradvance.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import in.eweblabs.careeradvance.Entity.City;
import in.eweblabs.careeradvance.Entity.Currency;


/**
 * Created by Akash.Singh on 9/2/2015.
 */
public class CurrencyComparator {

    public static final int CODE_ATOZ = 0;
    public static final int CODE_ZTOA = 1;
    public static final int NAME_ATOZ = 2;
    public static final int NAME_ZTOA = 3;

    public void sort(int sortKind, ArrayList<Object> cityList) {
        if (sortKind == CODE_ATOZ)
            Collections.sort(cityList, CurrencyCodeAtoZ);
        else if (sortKind == CODE_ZTOA)
            Collections.sort(cityList, CurrencyCodeZtoA);
        else if (sortKind == NAME_ATOZ)
            Collections.sort(cityList, CurrencyNameAtoZ);
        else if (sortKind == NAME_ZTOA)
            Collections.sort(cityList, CurrencyNameZtoA);


    }


    public static Comparator<Object> CurrencyCodeAtoZ = new Comparator<Object>() {
        public int compare(Object currency1, Object currency2) {
            String nationalityCode1 = ((Currency) currency1).getCurrencyCode()
                    .toUpperCase();
            String nationalityCode2 = ((Currency) currency2).getCurrencyCode()
                    .toUpperCase();
            return nationalityCode1.compareTo(nationalityCode2);
        }
    };

    public static Comparator<Object> CurrencyCodeZtoA = new Comparator<Object>() {
        public int compare(Object currency1, Object currency2) {
            String nationalityCode1 = ((Currency) currency1).getCurrencyCode()
                    .toUpperCase();
            String nationalityCode2 = ((Currency) currency2).getCurrencyCode()
                    .toUpperCase();
            return nationalityCode2.compareTo(nationalityCode1);
        }
    };


    public static Comparator<Object> CurrencyNameAtoZ = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String cityCode1 = ((Currency) nationality1).getCurrencyName()
                    .toUpperCase();
            String cityCode2 = ((Currency) nationality2).getCurrencyName()
                    .toUpperCase();
            return cityCode1.compareTo(cityCode2);
        }
    };

    public static Comparator<Object> CurrencyNameZtoA = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String cityCode1 = ((Currency) nationality1).getCurrencyName()
                    .toUpperCase();
            String cityCode2 = ((Currency) nationality2).getCurrencyName()
                    .toUpperCase();
            return cityCode2.compareTo(cityCode1);
        }
    };


}
