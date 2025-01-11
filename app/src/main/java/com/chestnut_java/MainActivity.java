package com.chestnut_java;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.chestnut_java.ImagePagerAdapter;
import com.chestnut_java.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 imageViewPager;
    private CircleIndicator3 dotIndicator;
    private List<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        imageViewPager = findViewById(R.id.imageViewPager);
        dotIndicator = findViewById(R.id.dotIndicator);

        // Setup image list
        imageList = new ArrayList<>();
        imageList.add(R.drawable.elight3);
        imageList.add(R.drawable.elight2);
        imageList.add(R.drawable.elight1);

        // Setup ViewPager
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageList);
        imageViewPager.setAdapter(adapter);

        // Apply custom PageTransformer
        imageViewPager.setPageTransformer(new ScaleTransformer());

        // Setup dot indicators using CircleIndicator3
        dotIndicator.setViewPager(imageViewPager);

        // Setup greeting
        TextView tvGreeting = findViewById(R.id.tvGreeting);
        updateGreeting(tvGreeting);

        // Setup click listeners
        MaterialCardView currentLocationCard = findViewById(R.id.currentLocationCard);
        currentLocationCard.setOnClickListener(v -> {
            // Handle location selection
        });



        CompositePageTransformer transformer = new CompositePageTransformer();

// Add scaling effect to emphasize the centered page
        transformer.addTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleY(scale);  // Scale down adjacent pages slightly
            page.setScaleX(scale);  // Optional: Scale on X-axis for consistency
        });

// Add spacing between pages
        transformer.addTransformer((page, position) -> {
            float translationX = position * page.getWidth() * 0.1f;  // Adjust spacing factor (e.g., 0.1f)
            page.setTranslationX(translationX);
        });

// Set margin between pages
        int pageMarginPx = (int) getResources().getDimension(R.dimen.page_margin);  // Define in dimens.xml
        int offsetPx = (int) getResources().getDimension(R.dimen.offset_margin);    // Define in dimens.xml
        imageViewPager.setPageTransformer(transformer);
        imageViewPager.setOffscreenPageLimit(3);  // Load extra pages for smoother transitions

// Adjust padding and margin
        imageViewPager.setClipToPadding(false);
        imageViewPager.setClipChildren(false);
        imageViewPager.setPadding(offsetPx, 0, offsetPx, 0);  // Define offset for left and right padding
//        imageViewPager.setPageMargin(pageMarginPx);

    }

    private void updateGreeting(TextView tvGreeting) {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greeting = "Good Morning,";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greeting = "Good Afternoon,";
        } else {
            greeting = "Good Evening,";
        }
        tvGreeting.setText(greeting);
    }

    // Custom PageTransformer for scaling and elevation
    private static class ScaleTransformer implements ViewPager2.PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            float absPosition = Math.abs(position);
            float scale = 1 - (0.15f * absPosition); // Scale down non-centered items
            float elevation = (1 - absPosition) * 10; // Elevate the centered item

            // Apply scale and elevation
            page.setScaleY(scale);
            page.setScaleX(scale);
            page.setElevation(elevation);
        }
    }
}
