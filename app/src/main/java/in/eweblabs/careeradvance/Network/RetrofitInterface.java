package in.eweblabs.careeradvance.Network;

import in.eweblabs.careeradvance.Network.models.GenericResponse;
import in.eweblabs.careeradvance.Network.models.JobSugestionReqModel;
import in.eweblabs.careeradvance.Network.models.JobSugestionResModel;
import in.eweblabs.careeradvance.Network.models.RegisterDeviceModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Anwar on 5/21/2016.
 */
public interface RetrofitInterface {

    @Multipart
    @POST(Urls.UPLOAD_RESUME)
    Call<GenericResponse> uploadResume(@Part("loginId") RequestBody loginId,
                                       @Part("userJobAlert") RequestBody userJobAlert,
                                       @Part("userFastForwordEmails") RequestBody userFastForwordEmails,
                                       @Part("userFastForwordCalls") RequestBody userFastForwordCalls,
                                       @Part("userCommunicationClient") RequestBody userCommunicationClient,
                                       @Part("userSpecialOffer") RequestBody userSpecialOffer,
                                       @Part("userNotification") RequestBody userNotification,
                                       @Part MultipartBody.Part resumeDoc);
    @Streaming
    @GET
    public Call<ResponseBody> downloadResume(@Url String url);


    @POST(Urls.REGISTER_TOKEN_API)
    Call<GenericResponse> registerDevice(@Body RegisterDeviceModel registerDeviceModel);

    @POST(Urls.JOB_SUGGESTION_API)
    Call<JobSugestionResModel> jobSuggestions(@Body JobSugestionReqModel registerDeviceModel);

}
