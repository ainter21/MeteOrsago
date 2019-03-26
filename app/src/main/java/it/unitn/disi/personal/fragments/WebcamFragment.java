package it.unitn.disi.personal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.github.chrisbanes.photoview.PhotoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import it.unitn.disi.personal.R;

public class WebcamFragment extends Fragment {


    PhotoView webcamImageView;


    private final String WEBCAM_URL  = "http://meteorsago.altervista.org/swpi/raspimg.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_webcam,container,false);

        webcamImageView = v.findViewById(R.id.iv_webcam);

        loadWebcam();


        return v;

    }




    private void loadWebcam(){
        Glide.with(this).load(WEBCAM_URL)
//                .apply(RequestOptions.skipMemoryCacheOf(true))
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                .into(webcamImageView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.refresh){
            webcamImageView.setImageResource(R.drawable.ic_launcher_background);
            loadWebcam();
        }
        return true;
    }
}
