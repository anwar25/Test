package in.eweblabs.careeradvance.Search;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import in.eweblabs.careeradvance.Adapter.AppliedJobItemAdapter;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.Network.models.AppliedJobModel;
import in.eweblabs.careeradvance.Network.models.Result;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;

/**
 * Created by Anwar Shaikh on 1/9/2016.
 */
public class AppliedJobFragment extends Fragment implements IAsyncTaskRunner, DialogInterface.OnDismissListener {
    ArrayList<Result> jobArrayList;
    AppliedJobItemAdapter jobItemAdapter;
    RecyclerView recycler_view;
    private BaseActivityScreen activityHandle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_search_result_screen, container, false);
        ((BaseActivityScreen) getActivity()).setToolbarInitialization(this);
        //jobArrayList = (ArrayList<Job>) getArguments().getSerializable("Job");
        getAppliedJob();
        jobArrayList = new ArrayList<Result>();
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        mapControls();
        return view;
    }

    private void mapControls() {
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(llm);
        jobItemAdapter = new AppliedJobItemAdapter(activityHandle, jobArrayList);
        recycler_view.setAdapter(jobItemAdapter);
        jobItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activityHandle = (BaseActivityScreen) context;
        }
    }

    LoadingDialog loadingDialog;

    public void getAppliedJob() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.processing_wait));
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(BaseNetwork.USER_ID_PARAMETER, activityHandle.getmUserInfo().getUserId());
        //   hashMap.put(BaseNetwork.EMPID,job.getEmp_id());
        //   hashMap.put(BaseNetwork.DATETIME, Utils.getCurrentDateTime());
        AuthCommonTask authCommonTask = new AuthCommonTask(getActivity(), BaseNetwork.MY_APPLIED_JOBS, this, loadingDialog);
        authCommonTask.execute(hashMap);
    }

    @Override
    public void taskCompleted(Object obj) {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if (obj != null) {
            ResultMessage resultMessage = (ResultMessage) obj;
            String response = resultMessage.RESPONSE;
            AppliedJobModel appliedJobModel = (new Gson()).fromJson(response, AppliedJobModel.class);

            //Log.v("AppliedJobFragment",""+appliedJobModel);
            if (appliedJobModel != null && appliedJobModel.getResults() != null && appliedJobModel.getResults().size() != 0) {
                jobArrayList.addAll(appliedJobModel.getResults());
                jobItemAdapter.notifyDataSetChanged();
                //sucess
            } else {
                // not appleid to anyjobs yet
                MessageDialog messageDialog = new MessageDialog(getActivity());
                messageDialog.show();
                messageDialog.setTitle(getString(R.string.app_name));
                messageDialog.setMessageContent("You havent applied to any jobs yet ");
                messageDialog.setOnDismissListener(this);
            }
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        getActivity().getSupportFragmentManager().popBackStack();
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

}
