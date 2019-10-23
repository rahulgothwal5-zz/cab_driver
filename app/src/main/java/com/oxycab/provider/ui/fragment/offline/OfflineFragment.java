package com.oxycab.provider.ui.fragment.offline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseFragment;
import com.oxycab.provider.ui.activity.main.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.oxycab.provider.common.fcm.MyFirebaseMessagingService.INTENT_FILTER;

public class OfflineFragment extends BaseFragment implements OfflineIView {

    OfflinePresenter<OfflineFragment> presenter = new OfflinePresenter<>();
    @BindView(R.id.menu_img)
    ImageView menuImg;
    @BindView(R.id.go_online_btn)
    Button goOnlineBtn;
    @BindView(R.id.status)
    TextView status;
    Unbinder unbinder;

    DrawerLayout drawer;
    Context context;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_offline;
    }

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        this.context = getContext();
        presenter.attachView(this);
        drawer = activity().findViewById(R.id.drawer_layout);


        Bundle bundle = getArguments();
        if (bundle != null) {
            String statusStr = bundle.getString("status");
            assert statusStr != null;
            switch (statusStr) {
                case "offline":
                    this.status.setText(R.string.offline_desc);
                    break;
                case "onboarding":
                    this.status.setText(R.string.account_verification_desc);
                    break;
                case "banned":
                    this.status.setText(R.string.banned_desc);
                    break;
                default:
                    this.status.setText(statusStr);
                    break;
            }

        }

        return view;
    }


    @OnClick({R.id.menu_img, R.id.go_online_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_img:
                drawer.openDrawer(Gravity.START);
                break;
            case R.id.go_online_btn:
                HashMap<String, Object> map = new HashMap<>();
                map.put("service_status", "active");
                presenter.providerAvailable(map);
                break;
        }
    }

    @Override
    public void onSuccess(Object object) {
        String jsonInString = new Gson().toJson(object);
        try {
            JSONObject jsonObj = new JSONObject(jsonInString);
            if (jsonObj.has("error")){
                Toast.makeText(activity(), jsonObj.optString("error"), Toast.LENGTH_SHORT).show();
                ((MainActivity)activity()).getProfile();
            }
        } catch (Exception e) {

        }
        Intent intent = new Intent(INTENT_FILTER);
        context.sendBroadcast(intent);
    }

    @Override
    public void onError(Throwable e) {

    }
}
