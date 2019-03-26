package it.unitn.disi.personal;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import it.unitn.disi.personal.fragments.DataFragment;

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

public class MainActivity extends AppCompatActivity{


//    TextView extTempTextView;
//    TextView windTextView;
//    TextView intTempTextView;
//    TextView pressureTextView;
//    TextView intHumTextView;
//    TextView extHumTextView;
//    TextView dailyRainTextView;
//    TextView windTempTextView;
//    TextView hourRainTextView;
//    TextView rugTempTextView;
//    TextView yearRainTextView;
//    TextView maxTempTextView;
//    TextView minTempTextView;
//    TextView maxWindTextView;
//    TextView feelTempTextView;

    Toolbar toolbar;

    private static final String TAG = "MainActivity";
    private final String WEBCAM_URL  = "http://meteorsago.altervista.org/swpi/raspimg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        extTempTextView = findViewById(R.id.tv_ext_temp);
//        windTextView = findViewById(R.id.tv_wind);
//        intTempTextView = findViewById(R.id.tv_int_temp);
//        pressureTextView = findViewById(R.id.tv_press);
//        intHumTextView = findViewById(R.id.tv_int_hum);
//        extHumTextView = findViewById(R.id.tv_ext_hum);
//        dailyRainTextView = findViewById(R.id.tv_daily_rain);
//        windTempTextView = findViewById(R.id.tv_wind_temp);
//        hourRainTextView = findViewById(R.id.tv_hour_rain);
//        rugTempTextView = findViewById(R.id.tv_rug_temp);
//        yearRainTextView = findViewById(R.id.tv_year_temp);
//        maxTempTextView = findViewById(R.id.tv_max_temp);
//        minTempTextView = findViewById(R.id.tv_min_temp);
//        maxWindTextView = findViewById(R.id.tv_max_wind);
//        feelTempTextView = findViewById(R.id.tv_feel_temp);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataFragment()).commit();

//        loadData();

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
//            loadData();
        }else if(id == R.id.webcam){
            Intent intent = new Intent(MainActivity.this, WebcamActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



}
