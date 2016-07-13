package in.eweblabs.careeradvance.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import in.eweblabs.careeradvance.R;

/**
 * Created by Tanuj.Sareen on 9/8/2015.
 */
public class LoadingDialog extends Dialog {


  /*  ProgressBar customProgressBar;*/

    Context context;
    public LoadingDialog(Context context) {
        super(context);
        this.context = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.transparent_progress_layout);
        // MapWidgetIds();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void SetTitleMessage(String MessageTitle){
        ((TextView)findViewById(R.id.customProgressBar_message)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.customProgressBar_message)).setText(MessageTitle);
    }

    /*private void MapWidgetIds() {
        customProgressBar = (ProgressBar) findViewById(R.id.customProgressBar);
    }*/


}
