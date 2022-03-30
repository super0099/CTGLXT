package com.example.myfirst.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadDataAsyncTask extends AsyncTask<String,Void,String> {
    Context context;//上下关系,这个的上关系是ParnterAnalysisActivity类
    OnGetNetDataListener listener;
    ProgressDialog dialog;
    boolean isShowDialog;//判断是否开始加载层

    //设置加载层
    private void initDialog(){
        dialog = new ProgressDialog(context);
        dialog.setTitle("提示信息");
        dialog.setMessage("正在加载中");
    }
    //构造方法方便数据的传输,和赋值
    public LoadDataAsyncTask(Context context, OnGetNetDataListener listener,boolean isShowDialog) {
        this.context = context;
        this.listener = listener;
        this.isShowDialog = isShowDialog;
        //开始加载层
        initDialog();
    }

    public interface OnGetNetDataListener{
        public void onSucess(String json);
    }
    //运行在主线程当中,通常在此方法中初始化控件,这里任务打开加载层
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(isShowDialog){
            dialog.show();
        }
    }
    //运行在子线程当中,可以在此处进行耗时操作等逻辑(比如获取网络请求)
    @Override
    protected String doInBackground(String... params) {
        //开始网络请求
        String json= HttpUtils.getJsonFromNet(params[0]);
        //返回json格式的字符串
        return json;
    }
    //运行在主线程,可以在此处得到doInbackground返回的数据,在此处通常进行控件的更新
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(isShowDialog){
            //关闭加载层
            dialog.dismiss();
        }
        //把数据保存在接口里
        listener.onSucess(s);
    }
}
