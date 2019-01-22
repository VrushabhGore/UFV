package ufv.ca.abby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ufvonline extends AppCompatActivity {

    WebView ufvonline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ufvonline);

        ufvonline=(WebView) findViewById(R.id.ufv_online_site);
        ufvonline.loadUrl("http://www.ufv.ca/");
        ufvonline.getSettings().setJavaScriptEnabled(true);
        ufvonline.setWebViewClient(new WebViewClient());
        ufvonline.getSettings().setBuiltInZoomControls(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("UFV Online");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ufvonline.this,homescreen.class));
        this.overridePendingTransition(R.anim.swipe_to_right,
                R.anim.swipe_to_left);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
