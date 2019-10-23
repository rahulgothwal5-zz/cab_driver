package com.oxycab.provider.ui.activity;

import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.oxycab.provider.R;
import com.oxycab.provider.common.SharedHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReferActivity extends AppCompatActivity {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ButterKnife.bind(this);
        tvCode.setText(SharedHelper.getKey(this, "referal_code"));
    }

    @OnClick(R.id.tv_code)
    public void onViewClicked() {
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle(R.string.app_name)
                .setText("Hi, \nI am using Oxy Cab. This is my referral code: " + tvCode.getText().toString())
                .startChooser();
    }
}
