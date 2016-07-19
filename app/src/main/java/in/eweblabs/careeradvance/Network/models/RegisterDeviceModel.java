package in.eweblabs.careeradvance.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anwar on 7/18/2016.
 */
public class RegisterDeviceModel {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;

    public RegisterDeviceModel(String userId ,String deviceToken ,String deviceType){
        this.userId = userId ;
        this.deviceToken = deviceToken ;
        this.deviceType = deviceType ;
    }
    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The deviceToken
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     *
     * @param deviceToken
     * The deviceToken
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     *
     * @return
     * The deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     *
     * @param deviceType
     * The deviceType
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

}