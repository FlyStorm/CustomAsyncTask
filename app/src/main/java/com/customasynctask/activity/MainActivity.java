package com.customasynctask.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fi.activity.R;
import com.customasynctask.view.CustomAsyncTask;

//测试自定义AsyncTask类
public class MainActivity extends AppCompatActivity {

    private static final String TAG =MainActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
        new CustomAsyncTask() {
            @Override
            protected void onPreExecute() {
                Log.e(TAG,Thread.currentThread().getName()+"...CustomAsyncTask:我在做准备操作");
                Toast.makeText(getApplicationContext(),"CustomAsyncTask:我在做准备操作",Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doingBackGround(String s) {
                try {
                    //此方法在子线程中运行，睡眠5秒钟
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG,Thread.currentThread().getName()+"...CustomAsyncTask:我正在进行耗时操作，访问网络，参数是："+s);
                return s+":返回";
            }

            @Override
            protected void onPostExecute(String s) {
                Log.e(TAG,Thread.currentThread().getName()+"...CustomAsyncTask:我接受到的结果是："+s);
                Toast.makeText(getApplicationContext(),"CustomAsyncTask:我接受到的结果是："+s,Toast.LENGTH_SHORT).show();
            }
        }.execute(new String[]{"param1"});
    }
}
