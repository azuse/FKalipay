package moe.azusebox.ailpayistheslowestapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends Activity {
    private final String TAG = "FloatWindowService";
    private HomeListener mHomeWatcher;
    private FloatWindowService mFloatWindowService = null;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mFloatWindowService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected...");
            mFloatWindowService = ((FloatWindowService.ShowHomePageBinder)service).getFloatWindowService();
            if (mFloatWindowService != null) {
                mFloatWindowService.setMainActivity(MainActivity.this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floatwindow_layout);
        Uri uri=Uri.parse("alipayss://platformapi/startapp?appId=20000056");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
        Intent intent2 = new Intent(this, FloatWindowService.class);
        bindService(intent2, mConnection, Context.BIND_AUTO_CREATE);

        mHomeWatcher = new HomeListener(this);
        mHomeWatcher.setOnHomePressedListener(new HomeListener.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                System.exit(0);
            }
            @Override
            public void onHomeLongPressed() {

            }
        });
        mHomeWatcher.startWatch();
    }


}
