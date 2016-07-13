package in.eweblabs.careeradvance.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
public class MessageDialog extends Dialog {

    Context context;
    TextView title,message;


    public MessageDialog(Context context) {
        super(context);
        this.context = context;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setWindowAnimations(R.style.dialog_animation_bounce);
        setContentView(R.layout.message_dialog);

        title = (TextView) findViewById(R.id.title);
        message = (TextView) findViewById(R.id.message);

        setCancelable(false);
        setCanceledOnTouchOutside(true);
        ((Button)findViewById(R.id.btnClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    public void setTitle(String titleValue) {
        title.setText(titleValue);

    }

    public void setMessageContent(String webViewContent) {
        message.setText(webViewContent);

    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }
}
