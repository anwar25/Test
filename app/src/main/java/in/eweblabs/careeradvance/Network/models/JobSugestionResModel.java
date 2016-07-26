package in.eweblabs.careeradvance.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anwar on 7/26/2016.
 */
public class JobSugestionResModel {

    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords = new ArrayList<String>();

    /**
     *
     * @return
     * The success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The Success
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The keywords
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     *
     * @param keywords
     * The keywords
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

}
