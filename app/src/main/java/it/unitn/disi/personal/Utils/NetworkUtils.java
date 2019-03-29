package it.unitn.disi.personal.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";

    public static void callCounter(Context context, String url){

        RequestQueue requestQueue;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: OK");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: NON OK", error);
            }
        });


        requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
    }
}
