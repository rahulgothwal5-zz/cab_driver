package com.oxycab.provider.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WalletResponse {
    @SerializedName("wallet_balance")
    @Expose
    private Double walletBalance = 0.0;
    @SerializedName("wallet_passbooks")
    @Expose
    private List<WalletPassbook> walletPassbooks = new ArrayList<>();

    public List<WalletPassbook> getWalletPassbooks() {
        return walletPassbooks;
    }

    public void setWalletPassbooks(List<WalletPassbook> walletPassbooks) {
        this.walletPassbooks = walletPassbooks;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }
}
