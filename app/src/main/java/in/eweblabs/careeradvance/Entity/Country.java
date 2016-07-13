package in.eweblabs.careeradvance.Entity;

import java.io.Serializable;

/**
 * Created by Akash.Singh on 6/4/2015.
 * This class object contain country information
 */
public class Country implements Serializable{
    String countryCode;
    String countryName;

    public Country(){

    }
    public Country(String countryCode, String countryName) {
        setCountryCode(countryCode);
        setCountryName(countryName);

    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
