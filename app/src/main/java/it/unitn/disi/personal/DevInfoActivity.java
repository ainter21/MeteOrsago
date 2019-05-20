package it.unitn.disi.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DevInfoActivity extends AppCompatActivity {


    TextView emailTextView;
    TextView phoneTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dev);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Info");


        emailTextView = findViewById(R.id.tv_email);
        phoneTextView = findViewById(R.id.tv_phone);


        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("alberto.giust21@gmail.com") +
                        "?subject=" + Uri.encode("App: MeteOrsago");
                Uri uri = Uri.parse(uriText);

                intent.setData(uri);
                startActivity(intent);
            }
        });


        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent addContactIntent = new Intent(Intent.ACTION_INSERT);
                addContactIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME,"Alberto Giust");
                addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, getResources().getString(R.string.phone));
                addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, getResources().getString(R.string.email));
                startActivity(addContactIntent);
            }
        });

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
