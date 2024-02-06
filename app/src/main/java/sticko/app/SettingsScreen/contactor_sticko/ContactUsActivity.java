package sticko.app.SettingsScreen.contactor_sticko;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.Utils.UtilsForPopups;

public class ContactUsActivity extends AppCompatActivity {
    private String CONTACT_US_URL = "https://profile.sticko.fr/api/v1/contact";

    public ImageButton btn_close, btn_add, btn_done, btn_close_start;
    private EditText edt_name, edt_phone_number, edt_email, edt_message;
    private Session session;
    private Loader loader;
    View parentLayout;
    RelativeLayout main_container;
    private Toolbar toolbar;
    private TextView app_bar_heading;
    private String themeSelected, token;
    public Boolean email_check = false, name_check = false, phone_check = false, message_check = false;

    private void sendMsg(View v) throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("name", edt_name.getText().toString());
        map.put("email", edt_email.getText().toString());
        map.put("message", edt_message.getText().toString());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, CONTACT_US_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("update", result.toString());
                loader.hideLoader();
                if (result.has("success")) {
                    hideKeyboard(v);
                    UtilsForPopups.SuccessPopup(ContactUsActivity.this, "Sent","Vos commentaires sont soumis avec succès");

                    //delay to view notification
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent login_intent = new Intent(((FragmentActivity) ContactUsActivity.this), HomeScreenActivity.class);
                            ((FragmentActivity) ContactUsActivity.this).startActivity(login_intent);
                        }
                    }, 2000);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                if (error instanceof NetworkError) {
                    UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","NetworkError");
                } else if (error instanceof ServerError) {
                    UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","ServerError");
                } else if (error instanceof AuthFailureError) {
                    UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","AuthFailureError");
                } else if (error instanceof ParseError) {
                    UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","ParseError");
                } else if (error instanceof NoConnectionError) {
                    UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","NoConnectionError");
                } else if (error instanceof TimeoutError) {
                    UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","Oops. Timeout error!");

                }
            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(sr);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact__us_);

        toolbar = findViewById(R.id.toolbar_secondary);
        ((AppCompatActivity) ContactUsActivity.this).getSupportActionBar();
        btn_add = toolbar.findViewById(R.id.btn_add);
        parentLayout = toolbar.findViewById(android.R.id.content);
        btn_close_start = toolbar.findViewById(R.id.btn_close_start);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_close = findViewById(R.id.btn_close);
        btn_close.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_close_start.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.VISIBLE);
        app_bar_heading = toolbar.findViewById(R.id.app_bar_heading);
        app_bar_heading.setText("Contacter Sticko");
        //init
        session = new Session(ContactUsActivity.this);
        loader = new Loader(ContactUsActivity.this);
        // button listeners
        btn_close_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(((FragmentActivity) ContactUsActivity.this), HomeScreenActivity.class);
                ((FragmentActivity) ContactUsActivity.this).startActivity(login_intent);
            }
        });

        // selected theme
        SharedPreferences themeColor = getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }
        toolbar.setBackgroundColor(Color.parseColor(themeSelected));
        ((AppCompatActivity) ContactUsActivity.this).getSupportActionBar();
        main_container = findViewById(R.id.main_container);
        main_container.setBackgroundColor(Color.parseColor(themeSelected));
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email_check && !name_check && !phone_check && !message_check) {
                    if (checkAllFields()) {                        UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","Veuillez saisir une adresse e-mail valide");
                        UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","Veuillez saisir tous les champs");
                    } else if (checkEmail())
                        UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","Veuillez saisir une adresse e-mail valide");
                    else {
                        try {
                            sendMsg(view);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        main_container = findViewById(R.id.main_container);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone_number = findViewById(R.id.edt_phone_number);
        edt_message = findViewById(R.id.edt_message);
        ImageView iv_logo = findViewById(R.id.iv_logo);

        // hide keyboard listner
        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }

            }
        });
        edt_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_phone_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private boolean checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            UtilsForPopups.alertPopup(ContactUsActivity.this, "Alert","Fill required Fields");
            return true;
        } else if (!edt_email.getText().toString().matches(reg_ex)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkAllFields() {
        return TextUtils.isEmpty(edt_email.getText().toString()) ||
                TextUtils.isEmpty(edt_name.getText().toString()) ||
                TextUtils.isEmpty(edt_phone_number.getText().toString()) ||
                TextUtils.isEmpty(edt_message.getText().toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent((FragmentActivity) ContactUsActivity.this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) ContactUsActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}