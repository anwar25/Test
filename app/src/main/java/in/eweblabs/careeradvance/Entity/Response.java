package in.eweblabs.careeradvance.Entity;

import java.util.ArrayList;

/**
 * Created by Akash.Singh on 12/1/2015.
 */
public class Response {

    String Success;
    String message;
    ArrayList<Job> jobArrayList =  new ArrayList<>();

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Job> getJobArrayList() {
        return jobArrayList;
    }

    public void setJobArrayList(ArrayList<Job> jobArrayList) {
        this.jobArrayList = jobArrayList;
    }
}
