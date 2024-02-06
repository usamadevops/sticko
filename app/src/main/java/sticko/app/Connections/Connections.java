package sticko.app.Connections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sticko.app.Adapters.ActiveLinksAdapter;
import sticko.app.Connections.ui.main.SectionsPagerAdapter;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.R;
import sticko.app.Session;

public class Connections extends AppCompatActivity {
    private final String DELETE_URL = "https://profile.sticko.fr/api/v1/deleteSocialMedia";
    private final String UPDATE_DETAILS = "https://profile.sticko.fr/api/v1/updateSocialMedia";

    public static List<Integer> deleteList;
    private ImageButton btn_add, btn_done;
    private TextView app_bar_heading;
    private Loader loader;
    private Session session;
    private String registerToken;
    public  String token, themeSelected;
    RelativeLayout main_container;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    ViewPager view_pager;
    int myPosition = 1;



    public static ActiveLinksAdapter static_activeLinksAdapter;
    public static boolean static_isUpdate = true;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);
        tabLayout = findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar_secondary);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_add = toolbar.findViewById(R.id.btn_add);
        view_pager = findViewById(R.id.view_pager);
        app_bar_heading = toolbar.findViewById(R.id.app_bar_heading);
        app_bar_heading.setText("Liens");
        setSupportActionBar(toolbar);
        loader = new Loader(this);
        session = new Session(this);
        tabLayout.setupWithViewPager(view_pager);

        //get token

        SharedPreferences sharedPreferences = getSharedPreferences("Login_data",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

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
//        main_container.setBackgroundColor(Color.parseColor(themeSelected));

        if (session.getToken().equals("")) {
            registerToken = token;
            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Connections.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (static_activeLinksAdapter != null) {
                    List<ActiveLinksAdapter.SocialMedia> list = static_activeLinksAdapter.update();
                    try {
                        updateUserData(list);
                        DeleteSocialMedia();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.canResolveTextAlignment();
        tabs.setupWithViewPager(viewPager);


    }

    private void updateUserData(List<ActiveLinksAdapter.SocialMedia> list) throws JSONException {
        loader.showLoader();
        JSONArray array=new JSONArray();
        JSONObject obj1=new JSONObject();

        for(int i=0;i<list.size();i++){
            JSONObject obj=new JSONObject();
            try {
                obj.put("id",list.get(i).getId());
                obj.put("social_media_username",list.get(i).getSocial_media_username());
                obj.put("social_media_id",list.get(i).getSocial_media_id());
                obj.put("visible",list.get(i).isVisible());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }

        obj1.put("social_media",array);
        Log.i("socialmedia", String.valueOf(obj1));
        System.out.println(array);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, UPDATE_DETAILS, obj1, response -> {
            Log.i("respones_update", String.valueOf(response));
            loader.hideLoader();
            Toast.makeText(Connections.this, "Mise à jour du profil réussie", Toast.LENGTH_SHORT).show();
            list.clear();
            loader.hideLoader();

        }, error -> {
//                loader.setVisibility(View.GONE);
            loader.hideLoader();
            if (error instanceof NetworkError) {
                Toast.makeText(Connections.this, "NetworkError", Toast.LENGTH_SHORT).show();
            }  else if (error instanceof ServerError) {
                Toast.makeText(Connections.this, "ServerError", Toast.LENGTH_SHORT).show();

            } else if (error instanceof AuthFailureError) {
                Toast.makeText(Connections.this, "AuthFailureError", Toast.LENGTH_SHORT).show();

            } else if (error instanceof ParseError) {
                Toast.makeText(Connections.this, "ParseError", Toast.LENGTH_SHORT).show();

            } else if (error instanceof NoConnectionError) {
                Toast.makeText(Connections.this, "NoConnectionError", Toast.LENGTH_SHORT).show();

            } else if (error instanceof TimeoutError) {
                Toast.makeText(Connections.this,
                        "Oops. Timeout error!",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+registerToken);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Connections.this).add(sr);

    }



    private void DeleteSocialMedia() {
        if (deleteList != null) {
            int i = 0;
            try {

                for (i = 0; i < deleteList.size(); i++) {
                    deleteFromServer(deleteList.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFromServer(int id) throws JSONException {
        loader.showLoader();

        JSONObject map = new JSONObject();

        map.put("id", id);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, DELETE_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                Log.i("respones_update", String.valueOf(response));
                Intent intent = new Intent(Connections.this , HomeScreenActivity.class);
                deleteList.clear();
                startActivity(intent);
                finish();
                loader.hideLoader();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loader.setVisibility(View.GONE);
                loader.hideLoader();
                if (error instanceof NetworkError) {
                    Toast.makeText(Connections.this, "NetworkError", Toast.LENGTH_SHORT).show();
                }  else if (error instanceof ServerError) {
                    Toast.makeText(Connections.this, "ServerError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Connections.this, "AuthFailureError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof ParseError) {
                    Toast.makeText(Connections.this, "ParseError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Connections.this, "NoConnectionError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Connections.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " +registerToken);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Connections.this).add(sr);

    }
}