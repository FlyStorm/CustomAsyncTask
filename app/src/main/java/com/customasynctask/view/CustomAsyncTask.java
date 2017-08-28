package com.customasynctask.view;

import android.os.Handler;
import android.os.Message;

/**
 * 创建者     yangyanfei
 * 创建时间   2017/8/16 0016 2:55
 * 作用	      ${TODO}
 * <p/>
 * 版本       $$Rev$$
 * 更新者     $$Author$$
 * 更新时间   $$Date$$
 * 更新描述   ${TODO}
 */
//自定义AsyncTask类
public abstract class CustomAsyncTask {
    private static final int ON_POST_MSG = 0;
    //声明handler对象
    private final MyHandler mHandler;

    public CustomAsyncTask() {
        //实例化handler对象
        mHandler = new MyHandler();
    }

    /**
     * 运行在主线程，做准备操作
     */
    protected abstract void onPreExecute();

    /**
     * 运行在子线程，做耗时操作
     * @param s
     * @return
     */
    protected abstract String doingBackGround(String s);

    /**
     * 运行在主线程中，耗时操作完成，更新UI
     * @param s
     */
    protected abstract void onPostExecute(String s);
    public void execute(final String... strs) {
        onPreExecute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //做耗时操作，返回一个结果
                String result = doingBackGround(strs[0]);
                //把这个结果通过handler发消息给主线程，更新ui
                Message.obtain(mHandler,ON_POST_MSG,result).sendToTarget();
            }
        }).start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ON_POST_MSG:
                    String result = (String) msg.obj;
                    onPostExecute(result);
                    break;
                default:
                    break;
            }
        }
    }
}
