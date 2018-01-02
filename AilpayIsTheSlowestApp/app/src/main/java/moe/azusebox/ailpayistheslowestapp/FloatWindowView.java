package moe.azusebox.ailpayistheslowestapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FloatWindowView extends LinearLayout {
    /**
     * 记录悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 用于更新悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    /**
     * 点击监听器
     */
    private OnClickListener mClickLinstener;

    /**
     * 设置点击响应灵敏度
     */
    private int threshold = 30;

    public FloatWindowView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.floatwindow_layout, this);
        View view = findViewById(R.id.float_window_layout);
//        TextView goHomeView = (TextView)findViewById(R.id.gohome);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
//                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (needIntercept()) {
                    return true;
                } else {
                    mClickLinstener.onClick(this);
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 将悬浮窗的参数传入，用于更新悬浮窗的位置。
     *
     * @param params
     *            悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.gravity = Gravity.LEFT| Gravity.TOP;
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 是否拦截
     * @return true:拦截;false:不拦截.
     */
    private boolean needIntercept() {
        if (Math.abs(xDownInScreen - xInScreen) > threshold || Math.abs(yDownInScreen - yInScreen) > threshold) {
            return true;
        }
        return false;
    }

    public void setMoveThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mClickLinstener = l;
    }
}