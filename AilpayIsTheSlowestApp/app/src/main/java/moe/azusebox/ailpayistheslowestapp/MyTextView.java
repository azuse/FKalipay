package moe.azusebox.ailpayistheslowestapp;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int mLastx = -1;

    int mLasty = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mLasty != -1) {
                    RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)this.getLayoutParams();
                    linearParams.leftMargin +=(x-mLastx);
                    linearParams.topMargin +=(y-mLasty);
                    this.setLayoutParams(linearParams);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastx = x;
        mLasty = y;
        return true;
    }
}