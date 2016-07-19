package in.eweblabs.careeradvance.fcm;

import android.content.Context;

import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.Network.NetworkUtils;
import in.eweblabs.careeradvance.Network.RetrofitInstance;
import in.eweblabs.careeradvance.Network.models.GenericResponse;
import in.eweblabs.careeradvance.Network.models.RegisterDeviceModel;
import in.eweblabs.careeradvance.SessionManager;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.Utils.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anwar on 7/18/2016.
 */
public class RegisterToken {

    private static final String TAG = RegisterToken.class.getSimpleName();

    public static void sendRegistrationTokenToServer(final String token , final Context context) {
        if(!NetworkUtils.isConnectedToInternet(context)){
            return;
        }
        UserInfo userInfo  =  (new SessionManager(context)).getUserInfoFromShPref();
        if(userInfo != null){
            RegisterDeviceModel registerDeviceModel = new RegisterDeviceModel(userInfo.getUserId(),
                    token, StaticConstant.DEVICE_ANDROID);
            // Add custom implementation, as needed.
            Call<GenericResponse> call = RetrofitInstance.getInstance().registerDevice(registerDeviceModel);

            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    GenericResponse genericResponse = response.body();
                    if (genericResponse.getSuccess() == 1) {
                        Logger.d(TAG, "Device registered sucessfully::"+token);
                        (new SessionManager(context)).putString(BaseNetwork.DEVICE_TOKEN,token);
                    } else {
                        Logger.d(TAG, "Device failed to register:::token"+token);
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    Logger.e(TAG, "Device failed to register");
                }
            });
        }
    }
}
