package com.oxycab.provider.ui.activity.regsiter.details;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.CommonValidation;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RegisterExtraDetailsActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etAadhar)
    EditText etAadhar;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.etReferralCode)
    EditText etReferralCode;
    @BindView(R.id.ll_extra)
    LinearLayout llExtra;
    @BindView(R.id.registration_layout)
    LinearLayout registrationLayout;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    Map<String, String> map = new HashMap<String, String>();
    Type type = new TypeToken<Map<String, String>>() {
    }.getType();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_extra_details;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        map = new Gson().fromJson(getIntent().getStringExtra("data"), type);
    }


    @OnClick({R.id.back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                if (etAadhar.getText().toString().length()!=12) {
                    Toasty.error(this, getString(R.string.aadhar_validation), Toast.LENGTH_SHORT, true).show();
                    etAadhar.requestFocus();
                    return;
                } else if (!TextUtils.isEmpty(etEmail.getText().toString())) {
                    if (CommonValidation.isValidEmail(etEmail.getText().toString())) {
                        Toasty.error(this, getString(R.string.email_validation), Toast.LENGTH_SHORT, true).show();
                        etEmail.requestFocus();
                        return;
                    }
                } else {
                    map.put("email", etEmail.getText().toString());
                    map.put("aadhar_number", etAadhar.getText().toString());
                    map.put("referral_code", etReferralCode.getText().toString());
                    Intent intent = new Intent(activity(), AddDriverActivity.class);
                    intent.putExtra("data", new Gson().toJson(map));
                    startActivity(intent);
                }
                break;
        }
    }
}
