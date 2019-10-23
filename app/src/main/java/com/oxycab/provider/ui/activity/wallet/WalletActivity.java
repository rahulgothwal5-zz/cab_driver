package com.oxycab.provider.ui.activity.wallet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.oxycab.provider.MvpApplication;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.SharedHelper;
import com.oxycab.provider.data.network.model.WalletPassbook;
import com.oxycab.provider.data.network.model.WalletResponse;
import com.oxycab.provider.ui.adapter.WalletAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletActivity extends BaseActivity implements WalletIView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.wallet_balance)
    TextView walletBalance;
    @BindView(R.id.wallet_rv)
    RecyclerView walletRv;
    @BindView(R.id.error_layout)
    TextView errorLayout;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    List<WalletPassbook> list = new ArrayList<>();
    WalletAdapter adapter;
    WalletPresenter<WalletActivity> presenter = new WalletPresenter<>();

    NumberFormat numberFormat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {

        ButterKnife.bind(this);
        presenter.attachView(this);
        numberFormat = MvpApplication.getNumberFormat();
        swipeContainer.setOnRefreshListener(this);
        adapter = new WalletAdapter(list, activity());
        walletRv.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        walletRv.setItemAnimator(new DefaultItemAnimator());
        walletRv.setAdapter(adapter);

        walletHistory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void walletHistory() {
        swipeContainer.setRefreshing(true);
        presenter.walletHistory();
    }

    @Override
    public void onRefresh() {
        walletHistory();
    }

    @Override
    public void onSuccess(WalletResponse walletResponse) {
        swipeContainer.setRefreshing(false);
        walletBalance.setText(numberFormat.format(walletResponse.getWalletBalance()));

        SharedHelper.putKey(activity(), "wallet_balance", String.valueOf(walletResponse.getWalletBalance()));

        this.list.clear();
        this.list.addAll(walletResponse.getWalletPassbooks());
        adapter.notifyDataSetChanged();

        errorLayout.setVisibility(list.size() == 0 ? View.VISIBLE : View.GONE);
    }
}
