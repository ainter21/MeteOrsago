package it.unitn.disi.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class WebcamActivity extends AppCompatActivity {


    ImageView webcamImageView;
    Toolbar toolbar;

    private final String WEBCAM_URL  = "http://meteorsago.altervista.org/swpi/raspimg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcam);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Webcam");
        webcamImageView = findViewById(R.id.iv_webcam);

        Glide.with(this).load(WEBCAM_URL).into(webcamImageView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
