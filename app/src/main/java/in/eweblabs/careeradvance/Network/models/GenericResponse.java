package in.eweblabs.careeradvance.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 449781 on 7/18/2016.
 */
public class GenericResponse {

    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("image_file")
    @Expose
    private String imageFile;

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
     * The imageFile
     */
    public String getImageFile() {
        return imageFile;
    }

    /**
     *
     * @param imageFile
     * The image_file
     */
    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

}
