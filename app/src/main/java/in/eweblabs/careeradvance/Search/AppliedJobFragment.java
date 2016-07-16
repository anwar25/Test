package in.eweblabs.careeradvance.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import in.eweblabs.careeradvance.Account.SignInScreen;
import in.eweblabs.careeradvance.Adapter.JobItemAdapter;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Job;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Interface.IRefreshList;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;

/**
 * Created by Anwar Shaikh on 1/9/2016.
 */
public class AppliedJobFragment extends Fragment implements IRefreshList , JobItemAdapter.ApplyJobListener
    ,IAsyncTaskRunner{
    ArrayList<Job> jobArrayList = new ArrayList<>();
    JobItemAdapter jobItemAdapter;
    RecyclerView recycler_view;
    private BaseActivityScreen activityHandle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_search_result_screen,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        //jobArrayList = (ArrayList<Job>) getArguments().getSerializable("Job");
        getAppliedJob();
        jobArrayList = new ArrayList<Job>();
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        CreateJob();
        return view;
    }

    private void CreateJob() {
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(llm);
        jobItemAdapter = new JobItemAdapter(getActivity(),jobArrayList,this,this);
        recycler_view.setAdapter(jobItemAdapter);
        jobItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activityHandle = (BaseActivityScreen) context;
        }
    }
    @Override
    public void jobApplied(Job job) {
      //  Toast.makeText(getContext(), "Item lcicke position:"+job.getCompany() , Toast.LENGTH_SHORT).show();
        if(activityHandle.getmUserInfo() != null){
        }else{
            activityHandle.onReplaceFragment(new SignInScreen(), true);
        }

        //shareTextUrl();
    }
    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
      //  share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    LoadingDialog loadingDialog;
    public void getAppliedJob() {
        loadingDialog =  new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.processing_wait));
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(BaseNetwork.USER_ID_PARAMETER, activityHandle.getmUserInfo().getUserId());
    //   hashMap.put(BaseNetwork.EMPID,job.getEmp_id());
     //   hashMap.put(BaseNetwork.DATETIME, Utils.getCurrentDateTime());
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.MY_APPLIED_JOBS,this,loadingDialog);
        authCommonTask.execute(hashMap);
    }

    @Override
    public void taskCompleted(Object obj) {
        if(loadingDialog!=null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(obj!=null){
            ResultMessage resultMessage = (ResultMessage) obj;
            Response response = (Response) resultMessage.RESULT_OBJECT;
           /* if(!TextUtils.isEmpty(response.getMessage())){
                MessageDialog messageDialog =  new MessageDialog(getActivity());
                messageDialog.show();
                messageDialog.setTitle(getString(R.string.app_name));
                messageDialog.setMessageContent(response.getMessage());
            }*/
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
    public void onRefresh(Object object) {
        Job job = (Job) object;
        JobDetailFragment jobDetailFragment =  new JobDetailFragment();
        Bundle bundle =  new Bundle();
        bundle.putSerializable("JobDetail",job);
        jobDetailFragment.setArguments(bundle);
        ((BaseActivityScreen) getActivity()).onReplaceFragment(jobDetailFragment,true);
    }
}
