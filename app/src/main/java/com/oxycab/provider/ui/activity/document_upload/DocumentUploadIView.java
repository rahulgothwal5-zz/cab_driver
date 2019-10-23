package com.oxycab.provider.ui.activity.document_upload;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.ui.adapter.DocumentAdapter;

public interface DocumentUploadIView extends MvpView {
    void onSuccess(DocumentAdapter adapter);
    void onSuccess(boolean b);
}
