package it.unitn.disi.personal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import it.unitn.disi.personal.HomeAdapter;
import it.unitn.disi.personal.R;
import it.unitn.disi.personal.Utils.HomeData;
import it.unitn.disi.personal.Utils.WeatherData;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    ViewFlipper viewFlipper;
    RecyclerView homeRecyclerView;
    HomeAdapter homeAdapter;
    ArrayList<HomeData> homeData;
    SwipeRefreshLayout swipeRefreshLayout;

    TextView lastUpdateTextView;

    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);


        lastUpdateTextView = v.findViewById(R.id.tv_last_update);
        int images[]  = {
                R.drawable.slideshow1,
                R.drawable.slideshow2,
                R.drawable.slideshow3,
                R.drawable.slideshow4,
                R.drawable.slideshow5,
                R.drawable.slideshow6,
                R.drawable.slideshow7,
                R.drawable.slideshow8,
                R.drawable.slideshow9
        };
        viewFlipper = v.findViewById(R.id.vf_slideshow);

        for(int i = 0; i <images.length; i++){

            flipperImages(images[i]);
        }


        homeData = new ArrayList<>();

        homeRecyclerView = v.findViewById(R.id.rv_home);

        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecyclerView.setHasFixedSize(true);

        homeAdapter = new HomeAdapter(homeData);

        homeRecyclerView.setAdapter(homeAdapter);

        swipeRefreshLayout = v.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                loadRecyclerViewData();
            }
        });

        return v;
    }

    private void loadRecyclerViewData() {

        swipeRefreshLayout.setRefreshing(true);

        homeData.clear();
        loadData();

    }

    private void loadData() {


        RequestQueue requestQueue;
        String url = "http://meteorsago.altervista.org/swpi/meteo.txt";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {


//                    homeData.add(new HomeData(R.drawable.ic_time,getResources().getString(R.string.last_measure_time),
//                            response.getString("last_measure_time")));
                    Pattern pattern = Pattern.compile("\\d\\d\\:\\d\\d");
                    Matcher matcher = pattern.matcher(response.getString("last_measure_time"));
                    while(matcher.find()){
                        String hour = matcher.group(0);
                        lastUpdateTextView.setText("Ultimo aggiornamento: " + hour);
                    }




                    homeData.add(new HomeData(R.drawable.ic_ext_temp,getResources().getString(R.string.ext_temp),
                            String.format("%.1f", response.getDouble("temp_out")) + " °C"));

                    homeData.add(new HomeData(R.drawable.ic_max_temp,getResources().getString(R.string.max_temp),
                            String.format("%.1f", response.getDouble("TempOutMax")) + " °C"));
                    homeData.add(new HomeData(R.drawable.ic_min_temp,getResources().getString(R.string.min_temp),
                            String.format("%.1f", response.getDouble("TempOutMin")) + " °C"));



                    homeData.add(new HomeData(R.drawable.ic_wind,getResources().getString(R.string.wind),
                            String.format("%.2f", response.getDouble("wind_ave")) + " Km/h "
                                    + response.getString("wind_dir_code")));


                    homeData.add(new HomeData(R.drawable.ic_hum,getResources().getString(R.string.ext_hum),
                            String.format("%.1f", response.getDouble("hum_out")) + " %" ));
                    int trend;
                    if (response.getDouble("pressure_trend") > 0){
                        trend = 1;
                    }else if (response.getDouble("pressure_trend") == 0){
                        trend = 0;
                    }else {
                        trend = -1;
                    }
                    homeData.add(new HomeData(R.drawable.ic_press,getResources().getString(R.string.press),
                            String.format("%.2f", response.getDouble("rel_pressure"))
                                    + " hPa", trend));



                    homeData.add(new HomeData(R.drawable.ic_rain,getResources().getString(R.string.daily_rain),
                            String.format("%.2f", response.getDouble("rain_rate")) + " mm"));

                    homeData.add(new HomeData(R.drawable.ic_rain1h, getResources().getString(R.string.hour_rain),
                            String.format("%.2f", response.getDouble("rain_rate_1h")) + " mm"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                homeAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });


        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(jsonObjectRequest);
    }

    public void flipperImages(int image){

        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(image).into(imageView);
//        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);



    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData();
    }
}
