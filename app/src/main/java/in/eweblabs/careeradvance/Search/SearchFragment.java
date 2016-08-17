package in.eweblabs.careeradvance.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import in.eweblabs.careeradvance.Account.ProfileScreen;
import in.eweblabs.careeradvance.Account.SignInScreen;
import in.eweblabs.careeradvance.Adapter.JobSugestionAdapter;
import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.RecentSearch;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;

/**
 * Created by Akash.Singh on 11/17/2015.
 */
public class SearchFragment extends Fragment implements IAsyncTaskRunner , RecentJobsAdapter.RecentJobListener{

    AutoCompleteTextView edit_job_title,edit_your_location;

    private JobSugestionAdapter mLocationSuggestionAdapter ;
    private JobSugestionAdapter mJobKeywordAdapter ;
    private RecyclerView mRecentJobSearchView;
    private TextView recentSearchHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_search_screen,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        WidgetMapping(view);
        return view;
    }

    private void WidgetMapping(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        edit_job_title = (AutoCompleteTextView) view.findViewById(R.id.edit_job_title);
        edit_your_location = (AutoCompleteTextView) view.findViewById(R.id.edit_your_location);
        recentSearchHeader = (TextView) view.findViewById(R.id.recentJobSearchHeader);
        SetJobKeywordAutoCompleteTextView();
        SetJobLocationAutoCompleteTextView();
        ((AppCompatButton)view.findViewById(R.id.btn_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PerformSearchProcess();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = ApplicationController.getInstance().getUserInfo();
                if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserEmail())) {
                    ((BaseActivityScreen) getActivity()).onReplaceFragment(new ProfileScreen(), true);
                } else {
                    SignInScreen signInScreen = new SignInScreen();
                    Bundle bundle = new Bundle();
                    bundle.putString("activity", StaticConstant.SIGN_IN);
                    signInScreen.setArguments(bundle);
                    ((BaseActivityScreen) getActivity()).onReplaceFragment(signInScreen, true);
                }
            }
        });
        mRecentJobSearchView = (RecyclerView) view.findViewById(R.id.recentSearchRecyclerView);
        ArrayList<RecentSearch> recentSearchArrayList = ApplicationController.getInstance().getCareerAdvanceDBData().getRecentSearchRecord();
        if(recentSearchArrayList.size() == 0){
            recentSearchHeader.setVisibility(View.GONE);
        }else{
            RecentJobsAdapter recentJobsAdapter = new RecentJobsAdapter(recentSearchArrayList,this);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecentJobSearchView.setLayoutManager(mLayoutManager);
            mRecentJobSearchView.addItemDecoration(new DividerItemDecoration(getActivity()));
            mRecentJobSearchView.setAdapter(recentJobsAdapter);
        }

        //recentJobsAdapter.notifyDataSetChanged();

    }

    private void SetJobKeywordAutoCompleteTextView() {
        /*ArrayList<Object> objectArrayList = ApplicationController.getInstance().getCareerAdvanceDBData().getKeywordRecord();
        String[] arr = new String[objectArrayList.size()];
        for (int i = 0; i < objectArrayList.size() ; i++) {
            arr[i] = (String) objectArrayList.get(i);
        }*/
        ArrayList<String> jobKeywordList = new ArrayList<String>();
        mJobKeywordAdapter = new JobSugestionAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,StaticConstant.KEYWORDS,jobKeywordList);
        edit_job_title.setThreshold(2);
        edit_job_title.setAdapter(mJobKeywordAdapter);

    }

    private void SetJobLocationAutoCompleteTextView() {
        ArrayList<String> jobKeywordList = new ArrayList<String>();
        mLocationSuggestionAdapter = new JobSugestionAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,StaticConstant.LOCATION,jobKeywordList);
        edit_your_location.setThreshold(2);
        edit_your_location.setAdapter(mLocationSuggestionAdapter);
    }

    @Override
    public void jobSearchClicked(RecentSearch recentSearch) {
        loadingDialog =  new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.job_search_processing));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseNetwork.KEYWORD, recentSearch.getKeyword());
        hashMap.put(BaseNetwork.LOCATION, recentSearch.getLocation());
        hashMap.put(BaseNetwork.PAGE, "1");
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.SEARCHJOBBYKEYWORD,this,loadingDialog);
        authCommonTask.execute(hashMap);
    }

    LoadingDialog loadingDialog;
    public void PerformSearchProcess() {
        loadingDialog =  new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.job_search_processing));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseNetwork.KEYWORD, edit_job_title.getText().toString());
        hashMap.put(BaseNetwork.LOCATION, edit_your_location.getText().toString());
        hashMap.put(BaseNetwork.PAGE, "1");
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.SEARCHJOBBYKEYWORD,this,loadingDialog);
        authCommonTask.execute(hashMap);
    }


    @Override
    public void taskCompleted(Object obj) {
        if(loadingDialog!=null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(obj!=null){
            ResultMessage resultMessage = (ResultMessage) obj;
            Response response = (Response) resultMessage.RESULT_OBJECT;
            RecentSearch recentSearch =  new RecentSearch();
            recentSearch.setLocation(edit_your_location.getText().toString());
            recentSearch.setKeyword(edit_job_title.getText().toString());
            ApplicationController.getInstance().getCareerAdvanceDBData().InsertRecentSearchLocation(recentSearch);
            if(response.getJobArrayList().size()>0){
                SearchResultFragment searchResultFragment =  new SearchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString(BaseNetwork.KEYWORD, String.valueOf(edit_job_title.getText()));
                bundle.putString(BaseNetwork.LOCATION, String.valueOf(edit_your_location.getText()));
                bundle.putSerializable("Job",response.getJobArrayList());
                searchResultFragment.setArguments(bundle);
                ((BaseActivityScreen) getActivity()).onReplaceFragment(searchResultFragment, true);
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


}
