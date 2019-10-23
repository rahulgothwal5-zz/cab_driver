package com.oxycab.provider.ui.activity.notification;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;

import com.oxycab.provider.data.network.model.Notification;
import com.oxycab.provider.ui.activity.photoview.PhotoViewActivity;
import com.oxycab.provider.ui.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends BaseActivity implements NotificationIView, SwipeRefreshLayout.OnRefreshListener, NotificationAdapter.ClickListener {

    @BindView(R.id.orders_rv)
    RecyclerView ordersRv;
    @BindView(R.id.error_layout)
    TextView errorLayout;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    List<Notification> list = new ArrayList<>();
    NotificationAdapter adapter;
    NotificationPresenter<NotificationActivity> presenter = new NotificationPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        swipeContainer.setOnRefreshListener(this);
        adapter = new NotificationAdapter(list, activity());
        ordersRv.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        ordersRv.setItemAnimator(new DefaultItemAnimator());
        adapter.setClickListener(this);
        ordersRv.setAdapter(adapter);

        notifications();

    }

    private void notifications() {
        swipeContainer.setRefreshing(true);
        presenter.notifications();
    }

    @Override
    public void onRefresh() {
        notifications();
    }

    @Override
    public void onSuccess(List<Notification> notifications) {
        swipeContainer.setRefreshing(false);
        this.list.clear();
        this.list.addAll(notifications);
        adapter.notifyDataSetChanged();

        errorLayout.setVisibility(list.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void openImage(Notification item) {
        if(item.getImage() != null){
            Intent intent = new Intent(activity(), PhotoViewActivity.class);
            intent.putExtra("url", item.getImage());
            startActivity(intent);
        }
    }
}
