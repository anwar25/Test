package in.eweblabs.careeradvance.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
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
import in.eweblabs.careeradvance.Adapter.OnLoadMoreListener;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Job;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Interface.IRefreshList;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.Utils.Utils;

/**
 * Created by Akash.Singh on 1/9/2016.
 */
public class SearchResultFragment extends Fragment implements IRefreshList, JobItemAdapter.ApplyJobListener
        , IAsyncTaskRunner , View.OnClickListener {
    private ArrayList<Job> jobArrayList ;
    private JobItemAdapter jobItemAdapter;
    private RecyclerView mJobsRecyclerView;
    private BaseActivityScreen activityHandle;
    private LoadingDialog loadingDialog;
    private String mLocation , mKeyword ;
    private AppCompatButton mFilterButton ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_search_result_screen, container, false);
        ((BaseActivityScreen) getActivity()).setToolbarInitialization(this);
        if(jobArrayList == null){
            jobArrayList = (ArrayList<Job>) getArguments().getSerializable("Job");
            jobArrayList.add(null);
            mLocation = getArguments().getString(BaseNetwork.LOCATION);
            mKeyword = getArguments().getString(BaseNetwork.KEYWORD);
        }
        mJobsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mFilterButton = (AppCompatButton) view.findViewById(R.id.btn_filter);
        mFilterButton.setOnClickListener(this);
        CreateJob();
        return view;
    }

    private void CreateJob() {
        mJobsRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mJobsRecyclerView.setLayoutManager(mLayoutManager);
        jobItemAdapter = new JobItemAdapter(getActivity(), jobArrayList, this, this, true,mJobsRecyclerView);
        mJobsRecyclerView.setAdapter(jobItemAdapter);
        jobItemAdapter.notifyDataSetChanged();

        jobItemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //Log.e("haint", "Load More");
               // jobArrayList.add(null);
                //jobItemAdapter.notifyItemInserted(jobArrayList.size() - 1);
                pageCount++ ;
                //Load data
                PerformSearchProcess();
            }
        });

    }
    int pageCount = 1 ;

    public void PerformSearchProcess() {
     //   loadingDialog =  new LoadingDialog(getActivity());
     //   loadingDialog.show();
     //   loadingDialog.SetTitleMessage("Loading...");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(BaseNetwork.KEYWORD, mKeyword);
        hashMap.put(BaseNetwork.LOCATION, mLocation);
        hashMap.put(BaseNetwork.PAGE, String.valueOf(pageCount));
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.SEARCHJOBBYKEYWORD,this,loadingDialog);
        authCommonTask.execute(hashMap);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_filter :
                activityHandle.onReplaceFragment(new FilterFragment(), true);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activityHandle = (BaseActivityScreen) context;
        }
    }

    @Override
    public void jobApplied(Job job) {

        if (job.getType() != null && job.getType().equalsIgnoreCase(StaticConstant.JOB_OTHER)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(job.getJobUrl()));
            startActivity(i);
        } else {
            if (activityHandle.getmUserInfo() != null) {
                performApplyJob(job);
            } else {
                SignInScreen signInScreen = new SignInScreen();
                Bundle bundle = new Bundle();
                bundle.putString("activity", "JobApply");
                signInScreen.setArguments(bundle);
                activityHandle.onReplaceFragment(signInScreen, true);
            }
        }
        //  Toast.makeText(getContext(), "Item lcicke position:"+job.getCompany() , Toast.LENGTH_SHORT).show();
    }

    public void performApplyJob(Job job) {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.applying));
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(BaseNetwork.USER_ID_PARAMETER, activityHandle.getmUserInfo().getUserId());
        hashMap.put(BaseNetwork.JOBID, job.getJob_id());
        hashMap.put(BaseNetwork.EMPID, job.getEmp_id());
        hashMap.put(BaseNetwork.DATETIME, Utils.getCurrentDateTime());
        AuthCommonTask authCommonTask = new AuthCommonTask(getActivity(), BaseNetwork.APPLYJOB, this, loadingDialog);
        authCommonTask.execute(hashMap);
    }

    @Override
    public void taskCompleted(Object obj) {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if (obj != null) {
            ResultMessage resultMessage = (ResultMessage) obj;
            Response response = (Response) resultMessage.RESULT_OBJECT;
           // if for apply job
            if(resultMessage.TYPE.equalsIgnoreCase(BaseNetwork.APPLYJOB)){
                if (!TextUtils.isEmpty(response.getMessage())) {
                    MessageDialog messageDialog = new MessageDialog(getActivity());
                    messageDialog.show();
                    messageDialog.setTitle(getString(R.string.app_name));
                    messageDialog.setMessageContent(response.getMessage());
                }
            }else{
                jobArrayList.remove(jobArrayList.size() - 1);
                jobItemAdapter.notifyItemRemoved(jobArrayList.size());
                jobItemAdapter.notifyItemRangeChanged(jobArrayList.size(),jobItemAdapter.getItemCount());

              //  jobItemAdapter.notifyItemInserted(jobArrayList.size() - 1);
                if(response.getJobArrayList().size() < 10){
                    jobArrayList.addAll(response.getJobArrayList());
                    jobItemAdapter.notifyDataSetChanged();
                }else {
                    jobArrayList.addAll(response.getJobArrayList());
                    jobArrayList.add(null);
                    jobItemAdapter.notifyDataSetChanged();
                    jobItemAdapter.setLoaded();
                }

                //jobItemAdapter.setLoaded();
                //loading = false ;
            }
        }

    }

    @Override
    public void taskErrorMessage(Object obj) {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if (obj != null) {
            ResultMessage resultMessage = (ResultMessage) obj;
            if (!TextUtils.isEmpty(resultMessage.ERRORMESSAGE)) {
                MessageDialog messageDialog = new MessageDialog(getActivity());
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
        if (job.getType() != null && job.getType().equalsIgnoreCase(StaticConstant.JOB_OTHER)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(job.getJobUrl()));
            startActivity(i);
        } else {
            JobDetailFragment jobDetailFragment = new JobDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("JobDetail", job);
            jobDetailFragment.setArguments(bundle);
            ((BaseActivityScreen) getActivity()).onReplaceFragment(jobDetailFragment, true);
        }

    }


}
