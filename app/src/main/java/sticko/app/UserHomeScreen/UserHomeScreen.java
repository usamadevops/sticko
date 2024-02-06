package sticko.app.UserHomeScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
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
import sticko.app.Adapters.HomeAdapter;
import sticko.app.FollowsTapScreen.Follows;
import sticko.app.Loader;
import sticko.app.LoginScreen.LoginActivity;
import sticko.app.Models.Following_model;
import sticko.app.Models.HomeModel;
import sticko.app.R;
import sticko.app.Registrations.RegistrationActivity;
import sticko.app.Session;
import sticko.app.Utils.UtilsForPopups;

public class UserHomeScreen extends AppCompatActivity {
    private static final String USER_DETAILS_URL = "https://profile.sticko.fr/api/v1/user/";
    public static String NAVIGATION_PATH = "";
    public static String stat_follower;
    public static boolean following_user;
    private final String PATH = "https://profile.sticko.fr/";
    private static final String UNFOLLOW = "https://profile.sticko.fr/api/v1/user/";
    private static final String FOLLOW = "https://profile.sticko.fr/api/v1/user/";
    private final String DASHBOARD_URL = "https://profile.sticko.fr/api/v1/profile";
    public static List<String> following_list = new ArrayList<>();
    public static String userId;

    // rv var
    private RecyclerView rv_user_screen;
    private HomeAdapter homeAdapter;
    private final List<Integer> homeScreenModels = new ArrayList<>();
    // var
    private ImageView btn_add;
    private CircleImageView iv_profile;
    private TextView tv_name, tv_bio;
    private ImageButton btn_done, btn_share;
    private AppCompatButton btn_following;
    private String follow = "unfollow";
    private Session session;
    private Loader loader;
    public String registerToken, token;
    String username, bio, score;
    private RelativeLayout main_container;
    private Toolbar toolbar;
    GradientDrawable gd;
    private ImageView iv_logo_below, iv_logo;
    public List<HomeModel> connectionsList = new ArrayList<>();
    private String link_username;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        //tool bar
        //init
        btn_following = findViewById(R.id.btn_following);
        rv_user_screen = findViewById(R.id.rv_user_screen);
        rv_user_screen.setNestedScrollingEnabled(false);
        toolbar = findViewById(R.id.toolbar_secondary);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_add = toolbar.findViewById(R.id.btn_add);
        btn_share = toolbar.findViewById(R.id.btn_share);
        main_container = findViewById(R.id.main_container);
        btn_done.setVisibility(View.GONE);
        iv_logo_below = findViewById(R.id.iv_logo_below);
        iv_logo = findViewById(R.id.iv_logo);
        loader = new Loader(this);
        session = new Session(this);
        Uri uri = getIntent().getData();
        if (uri != null) {
            if (session.getToken().equals("")) {
                Intent login = new Intent(UserHomeScreen.this, LoginActivity.class);
                startActivity(login);
            } else {
                //getUserID();
                String str = String.valueOf(uri);
                // int index=str.lastIndexOf('/');
                String userID = str.substring(str.lastIndexOf("/"));
                userId = userID.substring(1);
            }
        }
        // logo set
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
                } else {
                    iv_logo_below.setVisibility(View.VISIBLE);
                    iv_logo.setVisibility(View.GONE);
                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NAVIGATION_PATH.equals("")) {
                    Intent intent = new Intent(UserHomeScreen.this, Follows.class);
                    startActivity(intent);
                }
                finish();
            }
        });

        btn_share.setVisibility(View.VISIBLE);
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

        SharedPreferences sharedPreferences = getSharedPreferences("Login_data",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        if (session.getToken().equals("")) {
            registerToken = token;
            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }

        if (!NAVIGATION_PATH.equals("TOP_50")) {
            if (following_user) {
                btn_following.setTag(0);
                btn_following.setText("UNFOLLOW");
                btn_following.setBackgroundDrawable(gd);
                btn_following.setTextColor(getResources().getColor(R.color.white));
            } else {
                btn_following.setTag(1);
                btn_following.setText(R.string.follow);
                btn_following.setTextColor(getResources().getColor(R.color.primary_color));
                btn_following.setBackground(getResources().getDrawable(R.drawable.button_background_follow));
            }
        }

        btn_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                if (status == 1) {
                    v.setTag(0); //pause
                    followUser();
                } else {
                    v.setTag(1); //pause
                    unfollowUser();
                }
            }
        });

        getProfileData(userId);
        rv_user_screen.setNestedScrollingEnabled(false);
        rv_user_screen.setLayoutManager(new GridLayoutManager(UserHomeScreen.this, 3));
    }

    private void unfollowUser() {
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, UNFOLLOW + userId + "/unfollow",
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        btn_following.setText(R.string.follow);
                        btn_following.setTextColor(getResources().getColor(R.color.primary_color));
                        btn_following.setBackground(getResources().getDrawable(R.drawable.button_background_follow));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        if (error instanceof NetworkError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "Oops. Timeout error!");

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + session.getToken());
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(UserHomeScreen.this).add(getRequest);

    }

    private void followUser() {
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, FOLLOW + userId + "/follow",
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        btn_following.setText("UNFOLLOW");
                        btn_following.setBackgroundDrawable(gd);
                        btn_following.setTextColor(getResources().getColor(R.color.white));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loader.hideLoader();
                        if (error instanceof NetworkError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "Oops. Timeout error!");

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + session.getToken());
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(UserHomeScreen.this).add(getRequest);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getProfileData(String id) {
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, USER_DETAILS_URL + id,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            socialMediaIcons(response);
                            homeAdapter = new HomeAdapter(UserHomeScreen.this, connectionsList);
                            Log.i("adapter", String.valueOf(homeAdapter));
                            rv_user_screen.setAdapter(homeAdapter);
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
                            userId = userData.getString("id");
                            Log.i("userData", String.valueOf(userData));
                            Log.i("username", String.valueOf(username));
                            TextView tv_name = findViewById(R.id.tv_name);
                            TextView tv_bio = findViewById(R.id.tv_bio);
                            CircleImageView imageView = findViewById(R.id.iv_profile);
                            score = userData.getString("score");
                            String imagePath = userData.getString("profile_picture_path");
                            tv_name.setText(username);
                            String themeColor = responseData.getString("theme");
                            if (themeColor.equals("#") || themeColor == null || themeColor.trim().equals("null") || themeColor.trim()
                                    .length() <= 0) {
                                themeColor = "#09122A";

                            }
                            gd = new GradientDrawable();
                            gd.setColor(Color.parseColor(themeColor));
                            gd.setCornerRadius(30);
                            gd.setStroke(2, Color.WHITE);
                            if (session.getUserName().equals(username)) {
                                btn_following.setVisibility(View.GONE);
                            } else if (themeColor == null || bio.trim().equals("null") || bio.trim()
                                    .length() <= 0) {
                                btn_following.setTextColor(getResources().getColor(R.color.white));
                                btn_following.setBackground(getResources().getDrawable(R.drawable.button_background_follwing));
                            } else if (btn_following.getText().equals("UNFOLLOW")) {
                                btn_following.setTextColor(getResources().getColor(R.color.white));
                                btn_following.setBackground(gd);
                            } else {
                                btn_following.setTextColor(getResources().getColor(R.color.primary_color));
                                btn_following.setBackground(getResources().getDrawable(R.drawable.button_background_follow));

                            }

                            toolbar.setBackgroundColor(Color.parseColor(themeColor));
                            main_container.setBackgroundColor(Color.parseColor(themeColor));
                            if (bio == null || bio.trim().equals("null") || bio.trim()
                                    .length() <= 0) {
                                tv_bio.setText("Bio");
                            } else
                                tv_bio.setText(bio);


                            Picasso.get().load(PATH + imagePath).placeholder(R.drawable.profile_icon).into(imageView);

                            if (NAVIGATION_PATH.equals("TOP_50")) {
                                if (following_list.size() > 0 && following_list.contains(username)) {
                                    btn_following.setTag(0);
                                    btn_following.setText("UNFOLLOW");
                                    btn_following.setTextColor(getResources().getColor(R.color.white));
                                    btn_following.setBackgroundDrawable(gd);
                                } else {
                                    btn_following.setTag(1);
                                    btn_following.setText(R.string.follow);
                                    btn_following.setTextColor(getResources().getColor(R.color.primary_color));
                                    btn_following.setBackground(getResources().getDrawable(R.drawable.button_background_follow));
                                }
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
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(UserHomeScreen.this, "Alert", "Oops. Timeout error!");

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + session.getToken());
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(UserHomeScreen.this).add(getRequest);


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
        Log.i("listofConnections", String.valueOf(connectionsList.size()));


    }
}