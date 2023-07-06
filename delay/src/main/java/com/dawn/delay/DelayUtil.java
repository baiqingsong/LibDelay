package com.dawn.delay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DelayUtil {
    /**
     * 获取WIFI强度
     */
    @SuppressLint("NewApi")
    public static int getWifiRssi(Context context){
        int level = Math.abs(((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getRssi());
//                LLog.i(MainActivity.this + " wifi state " + level);
        if(level < 0 || level > 100){
            return 0;
        }else{
            return 100 - level;
        }
    }
    /**
     * 获取延迟时间
     */
    public static String getDelay(String ip){
        String delay =new String();
        Process p =null;
        try{
            p = Runtime.getRuntime().exec("/system/bin/ping -c 4 "+ip);
            BufferedReader buf =new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = new String();
            while((str=buf.readLine())!=null){
                if(str.contains("avg")){
                    int i= str.indexOf("/",20);
                    int j= str.indexOf(".", i + 1);
//                    Log.i("dawn", "延迟：" + str.substring(i+1, j) + "    " + str);
                    delay = str.substring(i+1, j);
                }
            }
//            Log.e("dawn", "get delay : " + delay);
            return delay;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
