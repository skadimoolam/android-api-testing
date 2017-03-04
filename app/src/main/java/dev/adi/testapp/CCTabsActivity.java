package dev.adi.testapp;

import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CCTabsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctabs);
    }

    public void openCustomTab(View view) {
        String url = "https://google.com";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#111111"));
        builder.addDefaultShareMenuItem();
        builder.setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(CCTabsActivity.this, Uri.parse(url));
    }
}
