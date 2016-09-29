package login.demo.stack.stack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button login;
    WebView webView;
    String client_id=BuildConfig.client_id;
    String url ="https://stackexchange.com/oauth/dialog?client_id="+client_id+"&scope=private_info&redirect_uri=https://balajimanikandan.netau.net";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button)findViewById(R.id.login);
        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                fetchToken();

            }
        });

    }

    public void fetchToken()
    {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("#access_token")) {
                    String token_str = url;
                    String[] str = token_str.split("access_token=");
                    String token = str[1].substring(0, str[1].length() - 14);
                    Log.i("token", "token: " + token);
                    webView.setVisibility(View.GONE);
                    Intent intent = new Intent(MainActivity.this,ApiActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    finish();

                }
            }

        });
        webView.loadUrl(url);
    }

    }
