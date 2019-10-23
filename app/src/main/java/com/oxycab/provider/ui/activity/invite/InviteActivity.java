package com.oxycab.provider.ui.activity.invite;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.SharedHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity implements InviteIView {

    @BindView(R.id.call)
    Button call;
    @BindView(R.id.inviteCode)
    TextView inviteCode;

    String referral;

    InvitePresenter<InviteActivity> presenter = new InvitePresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        referral = SharedHelper.getKey(getApplicationContext(),"invite_code");
        inviteCode.setText(referral);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.call})
    void onClick(View view){
        if (view.getId() == R.id.call){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                i.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout this app,register with my referrel code and earn more,"+referral+
                        "" + getString(R.string.app_name) + "\nhttps://play.google.com/store/apps/details?id="
                        + BuildConfig.APPLICATION_ID);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
        }
    }


}
