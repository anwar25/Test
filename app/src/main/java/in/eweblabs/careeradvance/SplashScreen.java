package in.eweblabs.careeradvance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.HashMap;

import in.eweblabs.careeradvance.AsyncTask.AuthCommonTask;
import in.eweblabs.careeradvance.Entity.ResultMessage;
import in.eweblabs.careeradvance.Interface.IAsyncTaskRunner;
import in.eweblabs.careeradvance.Interface.ITimeCount;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.TimeHandler.TimeCount;

/**
* Created by Akash.Singh on 11/9/2015.
*/
public class SplashScreen extends AppCompatActivity implements ITimeCount,IAsyncTaskRunner {
    TimeCount timeCount;
    long SleepTime = 3000;
    Boolean SplashScreenAnimation = false;
    Boolean SplashScreenFetchData = false;
    ProgressBar progressBar;

    private ImageView mLogoImage ;

    @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLogoImage = (ImageView) findViewById(R.id.logoImageView);
        PerformProcessProcess();
        timeCount =  new TimeCount(SleepTime,1000);
        timeCount.setiTimeCount(SplashScreen.this);
        timeCount.start();


    }

    private void moveViewToScreenCenter( View view ){
        RelativeLayout root = (RelativeLayout) findViewById( R.id.splashScreenLayout );
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        final int originalPos[] = new int[2];
        view.getLocationOnScreen( originalPos );

        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        final int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

        TranslateAnimation anim = new TranslateAnimation( 0, xDest - originalPos[0] , 0, yDest - originalPos[1] );
        anim.setDuration(1000);
        anim.setFillAfter( true );
        final int finalXDest = xDest;
       /* anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              //  mLogoImage.setVisibility(View.INVISIBLE);
               // mLogoImage.layout( 0, finalXDest - originalPos[0] , 0, yDest - originalPos[1]);
                //Logger.v("animation","on animations end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        view.startAnimation(anim);
    }

    private boolean isAnimationStarted = false ;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!isAnimationStarted){
            moveViewToScreenCenter(mLogoImage);
            isAnimationStarted = true ;
        }
    }

    @Override
    public void OnTickListener(long timetick) {

    }

    @Override
    public void OnFinish() {
              SplashScreenAnimation = true;
                    if(SplashScreenAnimation==true && SplashScreenFetchData==true)
                    {
                        SplashScreenAnimation =false;
                        Intent i = new Intent(getApplicationContext(), BaseActivityScreen.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                    else
                        progressBar.setVisibility(View.VISIBLE);
                }


    public void PerformProcessProcess() {
        if(SplashScreenAnimation)
            progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        AuthCommonTask authCommonTask =  new AuthCommonTask(SplashScreen.this,this, BaseNetwork.JOBKEYWORDS);
        authCommonTask.execute(hashMap);
    }

    public void PerformLocationProcess() {
        if(SplashScreenAnimation)
            progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> hashMap = new HashMap<>();
        AuthCommonTask authCommonTask =  new AuthCommonTask(SplashScreen.this,this, BaseNetwork.JOBLOCATIONS);
        authCommonTask.execute(hashMap);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void taskCompleted(Object obj) {
        if(obj!=null)
        {
            ResultMessage resultMessage = (ResultMessage) obj;
            if(resultMessage.TYPE.equalsIgnoreCase(BaseNetwork.JOBKEYWORDS)){
                PerformLocationProcess();
            }
            else{
                SplashScreenFetchData = true;
                if(SplashScreenAnimation==true && SplashScreenFetchData==true){
                    progressBar.setVisibility(View.INVISIBLE);
                    SplashScreenFetchData = false;
                    Intent i = new Intent(getApplicationContext(), BaseActivityScreen.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }


        }

    }

    @Override
    public void taskErrorMessage(Object obj) {
        SplashScreenFetchData = true;
        if(SplashScreenAnimation==true && SplashScreenFetchData==true)
        {
            progressBar.setVisibility(View.INVISIBLE);
            SplashScreenFetchData = false;
            Intent i = new Intent(getApplicationContext(), BaseActivityScreen.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public void taskProgress(Object obj) {
        if(SplashScreenAnimation)
            progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void taskStarting() {

    }

    @Override
    public void onCanceled() {

    }
}
