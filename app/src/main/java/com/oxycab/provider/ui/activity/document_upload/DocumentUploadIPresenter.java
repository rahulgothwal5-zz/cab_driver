package com.oxycab.provider.ui.activity.document_upload;

import com.oxycab.provider.base.MvpPresenter;

import java.util.List;

import okhttp3.MultipartBody;

public interface DocumentUploadIPresenter<V extends DocumentUploadIView> extends MvpPresenter<V> {
    void documents(List<MultipartBody.Part> documents);
}
