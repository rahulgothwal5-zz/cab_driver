package com.oxycab.provider.ui.activity.regsiter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.CommonValidation;
import com.oxycab.provider.common.SharedHelper;
import com.oxycab.provider.data.network.model.MyOTP;
import com.oxycab.provider.ui.activity.otp.OTPActivity;
import com.oxycab.provider.ui.activity.regsiter.details.RegisterExtraDetailsActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.RequestBody;
import retrofit2.HttpException;

import static com.oxycab.provider.common.Constants.APP_REQUEST_CODE;

public class RegisterActivity extends BaseActivity implements RegisterIView {


    private static final String TAG = "RegisterActivity";
    private static final int PICK_OTP_VERIFY = 222;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtFirstName)
    EditText txtFirstName;
    @BindView(R.id.txtLastName)
    EditText txtLastName;
    @BindView(R.id.txtPhoneNumber)
    EditText txtPhoneNumber;
    @BindView(R.id.mobile_layout)
    LinearLayout mobile_layout;
    @BindView(R.id.registration_layout)
    LinearLayout registration_layout;
    @BindView(R.id.dial_code)
    CountryCodePicker dialCode;

    RegisterPresenter<RegisterActivity> presenter = new RegisterPresenter<>();
    @BindView(R.id.ll_name)
    LinearLayout llName;

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        registration_layout.setVisibility(View.GONE);
    }

    void register() {

        //All the String parameters, you have to put like

    }


    @OnClick({R.id.next, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (mobile_layout.getVisibility() == View.VISIBLE) {
                    if (txtPhoneNumber.getText().toString().length() > 9 && CommonValidation.Validation(txtPhoneNumber.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.invalid_mobile), Toast.LENGTH_SHORT, true).show();
                        return;
                    } else if (CommonValidation.isValidPhone(txtPhoneNumber.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.validmobile), Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    presenter.verifyMobileAlreadyExits(txtPhoneNumber.getText().toString());
                } else {
                    saveUser();
                }
                break;
            case R.id.back:
                onBackPressed();
                break;

        }
    }

    private void saveUser() {
        if (registration_layout.getVisibility() == View.VISIBLE) {
            if (CommonValidation.Validation(txtFirstName.getText().toString().trim())) {
                Toasty.error(this, getString(R.string.invalid_first_name), Toast.LENGTH_SHORT, true).show();
                txtFirstName.requestFocus();
                return;
            } else if (CommonValidation.Validation(txtLastName.getText().toString().trim())) {
                Toasty.error(this, getString(R.string.invalid_last_name), Toast.LENGTH_SHORT, true).show();
                txtLastName.requestFocus();
                return;
            } else {


                Map<String, String> map = new HashMap<>();
                map.put("first_name", txtFirstName.getText().toString());
                map.put("last_name", txtLastName.getText().toString());
                map.put("mobile", txtPhoneNumber.getText().toString().substring(txtPhoneNumber.getText().toString().length() - 10));
                map.put("password", "123456");
                map.put("password_confirmation", "123456");
                map.put("device_token", SharedHelper.getKeyFCM(this, "device_token"));
                map.put("device_id", SharedHelper.getKeyFCM(this, "device_id"));
                map.put("device_type", BuildConfig.DEVICE_TYPE);
                map.put("country_code", String.valueOf(dialCode.getSelectedCountryCodeWithPlus()));

                Intent intent = new Intent(activity(), RegisterExtraDetailsActivity.class);
                intent.putExtra("data", new Gson().toJson(map));
                startActivity(intent);

//                llName.setVisibility(View.GONE);
//                llExtra.setVisibility(View.VISIBLE);
            }
        }
//        else if (llExtra.getVisibility() == View.VISIBLE) {
//            if (TextUtils.isEmpty(etAadhar.getText().toString())) {
//                Toasty.error(this, getString(R.string.aadhar_validation), Toast.LENGTH_SHORT, true).show();
//                return;
//            } else {
//                llExtra.setVisibility(View.GONE);
//                llEmergency.setVisibility(View.VISIBLE);
//            }
//        } else if (llEmergency.getVisibility() == View.VISIBLE) {
//            if (contact1.getText().toString().isEmpty()) {
//                contact1.setError("Required");
//                return;
//            } else {
//                register();
//            }
//        }
    }


    @Override
    public void onSuccess(MyOTP otp) {

        Intent intent = new Intent(activity(), OTPActivity.class);
        intent.putExtra("mobile", txtPhoneNumber.getText().toString());
        intent.putExtra("country_code", String.valueOf(dialCode.getSelectedCountryCodeWithPlus()));
        intent.putExtra("otp", String.valueOf(otp.getOtp()));
        startActivityForResult(intent, PICK_OTP_VERIFY);

    }

    @Override
    public void onError(Throwable e) {

        hideLoading();

        HttpException error = (HttpException) e;


        try {

            String errorBody = error.response().errorBody().string();
            JSONObject jObjError = new JSONObject(errorBody);


            if (jObjError.has("email"))
                Toast.makeText(getApplicationContext(), jObjError.optString("email"), Toast.LENGTH_LONG).show();
            if (jObjError.has("error"))
                Toast.makeText(getApplicationContext(), jObjError.optString("error"), Toast.LENGTH_LONG).show();
            if (jObjError.has("message"))
                Toast.makeText(getApplicationContext(), jObjError.optString("error"), Toast.LENGTH_LONG).show();
            if (jObjError.has("mobile"))
                Toast.makeText(activity(), jObjError.optString("mobile"), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        } catch (Exception e1) {
            Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    public void fbPhoneLogin() {

        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);

        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);

        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == APP_REQUEST_CODE && data != null) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(Account account) {
                    Log.d("AccountKit", "onSuccess: Account Kit" + AccountKit.getCurrentAccessToken().getToken());
                    if (AccountKit.getCurrentAccessToken().getToken() != null) {
                        //SharedHelper.putKey(RegisterActivity.this, "account_kit_token", AccountKit.getCurrentAccessToken().getToken());
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        SharedHelper.putKey(RegisterActivity.this, "dial_code", phoneNumber.getCountryCode());
                        SharedHelper.putKey(RegisterActivity.this, "mobile", phoneNumber.getPhoneNumber());
                        txtPhoneNumber.setText(SharedHelper.getKey(RegisterActivity.this, "mobile"));
                        register();
                    }
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.e("AccountKit", "onError: Account Kit" + accountKitError);
                }
            });

        } else if (requestCode == PICK_OTP_VERIFY && resultCode == Activity.RESULT_OK) {
            registration_layout.setVisibility(View.VISIBLE);
            mobile_layout.setVisibility(View.GONE);
            Toast.makeText(this, "Thanks your Mobile is successfully verified, Please enter your First Name and Last Name to create your account", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
