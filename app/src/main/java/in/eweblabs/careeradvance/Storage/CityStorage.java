package in.eweblabs.careeradvance.Storage;

import android.content.Context;

import in.eweblabs.careeradvance.R;

/**
 * Created by Akash.Singh on 7/29/2015.
 */
public class CityStorage extends JSONStorage {
    public CityStorage(Context context) {
        super(context);
    }

    @Override
    public void setFileName() {
        FILE_NAME = "city_ca";
        mAssetsFileID = R.raw.city_ca;

    }
}