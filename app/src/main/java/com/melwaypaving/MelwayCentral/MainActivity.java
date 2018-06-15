/************************************************************************************************
 *                                             CHANGE LOG                                       *
 ************************************************************************************************
 * v0.6.2
 * Added support for notifications
 *
 * v0.6.3
 * Fixed some compatibility errors for Android versions below API 24 (Noughat)
 *
 * v0.6.4
 * Improved compatibility to include Android KitKat and above
 * Android versions below API 19 (KitKat) can run the app
 * without crashing but display content incorrectly
 * Changed Min API to 19 (KitKat)
 *
 * v0.6.5
 * Changed to new website on SquareSpace to allow for authentication
 *
 * v0.6.6
 * Changed color scheme to better match website
 *
 * v0.6.7
 * Changed color scheme again
 * Added round icon option
 * Removed extra icons to reduce unneeded clutter
 * Changed App Name from WACI-MW to Melway Central
 *
 * v0.6.8
 * Changed color scheme back
 * Now prevents screen rotation on smaller dp screens (most phones)
 *
 * v1.0.0
 * Released! Woooooo!
 *
 * v1.1.0
 * Added Loading Spinner
 * Added Home button to bottom-right of screen
 * Removed Title Bar
 * Updated libraries
 * Decreased app size significantly
 * Commented out unneeded code
 * Changed back button functionality, works more consistently
 * Testing Hardware acceleration performance
 * Other minor improvements and optimizations
 ************************************************************************************************/

package com.melwaypaving.MelwayCentral;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView = null;
    private ProgressBar mPbar = null; // Spinning loading bar

    ImageButton FAB; // Floating Action Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to prevent screen rotation on smaller screens (most phones)
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        FAB = (ImageButton) findViewById(R.id.imageButton); // creates Floating button
        FAB.setOnClickListener(new View.OnClickListener() { // onClick take to home page
            @Override
            public void onClick(View v) {
                myWebView.loadUrl(getString(R.string.urlString));
            }
        });

        this.myWebView = (WebView) findViewById(R.id.webview);
        mPbar = (ProgressBar) findViewById(R.id.webview_progress);
        myWebView.getSettings().setJavaScriptEnabled(true); // allows Javascript for the webpage
        myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        myWebView.setWebViewClient(new WebViewClient()); // displays webpage inside app
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                mPbar.setProgress(progress);
                if (progress >= 100) {
                    mPbar.setVisibility(View.GONE);
                } else {
                    mPbar.setVisibility(View.VISIBLE);
                }
            }
        });
        myWebView.loadUrl(getString(R.string.urlString)); // loads webpage url
    } // End of onCreate()

    /* Alternate method to use back button in webview, seems to work a bit better */
    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    
    /* Don't need this unless there are some urls I want to
       open in external browser rather than webview */
    /* Override WebViewClient to display webpage inside app */
//    public class myWebViewClient extends WebViewClient {
//        @SuppressWarnings("deprecation")
//        @Override
//        /* This is deprecated with Android N, fix this at some point... */
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//
//        @TargetApi(Build.VERSION_CODES.N)
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(request.getUrl().toString());
//            return true;
//        }
//    } // End of myWebViewClient()


} // End of MainActivity
