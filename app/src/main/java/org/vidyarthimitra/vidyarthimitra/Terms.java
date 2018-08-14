package org.vidyarthimitra.vidyarthimitra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Terms extends AppCompatActivity {
    WebView wb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        wb = (WebView)findViewById(R.id.webview);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl("http://www.vidyarthimitra.org/pages/terms_and_conditions");
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);


    }
    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }
    public void backClick() {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }
}
