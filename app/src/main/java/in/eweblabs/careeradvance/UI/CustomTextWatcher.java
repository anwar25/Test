package in.eweblabs.careeradvance.UI;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Anil on 12/4/2015.
 */
public class CustomTextWatcher implements TextWatcher {
    TextInputLayout textInputLayout ;
    public CustomTextWatcher(TextInputLayout textInputLayout){
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textInputLayout!=null && s!=null && s.length() > 0) {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        }
    }
}