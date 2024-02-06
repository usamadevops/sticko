package sticko.app.SettingsScreen.TutorialScreens.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sticko.app.Adapters.AddNewLinksAdapter;
import sticko.app.Adapters.FollowingAdapter_tutorials;
import sticko.app.Adapters.Tutorials_screen_five_Adapter;
import sticko.app.Models.Following_model;
import sticko.app.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    //screen four
    private RecyclerView rv_new_links;
    private AddNewLinksAdapter addNewLinksAdapter;
    private final List<Integer> newLinksList = new ArrayList<>();
    // screen five
    private RecyclerView rv_homeScreen;
    private Tutorials_screen_five_Adapter tutorials_screen_five_adapter;
    private final List<Integer> icon_list = new ArrayList<>();
    // screen nine
    private Toolbar toolbar;
    private TextView app_bar_heading;
    private RecyclerView rv_following;
    private FollowingAdapter_tutorials followingAdapter;
    private String themeSelected;


    private List<Following_model> following_list = new ArrayList<>();

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

        SharedPreferences themeColor = getContext().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = null;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {

                root = inflater.inflate(R.layout.tutorial_screen_one, container, false);
                RelativeLayout myLayout = (RelativeLayout) root.findViewById(R.id.rl_fields);
                for (int i = 0; i < myLayout.getChildCount(); i++) {
                    View view = myLayout.getChildAt(i);
                    view.setEnabled(false); // Or whatever you want to do with the view.
                }

                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                break;
            }
            case 2: {
                root = inflater.inflate(R.layout.tutorails_screen_two, container, false);

                RelativeLayout myLayout = (RelativeLayout) root.findViewById(R.id.rl_fields);
                for (int i = 0; i < myLayout.getChildCount(); i++) {
                    View view = myLayout.getChildAt(i);
                    view.setEnabled(false); // Or whatever you want to do with the view.
                }
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                break;
            }
            case 3: {

                root = inflater.inflate(R.layout.tutorial_screen_three, container, false);
                RelativeLayout myLayout = (RelativeLayout) root.findViewById(R.id.rl_activation_icon);
                for (int i = 0; i < myLayout.getChildCount(); i++) {
                    View view = myLayout.getChildAt(i);
                    view.setEnabled(false); // Or whatever you want to do with the view.
                }
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                break;
            }
            case 4: {
                root = inflater.inflate(R.layout.tutorial_screen_four, container, false);
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                rv_new_links = root.findViewById(R.id.rv_new_links);
                rv_new_links.setNestedScrollingEnabled(false);
                rv_new_links.setLayoutManager(new GridLayoutManager(getContext(), 3));
                addNewLinksAdapter = new AddNewLinksAdapter(getContext(), newLinksList);
                rv_new_links.setAdapter(addNewLinksAdapter);

                break;
            }
            case 5: {

                root = inflater.inflate(R.layout.tutorial_screen_five, container, false);
                rv_homeScreen = root.findViewById(R.id.rv_homeScreen);
                toolbar = root.findViewById(R.id.toolbar_main);
                rv_homeScreen.setNestedScrollingEnabled(false);
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                toolbar.setBackgroundColor(Color.parseColor(themeSelected));
                rv_homeScreen.setLayoutManager(new GridLayoutManager(getContext(), 3));
                tutorials_screen_five_adapter = new Tutorials_screen_five_Adapter(getContext(), newLinksList);
                rv_homeScreen.setAdapter(tutorials_screen_five_adapter);
                break;
            }
            case 6: {
                root = inflater.inflate(R.layout.tutorial_screen_six, container, false);
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                break;
            }
            case 7: {
                root = inflater.inflate(R.layout.tutorial_screen_seven, container, false);
                toolbar = root.findViewById(R.id.toolbar_secondary);
                app_bar_heading = toolbar.findViewById(R.id.app_bar_heading);
                app_bar_heading.setText("Liens");
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";
                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                toolbar.setBackgroundColor(Color.parseColor(themeSelected));

                break;
            }
            case 8: {
                root = inflater.inflate(R.layout.tutorial_screen_eight, container, false);
                rv_homeScreen = root.findViewById(R.id.rv_homeScreen);
                toolbar = root.findViewById(R.id.toolbar_main);
                rv_homeScreen.setNestedScrollingEnabled(false);
                rv_homeScreen.setLayoutManager(new GridLayoutManager(getContext(), 3));
                tutorials_screen_five_adapter = new Tutorials_screen_five_Adapter(getContext(), newLinksList);
                rv_homeScreen.setAdapter(tutorials_screen_five_adapter);
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                toolbar.setBackgroundColor(Color.parseColor(themeSelected));

                break;
            }
            case 9: {
                root = inflater.inflate(R.layout.tutorial_screen_nine, container, false);
                toolbar = root.findViewById(R.id.toolbar_secondary);
                ImageButton btn_done = toolbar.findViewById(R.id.btn_done);
                app_bar_heading = toolbar.findViewById(R.id.app_bar_heading);
                app_bar_heading.setText("Following");
                btn_done.setVisibility(View.GONE);

                rv_following = root.findViewById(R.id.rv_following);
                rv_following.setHasFixedSize(true);
                rv_following.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                followingAdapter = new FollowingAdapter_tutorials(getContext());
                rv_following.setAdapter(followingAdapter);
                followingAdapter.notifyDataSetChanged();
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                toolbar.setBackgroundColor(Color.parseColor(themeSelected));
                break;
            }
            case 10: {
                root = inflater.inflate(R.layout.tutorial_screen_ten, container, false);
                if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                        .length() <= 0) {
                    themeSelected = "#09122A";

                }
                RelativeLayout main_container = root.findViewById(R.id.main_container);
                main_container.setBackgroundColor(Color.parseColor(themeSelected));
                //toolbar.setBackgroundColor(Color.parseColor(themeSelected));
                break;
            }
        }
        return root;
    }

}