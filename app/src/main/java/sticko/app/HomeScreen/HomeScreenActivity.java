package sticko.app.HomeScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import sticko.app.ActivationScreen.ActivationActivity;
import sticko.app.Adapters.HomeAdapter;
import sticko.app.Connections.Connections;
import sticko.app.FollowsTapScreen.Follows;
import sticko.app.Loader;
import sticko.app.Models.HomeModel;
import sticko.app.QRCodeSection.QR_codeFragment;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.SettingsScreen.AccountFragment;
import sticko.app.SettingsScreen.Profile.ProfileFragment;
import sticko.app.Utils.UtilsForPopups;

public class HomeScreenActivity extends AppCompatActivity {
    private final String GET_THEME_URL = "https://profile.sticko.fr/api/v1/theme";
    private final String PATH = "https://profile.sticko.fr/";
    private final String DASHBOARD_URL = "https://profile.sticko.fr/api/v1/profile";
    private RecyclerView rv_homeScreen;
    private HomeAdapter homeAdapter;
    public ImageButton btn_add, btn_profile, btn_share, btn_QR_code;
    private TextView tv_name, tv_bio;
    private boolean doubleBackToExitPressedOnce = false;
    private AppCompatButton btn_following, btn_score;
    private Session session;
    private String registerToken;
    public String token;
    private CircleImageView iv_profile;
    private Loader loader;
    View parentLayout;
    String username, bio, score, imagePath, themeSelected;
    // connections
    public List<HomeModel> connectionsList = new ArrayList<>();
    // theme
    private RelativeLayout main_container;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ImageView iv_logo_below, iv_logo;
    private int user_id;
    private String link_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        toolbar = findViewById(R.id.toolbar_main);
        btn_add = toolbar.findViewById(R.id.btn_add);
        btn_share = toolbar.findViewById(R.id.btn_share);
        btn_profile = toolbar.findViewById(R.id.btn_profile);
        setSupportActionBar(toolbar);
        rv_homeScreen = findViewById(R.id.rv_homeScreen);
        btn_score = findViewById(R.id.btn_score);
        iv_profile = findViewById(R.id.iv_profile);
        btn_following = findViewById(R.id.btn_following);
        main_container = findViewById(R.id.main_container);
        progressBar = findViewById(R.id.progress);
        tv_name = findViewById(R.id.tv_name);
        iv_logo_below = findViewById(R.id.iv_logo_below);
        btn_QR_code = findViewById(R.id.btn_QR_code);
        iv_logo = findViewById(R.id.iv_logo);
        tv_bio = findViewById(R.id.tv_bio);
        session = new Session(this);
        loader = new Loader(this);
        parentLayout = findViewById(android.R.id.content);
        rv_homeScreen.setNestedScrollingEnabled(false);

        //set theme
//        main_container.setBackgroundColor(getResources()
//                .getColor(R.color.blue_violet));
        // intent

