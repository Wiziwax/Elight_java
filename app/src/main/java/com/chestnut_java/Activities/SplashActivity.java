package com.chestnut_java.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chestnut_java.Adapters.SplashAdapter;
import com.chestnut_java.R;
import com.chestnut_java.SplashItem;

import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<SplashItem> splashItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        viewPager = findViewById(R.id.viewPager);

        // Create the pages
        splashItems = new ArrayList<>();
        splashItems.add(new SplashItem("Power Up Your Day", "Get notified the moment electricity is restored in your area.", R.drawable.elight1));
        splashItems.add(new SplashItem("Stay Updated", "Monitor power distribution in your neighborhood.", R.drawable.elight2));
        splashItems.add(new SplashItem("Take Charge", "Control your energy consumption effectively.", R.drawable.elight3));

        // Set up the adapter
        SplashAdapter adapter = new SplashAdapter(splashItems);
        viewPager.setAdapter(adapter);

        // Dots Indicator
        setupDotsIndicator();
    }

    private void setupDotsIndicator() {
        // Add code for dots indicator (e.g., using TabLayout or custom implementation)
    }
}
