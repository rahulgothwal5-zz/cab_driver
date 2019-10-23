package com.oxycab.provider.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.oxycab.provider.R;
import com.oxycab.provider.data.network.model.Vehicle;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 08-05-2018.
 */
public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    private List<Vehicle> list;
    private Context context;
    private ClickListener clickListener;
    private int lastCheckedPos = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout itemView;
        private ImageView image;
        private TextView vehicleNumber, vehicleModel;
        private RadioButton selectionState;

        MyViewHolder(View view) {
            super(view);
            itemView = view.findViewById(R.id.itemView);
            image = view.findViewById(R.id.image);
            vehicleNumber = view.findViewById(R.id.vehicle_number);
            vehicleModel = view.findViewById(R.id.vehicle_model);
            selectionState = view.findViewById(R.id.selection_state);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Vehicle object = list.get(position);
            if (view.getId() == R.id.itemView) {
                lastCheckedPos = position;
                notifyDataSetChanged();
                //clickListener.click(object);
            }
        }
    }

    public VehicleAdapter(Context context, List<Vehicle> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_car, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vehicle obj = list.get(position);
        holder.vehicleNumber.setText(obj.getVehicleNumber());
        holder.vehicleModel.setText(obj.getVehicleModel());
        holder.selectionState.setChecked(lastCheckedPos == position);
        /*Service service = obj.getService();
        if (service != null) {
            Glide.with(context).load(service.getImage()).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(holder.image);
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void click(Vehicle vehicle);
    }

    public Vehicle getSelectedService() {
        if (list.size() > 0) {
            return list.get(lastCheckedPos);
        } else {
            return null;
        }
    }
}
