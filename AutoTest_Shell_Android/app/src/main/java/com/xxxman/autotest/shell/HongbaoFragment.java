package com.xxxman.autotest.shell;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tuzi on 2017/7/30.
 */

public class HongbaoFragment extends Fragment {

    Button runBtn2;
    Button runBtn3;
    Button runBtn4;
    Button runBtn5;
    Button runBtn6;
    Button runBtn7;
    TextView hongbaoView;
    EditText numberEdit1;
    EditText numberEdit1Next;
    String code;
    SQLUtil1 sqlUtil = new SQLUtil1();

    private static final String TAG = HongbaoFragment.class.getName();
    boolean is_code = false;

    public static HongbaoFragment newInstance() {
        HongbaoFragment fragment = new HongbaoFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_hongbao,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle bundle){

        runBtn2 = (Button) view.findViewById(R.id.runBtn2);
        runBtn3 = (Button) view.findViewById(R.id.runBtn3);
        runBtn4 = (Button) view.findViewById(R.id.runBtn4);
        runBtn5 = (Button) view.findViewById(R.id.runBtn5);
        runBtn6 = (Button) view.findViewById(R.id.runBtn6);
        runBtn7 = (Button) view.findViewById(R.id.runBtn7);
        numberEdit1 = (EditText) view.findViewById(R.id.number_edit1);
        numberEdit1.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        hongbaoView =(TextView)  view.findViewById(R.id.hongbao_view);
        numberEdit1Next = (EditText)  view.findViewById(R.id.number_edit1_next);
        //绑定运行按钮
        runBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runHongbao (view);
            }
        });
        //绑定运行按钮
        runBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runHongbao2(view);
            }
        });
        //绑定运行按钮
        runBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextLogin(view);
            }
        });
        //绑定运行按钮
        runBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除表
                //读取txt文档，插入到表
                try {
                    if(is_code) {
                        sqlUtil.delTable("hongbao");
                        String path = Environment.getExternalStorageDirectory().getCanonicalPath();
                        List<User> list = FileUtil.ReadTxtFile(path + "/bh_NumberList.txt");
                        Log.d(TAG, "user_list.txt中用户数量：" + list.size());
                        sqlUtil.inserCount(list);
                        UpdateThread thread = new UpdateThread();
                        thread.init(list, code);
                        thread.start();
                        Toast.makeText(HongbaoFragment.this.getActivity(), "更新成功，请重启花椒助手", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(HongbaoFragment.this.getActivity(), "请先注册！", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(HongbaoFragment.this.getActivity(), "读取用户文件出错！", Toast.LENGTH_LONG).show();
                }

            }
        });
        runBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_code){
                    new UiautomatorThread5().start();
                    Log.i(TAG, "runMyUiautomator: ");
                }else {
                    Toast.makeText(HongbaoFragment.this.getActivity(), "请先注册！", Toast.LENGTH_LONG).show();
                }
            }
        });
        runBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_code){
                    new UiautomatorThread6().start();
                    Log.i(TAG, "runMyUiautomator: ");
                }else {
                    Toast.makeText(HongbaoFragment.this.getActivity(), "请先注册！", Toast.LENGTH_LONG).show();
                }
            }
        });
        //创建表
        if (!sqlUtil.tabbleIsExist("hongbao")){
            sqlUtil.createTableCount();
        }
        //读取txt文档，插入到表
        if(sqlUtil.selectUserCount()==0){
            try {
                String path = Environment.getExternalStorageDirectory().getCanonicalPath();
                List<User> list = FileUtil.ReadTxtFile(path+"/bh_NumberList.txt");
                Log.d(TAG,"user_list.txt中用户数量："+list.size());
                sqlUtil.inserCount(list);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this.getActivity(), "读取用户文件出错！", Toast.LENGTH_LONG).show();
            }
        }

        //上一次登录信息
        List<User> list_login = sqlUtil.selectLoginCount();
        if(list_login.size()>0) {
            numberEdit1Next.setText(""+list_login.get(0).number);
        }

        int user_count = 0;
        int task_count = 0;
        int end_task_count = 0;
        int success_count = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(new Date());
        user_count =sqlUtil.selectUserCount();
        task_count = sqlUtil.selectHongbaoTaskCount();
        end_task_count = user_count-task_count;
        success_count = sqlUtil.selectHongbaoSuccessCount();
        hongbaoView.setText(dateString+"：总任务数"+user_count+"\n待处理数"+task_count+"，已处理"+end_task_count+"个，成功"+success_count+"个!");

        //判断是否注册
        String pubkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2zmsLpmPmamWcjznviihheXtecRJCQXj" +
                "n7rjq5OQscJvK+nK02SAjpSy1GBX4JNVJKLIC9XEtKHsB6pGMXK+C9mHSWYhgF2JwXqylDXPxBZR" +
                "3/JLrJO9awN8Jn9BLMAeXCnpGfuGnzH9RSim9+uXpRBwjbly7YCbWZEY+5n18dDQlXP4QBOyh7jE" +
                "0pKYeXoLkSdgWPxOL5tfuiSjewG06xMW+e2OQDvRFUhOgQM41eP8qF9KFaFduUzEiiQ5zYHUHHxC" +
                "4sqrIHs1HzZJT6701bh4C3JYOAPo/j6qJw3nEtjb+Oo2AVqGcQr5PsGcH9bGoHSXYulrhyZCWQCq" +
                "ioZotQIDAQAB";
        RSAUtils.loadPublicKey(pubkey);
        String value = sqlUtil.selectCode();
        String sn = SNUtil.getuniqueId(this.getContext());
        String enctytCode = null;
        try {
            enctytCode = RSAUtils.encryptWithRSA(sn);
            code = SNUtil.getMD5(enctytCode);
            code = SNUtil.getMD5(code);
            code= code.substring(0,12);
            Log.d(TAG,code);
            Log.d(TAG,value);
            is_code = ("@"+code).equals(value);
            if(value.length()==12){
                is_code = code.equals(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void runHongbao(View v) {
        if(is_code){
            if (sqlUtil.selectHongbaoUser().size() >0 || sqlUtil.selectHongbaoFailUser().size()>0) {
                UiautomatorThread3 thread3 = new UiautomatorThread3();
                Log.i(TAG, "runMyUiautomator: ");
                try {
                    Thread.sleep(10000);
                    Log.i(TAG, "runMyUiautomator: 10s----");
                    thread3.suspend();
                    Thread.sleep(10000);
                    Log.i(TAG, "runMyUiautomator: 20s----");
                    thread3.resume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(this.getActivity(), "无未完成任务可运行！", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this.getActivity(), "请先注册！", Toast.LENGTH_LONG).show();
        }
    }
    public void runHongbao2(View v) {
        if(is_code){
            String number_string = numberEdit1.getText().toString().trim();
            int number = Integer.parseInt(number_string);
            String path = null;
            try {
                path = Environment.getExternalStorageDirectory().getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<User> list = FileUtil.ReadTxtFile(path+"/bh_NumberList.txt",number);
            Log.d(TAG,"登录人数：---"+list.size());
            sqlUtil.inserLoginCount(list);
            if (list.size()>0 && sqlUtil.selectLoginCount().size()>0) {
                new UiautomatorThread4().start();
                Log.i(TAG, "runMyUiautomator: ");
            }else{
                Toast.makeText(this.getActivity(), "未找到该用户！", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this.getActivity(), "请先注册！", Toast.LENGTH_LONG).show();
        }
    }
    public void nextLogin(View v) {
        if(is_code){
            String number_string = numberEdit1Next.getText().toString().trim();
            int number = Integer.parseInt(number_string);
            String path = null;
            try {
                path = Environment.getExternalStorageDirectory().getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<User> list = FileUtil.ReadTxtFile(path+"/bh_NumberList.txt",number+1);
            Log.d(TAG,"登录人数：---"+list.size());
            sqlUtil.inserLoginCount(list);
            if (list.size()>0 && sqlUtil.selectLoginCount().size()>0) {
                new UiautomatorThread4().start();
                Log.i(TAG, "runMyUiautomator: ");
            }else{
                Toast.makeText(this.getActivity(), "未找到该用户！", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this.getActivity(), "请先注册！", Toast.LENGTH_LONG).show();
        }
    }
    static class UiautomatorThread3 extends BaseThread {

        public UiautomatorThread3() {
            super("HJTest3", false);
        }
        @Override
        public void process() {
            //super.run();
            String command = "am instrument --user 0 -w -r -e debug false -e class " +
                    "com.xxxman.autotest.shell.HJTest3 com.xxxman.autotest.shell.test/android.support.test.runner.AndroidJUnitRunner";
            ShellUtil.CommandResult rs = ShellUtil.execCommand(command, true);
            Log.i(TAG, "run: " + rs.result + "-------" + rs.responseMsg + "-------" + rs.errorMsg);
        }

    }
    class UiautomatorThread4 extends Thread {
        @Override
        public void run() {
            super.run();
            String command = "am instrument --user 0 -w -r -e debug false -e class " +
                    "com.xxxman.autotest.shell.HJTest4 com.xxxman.autotest.shell.test/android.support.test.runner.AndroidJUnitRunner";
            ShellUtil.CommandResult rs = ShellUtil.execCommand(command, true);
            Log.i(TAG, "run: " + rs.result + "-------" + rs.responseMsg + "-------" + rs.errorMsg);
        }
    }
    class UiautomatorThread5 extends Thread {
        @Override
        public void run() {
            super.run();
            String command = "am instrument --user 0 -w -r -e debug false -e class " +
                    "com.xxxman.autotest.shell.HJTest4A com.xxxman.autotest.shell.test/android.support.test.runner.AndroidJUnitRunner";
            ShellUtil.CommandResult rs = ShellUtil.execCommand(command, true);
            Log.i(TAG, "run: " + rs.result + "-------" + rs.responseMsg + "-------" + rs.errorMsg);
        }
    }
    class UiautomatorThread6 extends Thread {
        @Override
        public void run() {
            super.run();
            String command = "am instrument --user 0 -w -r -e debug false -e class " +
                    "com.xxxman.autotest.shell.HJTest4B com.xxxman.autotest.shell.test/android.support.test.runner.AndroidJUnitRunner";
            ShellUtil.CommandResult rs = ShellUtil.execCommand(command, true);
            Log.i(TAG, "run: " + rs.result + "-------" + rs.responseMsg + "-------" + rs.errorMsg);
        }
    }
    static class UpdateThread extends Thread {

        String code = "";
        List<User> list =null;
        @Override
        public void run() {
            super.run();
            MyConnection my  = new MyConnection();
            String url = Constant.URL();
            //更新到服务器
            String listStr = "[";
            for (User user : list) {
                listStr = listStr + "{n:" + user.number + ",a:" + user.phone + "},";
            }
            listStr = listStr.substring(0,listStr.length()-1);
            listStr = listStr + "]";
            String context = "{\"function\":\"F100009\",\"user\":{\"id\":\"1\",\"session\":\"123\"},\"content\":{\"phone\":\"" + code + "\",\"list\":\"" + listStr + "\"}}";
            Map<String, String> parms = new HashMap<>();
            parms.put("jsonContent", context);
            Log.d(TAG, "http请求" + context);
            String rs = my.getContextByHttp(url, parms);
            Log.d(TAG, "http请求结果" + rs);
        }
        public void init(List<User> list,String code) {
            this.list = list;
            this.code = code;
        }
    }
}