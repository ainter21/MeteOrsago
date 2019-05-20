package it.unitn.disi.personal;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import it.unitn.disi.personal.Utils.NetworkUtils;
import it.unitn.disi.personal.fragments.DataFragment;
import it.unitn.disi.personal.fragments.HomeFragment;
import it.unitn.disi.personal.fragments.WebPageFragment;
import it.unitn.disi.personal.fragments.WebcamFragment;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    Toolbar toolbar;

    private static final String TAG = "MainActivity";
    //private final String WEBCAM_URL = "http://meteorsago.altervista.org/swpi/raspimg.php";

    final String FIRST_TIME_PREFS = "FirstTimePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences firstTime = getSharedPreferences(FIRST_TIME_PREFS,MODE_PRIVATE);

        if(firstTime.getBoolean("my_first_time", true)){

            firstTime.edit().putBoolean("my_first_time", false).commit();
            NetworkUtils.callCounter(this, "https://meteorsago-server.herokuapp.com/newUser");
        }
        NetworkUtils.callCounter(this, "https://meteorsago-server.herokuapp.com/newAccess");

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //setto imageview sull headere della navigation view
        View v = navigationView.getHeaderView(0);
        ImageView headerImageView = (ImageView) v.findViewById(R.id.iv_nav_header);
        Glide.with(this).load(R.drawable.slideshow1).into(headerImageView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                return true;
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.info_dev:
                Intent intent = new Intent(MainActivity.this, DevInfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.info_app:
                Intent intent1 = new Intent(MainActivity.this, AppInfoActivity.class);
                startActivity(intent1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

//        Toast.makeText(this, "entro", Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawers();
        int itemId = menuItem.getItemId();

        switch (itemId) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_data:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataFragment()).commit();
                break;
            case R.id.nav_webcam:
//                Toast.makeText(this, String.valueOf(R.id.nav_webcam), Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WebcamFragment()).commit();
                break;
            case R.id.nav_website:
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://meteorsago.altervista.org/swpi/"));
//                startActivity(browserIntent);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, WebPageFragment.newInstance("http://meteorsago.altervista.org/swpi/", true)).commit();
                break;
            case R.id.nav_rate_us:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("alberto.giust21@gmail.com") +
                        "?subject=" + Uri.encode("Feedback!");
                Uri uri = Uri.parse(uriText);

                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.nav_lightning:
//                Intent lightningIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://it.blitzortung.org/live_dynamic_maps2.php?map=10"));
//                startActivity(lightningIntent);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, WebPageFragment.newInstance("http://map.blitzortung.org/#2.55/44.45/24.35", false)).commit();
                break;
            case R.id.nav_protezione_civile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, WebPageFragment.newInstance("http://www.protezionecivile.gov.it/attivita-rischi/meteo-idro/attivita/previsione-prevenzione/centro-funzionale-centrale-rischio-meteo-idrogeologico/monitoraggio-sorveglianza/mappa-radar", true)).commit();


        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
