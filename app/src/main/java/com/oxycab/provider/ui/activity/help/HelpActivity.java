package com.oxycab.provider.ui.activity.help;

import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.data.network.model.Help;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity implements HelpIView {

    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.imgMail)
    ImageView imgMail;
    @BindView(R.id.imgWeb)
    ImageView imgWeb;

    HelpPresenter<HelpActivity> presenter = new HelpPresenter<>();
    Help help;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.getHelp();
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

    @Override
    public void onSuccess(Help helpDetails) {
        help = helpDetails;
    }

    @Override
    public void onError(Throwable e) {

    }

    @OnClick({R.id.imgCall, R.id.imgMail, R.id.imgWeb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgCall:
                if (help != null) {
                    makeCall(help.getContactNumber());
                }
                break;
            case R.id.imgMail:
                if (help == null) {
                    return;
                }
                String to = help.getContactEmail();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + "-" + getString(R.string.help));
                startActivity(Intent.createChooser(intent, "Send feedback"));
                break;
            case R.id.imgWeb:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BASE_URL + "help"));
                startActivity(browserIntent);
                break;
        }
    }

    void makeCall(String number) {
        if (number == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);

    }
}
