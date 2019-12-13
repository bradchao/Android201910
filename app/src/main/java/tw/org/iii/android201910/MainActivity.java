package tw.org.iii.android201910;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText lottery;
    private LocationManager lmgr;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    123);
        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    private void init(){
        lottery = findViewById(R.id.lottery);
        webView = findViewById(R.id.webView);
        initWebView();

        lmgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myListener = new MyListener();
        lmgr.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,0,0,myListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        lmgr.removeUpdates(myListener);
    }

    private class MyListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.v("brad", lat + " , " + lng);

            webView.loadUrl("javascript:moveToLocation(" + lat + ", " + lng + ")");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


    private void initWebView(){
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyBrad(), "brad");

        //webView.loadUrl("https://www.iii.org.tw");
        webView.loadUrl("file:///android_asset/map.html");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    public class MyBrad {
        @JavascriptInterface
        public void callFramJS(String name) {
            Log.v("brad", "i got it => " + name);
        }
    }


    public void test1(View view) {
        webView.loadUrl("javascript:doHere(" + lottery.getText().toString() + ")");
    }
}
