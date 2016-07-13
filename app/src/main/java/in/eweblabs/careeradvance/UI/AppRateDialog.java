package in.eweblabs.careeradvance.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import in.eweblabs.careeradvance.R;

/**
 * Created by Akash.Singh on 8/4/2015.
 */
public class AppRateDialog extends Dialog implements View.OnClickListener{

    Context context;
    SharedPreferences.Editor editor;
    public AppRateDialog(Context context,final SharedPreferences.Editor editor) {
        super(context);
        this.context = context;
        this.editor = editor;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setWindowAnimations(R.style.dialog_animation_bounce);
        setContentView(R.layout.apprater_dialog);


        TextView app_rate_text = (TextView) findViewById(R.id.app_rate_text);
        app_rate_text.setText(String.format(context.getString(R.string.app_rate_text), context.getString(R.string.app_name)));

        ((Button)findViewById(R.id.btnRateName)).setOnClickListener(this);
        ((Button)findViewById(R.id.btnRemindMeLater)).setOnClickListener(this);
        ((Button)findViewById(R.id.btnNoThanks)).setOnClickListener(this);

        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnRateName:
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(context.getString(R.string.app_play_store_link,context.getPackageName())))));
                dismiss();
                break;
            case R.id.btnRemindMeLater:
                dismiss();
                break;
            case R.id.btnNoThanks:
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dismiss();
                break;
        }

    }
}
