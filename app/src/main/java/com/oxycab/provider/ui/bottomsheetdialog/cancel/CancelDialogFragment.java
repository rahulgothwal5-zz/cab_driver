package com.oxycab.provider.ui.bottomsheetdialog.cancel;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseBottomSheetDialogFragment;
import com.oxycab.provider.common.SharedHelper;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.oxycab.provider.common.fcm.MyFirebaseMessagingService.INTENT_FILTER;

public class CancelDialogFragment extends BaseBottomSheetDialogFragment implements CancelDialogIView {


    @BindView(R.id.txtComments)
    EditText comments;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;

    CancelDialogPresenter<CancelDialogFragment> presenter = new CancelDialogPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cancel;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

    }


    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharedHelper.getKey(getContext(), "cancel_id"));
        map.put("cancel_reason", comments.getText().toString());
        showLoading();
        presenter.cancelRequest(map);
    }

    @Override
    public void onSuccessCancel(Object object) {

        dismissAllowingStateLoss();
        hideLoading();

        Intent intent = new Intent(INTENT_FILTER);
        activity().sendBroadcast(intent);


    }

    @Override
    public void onError(Throwable e) {


    }



}
