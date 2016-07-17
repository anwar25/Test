package in.eweblabs.careeradvance.Account;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.Search.AppliedJobFragment;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;

/**
 * Created by Akash.Singh on 11/17/2015.
 */
public class SignInScreen extends Fragment implements IAsyncTaskRunner{
    EditText edit_email_address,edit_password;
    TextInputLayout input_email_address,input_password;
    private BaseActivityScreen activityHandle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_sign_in_screen,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        WidgetMapping(view);
        return view;
    }

    private void WidgetMapping(View view) {
        edit_email_address = (EditText) view.findViewById(R.id.edit_email_address);
        edit_password = (EditText) view.findViewById(R.id.edit_password);
        input_email_address = (TextInputLayout) view.findViewById(R.id.input_email_address);
        input_password = (TextInputLayout) view.findViewById(R.id.input_password);


        edit_email_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null && s.length() > 0) {
                    input_email_address.setError(null);
                    input_email_address.setErrorEnabled(false);
                }
            }
        });

        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null && s.length() > 0) {
                    input_password.setError(null);
                    input_password.setErrorEnabled(false);
                }
            }
        });

        ((AppCompatButton)view.findViewById(R.id.btnSignin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edit_email_address.getText())) {
                    input_email_address.setError(getString(R.string.hint_please_enter_your_email));
                    return;
                } else if (TextUtils.isEmpty(edit_password.getText())) {
                    input_password.setError(getString(R.string.hint_please_enter_your_password));
                    return;
                } else {
                    if(TextUtils.isEmpty(activityHandle.getSessionManager().getString(BaseNetwork.DEVICE_TOKEN))){
                        Toast.makeText(activityHandle, getString(R.string.failed_to_generate_token), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PerformSignInProcess();
                }

            }
        });
        ((LinearLayout)view.findViewById(R.id.ll_create_account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivityScreen) getActivity()).onReplaceFragment(new SignUpScreen(), true);
            }
        });

        ((TextView)view.findViewById(R.id.text_forget_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivityScreen) getActivity()).onReplaceFragment(new ForgetPassword(), true);
            }
        });

     }

    LoadingDialog loadingDialog;
    public void PerformSignInProcess() {
        loadingDialog =  new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage(getString(R.string.sign_in_processing));
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(BaseNetwork.USER_EMAIL_PARAMETER, edit_email_address.getText().toString());
        hashMap.put(BaseNetwork.USER_PASSWORD_PARAMETER, edit_password.getText().toString());
        hashMap.put(BaseNetwork.USER_F_NAME_PARAMETER, "");
        hashMap.put(BaseNetwork.USER_M_NAME_PARAMETER, "");
        hashMap.put(BaseNetwork.USER_L_NAME_PARAMETER, "");
        hashMap.put(BaseNetwork.AUTH_ID, "");
        hashMap.put(BaseNetwork.DEVICE_TOKEN,  activityHandle.getSessionManager().getString(BaseNetwork.DEVICE_TOKEN));
        hashMap.put(BaseNetwork.DEVICE_TYPE, StaticConstant.DEVICE_ANDROID);
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.LOGIN_METHOD,this,loadingDialog);
        authCommonTask.execute(hashMap);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activityHandle = (BaseActivityScreen) context;
        }
    }

    @Override
    public void taskCompleted(Object obj) {
        if(loadingDialog!=null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(obj!=null){
            ResultMessage resultMessage = (ResultMessage) obj;
            UserInfo userInfo = (UserInfo) resultMessage.RESULT_OBJECT;
            if(!TextUtils.isEmpty(userInfo.getUserEmail())){
                //SAVE USER INFO START
                ApplicationController.getInstance().setUserInfo(userInfo);
                activityHandle.getSessionManager().putString(StaticConstant.USER_INFO,resultMessage.RESPONSE);
                activityHandle.getSessionManager().putBoolean(StaticConstant.IS_LOGGED_IN , true);
                activityHandle.setmUserInfo(userInfo);
                //Save resume path
                activityHandle.getSessionManager().putString(StaticConstant.USER_RESUME_PATH,userInfo.getUserResumePath());
                //SAVE USER INFO END
                if(((String)getArguments().getString("activity")).equalsIgnoreCase(StaticConstant.SIGN_IN)){
                    getActivity().getSupportFragmentManager().popBackStack();
                    ((BaseActivityScreen) getActivity()).onReplaceFragment(new ProfileScreen(), true);
                }else if(((String)getArguments().getString("activity")).equalsIgnoreCase("JobApply")){
                    getActivity().getSupportFragmentManager().popBackStack();
                }else if(((String)getArguments().getString("activity")).equalsIgnoreCase(StaticConstant.APPLIED_JOB)){
                    ((BaseActivityScreen) getActivity()).onReplaceFragment(new AppliedJobFragment(), true);
                }

            }
            else if(!TextUtils.isEmpty(userInfo.getMessage())){
                MessageDialog messageDialog =  new MessageDialog(getActivity());
                messageDialog.show();
                messageDialog.setTitle(getString(R.string.app_name));
                messageDialog.setMessageContent(userInfo.getMessage());
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
