package com.oxycab.provider.ui.activity.document_upload;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.data.network.model.Document;
import com.oxycab.provider.ui.activity.document.DocumentActivity;
import com.oxycab.provider.ui.activity.document.DocumentPresenter;
import com.oxycab.provider.ui.adapter.DocumentAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class DocumentUploadActivity extends BaseActivity implements DocumentUploadIView {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tv_docHint)
    TextView tvDocHint;
    @BindView(R.id.iv_clickImg)
    ImageView ivClickImg;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.iv_imagePreview)
    ImageView ivImagePreview;
    @BindView(R.id.et_docId)
    EditText etDocId;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    Document document;
    DocumentUploadPresenter<DocumentUploadActivity> presenter = new DocumentUploadPresenter<DocumentUploadActivity>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_document_upload;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        document = (Document) getIntent().getSerializableExtra("document");
        tvDocHint.setText( "Upload " + (document.getName()));
    }


    @Override
    public void onSuccess(DocumentAdapter adapter) {

    }

    @Override
    public void onSuccess(boolean b) {
        if (b){
            onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL +document.getUrl()).apply(RequestOptions.placeholderOf(R.drawable.ic_photo_camera).dontAnimate().error(R.drawable.ic_photo_camera)).into(ivImagePreview);
    }

    @OnClick({R.id.card, R.id.tv_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card:
                pickImage();
                break;
            case R.id.tv_upload:
                List<MultipartBody.Part> myDocuments = new ArrayList<>();

                        RequestBody requestImageFile = RequestBody.create(MediaType.parse("image/*"), document.getDocument());
                        myDocuments.add(MultipartBody.Part.createFormData("document[" + document.getId() + "]", document.getDocument().getName(), requestImageFile));

                if (!myDocuments.isEmpty()) {
                    presenter.documents(myDocuments);
                } else {
                    Toast.makeText(this, "Please select documents", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void pickImage() {
        if (hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyImage.openChooserWithGallery(this, "", 0);
        } else {
            requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, DocumentUploadActivity.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                try {
                    File imgFile = new Compressor(activity()).compressToFile(imageFiles.get(0));
                    document.setDocument(imgFile);
                    Glide.with(activity()).load(document.getDocument()).apply(RequestOptions.placeholderOf(R.drawable.ic_photo_camera).dontAnimate().error(R.drawable.ic_photo_camera)).into(ivImagePreview);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ASK_MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean permission1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean permission2 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (permission1 && permission2) {
                        pickImage();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.please_give_permissions, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

}
