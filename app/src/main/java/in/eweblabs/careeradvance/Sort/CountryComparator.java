package in.eweblabs.careeradvance.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import in.eweblabs.careeradvance.Entity.Country;


/**
 * Created by Akash.Singh on 9/2/2015.
 */
public class CountryComparator {

    public static final int CODE_ATOZ = 0;
    public static final int CODE_ZTOA = 1;
    public static final int NAME_ATOZ = 2;
    public static final int NAME_ZTOA = 3;

    public void sort(int sortKind, ArrayList<Object> nationalityList) {
        if (sortKind == CODE_ATOZ)
            Collections.sort(nationalityList, CountryCodeAtoZ);
        else if (sortKind == CODE_ZTOA)
            Collections.sort(nationalityList, CountryCodeZtoA);
        else if (sortKind == NAME_ATOZ)
            Collections.sort(nationalityList, CountryNameAtoZ);
        else if (sortKind == NAME_ZTOA)
            Collections.sort(nationalityList, CountryNameZtoA);


    }


    public static Comparator<Object> CountryCodeAtoZ = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String nationalityCode1 = ((Country) nationality1).getCountryCode()
                    .toUpperCase();
            String nationalityCode2 = ((Country) nationality2).getCountryCode()
                    .toUpperCase();
            return nationalityCode1.compareTo(nationalityCode2);
        }
    };

    public static Comparator<Object> CountryCodeZtoA = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String nationalityCode1 = ((Country) nationality1).getCountryCode()
                    .toUpperCase();
            String nationalityCode2 = ((Country) nationality2).getCountryCode()
                    .toUpperCase();
            return nationalityCode2.compareTo(nationalityCode1);
        }
    };


    public static Comparator<Object> CountryNameAtoZ = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String currencyCode1 = ((Country) nationality1).getCountryCode()
                    .toUpperCase();
            String currencyCode2 = ((Country) nationality2).getCountryCode()
                    .toUpperCase();
            return currencyCode1.compareTo(currencyCode2);
        }
    };

    public static Comparator<Object> CountryNameZtoA = new Comparator<Object>() {
        public int compare(Object nationality1, Object nationality2) {
            String nationalityCode1 = ((Country) nationality1).getCountryCode()
                    .toUpperCase();
            String nationalityCode2 = ((Country) nationality2).getCountryCode()
                    .toUpperCase();
            return nationalityCode2.compareTo(nationalityCode1);
        }
    };


}
