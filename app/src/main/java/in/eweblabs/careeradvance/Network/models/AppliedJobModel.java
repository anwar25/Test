package in.eweblabs.careeradvance.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anwar on 7/16/2016.
 */
public class AppliedJobModel {

    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalRJobs")
    @Expose
    private Integer totalRJobs;
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();

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
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The totalRJobs
     */
    public Integer getTotalRJobs() {
        return totalRJobs;
    }

    /**
     *
     * @param totalRJobs
     * The totalRJobs
     */
    public void setTotalRJobs(Integer totalRJobs) {
        this.totalRJobs = totalRJobs;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "AppliedJobModel{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", totalRJobs=" + totalRJobs +
                ", results=" + results +
                '}';
    }
}
