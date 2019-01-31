package it.unitn.disi.personal;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity{


    TextView extTempTextView;
    TextView windTextView;
    TextView intTempTextView;
    TextView pressureTextView;
    TextView intHumTextView;
    TextView extHumTextView;
    TextView dailyRainTextView;
    TextView windTempTextView;
    TextView hourRainTextView;
    TextView rugTempTextView;
    TextView yearRainTextView;
    TextView maxTempTextView;
    TextView minTempTextView;
    TextView maxWindTextView;
    TextView feelTempTextView;

    Toolbar toolbar;

    private static final String TAG = "MainActivity";
    private final String WEBCAM_URL  = "http://meteorsago.altervista.org/swpi/raspimg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        extTempTextView = findViewById(R.id.tv_ext_temp);
        windTextView = findViewById(R.id.tv_wind);
        intTempTextView = findViewById(R.id.tv_int_temp);
        pressureTextView = findViewById(R.id.tv_press);
        intHumTextView = findViewById(R.id.tv_int_hum);
        extHumTextView = findViewById(R.id.tv_ext_hum);
        dailyRainTextView = findViewById(R.id.tv_daily_rain);
        windTempTextView = findViewById(R.id.tv_wind_temp);
        hourRainTextView = findViewById(R.id.tv_hour_rain);
        rugTempTextView = findViewById(R.id.tv_rug_temp);
        yearRainTextView = findViewById(R.id.tv_year_temp);
        maxTempTextView = findViewById(R.id.tv_max_temp);
        minTempTextView = findViewById(R.id.tv_min_temp);
        maxWindTextView = findViewById(R.id.tv_max_wind);
        feelTempTextView = findViewById(R.id.tv_feel_temp);



        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.refresh){
            loadData();
        }else if(id == R.id.webcam){
            Intent intent = new Intent(MainActivity.this, WebcamActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadData() {


        RequestQueue requestQueue;
        String url = "http://meteorsago.altervista.org/swpi/meteo.txt";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try{
                    extTempTextView.setText(String.format("%.1f", response.getDouble("temp_out")) + " °C");
                    windTextView.setText(String.format("%.2f", response.getDouble("wind_ave")) + " Km/h "
                    + response.getString("wind_dir_code"));
                    intTempTextView.setText(String.format("%.1f", response.getDouble("temp_in"))
                    + " °C");
                    pressureTextView.setText(String.format("%.2f", response.getDouble("rel_pressure"))
                    + " hPa" + (response.getDouble("pressure_trend")>=0? " ↑":" ↓"));
                    extHumTextView.setText(String.format("%.1f", response.getDouble("hum_out"))
                    + " %");
                    intHumTextView.setText(String.format("%.1f", response.getDouble("hum_in"))
                            + " %");
                    dailyRainTextView.setText(String.format("%.1f", response.getDouble("rain_rate_24h"))
                            + " mm");
                    windTempTextView.setText(String.format("%.1f", response.getDouble("wind_chill"))
                            + " °C");
                    hourRainTextView.setText(String.format("%.1f", response.getDouble("rain_rate"))
                            + " mm");
                    rugTempTextView.setText(String.format("%.1f", response.getDouble("dew_point"))
                            + " °C");
                    yearRainTextView.setText(String.format("%.1f", response.getDouble("rain"))
                            + " mm");
                    maxTempTextView.setText(String.format("%.1f", response.getDouble("TempOutMax"))
                            + " °C");
                    minTempTextView.setText(String.format("%.1f", response.getDouble("TempOutMin"))
                            + " °C");
                    maxWindTextView.setText(String.format("%.1f", response.getDouble("winDayMax"))
                            + " Km/h");
                    feelTempTextView.setText(String.format("%.2f", response.getDouble("temp_apparent"))
                            + " °C");


                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: ", error );
            }
        });


        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonObjectRequest);
    }
}
