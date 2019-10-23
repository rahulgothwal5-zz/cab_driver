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
import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.CommonValidation;
import com.oxycab.provider.common.SharedHelper;
import com.oxycab.provider.data.network.model.User;
import com.oxycab.provider.ui.activity.document.DocumentActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddDriverActivity extends BaseActivity implements AddDriverIView {


    AddDriverPresenter<AddDriverActivity> presenter = new AddDriverPresenter<AddDriverActivity>();
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contact_1)
    EditText contact1;
    @BindView(R.id.contact_2)
    EditText contact2;
    @BindView(R.id.ll_emergency)
    LinearLayout llEmergency;
    @BindView(R.id.registration_layout)
    LinearLayout registrationLayout;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    Map<String, String> map = new HashMap<String, String>();
    Type type = new TypeToken<Map<String, String>>(){}.getType();

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_driver;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        map = new Gson().fromJson(getIntent().getStringExtra("data"), map.getClass());
    }

    @Override
    public void onSuccess(User user) {
        SharedHelper.putKey(this, "user_id", String.valueOf(user.getId()));
        SharedHelper.putKey(this, "access_token", user.getAccessToken());
        SharedHelper.putKey(this, "loggged_in", "true");
        SharedHelper.putKey(this, "invite_code", user.getReferralCode());

        Toasty.success(this, "Registered Successfully!", Toast.LENGTH_SHORT, true).show();
        finishAffinity();

        Intent intent = new Intent(this, DocumentActivity.class);
        intent.putExtra("first", true);
        finish();
        startActivity(intent);
    }

    @OnClick({R.id.back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:

                if (!TextUtils.isEmpty(contact1.getText().toString())) {
                    if (CommonValidation.isValidPhone(contact1.getText().toString())) {
                        Toasty.error(this, getString(R.string.validmobile), Toast.LENGTH_SHORT, true).show();
                        contact1.requestFocus();
                        return;
                    }
                }
                if (!TextUtils.isEmpty(contact2.getText().toString())) {
                    if (CommonValidation.isValidPhone(contact2.getText().toString())) {
                        Toasty.error(this, getString(R.string.validmobile), Toast.LENGTH_SHORT, true).show();
                        contact2.requestFocus();
                        return;
                    }
                }
                Map<String, RequestBody> reqMap = new HashMap<>();
                reqMap.put("first_name", toRequestBody(map.get("first_name" )));
                reqMap.put("last_name", toRequestBody(map.get("last_name" )));
                reqMap.put("mobile", toRequestBody(map.get("mobile" )));
                reqMap.put("password",toRequestBody(map.get("password" )));
                reqMap.put("password_confirmation", toRequestBody(map.get("password_confirmation" )));
                reqMap.put("device_token",toRequestBody( map.get("device_token")));
                reqMap.put("device_id", toRequestBody(map.get("device_id" )));
                reqMap.put("device_type", toRequestBody(map.get("device_type" )));
                reqMap.put("country_code",toRequestBody(map.get("country_code" )));
                reqMap.put("email",toRequestBody(map.get("email" )));
                reqMap.put("aadhar_number",toRequestBody(map.get("aadhar_number" )));

                if (!map.get("referral_code" ).isEmpty())
                {
                    reqMap.put("referral_code",toRequestBody(map.get("referral_code" )));
                }

                reqMap.put("emergency_contact1", toRequestBody(contact1.getText().toString()));
                reqMap.put("emergency_contact2", toRequestBody(contact2.getText().toString()));

                List<MultipartBody.Part> parts = new ArrayList<>();
                showLoading();
                presenter.register(reqMap, parts);
            break;
        }
    }
}
