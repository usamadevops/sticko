package sticko.app.OnboardingScreen;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import sticko.app.R;
import sticko.app.SettingsScreen.AccountFragment;


public class OnboardingScreen extends AppCompatActivity {
    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout layoutOnboardingIndicator;
    private TextView tv_passer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);
        tv_passer = findViewById(R.id.tv_passer);
        tv_passer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountFragment accountFragment = new AccountFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, accountFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
//
        //vIEW pager
        final ViewPager2 onboardingViewPager = findViewById(R.id.onboarding_viewpager);
        onboardingViewPager.setAdapter(onBoardingAdapter);
        //indicator layout setup
        layoutOnboardingIndicator = findViewById(R.id.layout_onboardingIndicators);

        //setitems onboarding
        setupOnboardingItems();
        //viewPager
        ViewPager2 onBoardingViewPager = findViewById(R.id.onboarding_viewpager);
        onBoardingViewPager.setAdapter(onBoardingAdapter);
        //indicator methods
        setOnboardingIndicator();
        setCurrentOnboardingIndicator(0);
        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

    }

    private void setupOnboardingItems() {

        List<OnBoardingItem> onBoardingItems = new ArrayList<>();
        OnBoardingItem screens1 = new OnBoardingItem();
        screens1.setImage(R.drawable.screen1);

        OnBoardingItem screens2 = new OnBoardingItem();
        screens2.setImage(R.drawable.screen2);

        OnBoardingItem screens3 = new OnBoardingItem();
        screens3.setImage(R.drawable.screen3);
        OnBoardingItem screens4 = new OnBoardingItem();
        screens4.setImage(R.drawable.screen4);

        OnBoardingItem screens5 = new OnBoardingItem();
        screens5.setImage(R.drawable.screen5);

        OnBoardingItem screens6 = new OnBoardingItem();
        screens6.setImage(R.drawable.screen6);
        OnBoardingItem screens7 = new OnBoardingItem();
        screens7.setImage(R.drawable.screen7);

        OnBoardingItem screens8 = new OnBoardingItem();
        screens8.setImage(R.drawable.screen8);

        OnBoardingItem screens9 = new OnBoardingItem();
        screens9.setImage(R.drawable.screen9);
        OnBoardingItem screens10 = new OnBoardingItem();
        screens10.setImage(R.drawable.screen10);

        onBoardingItems.add(screens1);
        onBoardingItems.add(screens2);
        onBoardingItems.add(screens3);
        onBoardingItems.add(screens4);
        onBoardingItems.add(screens5);
        onBoardingItems.add(screens6);
        onBoardingItems.add(screens7);
        onBoardingItems.add(screens8);
        onBoardingItems.add(screens9);
        onBoardingItems.add(screens10);



        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);


    }

    private void setOnboardingIndicator() {

        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {

            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);

        }

    }

    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            } else {

                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
        }


    }

    @Override
    public void onBackPressed() {

    }
}
