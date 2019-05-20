package it.unitn.disi.personal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import it.unitn.disi.personal.R;

public class WebPageFragment extends Fragment {

    WebView lightningMapWebView;
    String url;
    boolean needZoom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_webpage,container,false);


        readBundle(getArguments());
        lightningMapWebView = v.findViewById(R.id.wv_lightning);

        lightningMapWebView.setWebViewClient(new WebViewClient());
        lightningMapWebView.loadUrl(url);
        WebSettings webSettings = lightningMapWebView.getSettings();

        if(needZoom) {
            lightningMapWebView.setInitialScale(100);
            webSettings.setBuiltInZoomControls(true);
        }
        webSettings.setJavaScriptEnabled(true);
        return v;
    }
    public static WebPageFragment newInstance(String url, boolean needZoom) {

        Bundle bundle = new Bundle();

        bundle.putString("url", url);
        bundle.putBoolean("needZoom", needZoom);

        WebPageFragment webPageFragment = new WebPageFragment();
        webPageFragment.setArguments(bundle);

        return webPageFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            url = bundle.getString("url");
            needZoom = bundle.getBoolean("needZoom");
        }else{
            url = "https://www.google.com";
        }
    }


}
