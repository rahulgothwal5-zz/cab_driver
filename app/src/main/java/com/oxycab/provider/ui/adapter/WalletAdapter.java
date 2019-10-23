package com.oxycab.provider.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxycab.provider.MvpApplication;
import com.oxycab.provider.R;
import com.oxycab.provider.data.network.model.WalletPassbook;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by suthakar@appoets.com on 28-06-2018.
 */
public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    private List<WalletPassbook> list;
    private Context context;

    private ClickListener clickListener;

    NumberFormat numberFormat;

    public WalletAdapter(List<WalletPassbook> list, Context con) {
        this.list = list;
        this.context = con;
        numberFormat = MvpApplication.getNumberFormat();
    }

    public void setList(List<WalletPassbook> list) {
        this.list = list;
    }

    public void setClickListener(WalletAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void openImage(WalletPassbook historyList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_wallet, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WalletPassbook item = list.get(position);

        holder.status.setText(item.getStatus() + " " + numberFormat.format(item.getAmount()));
        holder.date.setText(item.getCreatedAt());
        holder.description.setText(item.getVia() + " " + numberFormat.format(item.getAmount()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout itemView;
        private TextView status, date, description;
        private ImageView image;


        private MyViewHolder(View view) {
            super(view);

            itemView = view.findViewById(R.id.item_view);
            status = view.findViewById(R.id.status);
            date = view.findViewById(R.id.date);
            description = view.findViewById(R.id.description);
        }
    }
}