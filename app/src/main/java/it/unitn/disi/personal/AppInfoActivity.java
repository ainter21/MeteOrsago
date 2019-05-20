package it.unitn.disi.personal;

import androidx.appcompat.app.AppCompatActivity;
import it.unitn.disi.personal.R;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AppInfoActivity extends AppCompatActivity {

    TextView versionTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Info");

        versionTextView = findViewById(R.id.tv_version);

        try {
            versionTextView.setText(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

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
