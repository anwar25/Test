package in.eweblabs.careeradvance.Account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.Network.RetrofitInstance;
import in.eweblabs.careeradvance.Network.models.GenericResponse;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.Utils.ImageFilePath;
import in.eweblabs.careeradvance.Utils.Logger;
import in.eweblabs.careeradvance.service.DownloadService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anwar Shaikh on 11/24/2015.
 */
public class UploadResumeScreen extends Fragment implements View.OnClickListener {

    private static final String TAG = UploadResumeScreen.class.getSimpleName();
    int SELECT_FILE = 7777;
    RelativeLayout mUploadResumeLayout;
    private AppCompatButton mUploadButton;
    private BaseActivityScreen activityHandle;

    private String uploadedResumeUrl;
    private TextView mResumeTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_upload_resume_screen, container, false);
        ((BaseActivityScreen) getActivity()).setToolbarInitialization(this);
        MidWidgetMapping(view);
        return view;
    }

    private void MidWidgetMapping(View view) {

        //uploadedResumeUrl = activityHandle.getSessionManager().getString(StaticConstant.USER_RESUME_PATH);

        mUploadResumeLayout = (RelativeLayout) view.findViewById(R.id.uploadResumeLayout);

        mUploadResumeLayout.setOnClickListener(this);
        mResumeTextView = (TextView) view.findViewById(R.id.resumeTextView);
        mUploadButton = (AppCompatButton) view.findViewById(R.id.updateResumeButton);
        mUploadButton.setOnClickListener(this);
        CheckResumeBackground();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uploadResumeLayout:
                if(!TextUtils.isEmpty(activityHandle.getmUserInfo().getUserResumePath())){
                    downloadResume();
                }else{
                    Toast.makeText(activityHandle, getString(R.string.no_resume_uploaded), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.updateResumeButton:
                uploadResume();
                break;
        }

    }

    private void downloadResume() {
       /* Call<ResponseBody> call = RetrofitInstance.getInstance().downloadResume(activityHandle.getmUserInfo().getUserResumePath());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                //    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                //    Log.d(TAG, "file download was a success? " + writtenToDisk);
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });*/
       /* new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Call<ResponseBody> call = RetrofitInstance.getInstance().downloadResume(activityHandle.getmUserInfo().getUserResumePath());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "server contacted and has file");

                            //    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                            //    Log.d(TAG, "file download was a success? " + writtenToDisk);
                        } else {
                            Log.d(TAG, "server contact failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "error");
                    }
                });
                return null ;
            }
        }.execute();*/
        Intent intent = new Intent(activityHandle,DownloadService.class);
        intent.putExtra(StaticConstant.USER_RESUME_PATH,activityHandle.getmUserInfo().getUserResumePath());
        intent.putExtra(StaticConstant.USER_RESUME_TEXT,String.valueOf(mResumeTextView.getText()));
        activityHandle.startService(intent);
    }

    private void uploadResume() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setType("*/*");
        String[] mimetypes = {"application/pdf", "application/msword"
                , "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                , "application/rtf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, SELECT_FILE);
        Toast.makeText(activityHandle, getString(R.string.internal_storage_to_appear), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            System.out.println(selectedImageUri.toString());
            if (isLessThanPermittedSize(data)) {
                String selectedImagePath = ImageFilePath.getPath(activityHandle, selectedImageUri);
                Logger.v(TAG, "" + selectedImagePath);
                File destination = new File(selectedImagePath);
                uploadResumeToServer(destination);
            }
            // MEDIA GALLERY
            //String selectedImagePath = ImageFilePath.getPath(activityHandle, selectedImageUri);
            //Logger.v("UploadRsume",""+selectedImagePath);
        }
    }

    private void uploadResumeToServer(File resumeFile) {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.uploading_wait));

        UserInfo userInfo = activityHandle.getmUserInfo();
        RequestBody loginId = RequestBody.create(MediaType.parse("multipart/form-data"), userInfo.getUserId());
        RequestBody jobAlert = RequestBody.create(MediaType.parse("multipart/form-data"), userInfo.getUserJobAlert());
        RequestBody fastForwardEmails = RequestBody.create(MediaType.parse("multipart/form-data"), userInfo.getUserFastForwordEmails());
        RequestBody fastForwardCalls = RequestBody.create(MediaType.parse("multipart/form-data"), userInfo.getUserFastForwordCalls());
        RequestBody communicationClient = RequestBody.create(MediaType.parse("multipart/form-data"), userInfo.getUserCommunicationClient());
        RequestBody notification = RequestBody.create(MediaType.parse("multipart/form-data"), userInfo.getUserNotification());
        RequestBody specialOffer = RequestBody.create(MediaType.parse("multipart/form-data"), userInfo.getUserSpecialOffer());

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), resumeFile);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part resumeMultipart = MultipartBody.Part.createFormData("resume", resumeFile.getName(), requestFile);

        Call<GenericResponse> responseBody = RetrofitInstance.getInstance().uploadResume(loginId,
                jobAlert, fastForwardEmails, fastForwardCalls, communicationClient
                , specialOffer, notification, resumeMultipart);

        responseBody.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();

                int statusCode = response.code();
                GenericResponse genericResponse = response.body();
                if (genericResponse != null && genericResponse.getSuccess() == 1) {
                    MessageDialog messageDialog = new MessageDialog(getActivity());
                    messageDialog.show();
                    messageDialog.setTitle(getString(R.string.app_name));
                    messageDialog.setMessageContent(genericResponse.getMessage());

                    activityHandle.getmUserInfo().setUserResumePath(genericResponse.getImageFile());
                    Gson gson = new Gson();
                    String userInfoString = gson.toJson(activityHandle.getmUserInfo());
                    activityHandle.getSessionManager().putString(StaticConstant.USER_INFO,userInfoString);
                    CheckResumeBackground();
                } else {
                    Toast.makeText(activityHandle, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();
                MessageDialog messageDialog = new MessageDialog(getActivity());
                messageDialog.show();
                messageDialog.setTitle(getString(R.string.app_name));
                messageDialog.setMessageContent("" + t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activityHandle = (BaseActivityScreen) context;
        }
    }

    LoadingDialog loadingDialog;

    private void FileUpload(final File file) {
        (new AsyncTask() {
            @Override
            protected void onPreExecute() {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();
                super.onPreExecute();

            }

            @Override
            protected Object doInBackground(Object[] params) {
                String st = "{\"Success\":0,\"message\":\"Post parameter is not matching from server parameter.\"}";
                UserInfo userInfo = activityHandle.getmUserInfo();
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    String URL = BaseNetwork.URL_HOST + "uploadResume";
                    Logger.d(TAG, "::" + URL);
                    HttpPost httppost = new HttpPost(URL);
                    MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
                    mpEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    Logger.d(TAG, "file ::" + file.getAbsolutePath());
                    Logger.d(TAG, "file_path::" + file.getPath());
                    mpEntity.addPart("loginId", new StringBody(userInfo.getUserId()));
                    mpEntity.addPart("resume", new FileBody(file));
                    mpEntity.addPart(BaseNetwork.USER_RESUME_TEXT_PARAMETER, new StringBody(userInfo.getUserResumeText()));
                    mpEntity.addPart(BaseNetwork.USER_JOB_ALERT_PARAMETER, new StringBody(userInfo.getUserJobAlert()));
                    mpEntity.addPart(BaseNetwork.USER_FAST_FORWARD_EMAILS_PARAMETER, new StringBody(userInfo.getUserFastForwordEmails()));
                    mpEntity.addPart(BaseNetwork.USER_FAST_FORWARD_CALLS_PARAMETER, new StringBody(userInfo.getUserFastForwordCalls()));
                    mpEntity.addPart(BaseNetwork.USER_COMMUNICATION_PARAMETER, new StringBody(userInfo.getUserCommunicationClient()));
                    mpEntity.addPart(BaseNetwork.USER_NOTIFICATION_PARAMETER, new StringBody(userInfo.getUserNotification()));
                    mpEntity.addPart(BaseNetwork.USER_SPECIAL_OFFER_PARAMETER, new StringBody(userInfo.getUserSpecialOffer()));
                    HttpEntity entity = mpEntity.build();
                    httppost.setEntity(entity);
                    HttpResponse response = httpclient.execute(httppost);
                    st = EntityUtils.toString(response.getEntity());
                    Logger.d("log_tag", "In the try Loop" + st);


                } catch (Exception e) {
                    Logger.d("log_tag", "Error in http connection " + e.toString());
                }
                return st;
            }

            protected void onPostExecute(Object obj) {
                onPostExecute((String) obj);
            }

            protected void onPostExecute(String s) {
                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();
                if (!TextUtils.isEmpty(s)) {

                    try {
                        Logger.d("Response", "::" + s);
                        JSONObject jsonObjectLogin = new JSONObject(s);

                        if (jsonObjectLogin.has(StaticConstant.MESSAGE)) {
                            MessageDialog messageDialog = new MessageDialog(getActivity());
                            messageDialog.show();
                            messageDialog.setTitle(getString(R.string.app_name));
                            messageDialog.setMessageContent(jsonObjectLogin.getString(StaticConstant.MESSAGE));
                        }
                        if (jsonObjectLogin.has("image_file")) {
                            String image_file = jsonObjectLogin.getString("image_file");
                            activityHandle.getmUserInfo().setUserResumePath(image_file);
                            CheckResumeBackground();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).execute();


    }

    private void CheckResumeBackground() {
        String userResumePath = activityHandle.getmUserInfo().getUserResumePath();
        if (TextUtils.isEmpty(userResumePath)) {
            mUploadResumeLayout.setBackgroundResource(R.drawable.circle_redcolor);
            mResumeTextView.setText(getString(R.string.no_resume_uploaded));
        } else {
            mUploadResumeLayout.setBackgroundResource(R.drawable.circle_greycolor);
            if (userResumePath.endsWith(".rtf")) {
                mResumeTextView.setText(getString(R.string.resume_rtf));
            } else if (userResumePath.endsWith(".docx")) {
                mResumeTextView.setText(getString(R.string.resume_docx));
            } else if (userResumePath.endsWith(".doc")) {
                mResumeTextView.setText(getString(R.string.resume_doc));
            } else if (userResumePath.endsWith(".pdf")) {
                mResumeTextView.setText(getString(R.string.resume_pdf));
            }
        }

    }

    public boolean isLessThanPermittedSize(Intent data) {
        Uri uri = data.getData();
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();
        String displayName = null;
        Logger.d(TAG, "path*******:" + path);

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Logger.d(TAG, "displayName:*******:" + displayName);
                    int Size = cursor.getInt(cursor.getColumnIndex(OpenableColumns.SIZE)) / 1024;
                    Logger.d(TAG, "Size:*******:" + Size);

                    if (Size > 300) {
                        Toast.makeText(getActivity(), getString(R.string.file_less_than_300KB), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            } catch (Exception e) {
                Logger.d(TAG, "Size:*******:" + e.getMessage());
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
            long Size = myFile.getTotalSpace() / 1024;
            Logger.d(TAG, "Size:*******:" + Size);

            if (Size > 300) {
                Toast.makeText(getActivity(), getString(R.string.file_less_than_300KB), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private void MoveImagePath(InputStream input, String displayName) {
        try {
            File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString(), displayName);
            OutputStream os = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while ((bytesRead = input.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            input.close();
            //flush OutputStream to write any buffered data to file
            os.flush();
            os.close();
            FileUpload(destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
