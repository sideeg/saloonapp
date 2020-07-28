package com.sideeg.saloonapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.models.SaloonServiceData;
import com.sideeg.saloonapp.networking.ApiClient;
import com.sideeg.saloonapp.utility.LocalSession;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    Context mContext;
    List<SaloonServiceData> saloonServiceData;
    LocalSession localSession;

    public ServiceAdapter(Context mContext, List<SaloonServiceData> saloonServiceData) {
        this.mContext = mContext;
        this.saloonServiceData = saloonServiceData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View theView = LayoutInflater.from(mContext).inflate(R.layout.salon_sevice_item, parent, false);
        return new ViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            if (saloonServiceData.get(position).getServices() != null) {
                String image_url = ApiClient.URL +saloonServiceData.get(position).getServices().getPhoto_full_path();
                Glide.with(mContext)
                        .load(image_url)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imageView);
                holder.serviceName.setText(saloonServiceData.get(position).getServices().getService_name());
                holder.servicePrice.setText(saloonServiceData.get(position).getPrice() + " SDG");

        }
    }

    @Override
    public int getItemCount() {
        return saloonServiceData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView serviceName , servicePrice;

        public ViewHolder(View theView) {
            super(theView);
            serviceName = theView.findViewById(R.id.service_name_textView);
            servicePrice = theView.findViewById(R.id.service_price_textview);
            imageView = theView.findViewById(R.id.circleImageView);

        }
    }
}
