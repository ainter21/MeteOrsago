package it.unitn.disi.personal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import it.unitn.disi.personal.ArpavAdapter;
import it.unitn.disi.personal.R;
import it.unitn.disi.personal.Utils.Forecast;

public class ArpavFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "ArpavFragment";
    ArrayList<Forecast> forecasts;
    ArpavAdapter arpavAdapter;
    RecyclerView arpavRecylcerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_arpav, container, false);
        forecasts = new ArrayList<>();
        arpavRecylcerView = v.findViewById(R.id.rv_arpav);
        arpavRecylcerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        arpavRecylcerView.setHasFixedSize(true);
        arpavAdapter = new ArpavAdapter(forecasts);
        arpavRecylcerView.setAdapter(arpavAdapter);

        swipeRefreshLayout = v.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                loadRecyclerData();
            }
        });
        setHasOptionsMenu(true);


        return v;
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        loadRecyclerData();
    }


    private void loadRecyclerData() {
        Log.d(TAG, "loadRecyclerData: load");
        swipeRefreshLayout.setRefreshing(true);
        forecasts.clear();
        loadData();
    }

    private void loadData() {

        RequestQueue requestQueue;

        String url = "https://www.arpa.veneto.it/previsioni/it/xml/bollettino_utenti.xml";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: entro volley");
                parseXml(response);
                arpavAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, error.toString());
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    private void parseXml(String s) {

        boolean meteoVeneto = false;
        Log.d(TAG, "parseXml: " + s);

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(s));
            int eventType = xpp.getEventType();
            Log.d(TAG, "parseXml: " + eventType);
            Forecast currentForecast = null;


            while (eventType != XmlPullParser.END_DOCUMENT) {

                String eltName = null;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        eltName = xpp.getName();

                        if (eltName.equals("bollettino")) {
                            if (xpp.getAttributeValue(null, "name").equals("Meteo Veneto")) {
                                meteoVeneto = true;
                            } else {
                                meteoVeneto = false;
                            }
                        } else if (eltName.equals("img") && meteoVeneto) {
                            currentForecast = new Forecast();
                            currentForecast.setImageUrl(xpp.getAttributeValue(null, "src"));
                            currentForecast.setDate(xpp.getAttributeValue(null, "caption"));
                            forecasts.add(currentForecast);
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
