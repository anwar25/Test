package in.eweblabs.careeradvance.Storage;

import android.content.Context;

import in.eweblabs.careeradvance.R;

/**
 * Created by Akash.Singh on 7/29/2015.
 */
public class CountryStorage extends JSONStorage {
    public CountryStorage(Context context) {
        super(context);
    }

    @Override
    public void setFileName() {
        FILE_NAME = "country";
        mAssetsFileID = R.raw.country;

    }
}