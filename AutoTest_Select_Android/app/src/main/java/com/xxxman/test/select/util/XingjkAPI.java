package com.xxxman.test.select.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.xxxman.test.select.object.Task;
import com.xxxman.test.select.process.V_5_0_7.S01_Quit;
import com.xxxman.test.select.process.V_5_0_7.S01_Register;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Drizzt on 2017-11-02.
 */

public class XingjkAPI {

    private static final String TAG = XingjkAPI.class.getName();

    public static String  loginIn() throws Exception{
        String token = null;
        //登录
        String url = "http://api.xingjk.cn/api/do.php?action=loginIn&name=api-6p9wdfxt&password=a13670752777";
        Connection my  = new Connection();
        Log.d(TAG,"请求地址为："+url);
        Map<String,String> map = new HashMap<String,String>();
        String rs = my.getContextByHttp(url,map);
        Log.d(TAG,"返回结果为："+rs);
        if("1|".equals(rs.substring(0,2))){
            String[] r = rs.split("\\|");
            token = r[1];
        }
        Log.d(TAG,"token："+token);
        return token;
    }
    public static String getPhone(String token) throws Exception{
        String phone = null;
        //登录
        String url = "http://api.xingjk.cn/api/do.php?action=getPhone&sid=7435&token="+token;
        Connection my  = new Connection();
        Log.d(TAG,"请求地址为："+url);
        Map<String,String> map = new HashMap<String,String>();
        String rs = my.getContextByHttp(url,map);
        Log.d(TAG,"返回结果为："+rs);
        if("1|".equals(rs.substring(0,2))){
            String[] r = rs.split("\\|");
            phone = r[1];
        }
        Log.d(TAG,"phone："+phone);
        return phone;
    }
    public static String getMessage(String phone,String token) throws Exception{
        String message = null;
        String url = "http://api.xingjk.cn/api/do.php?action=getMessage&sid=7435&phone="+phone+"&token="+token;
        Connection my  = new Connection();
        Log.d(TAG,"请求地址为："+url);
        Map<String,String> map = new HashMap<String,String>();
        String rs = my.getContextByHttp(url,map);
        Log.d(TAG,"返回结果为："+rs);
        if("1|".equals(rs.substring(0,2))){
            String[] r = rs.split("\\|");
            message = r[1];
            if("【花椒直播】".equals(message.substring(0,6))){
                message = message.substring(6);
            }
            message = message.substring(0,6);
        }
        Log.d(TAG,"message："+message);
        return message;
    }
}
