package sticko.app.Connections;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import sticko.app.AddLinksScreen.AddNewLinksFragment;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.SvgDecoder.SvgDecoder;
import sticko.app.SvgDrawableTranscoder.SvgDrawableTranscoder;
import sticko.app.SvgSoftwareLayerSetter.SvgSoftwareLayerSetter;
import sticko.app.Utils.UtilsForPopups;

public class AddLinksActivity extends AppCompatActivity {
    private final static String ADD_LINK_URL = "https://profile.sticko.fr/api/v1/addSocialMedia";
    public static String PATH = "";
    public static String logoPath;
    public static String name;
    public static int social_media_id;
    public static String placeholder;
    private ImageView iv_logo;
    private TextView tv_heading;
    private EditText edt_connection;
    private ImageView btn_add;
    private Session session;
    private Loader loader;
    View parentLayout;
    private String registerToken;
    public String token, themeSelected;
    private ImageButton btn_close, btn_done, btn_close_start;
    public Boolean connection_check = false;
    RelativeLayout main_container;
    private Toolbar toolbar;
    private TextView app_bar_heading, tv_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_links);
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        btn_add = toolbar.findViewById(R.id.btn_add);
        parentLayout = findViewById(android.R.id.content);
        app_bar_heading = toolbar.findViewById(R.id.app_bar_heading);

        btn_close_start = toolbar.findViewById(R.id.btn_close_start);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_close = findViewById(R.id.btn_close);
        main_container = findViewById(R.id.main_container);
        iv_logo = findViewById(R.id.iv_logo);
        tv_heading = findViewById(R.id.tv_heading);
        edt_connection = findViewById(R.id.edt_connection);
        tv_description = findViewById(R.id.tv_description);
        tv_heading.setText(name);
        btn_close.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_close_start.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.VISIBLE);
        session = new Session(this);
        loader = new Loader(this);

        app_bar_heading.setText("Ajouter lien");
        // phone keyboard
        edt_connection.setHint("@Sticko");
        if (name.equals("phone") || name.equals("whatsapp")) {
            edt_connection.setInputType(InputType.TYPE_CLASS_PHONE);
            tv_description.setText("Renseigne ton phone d’utilisateur");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Login_data",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        // set theme color
        SharedPreferences themeColor = getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }
        toolbar.setBackgroundColor(Color.parseColor(themeSelected));
        main_container.setBackgroundColor(Color.parseColor(themeSelected));

        if (session.getToken().equals("")) {
            registerToken = token;
            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllFields()) {
                } else if (name.equals("website") || name.equals("Random")) {
                    if (!isValidUrl(edt_connection.getText().toString())) {
                        Snackbar.make(parentLayout, "Veuillez saisir une URL de site Web valide", Snackbar.LENGTH_SHORT).show();

                    } else {
                        try {
                            addConnection();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else {
                    try {
                        addConnection();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        btn_close_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(PATH.equals("FROM_NFC_WRITER")){
                    Fragment mFragment = new AddNewLinksFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, mFragment).commit();
                }
                else {
                    Intent intent = new Intent(AddLinksActivity.this, Connections.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        DrawableCompat.setTint(
                DrawableCompat.wrap(btn_close_start.getDrawable()),
                ContextCompat.getColor(AddLinksActivity.this, R.color.white));


        //svg loader
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(AddLinksActivity.this)
                .using(Glide.buildStreamModelLoader(Uri.class, AddLinksActivity.this), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());

        requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(logoPath))
                .into(iv_logo);
    }

    private void addConnection() throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("social_media_username", edt_connection.getText().toString());
        map.put("social_media_id", social_media_id);

        Log.i("Website", String.valueOf(map));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, ADD_LINK_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("respones_update", String.valueOf(response));
                loader.hideLoader();
                UtilsForPopups.SuccessPopup(AddLinksActivity.this, "Successful","Réseaux sociaux ajoutés avec succès");
                if(PATH.equals("FROM_NFC_WRITER")){
                    Fragment mFragment = null;
                    mFragment = new AddNewLinksFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, mFragment).commit();
                }
                else {
                    Intent intent = new Intent(AddLinksActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loader.setVisibility(View.GONE);
                loader.hideLoader();
                if (error instanceof NetworkError) {
                    Toast.makeText(AddLinksActivity.this, "NetworkError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AddLinksActivity.this, "ServerError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AddLinksActivity.this, "AuthFailureError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof ParseError) {
                    Toast.makeText(AddLinksActivity.this, "ParseError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AddLinksActivity.this, "NoConnectionError", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {
                    Toast.makeText(AddLinksActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
//                       

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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(AddLinksActivity.this).add(sr);

    }

    private boolean checkAllFields() {
        if (TextUtils.isEmpty(edt_connection.getText().toString())
        )
            return true;
        else
            return false;
    }

    private boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }
}