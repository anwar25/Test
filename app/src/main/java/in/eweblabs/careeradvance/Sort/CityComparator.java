package in.eweblabs.careeradvance.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import in.eweblabs.careeradvance.Entity.City;
import in.eweblabs.careeradvance.Entity.Country;


/**
 * Created by Akash.Singh on 9/2/2015.
 */
public class CityComparator {

    public static final int CODE_ATOZ = 0;
    public static final int CODE_ZTOA = 1;
    public static final int NAME_ATOZ = 2;
    public static final int NAME_ZTOA = 3;

    public void sort(int sortKind, ArrayList<Object> cityList) {
        if (sortKind == CODE_ATOZ)
            Collections.sort(cityList, CityCodeAtoZ);
        else if (sortKind == CODE_ZTOA)
            Collections.sort(cityList, CityCodeZtoA);
        else if (sortKind == NAME_ATOZ)
            Collections.sort(cityList, CityNameAtoZ);
        else if (sortKind == NAME_ZTOA)
            Collections.sort(cityList, CityNameZtoA);


    }


    public static Comparator<Object> CityCodeAtoZ = new Comparator<Object>() {
        public int compare(Object city1, Object city2) {
            String nationalityCode1 = ((City) city1).getCityCode()
                    .toUpperCase();
            String nationalityCode2 = ((City) city2).getCityCode()
                    .toUpperCase();
            return nationalityCode1.compareTo(nationalityCode2);
        }
    };

    public static Comparator<Object> CityCodeZtoA = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String nationalityCode1 = ((City) nationality1).getCityCode()
                    .toUpperCase();
            String nationalityCode2 = ((City) nationality2).getCityCode()
                    .toUpperCase();
            return nationalityCode2.compareTo(nationalityCode1);
        }
    };


    public static Comparator<Object> CityNameAtoZ = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String cityCode1 = ((City) nationality1).getCityName()
                    .toUpperCase();
            String cityCode2 = ((City) nationality2).getCityName()
                    .toUpperCase();
            return cityCode1.compareTo(cityCode2);
        }
    };

    public static Comparator<Object> CityNameZtoA = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String cityCode1 = ((City) nationality1).getCityName()
                    .toUpperCase();
            String cityCode2 = ((City) nationality2).getCityName()
                    .toUpperCase();
            return cityCode2.compareTo(cityCode1);
        }
    };


}
