package sticko.app.SettingsScreen.TutorialScreens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import sticko.app.FollowsTapScreen.Follows;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.QRCodeSection.AddQRWallet;
import sticko.app.R;
import sticko.app.SettingsScreen.AccountFragment;
import sticko.app.SettingsScreen.TutorialScreens.ui.main.SectionsPagerAdapter;
import sticko.app.databinding.ActivityTutorialsScreensBinding;

public class TutorialsScreens extends AppCompatActivity {

    private ActivityTutorialsScreensBinding binding;
    private final int dotsCount=10;    //No of tabs
    private ImageView[] dots;
    LinearLayout linearLayout;
    TextView btn_passer;
    private String themeSelected;
    private RelativeLayout main_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials_screens);
        binding = ActivityTutorialsScreensBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        drawPageSelectionIndicators(0);
        SharedPreferences themeColor =getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }
        RelativeLayout main_container = findViewById(R.id.main_container);
        main_container.setBackgroundColor(Color.parseColor(themeSelected));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                drawPageSelectionIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        btn_passer = findViewById(R.id.btn_passer);

        btn_passer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialsScreens.this , HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }


    private void drawPageSelectionIndicators(int mPosition){
        if(linearLayout!=null) {
            linearLayout.removeAllViews();
        }
        linearLayout=(LinearLayout)findViewById(R.id.viewPagerCountDots);
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            if(i==mPosition)
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.onboarding_indicator_active));
            else
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.onboarding_indicator_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(16, 0, 16, 0);
            linearLayout.addView(dots[i], params);
        }
    }

    @Override
    public void onBackPressed() {

    }
}