package com.oxycab.provider.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.oxycab.provider.R;
import com.oxycab.provider.common.SharedHelper;
import com.oxycab.provider.ui.activity.splash.SplashActivity;

import org.json.JSONObject;

import retrofit2.HttpException;
import retrofit2.Response;


public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment implements MvpView  {


    View view;


    public abstract int getLayoutId();


    Dialog progressDialog;

    public abstract void initView(View view);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            initView(view);
        }

//        progressDialog = new ProgressDialog(activity());
//        progressDialog.setMessage("Please wait");
//        progressDialog.setCancelable(false);

        progressDialog = new Dialog(activity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            progressdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCancelable(false);

        return view;
    }



    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
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
                else if (jObjError.has("error")){
                    if (!jObjError.optString("error").equalsIgnoreCase("token_expired")) {
                        Toast.makeText(activity(), jObjError.optString("error"), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Log.e("Error", jObjError.toString());
            } catch (Exception exp) {
                Log.e("Error", exp.getMessage());
            }

            if (response.code() == 401) {
                SharedHelper.clearSharedPreferences(activity());
                activity().finishAffinity();
                startActivity(new Intent(activity(), SplashActivity.class));
            }
        }
    }
}
