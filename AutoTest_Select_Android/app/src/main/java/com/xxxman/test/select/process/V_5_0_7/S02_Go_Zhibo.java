package com.xxxman.test.select.process.V_5_0_7;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

/**
 * 进入直播
 * Created by tuzi on 2017/10/22.
 */

public class S02_Go_Zhibo {

    private static final String TAG = S02_Go_Zhibo.class.getName();

    public static void start(String uid) throws Exception{
        UiDevice mUIDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        UiObject chaxun = mUIDevice.findObject(new UiSelector().resourceId("com.huajiao:id/explore_search_btn"));
        chaxun.click();
        UiObject huajiao_id = mUIDevice.findObject(new UiSelector().resourceId("com.huajiao:id/edit_keyword"));
        huajiao_id.setText(uid);
        Thread.sleep(1000);
        UiObject sousuo = mUIDevice.findObject(new UiSelector().text("搜索"));
        sousuo.click();
        Thread.sleep(2000);
        Log.d(TAG,"准备查找直播id：com.huajiao:id/search_video_item_live_tv");
        UiObject2 zhibao = mUIDevice.findObject(By.res("com.huajiao:id/search_video_item_live_tv"));
        if(zhibao==null){
            Log.d(TAG,"2次查找直播id：com.huajiao:id/search_video_item_live_tv");
            sousuo.click();
            Thread.sleep(3000);
            zhibao = mUIDevice.findObject(By.res("com.huajiao:id/search_video_item_live_tv"));
        }
        if(zhibao==null){
            Log.d(TAG,"3次查找直播id：com.huajiao:id/search_video_item_live_tv");
            sousuo.click();
            Thread.sleep(3000);
            zhibao = mUIDevice.findObject(By.res("com.huajiao:id/search_video_item_live_tv"));
        }
        Log.d(TAG,"点击直播id：com.huajiao:id/search_video_item_live_tv");
        Log.d(TAG," zhibao.getText():"+ zhibao.getText());
        zhibao.click();
    }
}
