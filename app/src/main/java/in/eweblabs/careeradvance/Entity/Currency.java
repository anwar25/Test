package in.eweblabs.careeradvance.Entity;

/**
 * Created by akash.singh on 11/30/2015.
 */
public class Currency {

    String currencyCode;
    String currencyName;

    public Currency(String currencyCode, String currencyName){
        setCurrencyCode(currencyCode);
        setCurrencyName(currencyName);
    }


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
