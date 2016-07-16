package in.eweblabs.careeradvance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

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

    @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        PerformProcessProcess();
        timeCount =  new TimeCount(SleepTime,1000);
        timeCount.setiTimeCount(SplashScreen.this);
        timeCount.start();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

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
