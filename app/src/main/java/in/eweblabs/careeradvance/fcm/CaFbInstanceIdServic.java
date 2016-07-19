package in.eweblabs.careeradvance.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Anwar on 7/13/2016.
 */
public class CaFbInstanceIdServic extends FirebaseInstanceIdService {

    private static final String TAG = "CaFbInstanceIdServic";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        RegisterToken.sendRegistrationTokenToServer(refreshedToken,this);
    }
    // [END refresh_token]

}