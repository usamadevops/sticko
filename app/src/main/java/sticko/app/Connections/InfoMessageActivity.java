package sticko.app.Connections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import sticko.app.R;

public class InfoMessageActivity extends AppCompatActivity {
    private String themeSelected;
    private ImageButton btn_close;
    RelativeLayout main_container;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info);
        main_container = findViewById(R.id.main_container);

        // set theme color
        SharedPreferences themeColor = getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";
        }

        main_container.setBackgroundColor(Color.parseColor(themeSelected));

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}