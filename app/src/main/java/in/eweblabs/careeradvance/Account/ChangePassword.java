package in.eweblabs.careeradvance.Account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.UI.CustomTextWatcher;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.UI.WebviewMessageDialog;

/**
 * Created by Akash.Singh on 11/17/2015.
 */
public class ChangePassword extends Fragment implements IAsyncTaskRunner {
    EditText edit_old_password,edit_new_password,edit_conform_password;
    TextInputLayout input_old_password,input_new_password,input_conform_password;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_change_password_screen,container,false);
        ((BaseActivityScreen)getActivity()).SetToolbarInitialization(this);
        WidgetMapping(view);
        return view;
    }

    private void WidgetMapping(View view) {
        edit_old_password = (EditText) view.findViewById(R.id.edit_old_password);
        edit_new_password = (EditText) view.findViewById(R.id.edit_new_password);
        edit_conform_password = (EditText) view.findViewById(R.id.edit_conform_password);
        input_old_password = (TextInputLayout) view.findViewById(R.id.input_old_password);
        input_new_password = (TextInputLayout) view.findViewById(R.id.input_new_password);
        input_conform_password = (TextInputLayout) view.findViewById(R.id.input_conform_password);
        edit_old_password.addTextChangedListener(new CustomTextWatcher(input_old_password));
        edit_new_password.addTextChangedListener(new CustomTextWatcher(input_new_password));
        edit_conform_password.addTextChangedListener(new CustomTextWatcher(input_conform_password));

        ((AppCompatButton)view.findViewById(R.id.btnChangePassword)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edit_old_password.getText())) {
                    input_old_password.setError(getString(R.string.hint_please_enter_your_old_password));
                    return;
                } else if (TextUtils.isEmpty(edit_new_password.getText())) {
                    input_new_password.setError(getString(R.string.hint_please_enter_your_new_password));
                    return;
                } else if (TextUtils.isEmpty(edit_conform_password.getText())) {
                    input_conform_password.setError(getString(R.string.hint_please_enter_your_conform_password));
                    return;
                } else if (!edit_new_password.getText().toString().equalsIgnoreCase(edit_conform_password.getText().toString())) {
                    input_conform_password.setError(getString(R.string.hint_password_are_not_matching));
                } else {
                    PerformSignUpProcess();
                }

            }
        });



    }

    LoadingDialog loadingDialog;
    public void PerformSignUpProcess() {
        loadingDialog =  new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage("Change Password, Processing...");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseNetwork.USER_ID_PARAMETER, ApplicationController.getInstance().getUserInfo().getUserId());
        hashMap.put(BaseNetwork.OLD_PASSWORD, edit_old_password.getText().toString());
        hashMap.put(BaseNetwork.NEW_PASSWORD, edit_new_password.getText().toString());
        hashMap.put(BaseNetwork.CONFIRM_PASSWORD, edit_conform_password.getText().toString());
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.CHANGE_PASSWORD_METHOD,this,loadingDialog);
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
