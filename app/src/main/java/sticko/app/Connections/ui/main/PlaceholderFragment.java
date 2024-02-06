package sticko.app.Connections.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sticko.app.Adapters.ActiveLinksAdapter;
import sticko.app.Adapters.Connections_Adapter;
import sticko.app.Connections.AddLinksActivity;
import sticko.app.Connections.Connections;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.Models.HomeModel;
import sticko.app.Models.ListOfConnections_model;
import sticko.app.R;
import sticko.app.Session;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CONNECTIONS_URL = "https://profile.sticko.fr/api/v1/listSocialMedia";
    private PageViewModel pageViewModel;
    private final String PATH = "https://profile.sticko.fr/";
    private final String DASHBOARD_URL = "https://profile.sticko.fr/api/v1/profile";
    private RecyclerView rv_new_links;
    private Connections_Adapter connectionsAdapter;
    private List<ListOfConnections_model> newLinksList = new ArrayList<>();
    private RecyclerView rv_active_links;
    private ActiveLinksAdapter activeLinksAdapter;
    private List<HomeModel> activeLinksList = new ArrayList<>();
    private Session session;
    private String registerToken;
    public  String token, themeSelected;
    public Loader loader;
    RelativeLayout main_container, rl_connections_user;
    private Toolbar toolbar;
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
        if (getArguments() != null) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1: {

                    root = inflater.inflate(R.layout.new_links_layout, container, false);
                    rv_new_links = root.findViewById(R.id.rv_new_links);
                    main_container = root.findViewById(R.id.main_container);

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

                    getNewLinks();
                    break;
                }
                case 2: {
                    root = inflater.inflate(R.layout.active_links_layouts, container, false);
                    rv_active_links = root.findViewById(R.id.rv_active_links);
                    rl_connections_user = root.findViewById(R.id.rl_connections_user);

                    session = new Session(getActivity());
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login_data",
                            Context.MODE_PRIVATE);
                    token = sharedPreferences.getString("token", "");
                    loader = new Loader(getActivity());
                    // set theme
                    SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                            Context.MODE_PRIVATE);
                    themeSelected = themeColor.getString("theme", "");
                    if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                            .length() <= 0) {
                        themeSelected = "#09122A";

                    }
                    rl_connections_user.setBackgroundColor(Color.parseColor(themeSelected));

                    if (session.getToken().equals("")) {
                        registerToken = token;
                        Log.i("tokenreg", registerToken);
                    } else {
                        registerToken = session.getToken();
                        Log.i("token", registerToken);
                    }
                    getActiveLinks();
                    break;
                }
            }
        }
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent login_intent = new Intent(((FragmentActivity) getContext()), HomeScreenActivity.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                    return true;

                }
                return false;
            }
        });
    }
    private void getNewLinks() {
        rv_new_links.setNestedScrollingEnabled(false);
        rv_new_links.setLayoutManager(new GridLayoutManager(getContext(), 3));

        getAllConnections();
    }

    private void getAllConnections() {
        loader.showLoader();
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, CONNECTIONS_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        loader.hideLoader();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject responseData = jsonObject.getJSONObject("success");
                            JSONArray userData = responseData.getJSONArray("social_medias");
                            Log.i("userData", String.valueOf(userData));
                            newLinksList.clear();

                            Gson gson = new Gson();
                            Type type = new TypeToken<List<ListOfConnections_model>>() {
                            }.getType();
                            newLinksList = gson.fromJson(userData.toString(), type);
                            Log.i("listofConnections", String.valueOf(newLinksList.size()));

                            connectionsAdapter = new Connections_Adapter(getContext(), newLinksList);
                            rv_new_links.setAdapter(connectionsAdapter);
                            connectionsAdapter.notifyDataSetChanged();

                            AddLinksActivity.PATH = "";
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loader.hideLoader();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loader.hideLoader();
                        if (error instanceof NetworkError) {
                            Toast.makeText(getContext(), "NetworkError", Toast.LENGTH_SHORT).show();
                        }  else if (error instanceof ServerError) {
                            Toast.makeText(getContext(), "ServerError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof ParseError) {
                            Toast.makeText(getContext(), "ParseError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(getContext(),
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }
//
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

    private void getActiveLinks() {

        rv_active_links.setHasFixedSize(true);
        rv_active_links.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getAllActiveLinks();
    }

    private void getAllActiveLinks() {
        loader.showLoader();
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, DASHBOARD_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        loader.hideLoader();
                        Log.d("ResponseActive", response);
                        try {
                            socialMediaIcons(response);
                            activeLinksAdapter = new ActiveLinksAdapter(getContext(), activeLinksList);
                            Connections.static_activeLinksAdapter = activeLinksAdapter;
                            Log.i("adapter", String.valueOf(activeLinksAdapter));
                            rv_active_links.setAdapter(activeLinksAdapter);
                            activeLinksAdapter.notifyDataSetChanged();
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
                            Toast.makeText(getContext(), "NetworkError", Toast.LENGTH_SHORT).show();
                        }  else if (error instanceof ServerError) {
                            Toast.makeText(getContext(), "ServerError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof ParseError) {
                            Toast.makeText(getContext(), "ParseError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(getContext(),
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }
//
//                        if (error.networkResponse.statusCode == 400) {
//                            Toast.makeText(getContext(), "bad request", Toast.LENGTH_SHORT).show();
//                        } else if (error.networkResponse.statusCode == 401) {
//                            Toast.makeText(getContext(), "token not matched", Toast.LENGTH_SHORT).show();
//                        }
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

    private void socialMediaIcons(String response) throws JSONException {
        Log.i("responseSocial", response);

        JSONObject jsonObject = new JSONObject(response);
        JSONObject responseData = jsonObject.getJSONObject("success");
        JSONArray userData = responseData.getJSONArray("socialMedias");
        Log.i("userData", String.valueOf(userData));
        activeLinksList.clear();

        Gson gson = new Gson();
        Type type = new TypeToken<List<HomeModel>>() {
        }.getType();
        activeLinksList = gson.fromJson(userData.toString(), type);
        Log.i("listofConnections", String.valueOf(activeLinksList.size()));

    }

}