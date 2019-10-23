package com.oxycab.provider.ui.activity.wallet;

import com.oxycab.provider.base.MvpPresenter;

public interface WalletIPresenter<V extends WalletIView> extends MvpPresenter<V> {
    void walletHistory();
}
