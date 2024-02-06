package sticko.app.ForgotPassword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.irozon.sneaker.Sneaker;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import sticko.app.Loader;
import sticko.app.ProcessingError;
import sticko.app.R;
import sticko.app.Utils.UtilsForPopups;
import sticko.app.VerificationsScreen.VerificationActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ImageView btn_back;
    private AppCompatButton btn_send_code;
    private String FORGOTPASSWORD_URL = "https://profile.sticko.fr/api/v1/reset/password";
    private EditText edt_email;
    private Loader loader;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edt_email = findViewById(R.id.edt_email);
        btn_back = findViewById(R.id.btn_back);
        btn_send_code = findViewById(R.id.btn_send_code);
        loader = new Loader(this);
        parentLayout = findViewById(android.R.id.content);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_email.getText().toString().equals("")) {
                    UtilsForPopups.alertPopup(ForgotPasswordActivity.this, "Alert","Enter registered email");
                } else if (checkEmail()) {
                    UtilsForPopups.alertPopup(ForgotPasswordActivity.this, "Alert","Veuillez saisir une adresse e-mail valide");

                } else
                    forgotPasswordRequest();


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
    }

    private void forgotPasswordRequest() {
        loader.showLoader();
        StringRequest sr = new StringRequest(Request.Method.POST, FORGOTPASSWORD_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String result) {
                loader.hideLoader();
                Log.i("result", result);
                Intent signIn = new Intent(ForgotPasswordActivity.this, VerificationActivity.class);
                startActivity(signIn);
                finish();

//

            }

        }, new Response.ErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                if (error != null && error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 400) {
                        new ProcessingError().showError(ForgotPasswordActivity.this);
                    } else if (error.networkResponse.statusCode == 401) {
                        UtilsForPopups.alertPopup(ForgotPasswordActivity.this, "Alert","User not found");

                    } else if (error.networkResponse.statusCode == 404) {
                        UtilsForPopups.alertPopup(ForgotPasswordActivity.this, "Alert","Network error");
                    }
                }
            }

        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params;
                params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public byte[] getBody() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("email", edt_email.getText().toString());
                    return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return null;
                }
            }
        };
//
        Volley.newRequestQueue(this).add(sr);


    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            UtilsForPopups.alertPopup(ForgotPasswordActivity.this, "Alert","Fill required Fields");
            return true;
        } else if (!edt_email.getText().toString().matches(reg_ex)) {
             return true;
        } else {
            return false;
        }
    }



}