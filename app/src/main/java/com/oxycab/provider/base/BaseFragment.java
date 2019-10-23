package com.oxycab.provider.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.oxycab.provider.R;


/**
 * Created by Lenovo on 02-04-2018.
 */

public abstract class BaseFragment extends Fragment implements MvpView {

    View view;
    Dialog progressDialog;
    public abstract int getLayoutId();
    public  abstract View initView(View view);


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    public FragmentActivity activity() {
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



}
