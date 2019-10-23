package com.oxycab.provider.ui.activity.document;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Document;
import com.oxycab.provider.ui.adapter.DocumentAdapter;

import java.util.List;

public interface DocumentIView extends MvpView {
    void onSuccess(List<Document> documents);
}
