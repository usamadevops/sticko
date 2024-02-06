package sticko.app.SettingsScreen.top_50;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sticko.app.Adapters.ActiveLinksAdapter;
import sticko.app.Adapters.FollowersAdapter;
import sticko.app.Adapters.FollowingAdapter;
import sticko.app.Adapters.Top_50_Adapter;
import sticko.app.Connections.Connections;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.Models.Following_model;
import sticko.app.Models.HomeModel;
import sticko.app.Models.Top_50_model;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.SettingsScreen.AccountFragment;
import sticko.app.UserHomeScreen.UserHomeScreen;
import sticko.app.Utils.UtilsForPopups;

public class Top_50_Fragment extends Fragment {
    //url
    private static final String TOP_50_URL = "https://profile.sticko.fr/api/v1/top-50";
    private final static String FOLLOWING_LIST_URL = "https://profile.sticko.fr/api/v1/listFollowing";

    private Toolbar toolbar;
    private ImageButton btn_close,btn_add, btn_done, btn_close_start;
    private TextView app_bar_heading;
    private String themeSelected,token;
    private RelativeLayout main_container;
    View parentLayout;
    private String registerToken;
    private Session session;
    public Loader loader;
    private RecyclerView rv_top_50;
    private Top_50_Adapter top_50_adapter;
    private List<Top_50_model> top_50_models = new ArrayList<>();


    public Top_50_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar_secondary);
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        btn_add = toolbar.findViewById(R.id.btn_add);
        parentLayout = toolbar.findViewById(android.R.id.content);
        btn_close_start = toolbar.findViewById(R.id.btn_close_start);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_add.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.GONE);
        app_bar_heading = toolbar.findViewById(R.id.app_bar_heading);
        app_bar_heading.setText("Top 50");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(((FragmentActivity) getContext()), HomeScreenActivity.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
            }
        });
        // selected theme
        SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }
        toolbar.setBackgroundColor(Color.parseColor(themeSelected));
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        main_container.setBackgroundColor(Color.parseColor(themeSelected));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_50_, container, false);
        main_container = view.findViewById(R.id.main_container);
        rv_top_50 = view.findViewById(R.id.rv_top_50);
        session = new Session(getActivity());
        loader = new Loader(getActivity());

        if (session.getToken().equals("")) {
            registerToken = token;
            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }

        rv_top_50.setHasFixedSize(true);

        rv_top_50.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getAllActiveLinks();
        getAllFollowings();
        return  view;
    }

    private void getAllFollowings() {
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, FOLLOWING_LIST_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject responseData = jsonObject.getJSONObject("success");
                            JSONArray userData = responseData.getJSONArray("following");
                            UserHomeScreen.following_list.clear();

                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Following_model>>() {
                            }.getType();
                            List<Following_model> following_modelList = gson.fromJson(userData.toString(), type);
                            for (Following_model following_model: following_modelList){
                                UserHomeScreen.following_list.add(following_model.getUsername());
                            }
                            Log.i("listofConnections", String.valueOf(UserHomeScreen.following_list.size()));
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
                            UtilsForPopups.alertPopup(getActivity(), "Alert","NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","Oops. Timeout error!");

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
        Volley.newRequestQueue(getContext()).add(getRequest);

    }

    private void getAllActiveLinks() {
        loader.showLoader();
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, TOP_50_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        loader.hideLoader();
                        Log.d("response_50", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject responseData = jsonObject.getJSONObject("success");
                            JSONArray userData = responseData.getJSONArray("users");
                            Log.i("userDatalist", String.valueOf(userData));
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Top_50_model>>() {
                            }.getType();
                            top_50_models = gson.fromJson(userData.toString(), type);
                            top_50_adapter = new Top_50_Adapter(getContext(), top_50_models);
                            Log.i("listIsssss", String.valueOf(top_50_models));
                            rv_top_50.setAdapter(top_50_adapter);
                            top_50_adapter.notifyDataSetChanged();
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
                            UtilsForPopups.alertPopup(getActivity(), "Alert","NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","Oops. Timeout error!");

                        }
//
//
                    }
                }
        ) ;
        Volley.newRequestQueue(getContext()).add(getRequest);


    }
    @Override
    public void onResume() {
        super.onResume();
        getAllFollowings();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent((FragmentActivity) getActivity(), HomeScreenActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;

                }
                return false;
            }
        });
    }
}