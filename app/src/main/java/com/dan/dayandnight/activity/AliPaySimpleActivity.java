package com.dan.dayandnight.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.dan.dayandnight.R;
import com.dan.dayandnight.dialog.UpdataDialog;

import java.util.Map;

public class AliPaySimpleActivity extends AppCompatActivity {


    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);

        btn = findViewById(R.id.alipay_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        String orderInfo = "alipay_sdk=alipay-sdk-java-4.7.12.ALL&app_id=2018011601908279&biz_content=%7B%22body%22%3A%22%E8%B4%AD%E4%B9%B0%E5%95%86%E5%93%81%22%2C%22out_trade_no%22%3A%2211453995733608%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%80%81%E6%9D%BF%E5%95%86%E5%93%81%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2210%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fpay.ghy9.com%2Fnotify%2F9&sign=T8cE3Q6Gp6ORfTt2BfICTCQ8O7X0v54LrlSQMoapiUPpEGukJ26a2JxHrAOIU9yQchIrWA1yCCf4kHv6FWuAyyuPpvO7VT%2FIDaEPTvag6azGyfCC6VkiE2Yff8%2FEWsv52bWQPH6zoZdjU7HifyRFXMiTUBm1kTeqKSDUckTREkrV8bmH9IW9fLfleStWm3n8DPaetc922qTxh%2B1zhbm0MjENDyR%2FOOmNt7qXpE2u7YrAWJyd0pz%2FUbw%2FH924GlCCWcLpqPp0mGKbhZNeB6bl8Hf4wkDh2byy3u2kWzUvYeXIX4qBvc2ghWPsavRYeeFovrq0dJ31YL6%2B7gDHHtyZxA%3D%3D&sign_type=RSA2&timestamp=2019-09-27+23%3A52%3A52&version=1.0";
                        PayTask alipay = new PayTask(AliPaySimpleActivity.this);
                        Map<String,String> result = alipay.payV2(orderInfo,true);

                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String,String> result = ((Map<String,String>) msg.obj);
            Toast.makeText(AliPaySimpleActivity.this, result.toString(),
                    Toast.LENGTH_LONG).show();
        };
    };

}