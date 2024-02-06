package sticko.app.FollowsTapScreen.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sticko.app.Adapters.FollowersAdapter;
import sticko.app.Adapters.FollowingAdapter;
import sticko.app.FollowsTapScreen.Follows;
import sticko.app.Loader;
import sticko.app.Models.Following_model;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.Utils.UtilsForPopups;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    private Context  context;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private final static String FOLLOWING_LIST_URL = "https://profile.sticko.fr/api/v1/listFollowing";
    private final static String FOLLOWERS_LIST_URL = "https://profile.sticko.fr/api/v1/listFollowers";
    private RecyclerView rv_following;
    private FollowingAdapter followingAdapter;
    private List<Following_model> following_list = new ArrayList<>();
    private RecyclerView rv_followers;
    private FollowersAdapter followersAdapter;
    private List<Following_model> followers_list = new ArrayList<>();
    private Session session;
    private String registerToken;
    public String token, themeSelected;
    private Loader loader;
    private RelativeLayout no_followers, main_container;
    List<Integer> followingUsers = new ArrayList<>();
    private SwipeRefreshLayout recyclerLayout;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = null;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                root = inflater.inflate(R.layout.following_layout, container, false);
                rv_following = root.findViewById(R.id.rv_following);
                no_followers = root.findViewById(R.id.no_followers);
                main_container = root.findViewById(R.id.main_container);
                session = new Session(getActivity());
                loader = new Loader(getActivity());
                no_followers.setVisibility(View.GONE);
                recyclerLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login_data",
                        Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");
                // set theme color
                SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                        Context.MODE_PRIVATE);
                themeSelected = themeColor.getString("theme", "");
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                // toolbar.setBackgroundColor(Color.parseColor(themeSelected));
                main_container.setBackgroundColor(Color.parseColor(themeSelected));

                if (session.getToken().equals("")) {
                    registerToken = token;
                    Log.i("tokenreg", registerToken);
                } else {
                    registerToken = session.getToken();
                    Log.i("token", registerToken);
                }
                followingList();
                recyclerLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        recyclerLayout.setRefreshing(true);
                        followingList();
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerLayout.setRefreshing(false);
                            }
                        }, 2000);

                    }
                });
                break;
            }
            case 2: {

                root = inflater.inflate(R.layout.followers_layout, container, false);

                rv_followers = root.findViewById(R.id.rv_followers);
                no_followers = root.findViewById(R.id.no_followers);
                main_container = root.findViewById(R.id.main_container);
                recyclerLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);

                session = new Session(getActivity());
                loader = new Loader(getActivity());
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login_data",
                        Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");
                // set theme color
                SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                        Context.MODE_PRIVATE);
                themeSelected = themeColor.getString("theme", "");
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                // toolbar.setBackgroundColor(Color.parseColor(themeSelected));
                main_container.setBackgroundColor(Color.parseColor(themeSelected));

                if (session.getToken().equals("")) {
                    registerToken = token;
                    Log.i("tokenreg", registerToken);
                } else {
                    registerToken = session.getToken();
                    Log.i("token", registerToken);
                }
                followersList();
                recyclerLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        recyclerLayout.setRefreshing(true);
                        followersList();
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerLayout.setRefreshing(false);
                            }
                        }, 2000);

                    }
                });
                break;
            }
        }

        return root;
    }

    private void followingList() {
        rv_following.setHasFixedSize(true);
        rv_following.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getAllFollowings();



    }

    private void getAllFollowings() {
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, FOLLOWING_LIST_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject responseData = jsonObject.getJSONObject("success");
                            JSONArray userData = responseData.getJSONArray("following");
                            following_list.clear();

                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Following_model>>() {
                            }.getType();
                            following_list = gson.fromJson(userData.toString(), type);
                            Log.i("listofConnections", String.valueOf(following_list.size()));

                            followingAdapter = new FollowingAdapter(getActivity(), following_list);
                            rv_following.setAdapter(followingAdapter);
                            followingAdapter.notifyDataSetChanged();
                            if (following_list.size() == 0) {
                                no_followers.setVisibility(View.VISIBLE);
                            } else
                                no_followers.setVisibility(View.GONE);
                            // get following list
                            for (int i = 0; i < following_list.size(); i++) {
                                Log.i("following", String.valueOf(following_list.get(i).getId()));
                                followingUsers.add(following_list.get(i).getId());
                            }
                            FollowersAdapter.followingusers = followingUsers;
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

    private void followersList() {
        rv_followers.setHasFixedSize(true);
        rv_followers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getAllFollowers();

    }

    private void getAllFollowers() {
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, FOLLOWERS_LIST_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject responseData = jsonObject.getJSONObject("success");
                            JSONArray userData = responseData.getJSONArray("followers");
                            followers_list.clear();
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Following_model>>() {
                            }.getType();
                            followers_list = gson.fromJson(userData.toString(), type);
                            Log.i("listofConnections", String.valueOf(followers_list.size()));

                            followersAdapter = new FollowersAdapter(getActivity(), followers_list);
                            rv_followers.setAdapter(followersAdapter);
                            followersAdapter.notifyDataSetChanged();
                            if (followers_list.size() == 0) {

                                no_followers.setVisibility(View.VISIBLE);
                            } else
                                no_followers.setVisibility(View.GONE);
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


}