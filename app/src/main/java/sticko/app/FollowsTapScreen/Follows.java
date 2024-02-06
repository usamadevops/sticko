package sticko.app.FollowsTapScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Logger;

import sticko.app.Adapters.FollowersAdapter;
import sticko.app.Connections.Connections;
import sticko.app.Following.FollowingFragment;
import sticko.app.FollowsTapScreen.ui.main.PlaceholderFragment;
import sticko.app.FollowsTapScreen.ui.main.SectionsPagerAdapter;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.R;

public class Follows extends AppCompatActivity {
    public static boolean refresh;
    private ImageButton btn_add ,btn_done;
    private TextView app_bar_heading;
    private Toolbar toolbar;
    private String themeSelected;
    private TabLayout tabLayout;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follows);
        tabLayout =(TabLayout) findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar_secondary);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_add = toolbar.findViewById(R.id.btn_add);
        app_bar_heading = toolbar.findViewById(R.id.app_bar_heading);
        app_bar_heading.setText("Follows");
        btn_done.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Follows.this , HomeScreenActivity.class);
                startActivity(intent);

            }
        });
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // set theme color
        SharedPreferences themeColor = getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }
        tabLayout.setBackgroundColor(Color.parseColor(themeSelected));

        toolbar.setBackgroundColor(Color.parseColor(themeSelected));
    }


}