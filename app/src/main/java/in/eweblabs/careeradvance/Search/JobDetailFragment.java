package in.eweblabs.careeradvance.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import in.eweblabs.careeradvance.Account.SignInScreen;
import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Job;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.Utils.TextUtility;
import in.eweblabs.careeradvance.Utils.Utils;

/**
 * Created by Akash.Singh on 1/11/2016.
 */
public class JobDetailFragment extends Fragment implements IAsyncTaskRunner{
    Job job;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_job_detail_screen,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        job = (Job) getArguments().getSerializable("JobDetail");
        WidgetMapping(view);
        return view;
    }

    private void WidgetMapping(View view) {

        ((TextView)view.findViewById(R.id.text_job_title)).setText(TextUtility.checkIsStringEmpty(job.getJob_title()));
        ((TextView)view.findViewById(R.id.text_company_name)).setText(TextUtility.checkIsStringEmpty(job.getCompany()));
        ((TextView)view.findViewById(R.id.text_job_exp)).setText(job.getExperience_min()+" - "+job.getExperience_max()+" "+getString(R.string.years));
        ((TextView)view.findViewById(R.id.text_job_location)).setText(job.getJob_loacation_city()+" "+job.getJob_loc_country());
        ((TextView)view.findViewById(R.id.text_job_post)).setText(TextUtility.checkIsStringEmpty(job.getDate_post()));
        if(job.getCtc_currency().equalsIgnoreCase("Rupees"))
            ((TextView)view.findViewById(R.id.text_currency)).setText(" ₹ ");
        else
            ((TextView)view.findViewById(R.id.text_currency)).setText(" $ ");

        ((TextView)view.findViewById(R.id.text_ctc)).setText(job.getCtc_min()+" - "+job.getCtc_max());
        ((TextView)view.findViewById(R.id.text_industry_area)).setText(TextUtility.checkIsStringEmpty(job.getIndustry()));
        ((TextView)view.findViewById(R.id.text_functional_ares)).setText(TextUtility.checkIsStringEmpty(job.getFunctional_area()));
        ((TextView)view.findViewById(R.id.text_role)).setText(TextUtility.checkIsStringEmpty(job.getHiring_for()));
        ((TextView)view.findViewById(R.id.text_job_type)).setText(TextUtility.checkIsStringEmpty(job.getJob_type()));
        ((TextView)view.findViewById(R.id.text_job_detail)).setText(TextUtility.checkIsStringEmpty(job.getJob_desc()));

        String education = TextUtility.checkIsStringEmpty(job.getUg_qualification());
        if(!TextUtils.isEmpty(education))
            education += ", "+ job.getPg_qualification();
        else
            education += job.getPg_qualification();
        if(!TextUtils.isEmpty(education))
            education += ", "+ job.getDoctrate();
        else
            education += job.getDoctrate();

        if(!TextUtils.isEmpty(education))
        ((TextView)view.findViewById(R.id.text_educational_requirement)).setText(education);

        ((TextView)view.findViewById(R.id.text_ed_company_name)).setText(job.getCompany());
        ((TextView)view.findViewById(R.id.text_ed_contact_person)).setText(job.getContact_person());
        ((TextView)view.findViewById(R.id.text_ed_contact_email)).setText(job.getEmail_id());
        ((TextView)view.findViewById(R.id.text_ed_contact_number)).setText(job.getContact_number());

        ((AppCompatButton)view.findViewById(R.id.btnApply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(job.getWebsite().equalsIgnoreCase("www.careeradvance.com")){
                    UserInfo userInfo = ApplicationController.getInstance().getUserInfo();
                    if(userInfo!=null && !TextUtils.isEmpty(userInfo.getUserEmail()))
                    {
                        PerformJobApplyProcess();
                    }else{
                        Bundle bundle =  new Bundle();
                        bundle.putString("activity", "JobApply");
                        SignInScreen signInScreen = new SignInScreen();
                        signInScreen.setArguments(bundle);
                        ((BaseActivityScreen) getActivity()).onReplaceFragment(signInScreen, true);
                    }

                }else{

                }
            }
        });

    }
    LoadingDialog loadingDialog;
    public void PerformJobApplyProcess() {
        loadingDialog =  new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage("Job Search, Processing...");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseNetwork.JOBID, job.getJob_id());
        hashMap.put(BaseNetwork.USERID, ApplicationController.getInstance().getUserInfo().getUserId());
        hashMap.put(BaseNetwork.EMPID, job.getEmp_id());
        hashMap.put(BaseNetwork.DATETIME, Utils.getCurrentDateTime());
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.APPLYJOB,this,loadingDialog);
        authCommonTask.execute(hashMap);
    }


    @Override
    public void taskCompleted(Object obj) {
        if(loadingDialog!=null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(obj!=null){
            ResultMessage resultMessage = (ResultMessage) obj;
            Response response = (Response) resultMessage.RESULT_OBJECT;
            if(!TextUtils.isEmpty(response.getMessage())){
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
}
