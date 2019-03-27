package it.unitn.disi.personal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.unitn.disi.personal.Utils.HomeData;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    ArrayList<HomeData> homeData;


    public HomeAdapter(ArrayList<HomeData> data){
        homeData = data;
    }

    public void setHomeData(ArrayList<HomeData> homeData) {
        this.homeData = homeData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_data_item, parent,false);

        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {

        if(homeData != null){
            return homeData.size();
        }
        return 0;
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder{


        ImageView iconImageView;
        TextView descriptionTextView;
        TextView dataTextView;
        ImageView trendImageView;


        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            iconImageView = itemView.findViewById(R.id.iv_weather_icon);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            dataTextView = itemView.findViewById(R.id.tv_data);
            trendImageView = itemView.findViewById(R.id.iv_trend);
        }

        public void bind(int listIndex){

            HomeData h = homeData.get(listIndex);

            iconImageView.setImageResource(h.getImage());
            descriptionTextView.setText(h.getDescription());
            dataTextView.setText(h.getData());

            if(h.getTrend() == -2){
                trendImageView.setVisibility(View.INVISIBLE);
            }else if (h.getTrend() == 1){
                trendImageView.setImageResource(R.drawable.ic_uparrow);
            }else if (h.getTrend() == 0){
                trendImageView.setImageResource(R.drawable.ic_equal);
            }else{
                trendImageView.setImageResource(R.drawable.ic_downarrow);
            }

        }
    }
}
