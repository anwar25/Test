package in.eweblabs.careeradvance.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.SeekBar;

import in.eweblabs.careeradvance.R;


/**
 * Created by Anwar on 6/18/2016.
 */
public class TextThumbSeekBar extends SeekBar {

    private int mThumbSize;
    private TextPaint mTextPaint;
    private int step = 10;
    private int max;
    private int min;
    private int currentValue ;
    private Rect bounds ;
    private String mSuffix;
    private String progressText ;

    public TextThumbSeekBar(Context context) {
        this(context, null);
    }

    public TextThumbSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public TextThumbSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mThumbSize = getResources().getDimensionPixelSize(R.dimen.thumb_size);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.thumb_text_size));
        mTextPaint.setTypeface(Typeface.MONOSPACE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //setProgressDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.tab_blue_color)));

        bounds = new Rect();
    }

    public void initialize( int min , int max,String prefix ,  int colorId ){
        this.max = max ;
        this.min = min ;
        setMax( (max - min) / step );
        mSuffix = prefix ;
        getProgressDrawable().setColorFilter(colorId, PorterDuff.Mode.SRC_IN);
    }

    public String getProgressText() {
        return progressText;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        currentValue = min + (getProgress() * step);

        progressText =  String.valueOf(currentValue)+ mSuffix;

       // Rect bounds = new Rect();
        mTextPaint.getTextBounds(progressText, 0, progressText.length(), bounds);

        int leftPadding = getPaddingLeft() - getThumbOffset();
        int rightPadding = getPaddingRight() - getThumbOffset();
        int width = getWidth() - leftPadding - rightPadding;
        float progressRatio = (float) getProgress() / getMax();
        float thumbOffset = mThumbSize * (.5f - progressRatio);
        float thumbX = progressRatio * width + leftPadding + thumbOffset;
        float thumbY = getHeight() / 2f + bounds.height() / 2f;

      //  Log.d("anwar","x:"+thumbX);
      //  Log.d("anwar","y:"+thumbY);
        canvas.drawText(progressText, thumbX, thumbY, mTextPaint);
      //  canvas.drawText(progressText, thumbX, thumbY+40, mTextPaint);
    }
}