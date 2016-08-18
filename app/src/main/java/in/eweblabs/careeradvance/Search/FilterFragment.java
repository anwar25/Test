package in.eweblabs.careeradvance.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;

import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.UI.TextThumbSeekBar;

/**
 * Created by Anwar on 8/16/2016.
 */
public class FilterFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener,
        IAsyncTaskRunner {

    private EditText mFilterEditText;
    private AppCompatButton mUpdateButton;
    private TextThumbSeekBar mSeekBar;
    private TextView mSeekbarProgressTextView;
    private String mLocation;
    private String mKeyword;
    private String mRadius;
    private String mSortBy;
    private String mJobType;

    private RadioGroup mFirstGroupRadio ;
    private RadioGroup mSecondGroupRadio ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment_layout, container, false);
        ((BaseActivityScreen) getActivity()).setToolbarInitialization(this);

        mFilterEditText = (EditText) view.findViewById(R.id.filterEditText);
        if (getArguments() != null) {
            mFilterEditText.setText(getArguments().getString(StaticConstant.FILTER_TEXT, ""));
            mLocation = getArguments().getString(StaticConstant.LOCATION,"");
            mKeyword = getArguments().getString(StaticConstant.KEYOWRDS,"");
        }
        mSeekbarProgressTextView = (TextView) view.findViewById(R.id.seekBarProgressText);
        mSeekbarProgressTextView.setText(getString(R.string.within_radius, 10));
        mUpdateButton = (AppCompatButton) view.findViewById(R.id.btn_update);
        mUpdateButton.setOnClickListener(this);
        mSeekBar = (TextThumbSeekBar) view.findViewById(R.id.seekBar);
        mSeekBar.initialize(10, 50, "Km", ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mSeekBar.setOnSeekBarChangeListener(this);
        mFirstGroupRadio = (RadioGroup) view.findViewById(R.id.firstRadioGroup);
        mSecondGroupRadio = (RadioGroup) view.findViewById(R.id.secondRadioGroup);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                performSearchProcess();
                break;

        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mSeekbarProgressTextView.setText(getString(R.string.within_radius, (progress + 1) * 10));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //   mSeekbarProgressTextView.setText(getString(R.string.within_radius, progress));
    }

    LoadingDialog loadingDialog;

    public void performSearchProcess() {

        mRadius = String.valueOf(mSeekBar.getCurrentValue());
        int firstGroupId = mFirstGroupRadio.getCheckedRadioButtonId() ;
        if(firstGroupId == R.id.sortByRelevanceRadio){
            mSortBy = StaticConstant.RELEVANCE;
        }else{
            mSortBy = StaticConstant.DATE  ;
        }
        int secondGroupId = mSecondGroupRadio.getCheckedRadioButtonId() ;
        if(secondGroupId == R.id.allJobTypesRadio){
            mJobType = StaticConstant.JOB_TYPE_ALL;
        }else if(secondGroupId == R.id.fullTimeRadio ){
            mJobType = StaticConstant.JOB_TYPE_FULLTIME ;
        }else if(secondGroupId == R.id.partTimeRadio){
            mJobType = StaticConstant.JOB_TYPE_PARTTIME;
        }else if(secondGroupId == R.id.fresherRadio){
            mJobType = StaticConstant.JOB_TYPE_FRESHER;
        }else if(secondGroupId == R.id.internshipRadio){
            mJobType = StaticConstant.JOB_TYPE_INTERNSHIP;
        }else if(secondGroupId == R.id.walkinRadio){
            mJobType = StaticConstant.JOB_TYPE_WALKIN;
        }

        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.updating));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseNetwork.KEYWORD, mKeyword);
        hashMap.put(BaseNetwork.LOCATION, mLocation);
        hashMap.put(BaseNetwork.PAGE, "1");
        hashMap.put(BaseNetwork.RADIUS,mRadius);
        hashMap.put(BaseNetwork.SORT_BY, mSortBy);
        hashMap.put(BaseNetwork.JOB_TYPE,mJobType);
        AuthCommonTask authCommonTask = new AuthCommonTask(getActivity(), BaseNetwork.SEARCHJOBBYKEYWORD, this, loadingDialog);
        authCommonTask.execute(hashMap);
    }


    @Override
    public void taskCompleted(Object obj) {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if (obj != null) {
            ResultMessage resultMessage = (ResultMessage) obj;
            Response response = (Response) resultMessage.RESULT_OBJECT;
            if (response.getJobArrayList().size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putString(BaseNetwork.KEYWORD,mKeyword);
                bundle.putString(BaseNetwork.LOCATION,mLocation);
                bundle.putString(BaseNetwork.PAGE, "1");
                bundle.putString(BaseNetwork.RADIUS,mRadius);
                bundle.putString(BaseNetwork.SORT_BY, mSortBy);
                bundle.putString(BaseNetwork.JOB_TYPE,mJobType);
                //bundle.putInt(BaseNetwork.RADIUS,);
                bundle.putSerializable("Job", response.getJobArrayList());
                ((BaseActivityScreen) getActivity()).setmFilterBundle(bundle);
                ((BaseActivityScreen) getActivity()).onBackPressed();
           //     ((BaseActivityScreen) getActivity()).onReplaceFragment(searchResultFragment, true);
            } else if (!TextUtils.isEmpty(response.getMessage())) {
                MessageDialog messageDialog = new MessageDialog(getActivity());
                messageDialog.show();
                messageDialog.setTitle(getString(R.string.app_name));
                messageDialog.setMessageContent(response.getMessage());
            }
        }

    }

    @Override
    public void taskErrorMessage(Object obj) {

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
