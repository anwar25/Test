package in.eweblabs.careeradvance.Account.UpdateProfile;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import in.eweblabs.careeradvance.Adapter.ViewPagerAdapter;
import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.CirclePageIndicator;
import in.eweblabs.careeradvance.UI.CustomViewPager;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.Utils.Logger;

/**
 * Created by Akash.Singh on 11/23/2015.
 */
public class UpdateProfileScreen extends Fragment implements ViewPager.OnPageChangeListener,IAsyncTaskRunner {
    CustomViewPager mPager;
    public UserInfo userInfo =  new UserInfo();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_edit_profile_screen,container,false);
        ((BaseActivityScreen)getActivity()).SetToolbarInitialization(this);
        userInfo = ApplicationController.getInstance().getUserInfo();
        MidWidgetMapping(view);
        return  view;
    }
    ViewPagerAdapter viewPagerAdapter ;
    private void MidWidgetMapping(View view) {

        //mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mPager = (CustomViewPager)view.findViewById(R.id.pager);
        mPager.setPagingEnabled(true);
        CirclePageIndicator mIndicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        viewPagerAdapter.addFrag(new YourContactInformation(), getString(R.string.title_your_contact_information));
        viewPagerAdapter.addFrag(new YourCurrentEmploymentDetails(),getString(R.string.title_your_contact_information));
        viewPagerAdapter.addFrag(new UploadYourDetailedResume(),getString(R.string.upload_your_detail_resume));
        mPager.setAdapter(viewPagerAdapter);
        mIndicator.setViewPager(mPager);
        ((AppCompatButton)view.findViewById(R.id.btncontinue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformUpdateProcess();
            }
        });
    }

    public void setCurrentViewPager(int currentItem) {
        mPager.setCurrentItem(currentItem);

    }

    public int getCurrentViewPager(){
       return mPager.getCurrentItem();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    LoadingDialog loadingDialog;
    public void PerformUpdateProcess() {
        Fragment fragment = viewPagerAdapter.getItem(mPager.getCurrentItem());
        if(fragment instanceof YourContactInformation)
        {
            YourContactInformation contactInformation = (YourContactInformation) fragment;
            UserInfo userInfo = contactInformation.CheckContactInformationStatus();
            if(userInfo!=null && !TextUtils.isEmpty(userInfo.getUserId()))
            {
                loadingDialog =  new LoadingDialog(getActivity());
                loadingDialog.show();
                loadingDialog.SetTitleMessage("Loading...,");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(BaseNetwork.USER_ID_PARAMETER, userInfo.getUserId());
                hashMap.put(BaseNetwork.USER_NAME_PARAMETER,userInfo.getUserName());
                hashMap.put(BaseNetwork.USER_M_NAME_PARAMETER,"");
                if(userInfo.getUserObjective()!=null)
                    hashMap.put(BaseNetwork.USER_OBJECTIVE_PARAMETER,userInfo.getUserObjective());
                else
                    hashMap.put(BaseNetwork.USER_OBJECTIVE_PARAMETER,"");

                String location = userInfo.getUserLocation();
                String County = "";
                String state = "";
                if(!TextUtils.isEmpty(location))
                {
                    String[] homeTown = location.split(",");
                    state = homeTown[0];
                    County = homeTown[1];
                }

                hashMap.put(BaseNetwork.USER_COUNTRY_PARAMETER,County);
                hashMap.put(BaseNetwork.USER_STATE_PARAMETER,state);

                if(!TextUtils.isEmpty(userInfo.getUserAddress()))
                    hashMap.put(BaseNetwork.USER_ADDRESS_PARAMETER,userInfo.getUserAddress());
                else
                    hashMap.put(BaseNetwork.USER_ADDRESS_PARAMETER,"");

                hashMap.put(BaseNetwork.USER_CITY_PARAMETER,userInfo.getUserHomeTownCity());

                if(!TextUtils.isEmpty(userInfo.getUserZip()))
                    hashMap.put(BaseNetwork.USER_POSTAL_CODE_PARAMETER,userInfo.getUserZip());
                else
                    hashMap.put(BaseNetwork.USER_POSTAL_CODE_PARAMETER,"");

                if(!TextUtils.isEmpty(userInfo.getUserPhone()))
                    hashMap.put(BaseNetwork.USER_CONTACT_NUM_PARAMETER,userInfo.getUserPhone());
                else
                    hashMap.put(BaseNetwork.USER_CONTACT_NUM_PARAMETER,"");

                AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.UPDATEPROFILE1,this,loadingDialog);
                authCommonTask.execute(hashMap);
            }else
                Logger.d("YourContactInformation",":Fail:");

        }
        else if(fragment instanceof YourCurrentEmploymentDetails){

            YourCurrentEmploymentDetails contactInformation = (YourCurrentEmploymentDetails) fragment;
            UserInfo userInfo = contactInformation.CheckContactInformationStatus();
            if(userInfo!=null && !TextUtils.isEmpty(userInfo.getUserId())) {
                loadingDialog =  new LoadingDialog(getActivity());
                loadingDialog.show();
                loadingDialog.SetTitleMessage("Loading...,");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(BaseNetwork.USER_ID_PARAMETER, userInfo.getUserId());
                if(userInfo.getUserExperienceYear()!=null)
                    hashMap.put(BaseNetwork.USER_WORK_EXP_YEAR_PARAMETER,userInfo.getUserExperienceYear());
                else
                    hashMap.put(BaseNetwork.USER_WORK_EXP_YEAR_PARAMETER,"");

                if(userInfo.getUserExperienceMonth()!=null)
                    hashMap.put(BaseNetwork.USER_WORK_EXP_MONTH_PARAMETER,userInfo.getUserExperienceMonth());
                else
                    hashMap.put(BaseNetwork.USER_WORK_EXP_MONTH_PARAMETER,"");

                if(userInfo.getUserSalaryCurrency()!=null)
                    hashMap.put(BaseNetwork.USER_CURRENCY_PARAMETER,userInfo.getUserSalaryCurrency());
                else
                    hashMap.put(BaseNetwork.USER_CURRENCY_PARAMETER,"");

                if(userInfo.getUserCTC()!=null)
                    hashMap.put(BaseNetwork.USER_CTC_PARAMETER,userInfo.getUserCTC());
                else
                    hashMap.put(BaseNetwork.USER_CTC_PARAMETER,"");

                if(userInfo.getUserKeySkills()!=null)
                    hashMap.put(BaseNetwork.USER_KEY_SKILLS_PARAMETER,userInfo.getUserKeySkills());
                else
                    hashMap.put(BaseNetwork.USER_KEY_SKILLS_PARAMETER,"");

                if(userInfo.getUserResumeHeadline()!=null)
                    hashMap.put(BaseNetwork.USER_RESUME_HEADLINE_PARAMETER,userInfo.getUserResumeHeadline());
                else
                    hashMap.put(BaseNetwork.USER_RESUME_HEADLINE_PARAMETER,"");

                if(userInfo.getUserIndustry()!=null)
                    hashMap.put(BaseNetwork.USER_INDUSTRY_PARAMETER,userInfo.getUserIndustry());
                else
                    hashMap.put(BaseNetwork.USER_INDUSTRY_PARAMETER,"");

                if(userInfo.getUserFunctionalArea()!=null)
                    hashMap.put(BaseNetwork.USER_FUNCTION_AREA_PARAMETER,userInfo.getUserFunctionalArea());
                else
                    hashMap.put(BaseNetwork.USER_FUNCTION_AREA_PARAMETER,"");

                if(userInfo.getUserBasicEducation()!=null)
                    hashMap.put(BaseNetwork.USER_BASIC_EDUCATION_PARAMETER,userInfo.getUserBasicEducation());
                else
                    hashMap.put(BaseNetwork.USER_BASIC_EDUCATION_PARAMETER,"");

                if(userInfo.getUserBasicEducationOther()!=null)
                    hashMap.put(BaseNetwork.USER_BASIC_EDUCATION_OTHER_PARAMETER,userInfo.getUserBasicEducationOther());
                else
                    hashMap.put(BaseNetwork.USER_BASIC_EDUCATION_OTHER_PARAMETER,"");

                if(userInfo.getUserMasterEducation()!=null)
                    hashMap.put(BaseNetwork.USER_MASTER_EDUCATION_PARAMETER,userInfo.getUserMasterEducation());
                else
                    hashMap.put(BaseNetwork.USER_MASTER_EDUCATION_PARAMETER,"");

                if(userInfo.getUserMasterEducationOther()!=null)
                    hashMap.put(BaseNetwork.USER_MASTER_EDUCATION_PARAMETER_OTHER,userInfo.getUserMasterEducationOther());
                else
                    hashMap.put(BaseNetwork.USER_MASTER_EDUCATION_PARAMETER_OTHER,"");


                if(userInfo.getUserDoctrateEducation()!=null)
                    hashMap.put(BaseNetwork.USER_DOCTRATE_EDUCATION_PARAMETER,userInfo.getUserDoctrateEducation());
                else
                    hashMap.put(BaseNetwork.USER_DOCTRATE_EDUCATION_PARAMETER,"");

                if(userInfo.getUserDoctrateEducationOther()!=null)
                    hashMap.put(BaseNetwork.USER_DOCTRATE_EDUCATION_PARAMETER_OTHER,userInfo.getUserDoctrateEducationOther());
                else
                    hashMap.put(BaseNetwork.USER_DOCTRATE_EDUCATION_PARAMETER_OTHER,"");


                if(userInfo.getUserDiplomaCourse()!=null)
                    hashMap.put(BaseNetwork.USER_DIPLOMA_PARAMETER,userInfo.getUserDiplomaCourse());
                else
                    hashMap.put(BaseNetwork.USER_DIPLOMA_PARAMETER,"");

                AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.UPDATEPROFILE2,this,loadingDialog);
                authCommonTask.execute(hashMap);
            }
            else
                Logger.d("YourCurrentEmploymentDetails",":Fail:");
        }else if(fragment instanceof UploadYourDetailedResume){
            UploadYourDetailedResume contactInformation = (UploadYourDetailedResume) fragment;
            UserInfo userInfo = contactInformation.CheckContactInformationStatus();
            if(userInfo!=null && !TextUtils.isEmpty(userInfo.getUserId())){
                loadingDialog =  new LoadingDialog(getActivity());
                loadingDialog.show();
                loadingDialog.SetTitleMessage("Loading...,");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(BaseNetwork.USER_ID_PARAMETER, userInfo.getUserId());
                hashMap.put(BaseNetwork.USER_RESUME_PATH_PARAMETER, userInfo.getUserResumePath());
                hashMap.put(BaseNetwork.USER_RESUME_TEXT_PARAMETER,userInfo.getUserResumeText());
                hashMap.put(BaseNetwork.USER_JOB_ALERT_PARAMETER,userInfo.getUserJobAlert());
                hashMap.put(BaseNetwork.USER_FAST_FORWARD_EMAILS_PARAMETER,userInfo.getUserFastForwordEmails());
                hashMap.put(BaseNetwork.USER_FAST_FORWARD_CALLS_PARAMETER,userInfo.getUserFastForwordCalls());
                hashMap.put(BaseNetwork.USER_COMMUNICATION_PARAMETER,userInfo.getUserCommunicationClient());
                hashMap.put(BaseNetwork.USER_NOTIFICATION_PARAMETER,userInfo.getUserNotification());
                hashMap.put(BaseNetwork.USER_SPECIAL_OFFER_PARAMETER,userInfo.getUserSpecialOffer());
                AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.UPDATEPROFILE3,this,loadingDialog);
                authCommonTask.execute(hashMap);
            }
            else
                Logger.d("UploadYourDetailedResume",":Fail:");
                Logger.d("UploadYourDetailedResume",":Fail:");

        }
       }

    @Override
    public void taskCompleted(Object obj) {
        if(loadingDialog!=null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(obj!=null) {
            ResultMessage resultMessage = (ResultMessage) obj;
            Response response = (Response) resultMessage.RESULT_OBJECT;
            if(!TextUtils.isEmpty(response.getSuccess()) && response.getSuccess().equalsIgnoreCase("1")){

                Fragment fragment = viewPagerAdapter.getItem(mPager.getCurrentItem());
                if(fragment instanceof YourContactInformation) {
                    YourContactInformation contactInformation = (YourContactInformation) fragment;
                    UserInfo userInfo = contactInformation.CheckContactInformationStatus();
                    ApplicationController.getInstance().getCareerAdvanceDBData().InsertUserRecord(userInfo);
                    ApplicationController.getInstance().setUserInfo(userInfo);
                    if (mPager.getCurrentItem() < 3)
                        setCurrentViewPager(mPager.getCurrentItem() + 1);

                }
                else if(fragment instanceof YourCurrentEmploymentDetails) {
                    YourCurrentEmploymentDetails contactInformation = (YourCurrentEmploymentDetails) fragment;
                    UserInfo userInfo = contactInformation.CheckContactInformationStatus();
                    ApplicationController.getInstance().getCareerAdvanceDBData().InsertUserRecord(userInfo);
                    ApplicationController.getInstance().setUserInfo(userInfo);
                    if (mPager.getCurrentItem() < 3)
                        setCurrentViewPager(mPager.getCurrentItem() + 1);
                }
                else if(fragment instanceof UploadYourDetailedResume) {
                    UploadYourDetailedResume contactInformation = (UploadYourDetailedResume) fragment;
                    UserInfo userInfo = contactInformation.CheckContactInformationStatus();
                    ApplicationController.getInstance().getCareerAdvanceDBData().InsertUserRecord(userInfo);
                    ApplicationController.getInstance().setUserInfo(userInfo);
                    setCurrentViewPager(0);
                    ((BaseActivityScreen)getActivity()).onBackPressed();
                }
                Toast.makeText(getActivity(),"your profile was updated successfully",Toast.LENGTH_LONG).show();

            }
            else if(!TextUtils.isEmpty(response.getMessage())){
                MessageDialog messageDialog =  new MessageDialog(getActivity());
                messageDialog.show();
                messageDialog.setTitle(getString(R.string.app_name));
                messageDialog.setMessageContent(response.getMessage());
            }
        }

    }

    @Override
    public void taskErrorMessage(Object obj) {
        if(loadingDialog!=null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(obj!=null) {
            ResultMessage resultMessage = (ResultMessage) obj;
            if(!TextUtils.isEmpty(resultMessage.ERRORMESSAGE)){
                MessageDialog messageDialog =  new MessageDialog(getActivity());
                messageDialog.show();
                messageDialog.setTitle(getString(R.string.app_name));
                messageDialog.setMessageContent(resultMessage.ERRORMESSAGE);
            }
        }


    }

    @Override
    public void taskProgress(Object obj) {

    }

    @Override
    public void taskStarting() {

    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 7777) {
                Fragment fragment = viewPagerAdapter.getItem(mPager.getCurrentItem());
                {
                    if(fragment instanceof UploadYourDetailedResume)
                    {
                        ((UploadYourDetailedResume)fragment).UploadResumeIntentResult(data);
                    }
                }
            }
        }
    }

}
