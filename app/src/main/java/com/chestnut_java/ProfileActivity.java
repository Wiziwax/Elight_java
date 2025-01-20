package com.chestnut_java;

import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout menuContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        menuContainer = findViewById(R.id.menuContainer);

        // Initially hide the menu container
        menuContainer.setAlpha(0f);
        menuContainer.setTranslationY(50f);

        // Start the animation after a short delay
        menuContainer.postDelayed(this::startMenuAnimation, 800);
    }

    private void startMenuAnimation() {
        menuContainer.animate()
                .alpha(1f)
                .translationY(0f)
                .setInterpolator(new OvershootInterpolator(2.0f))
                .setDuration(1000)
                .start();
    }
}