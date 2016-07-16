package in.eweblabs.careeradvance.Account;

import android.app.Activity;
import android.content.Context;
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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import in.eweblabs.careeradvance.Account.UpdateProfile.UpdateProfileScreen;
import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.City;
import in.eweblabs.careeradvance.Entity.Country;
import in.eweblabs.careeradvance.Entity.Currency;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.CircleImageView;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.Utils.Logger;
import in.eweblabs.careeradvance.Utils.TextUtility;
import in.eweblabs.careeradvance.loader.ImageLoader;

/**
 * Created by Akash.singh on 11/20/2015.
 */
public class ProfileScreen extends Fragment {
    CircleImageView profile_img;
    int REQUEST_CAMERA = 9999;
    int SELECT_FILE = 8888;
    ImageLoader imageLoader;
    private BaseActivityScreen activityHandle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_profile_screen,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        WidgetMapping(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activityHandle = (BaseActivityScreen) context;
        }
    }

    private void WidgetMapping(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivityScreen) getActivity()).onReplaceFragment(new UpdateProfileScreen(), true);
            }
        });

        profile_img = (CircleImageView)view.findViewById(R.id.profile_img);
        imageLoader =  new ImageLoader(getActivity());

        UserInfo userInfo = activityHandle.getmUserInfo();
        if(userInfo!=null && !TextUtils.isEmpty(userInfo.getUserEmail()))
        {

            ((TextView)view.findViewById(R.id.txt_username)).setText(userInfo.getUserName());
            ((TextView)view.findViewById(R.id.text_email_address)).setText(userInfo.getUserEmail());
            Logger.d("profileScreen","::"+userInfo.getUserAvatar());
            if(!TextUtils.isEmpty(userInfo.getUserAvatar()));
            imageLoader.DisplayImage(userInfo.getUserAvatar(),profile_img);
        }
        ((AppCompatButton)view.findViewById(R.id.logout_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationController.getInstance().getCareerAdvanceDBData().resetUserRecordTable();
                ApplicationController.getInstance().setUserInfo(new UserInfo());
                ((BaseActivityScreen) getActivity()).onBackPressed();
            }
        });

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        ((TextView)view.findViewById(R.id.profile_resume_headline)).setText(TextUtility.checkIsStringEmpty(userInfo.getUserResumeHeadline()));
        ((TextView)view.findViewById(R.id.profile_objective)).setText(TextUtility.checkIsStringEmpty(userInfo.getUserObjective()));
        ((TextView)view.findViewById(R.id.profile_experience)).setText(TextUtility.checkIsStringIntegerEmpty(userInfo.getUserExperienceYear())+" " +
                ""+getString(R.string.years)+" "+TextUtility.checkIsStringIntegerEmpty(userInfo.getUserExperienceMonth()) + " " + getString(R.string.months));
        ((TextView)view.findViewById(R.id.profile_salary_expectation)).setText(StaticConstant.CURRENCY_USE+" "+TextUtility.checkIsStringIntegerEmpty(userInfo.getUserSalaryExpectation()));
        ((TextView)view.findViewById(R.id.profile_key_skills)).setText(TextUtility.checkIsStringEmpty(userInfo.getUserKeySkills()));
        InitializationCurrentSalary(view);
        InitializationLocation(view);
    }

    private void InitializationCurrentSalary(View view) {
        String Amount = "";
        String Amount_Value = activityHandle.getmUserInfo().getUserCTC() ;
        List<String> objectNameArrayListAmount = Arrays.asList(getResources().getStringArray(R.array.amount));
        List<String> objectCodeArrayListAmount = Arrays.asList(getResources().getStringArray(R.array.amount_value));
        for (int i = 0; i < objectNameArrayListAmount.size(); i++) {
            if(!TextUtils.isEmpty(Amount_Value) && objectCodeArrayListAmount.get(i).equalsIgnoreCase(Amount_Value)){
                Amount = objectNameArrayListAmount.get(i);
                break;
            }
        }
        HashMap<Object,Object> objectArrayListCurr = ApplicationController.getInstance().getDataModel().GetCurrencyList();
        String CurrencyCode = activityHandle.getmUserInfo().getUserSalaryCurrency();
        Currency currency = (Currency) objectArrayListCurr.get(CurrencyCode);
        if(currency!=null)
            ((TextView)view.findViewById(R.id.profile_salary_expectation)).setText(currency.getCurrencyName()+" "+TextUtility.checkIsStringEmpty(Amount));
        else
            ((TextView)view.findViewById(R.id.profile_salary_expectation)).setText("$ " + TextUtility.checkIsStringEmpty(Amount));



    }

    private void InitializationLocation(View view) {

        String UserLocation = activityHandle.getmUserInfo().getUserLocation();
        String stateUser = "";
        String countryUser = "";
        String LocationName = "" ;
        if(!TextUtils.isEmpty(UserLocation))
        {
            String[] userLocation = UserLocation.split(", ");
            stateUser = userLocation[0];
            countryUser = userLocation[1];
        }

        if(!TextUtils.isEmpty(countryUser))
        {
            HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCountryList();
            Country country  = (Country) objectArrayList.get(countryUser);
            if(country!=null)
                LocationName  = country.getCountryName();
        }
        if(!TextUtils.isEmpty(stateUser))
        {
            HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCityList();

            City city  = (City) objectArrayList.get(stateUser);
            if(stateUser!=null)
                LocationName += StaticConstant.Regular_Expression_LOCATION+city.getCityName();
        }

        ((TextView)view.findViewById(R.id.profile_location)).setText(TextUtility.checkIsStringEmpty(LocationName));

    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Memory", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Profile Picture!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Memory")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                String FileName =  (getString(R.string.app_name)+"_img_Profile" + ".jpg").replaceAll("\\s+","");
                File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString(),FileName);
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profile_img.setImageBitmap(thumbnail);
                ImageUpload();
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getActivity(), selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                String FileName =  (getString(R.string.app_name)+"_img_Profile" + ".jpg").replaceAll("\\s+","");
                File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString(),FileName);
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profile_img.setImageBitmap(bm);
                ImageUpload();
            }
        }
       }

    LoadingDialog loadingDialog;
    private void ImageUpload() {
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
                    String URL = BaseNetwork.URL_HOST+"uploadProfilePic";
                    HttpPost httppost = new HttpPost(URL);
                    MultipartEntityBuilder mpEntity=MultipartEntityBuilder.create();
                    mpEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    String FileName =  (getString(R.string.app_name)+"_img_Profile" + ".jpg").replaceAll("\\s+","");
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString(),FileName);
                    Logger.d("file_path", "::" + file.getPath());
                    mpEntity.addPart("loginId", new StringBody(activityHandle.getmUserInfo().getUserId()));
                    mpEntity.addPart("profileImage", new FileBody(file));
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
                            String ImageFile = jsonObjectLogin.getString("image_file");
                            activityHandle.getmUserInfo().setUserAvatar(jsonObjectLogin.getString("image_file"));
                            if(!TextUtils.isEmpty(ImageFile));
                            imageLoader.DisplayImage(ImageFile, profile_img);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).execute();


    }

}
