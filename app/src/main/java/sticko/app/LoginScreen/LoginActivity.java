
package sticko.app.LoginScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.irozon.sneaker.Sneaker;

import org.json.JSONException;
import org.json.JSONObject;

import sticko.app.ForgotPassword.ForgotPasswordActivity;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Loader;
import sticko.app.R;
import sticko.app.Registrations.RegistrationActivity;
import sticko.app.Session;
import sticko.app.Utils.UtilsForPopups;

public class LoginActivity extends AppCompatActivity {
    private String LOGIN_URL = "https://profile.sticko.fr/api/v1/login";
    private AppCompatButton btn_register, btn_signIn;
    private TextView forgot_password, tv_not_acc;
    private EditText edt_email, edt_password;
    View parentLayout;
    private Loader loader;
    private Session session;
    String token;
    private Boolean check_email = false, check_password = false;
    CheckBox checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loader = new Loader(this);
        session = new Session(this);
        parentLayout = findViewById(android.R.id.content);
        btn_register = findViewById(R.id.btn_register);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_signIn = findViewById(R.id.btn_signIn);
        forgot_password = findViewById(R.id.forgot_password);
        checkbox = findViewById(R.id.checkbox);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(register);

            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!check_email && !check_password) {
                    if (checkAllFields()) {
                        UtilsForPopups.alertPopup(LoginActivity.this, "Error","Veuillez saisir tous les champs");
                    } else if (checkEmail()) {
                        UtilsForPopups.alertPopup(LoginActivity.this, "Error","Veuillez saisir une adresse e-mail valide");

                    } else {
                        checkInternetConnection();
                    }
                }
//                Intent signIn = new Intent(LoginActivity.this , HomeScreenActivity.class );
//                startActivity(signIn);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotPassword = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPassword);

            }
        });
        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    private void login() throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("email", edt_email.getText().toString());
        map.put("password", edt_password.getText().toString());
        Log.i("map", String.valueOf(map));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                Log.i("response", String.valueOf(result));
                try {
                    JSONObject jo = result.getJSONObject("success");
                    token = jo.getString("token");
                    session.setToken(token);
                    session.setRememberMe(checkbox.isChecked());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent signIn = new Intent(LoginActivity.this, HomeScreenActivity.class);
                signIn.putExtra("token", token);
                startActivity(signIn);
                finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                if (error != null && error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 205) {
                        UtilsForPopups.alertPopup(LoginActivity.this, "Error","Inactive account");

                    } else if (error.networkResponse.statusCode == 401) {
                        UtilsForPopups.alertPopup(LoginActivity.this, "Error","L'e-mail ou le mot de passe ne correspond pas");

                    } else if (error.networkResponse.statusCode == 404) {
                        UtilsForPopups.alertPopup(LoginActivity.this, "Error","Network error");
                    } else if (error.networkResponse.statusCode == 422) {
                        UtilsForPopups.alertPopup(LoginActivity.this, "Error","Invalid Parameters");

                    }
                }
                assert error != null;
                error.printStackTrace();
            }

        });
        Volley.newRequestQueue(this).add(sr);

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean checkAllFields() {
        if (TextUtils.isEmpty(edt_email.getText().toString()) ||
                TextUtils.isEmpty(edt_password.getText().toString())
        )
            return true;
        else
            return false;
    }

    private boolean checkEmail() {
//        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            UtilsForPopups.alertPopup(LoginActivity.this, "Error","Veuillez saisir une adresse e-mail valide");
            return true;
        }
//        else if (!edt_email.getText().toString().matches(reg_ex)) {
//            UtilsForPopups.alertPopup(LoginActivity.this, "Alert","Veuillez saisir une adresse e-mail valide");
//            return true;
//        }
        else {
            return false;
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            try {
                login();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            loader.hideLoader();
            UtilsForPopups.alertPopup(LoginActivity.this, "Error","Veuillez vérifiez votre connexion à internet et réessayez");
            return false;
        }
    }
}