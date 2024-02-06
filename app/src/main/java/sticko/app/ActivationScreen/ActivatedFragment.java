package sticko.app.ActivationScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import sticko.app.AddLinksScreen.AddNewLinksFragment;
import sticko.app.R;

public class ActivatedFragment extends Fragment {
    private String themeSelected;
    public Toolbar toolbar;
    private RelativeLayout main_container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activated, container, false);
        main_container = view.findViewById(R.id.main_container);

        // set theme color
        SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }

        main_container.setBackgroundColor(Color.parseColor(themeSelected));
// handler
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AddNewLinksFragment addNewLinksFragment = new AddNewLinksFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, addNewLinksFragment, "tag")
                        .addToBackStack(null)
                        .commit();

            }
        }, 2000);

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
//                    Intent intent = new Intent((FragmentActivity) getActivity(), Announcements.class);
//                    startActivity(intent);
//                    getActivity().finish();

//

                    return true;

                }
                return false;
            }
        });

    }
}