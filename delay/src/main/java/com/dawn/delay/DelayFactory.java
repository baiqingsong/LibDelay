package com.dawn.delay;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class DelayFactory {
    private Context mContext;
    //单例模式
    private static DelayFactory mInstance;
    private DelayFactory(Context context){
        mContext = context;
    }
    public static DelayFactory getInstance(Context context){
        if(mInstance == null){
            synchronized (DelayFactory.class){
                if(mInstance == null){
                    mInstance = new DelayFactory(context);
                }
            }
        }
        return mInstance;
    }

    private OnDelayListener mListener;
    public void setListener(OnDelayListener listener){
        mListener = listener;
    }
    private static final int h_cycle_delay = 0x101;//循环查询网络延迟

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == h_cycle_delay){
                cycleDelay();
            }
        }
    };

    private int cycleTime = 15000;//循环时间
    private String requestUrl = "http://www.baidu.com";//请求地址

    /**
     * 循环查询网络延迟和信号强度
     */
    public void cycleDelay(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    int signal = DelayUtil.getWifiRssi(mContext);
                    String delay = DelayUtil.getDelay(requestUrl);
                    if(mListener != null){
                        mListener.onDelay(signal, delay);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
        mHandler.removeMessages(h_cycle_delay);
        mHandler.sendEmptyMessageDelayed(h_cycle_delay, cycleTime);
    }

    /**
     * 设置循环时间
     * @param cycleTime 循环时间
     */
    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    /**
     * 设置请求地址
     * @param requestUrl 请求地址
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
