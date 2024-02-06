package sticko.app.SettingsScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import sticko.app.ActivationScreen.ActivationActivity;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.LoginScreen.LoginActivity;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.SettingsScreen.Profile.ProfileFragment;
import sticko.app.SettingsScreen.TutorialScreens.TutorialsScreens;
import sticko.app.SettingsScreen.contactor_sticko.ContactUsActivity;
import sticko.app.SettingsScreen.top_50.Top_50_Fragment;

public class AccountFragment extends Fragment {
    private TextView tv_tutorial, tv_top_50, tv_contact_us, tv_profile, tv_activate_sticko, tv_buy_a_sticko, tv_disconnect;
    private ImageView btn_add;
    private ImageButton btn_close, btn_done;
    private AppCompatButton btn_deactivate_account;
    private Session session;
    private RelativeLayout main_container;
    public Toolbar toolbar;
    private String themeSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        session = new Session(getActivity());
        tv_tutorial = view.findViewById(R.id.tv_tutorial);
        tv_profile = view.findViewById(R.id.tv_profile);
        tv_contact_us = view.findViewById(R.id.tv_contact_us);
        tv_top_50 = view.findViewById(R.id.tv_top_50);

        tv_activate_sticko = view.findViewById(R.id.tv_activate_sticko);
        tv_buy_a_sticko = view.findViewById(R.id.tv_buy_a_sticko);
        tv_disconnect = view.findViewById(R.id.tv_disconnect);
        main_container = view.findViewById(R.id.main_container);
        btn_close = view.findViewById(R.id.btn_close);

        btn_deactivate_account = view.findViewById(R.id.btn_deactivate_account);
        // set theme color
        SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }

        main_container.setBackgroundColor(Color.parseColor(themeSelected));

        //btn close
        // close
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((FragmentActivity) getActivity(), HomeScreenActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        tv_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((FragmentActivity) getActivity(), TutorialsScreens.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, profileFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        tv_activate_sticko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((FragmentActivity) getActivity(), ActivationActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        tv_buy_a_sticko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://sticko.fr"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });
        tv_disconnect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login_data",
                        Context.MODE_PRIVATE);
                Intent intent = new Intent((FragmentActivity) getActivity(), LoginActivity.class);
                startActivity(intent);
                session.remove();
                themeSelected = "#09122A";
                sharedPreferences.edit().clear();
                getActivity().finish();
            }
        });
        btn_deactivate_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeactivatedAlertFragment deactivatedAlertFragment = new DeactivatedAlertFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, deactivatedAlertFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        tv_top_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Top_50_Fragment top_50_fragment = new Top_50_Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, top_50_fragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        tv_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((FragmentActivity) getActivity(), ContactUsActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
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
//                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
//                    fragmentTransaction.commit();
                    return true;

                }
                return false;
            }
        });

    }
}