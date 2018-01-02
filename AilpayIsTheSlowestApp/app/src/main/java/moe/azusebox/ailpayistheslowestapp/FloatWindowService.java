package moe.azusebox.ailpayistheslowestapp;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class FloatWindowService extends Service {


    private final String TAG = "FloatWindowService";

    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private FloatWindowView mFloatView;

    private MainActivity mainActivity;

    @Override
    public void onCreate() {

        super.onCreate();
        Log.i(TAG, "onCreate");
        //
        initWindowParams();
        initView();
        mWindowManager.addView(mFloatView, wmParams);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return new ShowHomePageBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatView != null) {
            //移除悬浮窗口
            Log.i(TAG, "removeView");
            mWindowManager.removeView(mFloatView);
        }
        Log.i(TAG, "onDestroy");
    }

    private void initView() {
//        mFloatView = LayoutInflater.from(getApplication()).inflate(R.layout.floatwindow_layout, null);
        mFloatView = new FloatWindowView(getApplicationContext());
        mFloatView.setParams(wmParams);
        mFloatView.setMoveThreshold(5);
        mFloatView.setClickable(true);
        mFloatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click float window....");
                if (mainActivity == null) {
                    Log.d(TAG, "main activity is null");
                    return;
                }
                Log.d(TAG, "tabhost setCurrentTab to home page");
//                mainActivity.onKeyDown(MainActivity.KEYCODE_F0, new KeyEvent(KeyEvent.ACTION_DOWN, MainActivity.KEYCODE_F0));
            }
        });
    }

    /**
     * 初始化 LayoutParams
     */
    private void initWindowParams() {
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.CENTER| Gravity.CENTER;
        wmParams.y = 150;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.alpha = 1;
    }

    public void setMainActivity(MainActivity activity) {
        mainActivity = activity;
    }

    public class ShowHomePageBinder extends Binder
    {
        public FloatWindowService getFloatWindowService()
        {
            return FloatWindowService.this;
        }
    }
}