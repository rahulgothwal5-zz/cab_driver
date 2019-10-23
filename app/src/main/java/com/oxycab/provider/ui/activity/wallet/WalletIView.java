package com.oxycab.provider.ui.activity.wallet;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.WalletResponse;

public interface WalletIView extends MvpView {
    void onSuccess(WalletResponse walletResponse);
}
