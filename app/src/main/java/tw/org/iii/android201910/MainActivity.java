package tw.org.iii.android201910;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lottery = findViewById(R.id.lottery);
        webView = findViewById(R.id.webView);
        initWebView();
    }

    private void initWebView(){
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyBrad(), "brad");

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
