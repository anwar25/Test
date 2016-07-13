package in.eweblabs.careeradvance.Entity;

/**
 * Created by akash.singh on 11/30/2015.
 */
public class City {

    String cityCode;
    String cityName;

    public City(String cityCode, String cityName){
        setCityCode(cityCode);
        setCityName(cityName);
    }


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
