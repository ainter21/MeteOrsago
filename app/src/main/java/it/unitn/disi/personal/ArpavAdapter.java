package it.unitn.disi.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;

import it.unitn.disi.personal.Utils.Forecast;

public class ArpavAdapter extends RecyclerView.Adapter<ArpavAdapter.ArpavViewHolder> {


    ArrayList<Forecast> forecasts;


    public ArpavAdapter(ArrayList<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public void setForecasts(ArrayList<Forecast> forecasts) {
        this.forecasts = forecasts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArpavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.arpav_item, parent, false);
        return new ArpavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArpavViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {

        if (forecasts != null) {
            return forecasts.size();
        }
        return 0;
    }

    public class ArpavViewHolder extends RecyclerView.ViewHolder {

        ImageView forecastImage1;
        ImageView forecastImage2;
        TextView forecastDate;
        TextView forecastMoment1;
        TextView forecastMoment2;
        Context context;

        public ArpavViewHolder(View v) {

            super(v);
            context = v.getContext();
            forecastImage1 = v.findViewById(R.id.iv_forecast);
            forecastDate = v.findViewById(R.id.tv_forecast_date);


        }

        public void bind(int listIndex) {
            Forecast f = forecasts.get(listIndex);

            forecastDate.setText(f.getDate());
            loadArpavImage(context, f.getImageUrl(), forecastImage1);

        }

        private void loadArpavImage(Context context, String url, ImageView imageView){
            Glide.with(context).load(url)
//                .apply(RequestOptions.skipMemoryCacheOf(true))
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                    .into(imageView);
        }
    }



}
