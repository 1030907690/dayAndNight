package com.dan.dayandnight.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dan.dayandnight.R;
import com.dan.dayandnight.dialog.UpdataDialog;
import com.dan.dayandnight.utils.ToastUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String INIT_URL = "https://raw.githubusercontent.com/1030907690/dayAndNight/master/VersionManager.json";

    private TextView initTextView;

    private SharedPreferences sharedPreferences;
    private UpdataDialog updataDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initVersionData();
        checkVersionGet(null);

    }

    /***
     * 去下载
     * */
    private void toDownLoad(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 返回当前程序版本名  build.gradle
     */
    public String getAppVersionName(Context context) {
        try {

            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = pi.versionName;
            int versioncode = pi.versionCode;
            Log.d("versionName:---" + versionName, "versioncode:---" + versioncode);
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
            return versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return null;
    }


    /***
     * 对比版本
     * */
    private void comparison(String currentVersion,final String url) {
        //String version = sharedPreferences.getString("version", "1.0.0");
        String version = getAppVersionName(MainActivity.this);
        if (0 != version.compareTo(currentVersion)) {
            ToastUtil.showToast(MainActivity.this, "需要更新");
            updataDialog.show();

            updataDialog.setOnCenterItemClickListener(new UpdataDialog.OnCenterItemClickListener() {
                @Override
                public void OnCenterItemClick(UpdataDialog dialog, View view) {
                    switch (view.getId()){
                        case R.id.dialog_sure:
                            /**调用系统自带的浏览器去下载最新apk*/
                            toDownLoad(url);
                            break;
                    }
                    updataDialog.dismiss();
                }
            });

        }
    }

    /***
     * 初始化版本数据
     * */
    private void initVersionData() {
        //步骤1：创建一个SharedPreferences对象
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件
        editor.putString("version", "1.0.0");
        //步骤4：提交
        editor.commit();
    }


    private void initView() {
        initTextView = findViewById(R.id.initViewText);
        //初始化弹窗 布局 点击事件的id
        updataDialog = new UpdataDialog(this,R.layout.dialog_version_update,
                new int[]{R.id.dialog_sure});
    }

    public void checkVersionGet(View view) {
        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url(INIT_URL).build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(MainActivity.this, "Get 失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = JSONObject.parseObject(responseStr);
                        String currentVersion = jsonObject.getString("currentVersion");
                        initTextView.setText("当前版本 :" + currentVersion);
                        comparison(currentVersion,jsonObject.getString("downloadUrl"));
                    }
                });
            }
        });
    }

    public void checkVersionPost(View view) {
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数
        FormBody formBody = new FormBody
                .Builder()
                /*.add("username", "initObject")
                .add("password", "initObject")*/
                .build();
        final Request request = new Request.Builder()
                .url(INIT_URL)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtil.showToast(MainActivity.this, "Post Parameter 失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                ToastUtil.showToast(MainActivity.this, "Code：" + String.valueOf(response.code()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initTextView.setText(responseStr);
                    }
                });
            }
        });
    }
}
