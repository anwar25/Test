package in.eweblabs.careeradvance.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anwar on 7/26/2016.
 */
public class JobSugestionReqModel {

    @SerializedName("sarchKey")
    @Expose
    private String sarchKey;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     *
     * @return
     * The sarchKey
     */
    public String getSarchKey() {
        return sarchKey;
    }

    /**
     *
     * @param sarchKey
     * The sarchKey
     */
    public void setSarchKey(String sarchKey) {
        this.sarchKey = sarchKey;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
