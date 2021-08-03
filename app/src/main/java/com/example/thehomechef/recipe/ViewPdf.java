package com.example.thehomechef.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URLEncoder;import com.example.thehomechef.R;

public class ViewPdf extends AppCompatActivity {

    WebView showpdf;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        showpdf= findViewById(R.id.showpdf);
        showpdf.getSettings().setJavaScriptEnabled(true);

        String filename = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("opening...!!!");

        showpdf.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view , url , favicon);
                pd.show();
            }
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view , url );
                pd.dismiss();
            }
        });

        String  url = "";
        try {
        url = URLEncoder.encode(fileurl, "UTF-8");
        }catch(Exception ignored)
        {

        }

        showpdf.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);



        }
}
