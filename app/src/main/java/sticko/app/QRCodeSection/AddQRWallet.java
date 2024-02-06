package sticko.app.QRCodeSection;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import sticko.app.R;
import sticko.app.SettingsScreen.AccountFragment;

public class AddQRWallet extends Fragment {

    private String themeSelected;
    private RelativeLayout main_container;
    public ImageButton btn_close;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =inflater.inflate(R.layout.fragment_add_q_r_wallet, container, false);
        main_container = view.findViewById(R.id.main_container);
        btn_close = view.findViewById(R.id.btn_close);
        //set theme
        SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }

        main_container.setBackgroundColor(Color.parseColor(themeSelected));
        // close
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QR_codeFragment accountFragment = new QR_codeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, accountFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}