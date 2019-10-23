package com.oxycab.provider.ui.fragment.offline;

import com.oxycab.provider.base.MvpPresenter;

import java.util.HashMap;

public interface OfflineIPresenter<V extends OfflineIView> extends MvpPresenter<V> {
    void providerAvailable(HashMap<String, Object> obj);
}
