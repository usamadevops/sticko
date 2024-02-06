package sticko.app.AddLinksScreen;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sticko.app.Adapters.AddNewLinksAdapter;
import sticko.app.Adapters.Connections_Adapter;
import sticko.app.Connections.AddLinksActivity;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.Models.ListOfConnections_model;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.SettingsScreen.AccountFragment;

public class AddNewLinksFragment extends Fragment {
    private static final String CONNECTIONS_URL = "https://profile.sticko.fr/api/v1/listSocialMedia";
    private RecyclerView rv_new_links;
    private Connections_Adapter connectionsAdapter;
    private List<ListOfConnections_model> newLinksList = new ArrayList<>();
    public ImageView btn_add;
    public ImageButton btn_close, btn_done, btn_close_start;
    //set theme
    private RelativeLayout main_container;
    public Toolbar toolbar;
    private String themeSelected;
    public Loader loader;
    private Session session;
    private String registerToken;
    public  String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_add_links, container, false);
        rv_new_links = view.findViewById(R.id.rv_new_links);
        main_container = view.findViewById(R.id.main_container);

        Toolbar toolbar = view.findViewById(R.id.toolbar_secondary);
        btn_add = toolbar.findViewById(R.id.btn_add);
        btn_close_start = toolbar.findViewById(R.id.btn_close_start);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_close = view.findViewById(R.id.btn_close);
        btn_close.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_close_start.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar();

        btn_close_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountFragment accountFragment = new AccountFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, accountFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        // set theme color
        SharedPreferences themeColor =getActivity().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }

        toolbar.setBackgroundColor(Color.parseColor(themeSelected));
        main_container.setBackgroundColor(Color.parseColor(themeSelected));

        session = new Session(getActivity());
        loader = new Loader(getActivity());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login_data",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        if (session.getToken().equals("")) {
            registerToken = token;
            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }

        getNewLinks();
        return view;
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

                            AddLinksActivity.PATH = "FROM_NFC_WRITER";
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

    @Override
    public void onResume() {
        super.onResume();

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