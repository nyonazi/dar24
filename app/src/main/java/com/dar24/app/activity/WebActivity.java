package com.dar24.app.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dar24.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class WebActivity extends BaseAppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String title;
    private String url;
    private MaterialDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra("title");
        if (title != null) setTitle(title);
        url = getIntent().getStringExtra("url");
        if (url == null) {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        } else if (!url.startsWith("http")) {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgressDialog("Loading", true);
            }

            public void onPageFinished(WebView view, String url) {
                showProgressDialog("", false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().length() == 0) {
                    return false;
                }
                /*This line is commented to stop link click to function in webview*/
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                //view.getContext().startActivity(intent);
                return true;
            }
        });

        webView.loadUrl(url);
    }

    private void showProgressDialog(String title, boolean visible) {
        if (visible) {
            progressDialog = new MaterialDialog.Builder(this)
                    .title(title)
                    .progress(true, 0)
                    .progressIndeterminateStyle(true)
                    .show();
        } else {
            if (progressDialog != null) progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
