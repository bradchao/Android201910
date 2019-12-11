package tw.org.iii.android201910;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        initWebView();
    }

    private void initWebView(){
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        //webView.loadUrl("https://www.iii.org.tw");
        webView.loadUrl("file:///android_asset/brad.html");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
