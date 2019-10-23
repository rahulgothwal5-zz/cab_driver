package com.oxycab.provider.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.data.network.model.Notification;

import java.util.List;

/**
 * Created by suthakar@appoets.com on 28-06-2018.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<Notification> list;
    private Context context;

    private ClickListener clickListener;

    public NotificationAdapter(List<Notification> list, Context con) {
        this.list = list;
        this.context = con;
    }

    public void setList(List<Notification> list) {
        this.list = list;
    }

    public void setClickListener(NotificationAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void openImage(Notification historyList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification item = list.get(position);

        holder.description.setText(item.getMessage());
        holder.image.setVisibility(item.getImage() == null ? View.GONE : View.VISIBLE);
        Glide.with(context).load(BuildConfig.BASE_IMAGE_URL + item.getImage()).apply(RequestOptions.placeholderOf(R.drawable.ic_photo_camera).dontAnimate().error(R.drawable.ic_photo_camera)).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView itemView;
        private TextView description;
        private ImageView image;


        private MyViewHolder(View view) {
            super(view);

            itemView = view.findViewById(R.id.item_view);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.openImage(list.get(getAdapterPosition()));
                }
            });
        }
    }
}