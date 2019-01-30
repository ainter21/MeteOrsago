package it.unitn.disi.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {


    ImageView webcamImageView;
    private final String WEBCAM_URL  = "http://meteorsago.altervista.org/swpi/raspimg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webcamImageView = findViewById(R.id.imageView);


        Glide.with(this).load(WEBCAM_URL).apply(RequestOptions.centerCropTransform()).into(webcamImageView);
    }
}
