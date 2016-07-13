package in.eweblabs.careeradvance.TimeHandler;

import android.os.CountDownTimer;

import in.eweblabs.careeradvance.Interface.ITimeCount;

/**
 * Created by Anil on 11/9/2015.
 */
public class TimeCount extends CountDownTimer {

    public ITimeCount getiTimeCount() {
        return iTimeCount;
    }

    public void setiTimeCount(ITimeCount iTimeCount) {
        this.iTimeCount = iTimeCount;
    }

    ITimeCount iTimeCount;
    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        getiTimeCount().OnTickListener(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        getiTimeCount().OnFinish();
    }
}