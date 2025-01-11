package com.chestnut_java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
