package com.oxycab.provider.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxycab.provider.MvpApplication;
import com.oxycab.provider.R;
import com.oxycab.provider.data.network.model.Ride;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import static com.oxycab.provider.common.Utilities.getTime;


/**
 * Created by suthakar@appoets.com on 28-06-2018.
 */
public class EarningsTripAdapter extends RecyclerView.Adapter<EarningsTripAdapter.MyViewHolder> {

    private List<Ride> list;
    private Context context;
    private NumberFormat numberFormat;
    public EarningsTripAdapter(List<Ride> list, Context con) {
        this.list = list;
        this.context = con;
        numberFormat = MvpApplication.getNumberFormat();
    }

    public void setList(List<Ride> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_earnings, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ride ride = list.get(position);

        holder.lblDistance.setText(ride.getDistance()+" Km");
        if(ride.getPayment()!=null)
            holder.lblAmount.setText(numberFormat.format(ride.getPayment().getProviderPay()));
        else
            holder.lblAmount.setText("-");
        try {
            holder.lblTime.setText(getTime(ride.getAssignedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView lblTime, lblDistance, lblAmount;

        private MyViewHolder(View view) {
            super(view);

            lblTime = view.findViewById(R.id.lblTime);
            lblDistance = view.findViewById(R.id.lblDistance);
            lblAmount = view.findViewById(R.id.lblAmount);



        }
    }
}