package com.chestnut_java;

import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;

// Add this class to your project
public class KeyboardAwareScrollHelper {
    private final ScrollView scrollView;
    private final View rootView;
    private final int extraPadding;

    public KeyboardAwareScrollHelper(ScrollView scrollView, int extraPaddingDp) {
        this.scrollView = scrollView;
        this.rootView = scrollView.getRootView();
        this.extraPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                extraPaddingDp,
                scrollView.getResources().getDisplayMetrics()
        );
        setupKeyboardListener();
    }

    private void setupKeyboardListener() {
        // Listen for focus changes on all EditText views
        ViewGroup viewGroup = (ViewGroup) scrollView.getChildAt(0);
        recursivelyAddFocusListener(viewGroup);
    }

    private void recursivelyAddFocusListener(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof EditText || child instanceof AutoCompleteTextView) {
                child.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        scrollToView(v);
                    }
                });
            } else if (child instanceof ViewGroup) {
                recursivelyAddFocusListener((ViewGroup) child);
            }
        }
    }

    private void scrollToView(final View view) {
        // Post a delayed runnable to ensure keyboard is shown
        view.postDelayed(() -> {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);

            // Get keyboard height
            Rect visibleFrame = new Rect();
            rootView.getWindowVisibleDisplayFrame(visibleFrame);
            int keyboardHeight = rootView.getHeight() - visibleFrame.bottom;

            if (keyboardHeight > 0) {
                // Calculate if view is obscured by keyboard
                if (rect.bottom > visibleFrame.bottom) {
                    // Calculate how much to scroll
                    int scrollNeeded = rect.bottom - visibleFrame.bottom + extraPadding;
                    scrollView.smoothScrollBy(0, scrollNeeded);
                }
            }
        }, 300); // Delay to ensure keyboard is fully shown
    }
}