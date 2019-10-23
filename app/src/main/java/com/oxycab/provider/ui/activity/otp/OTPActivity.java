package com.oxycab.provider.ui.activity.otp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.OtpView;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.data.network.model.MyOTP;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OTPActivity extends BaseActivity implements OTPIView {

    @BindView(R.id.pin_entry)
    OtpView pinEntry;
    @BindView(R.id.otp_description)
    TextView otpDescription;
    String mobile, OTP, countryCode;
    private OTPPresenter<OTPActivity> presenter = new OTPPresenter<>();
    String description = "";
    HashMap<String, Object> map = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mobile = extras.getString("mobile", "");
            countryCode = extras.getString("country_code", "");
            OTP = extras.getString("otp", "");
            map.put("username", mobile);
            otpDescription.setText("OTP sent to your mobile number " + mobile.substring(mobile.length() - 10));
        }

        pinEntry.setOtpCompletionListener(otp -> {
            if (pinEntry.getText().toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
                return;
            }
            if (pinEntry.getText().toString().equalsIgnoreCase(OTP)) {
                Intent intent = new Intent();
                intent.putExtra("mobile", mobile);
                intent.putExtra("country_code", countryCode);
                intent.putExtra("otp", pinEntry.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, R.string.wrong_otp, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.submit, R.id.resend_otp, R.id.voice_call_otp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (pinEntry.getText().toString().isEmpty()) {
                    Toast.makeText(this, getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pinEntry.getText().toString().equalsIgnoreCase(OTP)) {
                    Intent intent = new Intent();
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("country_code", countryCode);
                    intent.putExtra("otp", pinEntry.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.wrong_otp, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.resend_otp:
                showLoading();
                description = "OTP resent to your mobile number " + countryCode + mobile;
                presenter.sendOTP(mobile);
                break;
            case R.id.voice_call_otp:
                showLoading();
                description = "You will receive voice call to your mobile number " + countryCode + mobile;
                presenter.sendVoiceOTP(mobile);
                break;
        }
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                System.out.println("BroadcastReceiver" + message);
                pinEntry.setText(message);
            }
        }
    };

    @Override
    public void onSuccess(MyOTP otp) {
        hideLoading();
        pinEntry.setText("");
        OTP = otp.getOtp();
        otpDescription.setText(description);
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }
}
