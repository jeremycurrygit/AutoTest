package com.xxxman.autotest.shell;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static final String TAG = "MainActivity_HJ";
    boolean is_code = false;
    Button runBtn;
    Button getRoot;
    TextView rootTextView;
    TextView taskView;
    TextView registerView;
    SQLUtil sqlUtil = new SQLUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runBtn = (Button) findViewById(R.id.runBtn);
        //getRoot = (Button) findViewById(R.id.getRoot);
        rootTextView = (TextView) findViewById(R.id.root_view);

        taskView =  (TextView) findViewById(R.id.task_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"11111");
                if(is_code) {
                    Toast.makeText(getApplicationContext(), "已注册成功！", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });
        if (!sqlUtil.tabbleIsExist("count")){
            sqlUtil.createTableCount();
        }
        if (!sqlUtil.tabbleIsExist("code")){
            sqlUtil.createTableCode();
        }
        //读取txt文档
        if(sqlUtil.selectUserCount()==0){
            try {
                String path = Environment.getExternalStorageDirectory().getCanonicalPath();
                List<User> list = FileUtil.ReadTxtFile(path+"/NumberList.txt");
                Log.d(TAG,"user_list.txt中用户数量："+list.size());
                sqlUtil.inserCount(list);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "读取用户文件出错！", Toast.LENGTH_LONG).show();
            }
        }

        int user_count = 0;
        int task_count = 0;
        int end_task_count = 0;
        //int end_count = 0;
        int success_count = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(new Date());
        user_count =sqlUtil.selectUserCount();
        task_count =sqlUtil.selectTaskCount();
        end_task_count =sqlUtil.selectEndTaskCount();
        success_count =sqlUtil.selectSuccessCount();
        taskView.setText(dateString+":总任务数"+user_count+"\n待处理数"+task_count+"，已处理"+end_task_count+"个，成功"+success_count+"个!" );

        //判断是否Root
        if(ShellUtil.hasRootPermission()){
            rootTextView.setText("已经获取Root权限！" );
            rootTextView.setTextColor(ContextCompat.getColor(this, R.color.green));
        }

        String pubkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2zmsLpmPmamWcjznviihheXtecRJCQXj" +
                "n7rjq5OQscJvK+nK02SAjpSy1GBX4JNVJKLIC9XEtKHsB6pGMXK+C9mHSWYhgF2JwXqylDXPxBZR" +
                "3/JLrJO9awN8Jn9BLMAeXCnpGfuGnzH9RSim9+uXpRBwjbly7YCbWZEY+5n18dDQlXP4QBOyh7jE" +
                "0pKYeXoLkSdgWPxOL5tfuiSjewG06xMW+e2OQDvRFUhOgQM41eP8qF9KFaFduUzEiiQ5zYHUHHxC" +
                "4sqrIHs1HzZJT6701bh4C3JYOAPo/j6qJw3nEtjb+Oo2AVqGcQr5PsGcH9bGoHSXYulrhyZCWQCq" +
                "ioZotQIDAQAB";
        RSAUtils.loadPublicKey(pubkey);
        String value = sqlUtil.selectCode();
        String sn = SNUtil.getuniqueId(fab.getContext());
        String enctytCode = null;
        try {
            enctytCode = RSAUtils.encryptWithRSA(sn);
            String code = SNUtil.getMD5(enctytCode);
            code= code.substring(0,12);
            Log.d(TAG,code);
            Log.d(TAG,value);
            is_code = code.equals(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(is_code){
            fab.hide();
        }
    }

    public void runMyUiautomator(View v) {
        if(is_code){
            if (sqlUtil.selectTaskCount()>0 || sqlUtil.selectFailCount().size()>0) {
                new UiautomatorThread().start();
                Log.i(TAG, "runMyUiautomator: ");
            }else{
                Toast.makeText(getApplicationContext(), "无未完成任务可运行！", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "请先注册！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            String command = "am instrument --user 0 -w -r -e debug false -e class " +
                    "com.xxxman.autotest.shell.HJTest1 com.xxxman.autotest.shell.test/android.support.test.runner.AndroidJUnitRunner";
            ShellUtil.CommandResult rs = ShellUtil.execCommand(command, true);
            Log.e(TAG, "run: " + rs.result + "-------" + rs.responseMsg + "-------" + rs.errorMsg);
        }
    }
    // 获取ROOT权限
    public void get_root(View v) {
            try {
                Toast.makeText(getApplicationContext(), "正在获取ROOT权限...", Toast.LENGTH_LONG).show();
                Runtime.getRuntime().exec("su");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "获取ROOT权限时出错!", Toast.LENGTH_LONG).show();
            }

    }

    public void register(View v){

    }

}
