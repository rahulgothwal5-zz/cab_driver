package com.oxycab.provider.ui.activity.fleet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.data.network.model.Vehicle;
import com.oxycab.provider.ui.activity.main.MainActivity;
import com.oxycab.provider.ui.adapter.VehicleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VehiclesActivity extends BaseActivity implements FleetIView {


    @BindView(R.id.vehicle_rv)
    RecyclerView carRv;
    VehicleAdapter adapter;
    List<Vehicle> list = new ArrayList<>();
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private FleetPresenter<VehiclesActivity> presenter = new FleetPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicles;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);

        carRv.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        carRv.setItemAnimator(new DefaultItemAnimator());
        adapter = new VehicleAdapter(activity(), list);
        carRv.setAdapter(adapter);

        presenter.vehicles();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSuccessVehicle(List<Vehicle> vehicles) {
        list.clear();
        list.addAll(vehicles);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessVehicle(Object object) {
        finishAffinity();
        startActivity(new Intent(activity(), MainActivity.class));
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (adapter != null && adapter.getSelectedService() != null) {
            Vehicle vehicle = adapter.getSelectedService();
            presenter.setVehicle(vehicle.getId());

        } else {
            Toast.makeText(this, getString(R.string.invalid_vehicle), Toast.LENGTH_SHORT).show();
        }
    }
}
