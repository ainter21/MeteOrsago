package it.unitn.disi.personal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import it.unitn.disi.personal.MainActivity;
import it.unitn.disi.personal.R;
import it.unitn.disi.personal.Utils.WeatherData;
import it.unitn.disi.personal.WeatherDataAdapter;

public class DataFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView dataRecyclerView;
    WeatherDataAdapter weatherDataAdapter;
    ArrayList<WeatherData> weatherData;
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = "DataFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_data, container, false);

        weatherData = new ArrayList<>();
        dataRecyclerView = v.findViewById(R.id.rv_weather_data);

        dataRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataRecyclerView.setHasFixedSize(true);

        weatherDataAdapter = new WeatherDataAdapter(weatherData);

        dataRecyclerView.setAdapter(weatherDataAdapter);

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

        weatherData.clear();
        loadData();

    }

    @Override
    public void onRefresh() {

        loadRecyclerViewData();
    }


    private void loadData() {


        RequestQueue requestQueue;
        String url = "http://meteorsago.altervista.org/swpi/meteo.txt";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {

                    weatherData.add(new WeatherData(getResources().getString(R.string.ext_temp),
                            String.format("%.1f", response.getDouble("temp_out")) + " °C"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.int_temp),
                            String.format("%.1f", response.getDouble("temp_in")) + " °C"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.wind),
                            String.format("%.2f", response.getDouble("wind_ave")) + " Km/h "
                            + response.getString("wind_dir_code")));
                    weatherData.add(new WeatherData(getResources().getString(R.string.wind_temp),
                            String.format("%.1f", response.getDouble("wind_chill")) + " °C"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.press),
                            String.format("%.2f", response.getDouble("rel_pressure"))
                            + " hPa" + (response.getDouble("pressure_trend") >= 0 ? " ↑" : " ↓") ));
                    weatherData.add(new WeatherData(getResources().getString(R.string.ext_hum),
                            String.format("%.1f", response.getDouble("hum_out")) + " %" ));
                    weatherData.add(new WeatherData(getResources().getString(R.string.int_hum),
                            String.format("%.1f", response.getDouble("hum_in")) + " %"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.daily_rain),
                            String.format("%.1f", response.getDouble("rain_rate_24h")) + " mm"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.hour_rain),
                            String.format("%.1f", response.getDouble("rain_rate")) + " mm"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.rug_temp),
                            String.format("%.1f", response.getDouble("dew_point")) + " °C"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.year_rain),
                            String.format("%.1f", response.getDouble("rain")) + " mm"));

                    weatherData.add(new WeatherData(getResources().getString(R.string.max_temp),
                            String.format("%.1f", response.getDouble("TempOutMax")) + " °C"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.min_temp),
                            String.format("%.1f", response.getDouble("TempOutMin")) + " °C"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.max_wind),
                            String.format("%.1f", response.getDouble("winDayMax")) + " Km/h"));
                    weatherData.add(new WeatherData(getResources().getString(R.string.feel_temp),
                            String.format("%.2f", response.getDouble("temp_apparent")) + " °C"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                weatherDataAdapter.notifyDataSetChanged();
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
}
