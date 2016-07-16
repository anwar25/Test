package in.eweblabs.careeradvance.Account;

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
import java.util.HashMap;
import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.Utils.Utils;

/**
 * Created by Akash.singh on 12/29/2015.
 */
public class ForgetPassword extends Fragment implements IAsyncTaskRunner {
    EditText edit_email_address;
    TextInputLayout input_email_address;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_forget_password_screen,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        WidgetMapping(view);
        return view;
    }

    private void WidgetMapping(View view) {
        edit_email_address = (EditText) view.findViewById(R.id.edit_email_address);
        input_email_address = (TextInputLayout) view.findViewById(R.id.input_email_address);


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


        ((AppCompatButton)view.findViewById(R.id.btnForget)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edit_email_address.getText().toString())) {
                    input_email_address.setError(getString(R.string.hint_please_enter_your_email));
                    return;
                }
                if (!Utils.isEmailValid(edit_email_address.getText().toString())) {
                    input_email_address.setError(getString(R.string.hint_please_enter_valid_email));
                } else {
                    PerformForgetPasswordProcess();
                }

            }
        });
    }

    LoadingDialog loadingDialog;
    public void PerformForgetPasswordProcess() {
        loadingDialog =  new LoadingDialog(getActivity());
        loadingDialog.show();
        loadingDialog.SetTitleMessage("Forget Password, Processing...");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseNetwork.USER_EMAIL_PARAMETER, edit_email_address.getText().toString());
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.FORGET_PASSWORD_METHOD,this,loadingDialog);
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
    public void taskProgress(Object obj) {

    }

    @Override
    public void taskStarting() {

    }

    @Override
    public void onCanceled() {

    }

}
