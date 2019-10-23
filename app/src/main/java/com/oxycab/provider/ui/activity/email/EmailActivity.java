package com.oxycab.provider.ui.activity.email;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.CommonValidation;
import com.oxycab.provider.common.SharedHelper;
import com.oxycab.provider.data.network.model.User;
import com.oxycab.provider.ui.activity.main.MainActivity;
import com.oxycab.provider.ui.activity.otp.OTPActivity;
import com.oxycab.provider.ui.activity.regsiter.RegisterActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.HttpException;
import retrofit2.Response;

public class EmailActivity extends BaseActivity implements EmailIView {

    @BindView(R.id.mobile)
    EditText email;
    @BindView(R.id.dial_code)
    CountryCodePicker dialCode;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.next)
    TextView next;

    EmailIPresenter<EmailActivity> presenter = new EmailPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_email;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
    }

    @OnClick({R.id.back, R.id.sign_up, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                activity().onBackPressed();
                break;
            case R.id.sign_up:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.next:
                login();
                break;
        }
    }

    private void login() {
        if (email.getText().toString().length() > 9 && CommonValidation.Validation(email.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.phonenumberValidation), Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("grant_type", "password");
        map.put("mobile", (email.getText().toString().substring(email.getText().toString().length() - 10)));
        map.put("country_code", String.valueOf(dialCode.getSelectedCountryCodeWithPlus()));
        map.put("password", "123456");
        map.put("client_secret", BuildConfig.CLIENT_SECRET);
        map.put("client_id", BuildConfig.CLIENT_ID);
        map.put("device_token", SharedHelper.getKeyFCM(this, "device_token"));
        map.put("device_id", SharedHelper.getKeyFCM(this, "device_id"));
        map.put("device_type", BuildConfig.DEVICE_TYPE);
        map.put("scope", "");
        showLoading();
        presenter.login(map);
    }

    @Override
    public void onSuccess(User user) {
        hideLoading();
        SharedHelper.putKey(this, "access_token", user.getAccessToken());
        SharedHelper.putKey(this, "user_id", String.valueOf(user.getId()));
        SharedHelper.putKey(this, "loggged_in", "true");
        Toasty.success(activity(), "Loggedin Successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }


    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e instanceof HttpException) {
            Response response = ((HttpException) e).response();
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                if (jObjError.has("message"))
                    Toast.makeText(activity(), jObjError.optString("message"), Toast.LENGTH_SHORT).show();
                if (jObjError.has("otp")) {
                    Intent intent = new Intent(activity(), OTPActivity.class);
                    intent.putExtra("mobile", email.getText().toString());
                    intent.putExtra("country_code", String.valueOf(dialCode.getSelectedCountryCodeWithPlus()));
                    intent.putExtra("otp", String.valueOf(jObjError.optString("otp")));
                    startActivityForResult(intent, PICK_OTP_VERIFY);
                }
            } catch (Exception exp) {
                Log.e("Error", exp.getMessage());
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_OTP_VERIFY && resultCode == Activity.RESULT_OK) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("otp", data.getStringExtra("otp"));
            map.put("mobile", email.getText().toString());
            map.put("country_code", String.valueOf(dialCode.getSelectedCountryCodeWithPlus()));
            map.put("device_token", SharedHelper.getKeyFCM(this, "device_token"));
            map.put("device_id", SharedHelper.getKeyFCM(this, "device_id"));
            map.put("grant_type", "password");
            map.put("password", "123456");
            map.put("client_secret", BuildConfig.CLIENT_SECRET);
            map.put("client_id", BuildConfig.CLIENT_ID);
            map.put("device_type", BuildConfig.DEVICE_TYPE);
            showLoading();
            presenter.verifyOTP(map);
            Toast.makeText(this, "Thanks your Mobile is successfully verified", Toast.LENGTH_SHORT).show();
        }
    }
}
