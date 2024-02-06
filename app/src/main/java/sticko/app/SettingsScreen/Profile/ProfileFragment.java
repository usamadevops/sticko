package sticko.app.SettingsScreen.Profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import sticko.app.AppConstant;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.Models.Message;
import sticko.app.R;
import sticko.app.RestClient;
import sticko.app.Session;
import sticko.app.Utils.Utils;
import sticko.app.Utils.UtilsForPopups;

import static android.app.Activity.RESULT_OK;
import static sticko.app.Utils.Utils.createMultipartBodyForImage;

public class ProfileFragment extends Fragment {
    public static String token;
    public static boolean checkUser;
    private String UPDATE_PROFILE_URL = "https://profile.sticko.fr/api/v1/updateProfile";
    private ImageView btn_add;
    private final String DASHBOARD_URL = "https://profile.sticko.fr/api/v1/profile";
    private final String PATH = "https://profile.sticko.fr/";
    private String UPDATE_IMAGE_URL = "https://profile.sticko.fr/api/v1/updateImage";
    private ImageButton btn_close, btn_done, btn_close_start;
    private CircleImageView color_dark_gray, color_main, color_puruple, color_blue_violet,
            color_black, color_turquolse, color_light_green, color_blue;
    boolean selectTheme = false;
    private EditText edt_bio, edt_username, edt_name, edt_phone_number, edt_dob, edt_address;
    private TextView tv_theme, tv_change_profile;
    private static final int SELECT_FILE = 1;
    private CircleImageView iv_profile;
    private Session session;
    private Loader loader;
    View parentLayout;
    String registerToken;
    String username, bio, name, phone_no, dob, address, score, imagePath, themeSelected, theme_color;
    File imageFile;
    ProgressBar progressBar;
    RelativeLayout main_container;
    private Toolbar toolbar;
    SharedPreferences sp_themeColor;
    Bitmap profileBitmap;
    String path;
    boolean selected = false;
    private Uri picUri;
    protected int LOAD_IMAGE_CAMERA = 0, CROP_IMAGE = 1, LOAD_IMAGE_GALLARY = 2;
    private String link_username;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar_secondary);
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        btn_add = toolbar.findViewById(R.id.btn_add);
        parentLayout = toolbar.findViewById(android.R.id.content);

        btn_close_start = toolbar.findViewById(R.id.btn_close_start);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_close = view.findViewById(R.id.btn_close);
        btn_close.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_close_start.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.VISIBLE);
        session = new Session(getContext());
        //get token
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login_data",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        // set theme color
        sp_themeColor = getContext().getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = sp_themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        } else {
            toolbar.setBackgroundColor(Color.parseColor(themeSelected));
            main_container.setBackgroundColor(Color.parseColor(themeSelected));
        }
        if (session.getToken().equals("")) {
            registerToken = token;

            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }
        //check user
        if (!checkUser) {
            toolbar.setBackgroundColor(Color.parseColor("#09122A"));
            main_container.setBackgroundColor(Color.parseColor("#09122A"));
        } else {
            toolbar.setBackgroundColor(Color.parseColor(themeSelected));
            main_container.setBackgroundColor(Color.parseColor(themeSelected));
        }

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (selected) {
                        uploadImage();
                    }
                    if (edt_name.getText().toString().contains(" ")) {
                        Toast.makeText(getContext(), "Le nom d'utilisateur ne doit pas contenir d'espaces", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (edt_name.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Veuillez saisir le nom d'utilisateur", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    updateProfile();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        btn_close_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((FragmentActivity) getActivity(), HomeScreenActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        DrawableCompat.setTint(
                DrawableCompat.wrap(btn_close_start.getDrawable()),
                ContextCompat.getColor(getContext(), R.color.white));
        getProfileData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        color_dark_gray = view.findViewById(R.id.color_dark_gray);
        edt_bio = view.findViewById(R.id.edt_bio);
        edt_name = view.findViewById(R.id.edt_name);
        edt_phone_number = view.findViewById(R.id.edt_phone_number);
        edt_address = view.findViewById(R.id.edt_address);
        edt_dob = view.findViewById(R.id.edt_dob);
        edt_username = view.findViewById(R.id.edt_username);
        tv_theme = view.findViewById(R.id.tv_theme);
        color_main = view.findViewById(R.id.color_main);
        color_blue_violet = view.findViewById(R.id.color_blue_violet);
        color_black = view.findViewById(R.id.color_black);
        color_turquolse = view.findViewById(R.id.color_turquolse);
        color_light_green = view.findViewById(R.id.color_light_green);
        color_blue = view.findViewById(R.id.color_blue);
        color_puruple = view.findViewById(R.id.color_puruple);
        iv_profile = view.findViewById(R.id.iv_profile);
        tv_change_profile = view.findViewById(R.id.tv_change_profile);
        progressBar = view.findViewById(R.id.progress);
        main_container = view.findViewById(R.id.main_container);
        loader = new Loader(getContext());

        tv_change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                        }).check();
                UpdateImage();
            }
        });
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                        }).check();
                UpdateImage();
            }
        });
        edt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(getActivity());
                }
            }
        });
        edt_bio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(getActivity());
                }
            }
        });


        color_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "000000";
                selectTheme = true;
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.black)));
                main_container.setBackgroundColor(Color.BLACK);
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));
            }
        });
        color_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "0084E2";
                selectTheme = true;
                main_container.setBackgroundColor(getResources()
                        .getColor(R.color.blue));
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.blue)));
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));
            }
        });
        color_light_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "96CB04";
                selectTheme = true;
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.light_green)));
                main_container.setBackgroundColor(getResources()
                        .getColor(R.color.light_green));
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_close_start.getDrawable()),
                        ContextCompat.getColor(getContext(), R.color.white));
            }
        });
        color_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "09122A";
                selectTheme = true;
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.primary_color)));
                main_container.setBackgroundColor(getResources()
                        .getColor(R.color.primary_color));
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_close_start.getDrawable()),
                        ContextCompat.getColor(getContext(), R.color.white));
            }
        });
        color_puruple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "510084";
                selectTheme = true;
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.purple)));
                main_container.setBackgroundColor(getResources()
                        .getColor(R.color.purple));
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_close_start.getDrawable()),
                        ContextCompat.getColor(getContext(), R.color.white));
            }
        });
        color_turquolse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "04C3CB";
                selectTheme = true;
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.turquolse)));
                main_container.setBackgroundColor(getResources()
                        .getColor(R.color.turquolse));
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_close_start.getDrawable()),
                        ContextCompat.getColor(getContext(), R.color.white));
            }
        });
        color_blue_violet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "b20090";
                selectTheme = true;
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.blue_violet)));
                main_container.setBackgroundColor(getResources()
                        .getColor(R.color.blue_violet));
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_close_start.getDrawable()),
                        ContextCompat.getColor(getContext(), R.color.white));
            }
        });
        color_dark_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme_color = "242627";
                selectTheme = true;
                Toolbar toolbar = getActivity().findViewById(R.id.toolbar_secondary);
                ((AppCompatActivity) getActivity()).getSupportActionBar();
                toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.dark_gray)));
                main_container.setBackgroundColor(getResources()
                        .getColor(R.color.dark_gray));
                tv_theme.setTextColor(getResources()
                        .getColor(R.color.white));

                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_close_start.getDrawable()),
                        ContextCompat.getColor(getContext(), R.color.white)
                );
            }
        });
        return view;
    }

    private void UpdateImage() {
        openImageChooserDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    private void getProfileData() {
        loader.showLoader();
        String token = session.getToken();
        Log.i("token", token);
        StringRequest getRequest = new StringRequest(Request.Method.GET, DASHBOARD_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("profile_getData", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject responseData = jsonObject.getJSONObject("success");
                            JSONObject userData = responseData.getJSONObject("user");
                            //get data
                            username = userData.getString("username");
                            link_username = userData.getString("link_username");
                            bio = userData.getString("bio");
                            score = userData.getString("score");
                            name = userData.getString("name");
                            phone_no = userData.getString("telephone");
                            address = userData.getString("address");
                            dob = userData.getString("dob");
                            //set data
                            imagePath = userData.getString("profile_picture_path");
                            progressBar.setVisibility(View.GONE);
                            loader.hideLoader();
                            Picasso.get().load(PATH + imagePath).placeholder(R.drawable.profile_icon).into(iv_profile);
                            if (username == null || username.trim().equals("null") || username.trim()
                                    .length() <= 0) {
                                edt_username.setHint(getResources().getString(R.string.username_profile));
                            } else
                                edt_username.setText("profile.sticko.fr/user/" + link_username);
                            if (username == null || username.trim().equals("null") || username.trim()
                                    .length() <= 0) {
                                edt_name.setHint(getResources().getString(R.string.user_name));
                            } else
                                edt_name.setText(username);
                            if (phone_no == null || phone_no.trim().equals("null") || phone_no.trim()
                                    .length() <= 0) {
                                edt_phone_number.setHint(getResources().getString(R.string.user_phone_no));
                            } else
                                edt_phone_number.setText(phone_no);
                            if (address == null || address.trim().equals("null") || address.trim()
                                    .length() <= 0) {
                                edt_address.setHint(getResources().getString(R.string.address));
                            } else
                                edt_address.setText(address);
                            if (dob == null || dob.trim().equals("null") || dob.trim()
                                    .length() <= 0) {
                                edt_dob.setHint(getResources().getString(R.string.user_dob));
                            } else
                                edt_dob.setText(dob);
                            if (bio == null || bio.trim().equals("null") || bio.trim()
                                    .length() <= 0) {
                                edt_bio.setHint("Bio");
                            } else
                                edt_bio.setText(bio);
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
                        progressBar.setVisibility(View.GONE);
                        if (error instanceof NetworkError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert", "NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert", "ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert", "AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert", "ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert", "NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(getActivity(), "Alert", "Oops. Timeout error!");

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

    private void updateProfile() throws JSONException {
        String selectedTheme = themeSelected;

        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("name", name);
        map.put("username", edt_name.getText().toString());
        map.put("bio", edt_bio.getText().toString());
        map.put("telephone", edt_phone_number.getText().toString());
        map.put("dob", edt_dob.getText().toString());
        map.put("address", edt_address.getText().toString());
        if (selectTheme) {
            map.put("theme", theme_color);
        } else
            map.put("theme", selectedTheme.substring(1));

        Log.i("mapProfile", String.valueOf(map));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, UPDATE_PROFILE_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("update", result.toString());
                loader.hideLoader();
                if (result.has("success")) {
                    UtilsForPopups.SuccessPopup(getActivity(), "Updated", "Mise à jour du profil réussie");

                    // SuccessPopUP("Profile Updated", "Your profile has been updated");
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                if (error instanceof NetworkError) {
                    UtilsForPopups.alertPopup(getActivity(), "Alert", "NetworkError");
                } else if (error instanceof ServerError) {
                    UtilsForPopups.alertPopup(getActivity(), "Alert", "ServerError");
                } else if (error instanceof AuthFailureError) {
                    UtilsForPopups.alertPopup(getActivity(), "Alert", "AuthFailureError");
                } else if (error instanceof ParseError) {
                    UtilsForPopups.alertPopup(getActivity(), "Alert", "ParseError");
                } else if (error instanceof NoConnectionError) {
                    UtilsForPopups.alertPopup(getActivity(), "Alert", "NoConnectionError");
                } else if (error instanceof TimeoutError) {
                    UtilsForPopups.alertPopup(getActivity(), "Alert", "Oops. Timeout error!");

                }
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + registerToken);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);

    }

    public void openImageChooserDialog() {

        CropImage.activity()
                .setAspectRatio(2, 2)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setFixAspectRatio(true)
                .start(getContext(), this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                selected = true;
                Uri resultUri = result.getUri();
                try {
                    profileBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profileBitmap = Utils.BitmapResize.fixResolution(profileBitmap, 1024, 1024);
                imageFile = Utils.bitmapToFile(getContext(), profileBitmap, "Profile", 100);
                iv_profile.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        iv_profile.setImageURI(resultUri);
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void startCrop(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getActivity());
    }


    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public static String getPath(Context context, Uri uri) {
        try {
            String result = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                    result = cursor.getString(column_index);
                }
                cursor.close();
            }
            if (result == null) {
                result = null;
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void uploadImage() throws JSONException {
        if (imageFile != null) {
            loader.showLoader();
            Call<Message<String>> call = RestClient.getClient().AddImage(AppConstant.Bearer + registerToken, createMultipartBodyForImage(imageFile, "file"));

            call.enqueue(new Callback<Message<String>>() {
                @Override
                public void onResponse(Call<Message<String>> call, retrofit2.Response<Message<String>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            String url = response.body().getData();
                            Bitmap imageBitmap = null;

                            try {
                                imageBitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            if (imageBitmap != null)
                                iv_profile.setImageBitmap(imageBitmap);
                            else
                                iv_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                        }
                    } else {
                        loader.hideLoader();
                        //Toast.makeText(getContext(), "not uploaded", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Message<String>> call, Throwable t) {
                    System.out.println("Call Error" + t.getMessage());
                }
            });
        } else {
            UtilsForPopups.alertPopup(getActivity(), "Alert", "Image is empty");
        }
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

    protected void CropImage(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 2);
            cropIntent.putExtra("aspectY", 3);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 600);
            cropIntent.putExtra("outputY", 600);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_IMAGE);

        } catch (ActivityNotFoundException e) {
            UtilsForPopups.alertPopup(getActivity(), "Alert", "Your device doesn't support the crop action!");
        }
    }
}