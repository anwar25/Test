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
import android.widget.Button;
import android.widget.EditText;
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
import in.eweblabs.careeradvance.UI.LoadingDialog;
import in.eweblabs.careeradvance.UI.MessageDialog;
import in.eweblabs.careeradvance.UI.WebviewMessageDialog;

/**
 * Created by Akash.Singh on 11/17/2015.
 */
public class SignUpScreen extends Fragment implements IAsyncTaskRunner {
    EditText edit_first_name,edit_last_name,edit_email_address,edit_password;
    TextInputLayout input_first_name,input_last_name,input_email_address,input_password;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_sign_up_screen,container,false);
        ((BaseActivityScreen)getActivity()).SetToolbarInitialization(this);
        WidgetMapping(view);
        return view;
    }

    private void WidgetMapping(View view) {
        SpanningMethod(view);
        edit_email_address = (EditText) view.findViewById(R.id.edit_email_address);
        edit_password = (EditText) view.findViewById(R.id.edit_password);
        input_email_address = (TextInputLayout) view.findViewById(R.id.input_email_address);
        input_password = (TextInputLayout) view.findViewById(R.id.input_password);
        edit_first_name = (EditText) view.findViewById(R.id.edit_first_name);
        input_first_name = (TextInputLayout) view.findViewById(R.id.input_first_name);
        edit_last_name = (EditText) view.findViewById(R.id.edit_last_name);
        input_last_name = (TextInputLayout) view.findViewById(R.id.input_last_name);

        edit_first_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null && s.length() > 0) {
                    input_first_name.setError(null);
                    input_first_name.setErrorEnabled(false);
                }
            }
        });

        edit_last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null && s.length() > 0) {
                    input_last_name.setError(null);
                    input_last_name.setErrorEnabled(false);
                }
            }
        });

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
                if (s != null && s.length() > 0) {
                    input_password.setError(null);
                    input_password.setErrorEnabled(false);
                }
            }
        });

    }
    WebviewMessageDialog webviewMessageDialog =null;
    WebviewMessageDialog webviewProfileMessageDialog =null;
    private void SpanningMethod(View view) {
        // this is the text that we going to work on
        TextView term_and_condition = (TextView)view.findViewById(R.id.term_and_condition);
        SpannableString text = new SpannableString("By creating an account, you agree to Career Advance T&C and consent of our policy");
        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hotel_checkout_deactivated_tab_color)), text.length() - 18, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(fcs, text.length() - 6, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(bss,text.length() - 6, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {

                if(webviewProfileMessageDialog==null)
                    webviewProfileMessageDialog =new WebviewMessageDialog(getActivity());
                if(webviewProfileMessageDialog!=null && !webviewProfileMessageDialog.isShowing())
                {

                    webviewProfileMessageDialog.show();
                    webviewProfileMessageDialog.setTitle(getString(R.string.privacy_policy));
                    webviewProfileMessageDialog.setWebViewContent("file:///android_asset/privacy_policy.html");
                }

            } },  text.length() - 6,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final ForegroundColorSpan fcs1 = new ForegroundColorSpan(Color.BLUE);
        final StyleSpan bss1 = new StyleSpan(android.graphics.Typeface.BOLD);
        // text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hotel_checkout_deactivated_tab_color)), text.length() - 18, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(fcs1,text.length() - 44,text.length() -25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(bss1,text.length() - 44,text.length() - 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {
                if(webviewMessageDialog==null)
                        webviewMessageDialog =new WebviewMessageDialog(getActivity());
                if(webviewMessageDialog!=null && !webviewMessageDialog.isShowing())
                {
                    webviewMessageDialog.show();
                    webviewMessageDialog.setTitle(getString(R.string.term_and_condition));
                    webviewMessageDialog.setWebViewContent("file:///android_asset/term_and_condition.html");
                }



            } }, text.length() - 44,text.length() - 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        term_and_condition.setHighlightColor(getResources().getColor(android.R.color.transparent));
        term_and_condition.setText(text);
        term_and_condition.setMovementMethod(LinkMovementMethod.getInstance());

        ((AppCompatButton)view.findViewById(R.id.btnSignup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edit_email_address.getText())) {
                    input_email_address.setError(getString(R.string.hint_please_enter_your_email));
                    return;
                } else if (TextUtils.isEmpty(edit_password.getText())) {
                    input_password.setError(getString(R.string.hint_please_enter_your_password));
                    return;
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
        loadingDialog.SetTitleMessage("Sign In, Processing...");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseNetwork.USER_EMAIL_PARAMETER, edit_email_address.getText().toString());
        hashMap.put(BaseNetwork.USER_PASSWORD_PARAMETER, edit_password.getText().toString());
        hashMap.put(BaseNetwork.USER_F_NAME_PARAMETER, edit_first_name.getText().toString());
        hashMap.put(BaseNetwork.USER_L_NAME_PARAMETER, edit_last_name.getText().toString());
        AuthCommonTask authCommonTask =  new AuthCommonTask(getActivity(),BaseNetwork.ADD_USERS,this,loadingDialog);
        authCommonTask.execute(hashMap);
    }


    @Override
    public void taskCompleted(Object obj) {
        if(loadingDialog!=null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if(obj!=null){
            ResultMessage resultMessage = (ResultMessage) obj;
            UserInfo userInfo = (UserInfo) resultMessage.RESULT_OBJECT;
            if(!TextUtils.isEmpty(userInfo.getMessage())){
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