        // get token
        SharedPreferences sharedPreferences = getSharedPreferences("Login_data",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        ViewTreeObserver observer = scrollView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int viewHeight = scrollView.getMeasuredHeight();
                int contentHeight = scrollView.getChildAt(0).getHeight();
                if (viewHeight - contentHeight < 0) {
                    // scrollable
                    iv_logo_below.setVisibility(View.GONE);
                    iv_logo.setVisibility(View.VISIBLE);
                    // Toast.makeText(HomeScreenActivity.this, "scrolable", Toast.LENGTH_SHORT).show();
                } else {
                    iv_logo_below.setVisibility(View.VISIBLE);
                    iv_logo.setVisibility(View.GONE);
                    // Toast.makeText(HomeScreenActivity.this, "not scrolable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (session.getToken().equals("")) {
            registerToken = token;
            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivationActivity.username = username;
                AccountFragment accountFragment = new AccountFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, accountFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, Connections.class);
                startActivity(intent);


            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sticko");
                intent.putExtra(Intent.EXTRA_TEXT, "https://profile.sticko.fr/user/" + link_username);
                startActivity(Intent.createChooser(intent, "choose one"));

            }
        });
        btn_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, Follows.class);
                startActivity(intent);

            }
        });
        // QR button
        btn_QR_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QR_codeFragment.userName = link_username;
                QR_codeFragment accountFragment = new QR_codeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, accountFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        checkInternetConnection();
        rv_homeScreen.setNestedScrollingEnabled(false);
        rv_homeScreen.setLayoutManager(new GridLayoutManager(HomeScreenActivity.this, 3));
        ActivationActivity.username = username;
    }

    private void getProfileData() {
        loader.showLoader();
        progressBar.setVisibility(View.VISIBLE);
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, DASHBOARD_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response_api", response);
                        try {
                            loader.hideLoader();
                            socialMediaIcons(response);
                            homeAdapter = new HomeAdapter(HomeScreenActivity.this, connectionsList);
                            Log.i("adapter", String.valueOf(homeAdapter));
                            rv_homeScreen.setAdapter(homeAdapter);
                            homeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject responseData = jsonObject.getJSONObject("success");
                            JSONObject userData = responseData.getJSONObject("user");
                            link_username = userData.getString("link_username");
                            username = userData.getString("username");
                            bio = userData.getString("bio");
                            score = userData.getString("score");
                            imagePath = userData.getString("profile_picture_path");
                            tv_name.setText(username);
                            session.setUsername(username);
                            progressBar.setVisibility(View.GONE);

                            Picasso.get().load(PATH + imagePath).placeholder(R.drawable.profile_icon).into(iv_profile);
                            user_id = Integer.parseInt(userData.getString("id"));
                            if (bio == null || bio.trim().equals("null") || bio.trim()
                                    .length() <= 0) {
                                tv_bio.setText("Bio");
                            } else {
                                tv_bio.setText(bio);
                            }
                            if (score != null && !score.isEmpty() && !score.equals("null")) {
                                btn_score.setText(score + " STICKS");
                            } else {
                                btn_score.setText("0" + " STICKS");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loader.hideLoader();
                        if (error instanceof NetworkError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","Oops. Timeout error!");

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + registerToken);
                params.put("Content-Type", "apptokenlication/json");
                return params;
            }
        };
        Volley.newRequestQueue(HomeScreenActivity.this).add(getRequest);


    }

    void socialMediaIcons(String response) throws JSONException {
        Log.i("responseSocial", response);

        JSONObject jsonObject = new JSONObject(response);
        JSONObject responseData = jsonObject.getJSONObject("success");
        JSONArray userData = responseData.getJSONArray("socialMedias");
        Log.i("userData", String.valueOf(userData));

        connectionsList.clear();
        Gson gson = new Gson();
        Type type = new TypeToken<List<HomeModel>>() {
        }.getType();
        connectionsList = gson.fromJson(userData.toString(), type);


    }

    private void getThemeData() {

        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, GET_THEME_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("success");

                            themeSelected = jsonObject.getString("theme");
                            ProfileFragment.checkUser = true;
                            if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                                    .length() <= 0) {
                                themeSelected = "#09122A";


                            }
                            toolbar.setBackgroundColor(Color.parseColor(themeSelected));
                            setSupportActionBar(toolbar);
                            main_container.setBackgroundColor(Color.parseColor(themeSelected));
                            SharedPreferences sharedPreferences = getSharedPreferences("themeColor",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("theme", themeSelected);
                            editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loader.hideLoader();
                       if (error instanceof NetworkError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","Oops. Timeout error!");

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + registerToken);
                params.put("Content-Type", "apptokenlication/json");
                return params;
            }
        };
        Volley.newRequestQueue(HomeScreenActivity.this).add(getRequest);


    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            getThemeData();
            getProfileData();
            return true;
        } else {
            loader.hideLoader();
            UtilsForPopups.alertPopup(HomeScreenActivity.this, "Alert","Veuillez vérifiez votre connexion à internet et réessayez");
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            super.onBackPressed();
//            FragmentManager fm = getSupportFragmentManager();
//            if (fm.getBackStackEntryCount() == 0) {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();


            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Appuyez à nouveau pour quitter", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
//            } else {
////            super.onBackPressed();
//                fm.popBackStack();
//            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean update = false;


    }
}
