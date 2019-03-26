package it.unitn.disi.personal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.unitn.disi.personal.Utils.WeatherData;

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder> {

    ArrayList<WeatherData> weatherData;

    public WeatherDataAdapter(ArrayList<WeatherData> data){

        weatherData = data;
    }

    public void setWeatherData(ArrayList<WeatherData> weatherData) {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new WeatherDataViewHolder(v);
    }

    @Override
    public int getItemCount() {
        if (weatherData != null){
            return weatherData.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDataViewHolder holder, int position) {
        holder.bind(position);
    }

    public class WeatherDataViewHolder extends RecyclerView.ViewHolder{

        TextView descriptinTextView;
        TextView dataTextView;
        ImageView trendImageView;

        public WeatherDataViewHolder(View v){

            super(v);

            descriptinTextView = v.findViewById(R.id.tv_description);
            dataTextView = v.findViewById(R.id.tv_data);
            trendImageView = v.findViewById(R.id.iv_trend);
        }

        public void bind(int listIndex){

            WeatherData w = weatherData.get(listIndex);

            descriptinTextView.setText(w.getDescription());
            dataTextView.setText(w.getData());
            if(w.getTrend() == -2){
                trendImageView.setVisibility(View.INVISIBLE);
            }else if (w.getTrend() == 1){
                trendImageView.setImageResource(R.drawable.ic_uparrow);
            }else if (w.getTrend() == 0){
                trendImageView.setImageResource(R.drawable.ic_equal);
            }else{
                trendImageView.setImageResource(R.drawable.ic_downarrow);
            }

        }
    }
}
