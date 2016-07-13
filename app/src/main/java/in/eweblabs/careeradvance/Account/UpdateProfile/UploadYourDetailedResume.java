package in.eweblabs.careeradvance.Account.UpdateProfile;

import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.io.FileSystemUtils;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.Utils.Logger;

/**
 * Created by Akash.Singh on 11/24/2015.
 */
public class UploadYourDetailedResume extends Fragment {

    int SELECT_FILE = 7777;
    ImageView image_document;
    CheckBox checkbox_job_alerts,checkbox_imp_notification,checkbox_fastforword_emails,checkbox_I_have_read_and_understood_and_agree,checkbox_other_promotions,checkbox_fastforword_calls,checkbox_communication_clients;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_your_detailed_resume,container,false);
        MidWidgetMapping(view);
        return view;
    }

    private void MidWidgetMapping(View view) {
        image_document = (ImageView) view.findViewById(R.id.image_document);
        checkbox_job_alerts = (CheckBox) view.findViewById(R.id.checkbox_job_alerts);
        checkbox_imp_notification = (CheckBox) view.findViewById(R.id.checkbox_imp_notification);
        checkbox_fastforword_emails = (CheckBox) view.findViewById(R.id.checkbox_fastforword_emails);
        checkbox_fastforword_calls = (CheckBox) view.findViewById(R.id.checkbox_fastforword_calls);
        checkbox_other_promotions = (CheckBox) view.findViewById(R.id.checkbox_other_promotions);
        checkbox_communication_clients = (CheckBox) view.findViewById(R.id.checkbox_communication_clients);
        checkbox_I_have_read_and_understood_and_agree = (CheckBox) view.findViewById(R.id.checkbox_I_have_read_and_understood_and_agree);
        Initialization();
        image_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDocument();
            }
        });



    }

    private void Initialization() {
        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserJobAlert())
                && ((UpdateProfileScreen)getParentFragment()).userInfo.getUserJobAlert().equalsIgnoreCase(checkbox_job_alerts.getText().toString()))
            checkbox_job_alerts.setChecked(true);
        else
            checkbox_job_alerts.setChecked(false);

        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserNotification())
                && ((UpdateProfileScreen)getParentFragment()).userInfo.getUserNotification().equalsIgnoreCase(checkbox_imp_notification.getText().toString()))
            checkbox_imp_notification.setChecked(true);
        else
            checkbox_imp_notification.setChecked(false);

        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserFastForwordEmails())
                && ((UpdateProfileScreen)getParentFragment()).userInfo.getUserFastForwordEmails().equalsIgnoreCase(checkbox_fastforword_emails.getText().toString()))
            checkbox_fastforword_emails.setChecked(true);
        else
            checkbox_fastforword_emails.setChecked(false);

        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserFastForwordCalls())
                && ((UpdateProfileScreen)getParentFragment()).userInfo.getUserFastForwordCalls().equalsIgnoreCase(checkbox_fastforword_calls.getText().toString()))
            checkbox_fastforword_calls.setChecked(true);
        else
            checkbox_fastforword_calls.setChecked(false);

        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserCommunicationClient())
                && ((UpdateProfileScreen)getParentFragment()).userInfo.getUserCommunicationClient().equalsIgnoreCase(checkbox_communication_clients.getText().toString()))
            checkbox_communication_clients.setChecked(true);
        else
            checkbox_communication_clients.setChecked(false);


        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserSpecialOffer())
                && ((UpdateProfileScreen)getParentFragment()).userInfo.getUserSpecialOffer().equalsIgnoreCase(checkbox_other_promotions.getText().toString()))
            checkbox_other_promotions.setChecked(true);
        else
            checkbox_other_promotions.setChecked(false);


        CheckResumeBackground(((UpdateProfileScreen) getParentFragment()).userInfo.getUserResumePath());

    }


    public UserInfo CheckContactInformationStatus() {
        if(checkbox_job_alerts.isChecked())
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserJobAlert(checkbox_job_alerts.getText().toString());
        else
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserJobAlert("");

        if(checkbox_imp_notification.isChecked())
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserNotification(checkbox_imp_notification.getText().toString());
        else
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserNotification("");

        if(checkbox_fastforword_emails.isChecked())
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserFastForwordEmails(checkbox_fastforword_emails.getText().toString());
        else
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserFastForwordEmails("");

        if(checkbox_fastforword_calls.isChecked())
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserFastForwordCalls(checkbox_fastforword_calls.getText().toString());
        else
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserFastForwordCalls("");

        if(checkbox_communication_clients.isChecked())
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserCommunicationClient(checkbox_communication_clients.getText().toString());
        else
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserSpecialOffer("");

        if(checkbox_other_promotions.isChecked())
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserSpecialOffer(checkbox_other_promotions.getText().toString());
        else
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserSpecialOffer("");

        return ((UpdateProfileScreen)getParentFragment()).userInfo;
    }

    private void selectDocument() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, SELECT_FILE);
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
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    String URL = BaseNetwork.URL_HOST+"uploadResume";
                    Logger.d("URL", "::" + URL);
                    HttpPost httppost = new HttpPost(URL);
                    MultipartEntityBuilder mpEntity=MultipartEntityBuilder.create();
                    mpEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    Logger.d("file", "::" + file.getAbsolutePath());
                    Logger.d("file_path", "::" + file.getPath());
                    mpEntity.addPart("loginId", new StringBody(ApplicationController.getInstance().getUserInfo().getUserId()));
                    mpEntity.addPart("resume", new FileBody(file));
                    mpEntity.addPart(BaseNetwork.USER_RESUME_TEXT_PARAMETER,new StringBody(((UpdateProfileScreen)getParentFragment()).userInfo.getUserResumeText()));
                    mpEntity.addPart(BaseNetwork.USER_JOB_ALERT_PARAMETER,new StringBody(((UpdateProfileScreen)getParentFragment()).userInfo.getUserJobAlert()));
                    mpEntity.addPart(BaseNetwork.USER_FAST_FORWARD_EMAILS_PARAMETER,new StringBody(((UpdateProfileScreen)getParentFragment()).userInfo.getUserFastForwordEmails()));
                    mpEntity.addPart(BaseNetwork.USER_FAST_FORWARD_CALLS_PARAMETER,new StringBody(((UpdateProfileScreen)getParentFragment()).userInfo.getUserFastForwordCalls()));
                    mpEntity.addPart(BaseNetwork.USER_COMMUNICATION_PARAMETER, new StringBody(((UpdateProfileScreen)getParentFragment()).userInfo.getUserCommunicationClient()));
                    mpEntity.addPart(BaseNetwork.USER_NOTIFICATION_PARAMETER, new StringBody(((UpdateProfileScreen)getParentFragment()).userInfo.getUserNotification()));
                    mpEntity.addPart(BaseNetwork.USER_SPECIAL_OFFER_PARAMETER, new StringBody(((UpdateProfileScreen)getParentFragment()).userInfo.getUserSpecialOffer()));
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

            protected  void onPostExecute(Object obj)
            {
                onPostExecute((String)obj);
            }

            protected void onPostExecute(String s)
            {
                if(loadingDialog!=null && loadingDialog.isShowing())
                    loadingDialog.dismiss();
                if(!TextUtils.isEmpty(s)){

                    try {
                        Logger.d("Response","::"+s);
                        JSONObject jsonObjectLogin =  new JSONObject(s);

                        if(jsonObjectLogin.has(StaticConstant.MESSAGE)){
                            MessageDialog messageDialog =  new MessageDialog(getActivity());
                            messageDialog.show();
                            messageDialog.setTitle(getString(R.string.app_name));
                            messageDialog.setMessageContent(jsonObjectLogin.getString(StaticConstant.MESSAGE));
                        }
                        if(jsonObjectLogin.has("image_file")){
                            String image_file = jsonObjectLogin.getString("image_file");
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserResumePath(image_file);
                            CheckResumeBackground(((UpdateProfileScreen) getParentFragment()).userInfo.getUserResumePath());
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).execute();


    }

    private void CheckResumeBackground(String userResumePath) {
        if(!TextUtils.isEmpty(userResumePath))
            image_document.setBackgroundResource(R.drawable.circle_redcolor);
        else
            image_document.setBackgroundResource(R.drawable.circle_greycolor);

    }

    public void UploadResumeIntentResult(Intent data){
        Uri uri = data.getData();
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();
        String displayName = null;
        Logger.d("path",":*******:"+path);

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Logger.d("displayName",":*******:"+displayName);
                    int Size = cursor.getInt(cursor.getColumnIndex(OpenableColumns.SIZE))/1024;
                    Logger.d("Size",":*******:"+Size);
                    if(displayName.endsWith(".doc")||displayName.endsWith(".pdf")||displayName.endsWith(".docx")
                            ||displayName.endsWith(".rtf")){
                        if(Size<300){
                            InputStream input = getActivity().getContentResolver().openInputStream(uri);
                            MoveImagePath(input,displayName);

                        }else
                            Toast.makeText(getActivity(),"File should be less than 300 KB",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getActivity(),"Please choose proper file format",Toast.LENGTH_LONG).show();

                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
            long Size =  myFile.getTotalSpace()/1024;
            Logger.d("Size",":*******:"+Size);
            if(displayName.endsWith(".doc")||displayName.endsWith(".pdf")||displayName.endsWith(".docx")
                    ||displayName.endsWith(".rtf")){
                if(Size<300){
                    File destination = new File(myFile,displayName);
                    FileUpload(destination);
                }else
                    Toast.makeText(getActivity(),"File should be less than 300 KB",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(getActivity(),"Please choose proper file format",Toast.LENGTH_LONG).show();


            }
        }
    }

    private void MoveImagePath(InputStream input, String displayName) {
        try {
            File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString(),displayName);
            OutputStream os = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while((bytesRead = input.read(buffer)) !=-1){
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
