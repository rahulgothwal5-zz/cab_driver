package com.oxycab.provider.ui.activity.document;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.RecyclerItemClickListener;
import com.oxycab.provider.data.network.model.Document;
import com.oxycab.provider.ui.activity.document_upload.DocumentUploadActivity;
import com.oxycab.provider.ui.activity.main.MainActivity;
import com.oxycab.provider.ui.adapter.DocumentListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;

/*
    created by santhosh@appoets.com
*/

public class DocumentActivity extends BaseActivity implements DocumentIView {

    @BindView(R.id.document_rv)
    RecyclerView documentRv;

    DocumentPresenter<DocumentActivity> presenter = new DocumentPresenter<DocumentActivity>();
    DocumentListAdapter personalAdapter, driverAdapter;
    ArrayList<Document> personalDocuments = new ArrayList<>();
    ArrayList<Document> driverDocuments = new ArrayList<>();
    Integer currentPosition;
    boolean fromRegister = false;
    @BindView(R.id.rv_personalDetails)
    RecyclerView rvPersonalDetails;


    @Override
    public int getLayoutId() {
        return R.layout.activity_document;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        if (getIntent().getBooleanExtra("first", false) == true) {
            fromRegister = true;
            if (getSupportActionBar() != null) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.documents();
    }

    public void pickImage() {
        if (hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyImage.openChooserWithGallery(this, "", 0);
        } else {
            requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onSuccess(List<Document> documents) {
        driverAdapter = null;
        personalAdapter = null;
        driverDocuments.clear();
        personalDocuments.clear();

        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getType().equalsIgnoreCase("driver")) {
                driverDocuments.add(documents.get(i));
            } else {
                personalDocuments.add(documents.get(i));
            }
        }
        driverAdapter = new DocumentListAdapter(activity(), driverDocuments);
        personalAdapter = new DocumentListAdapter(activity(), personalDocuments);


        documentRv.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        documentRv.setItemAnimator(new DefaultItemAnimator());

        documentRv.setAdapter(driverAdapter);
        documentRv.addOnItemTouchListener(new RecyclerItemClickListener(this, documentRv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(activity(), DocumentUploadActivity.class);
                intent.putExtra("document", driverDocuments.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        rvPersonalDetails.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        rvPersonalDetails.setItemAnimator(new DefaultItemAnimator());

        rvPersonalDetails.setAdapter(personalAdapter);
        rvPersonalDetails.addOnItemTouchListener(new RecyclerItemClickListener(this, rvPersonalDetails, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(activity(), DocumentUploadActivity.class);
                intent.putExtra("document", personalDocuments.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }


    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (fromRegister){
            finishAffinity();
            startActivity(new Intent(activity(), MainActivity.class));
        }else{
            onBackPressed();
        }

    }
}



