package sticko.app.QRCodeSection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.R;

public class QR_codeFragment extends Fragment {
    public static String userName;
    private LinearLayout ll_add_qr_wallet,ll_share_profile;
    public TextView user_url;
    public ImageButton btn_close;
    //theme var
    private String themeSelected;
    private RelativeLayout main_container;
    //imageQR
    private ImageView QR_code;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_r_code, container, false);
        ll_add_qr_wallet = view.findViewById(R.id.ll_add_qr_wallet);
        ll_share_profile = view.findViewById(R.id.ll_share_profile);
        main_container = view.findViewById(R.id.main_container);
        QR_code = view.findViewById(R.id.QR_code);

        btn_close = view.findViewById(R.id.btn_close);

        user_url = view.findViewById(R.id.user_url);

        //set theme
        SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }

        main_container.setBackgroundColor(Color.parseColor(themeSelected));


        //set text
        user_url.setText("profile.sticko.fr/user/"+userName);

        //set QR
        QRGEncoder qrgEncoder = new QRGEncoder("profile.sticko.fr/user/"+userName,null, QRGContents.Type.TEXT,500);
        Bitmap bitmap = null;
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        QR_code.setImageBitmap(bitmap);

        // share button listnr
        ll_share_profile.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sticko");
                intent.putExtra(Intent.EXTRA_TEXT, "https://profile.sticko.fr/user/"+userName);
                startActivity(Intent.createChooser(intent, "choose one"));

            }
        });
        // click listnr
        ll_add_qr_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddQRWallet accountFragment = new AddQRWallet();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, accountFragment, "tag")
                        .addToBackStack(null)
                        .commit();
            }
        });
        // close button listner
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((FragmentActivity) getActivity() , HomeScreenActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}