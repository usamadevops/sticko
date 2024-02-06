package sticko.app.SettingsScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.LoginScreen.LoginActivity;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.Utils.UtilsForPopups;

public class DeactivatedAlertFragment extends Fragment {
    private String DELETE_USER_URL = "https://profile.sticko.fr/api/v1/deleteAccount";
    private ImageView btn_add;
    private ImageButton btn_close, btn_done, btn_close_start;
    private Session session;
    private Loader loader;
    private RelativeLayout main_container;
    private Toolbar toolbar;
    private String themeSelected;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar_secondary);
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        btn_add = toolbar.findViewById(R.id.btn_add);
        btn_close_start = toolbar.findViewById(R.id.btn_close_start);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_close = view.findViewById(R.id.btn_close);
        btn_close.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_close_start.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
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

    private void deleteUser() {
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.POST, DELETE_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        UtilsForPopups.SuccessPopup(getActivity(), "Deleted","Utilisateur supprimé avec succès");
                        Intent intent = new Intent((FragmentActivity) getActivity(), LoginActivity.class);
                        startActivity(intent);
                        session.remove();
                        getActivity().finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        if (error.networkResponse.statusCode == 400) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","bad request");

                        } else if (error.networkResponse.statusCode == 401) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert","token not matched");
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(getRequest);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_deactivated_alert, container, false);
        loader = new Loader(getContext());
        main_container = root.findViewById(R.id.main_container);
        session = new Session(getActivity());

        return root;
    }


}