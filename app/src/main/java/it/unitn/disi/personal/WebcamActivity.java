package it.unitn.disi.personal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class WebcamActivity extends AppCompatActivity {


    PhotoView webcamImageView;




    private final String WEBCAM_URL  = "http://meteorsago.altervista.org/swpi/raspimg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcam);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Webcam");
        webcamImageView = findViewById(R.id.iv_webcam);


        loadWebcam();





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }else if(id==R.id.refresh){
            loadWebcam();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.refresh, menu);

        MenuItem menuItem = menu.findItem(R.id.webcam);
        menuItem.setVisible(false);
        return true;
    }

    private void loadWebcam(){
        Glide.with(this).load(WEBCAM_URL).into(webcamImageView);
    }





}
