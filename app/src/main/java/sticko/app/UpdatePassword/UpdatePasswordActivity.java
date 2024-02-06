package sticko.app.UpdatePassword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import sticko.app.Loader;
import sticko.app.LoginScreen.LoginActivity;
import sticko.app.ProcessingError;
import sticko.app.R;
import sticko.app.Utils.UtilsForPopups;

public class UpdatePasswordActivity extends AppCompatActivity {
    public static StringBuilder code;
    private final String UPDATE_PASSWORD_URL = "https://profile.sticko.fr/api/v1/change/password";
    private AppCompatButton btn_confirm;
    private ImageView btn_back;
    private EditText edt_confirm_pas, edt_newPassword;
    View parentLayout;
    private Loader loader;
    public Boolean password_check = false, confirm_password_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        btn_back = findViewById(R.id.btn_back);
        loader = new Loader(this);
        parentLayout = findViewById(android.R.id.content);
        btn_confirm = findViewById(R.id.btn_confirm);
        edt_newPassword = findViewById(R.id.edt_newPassword);
        edt_confirm_pas = findViewById(R.id.edt_confirm_pas);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFieldsForEmptyValues();
                finish();

            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!password_check && !confirm_password_check) {
                    if (checkAllFields()) {
                        UtilsForPopups.alertPopup(UpdatePasswordActivity.this, "Error","Veuillez saisir tous les champs");
                    } else if (!edt_newPassword.getText().toString().equals(edt_confirm_pas.getText().toString())) {
                        UtilsForPopups.alertPopup(UpdatePasswordActivity.this, "Error","Le mot de passe et le mot de passe de confirmation ne correspondent pas");

                    } else if (checkPasswords()) {
                        UtilsForPopups.alertPopup(UpdatePasswordActivity.this, "Error","Le mot de passe doit comporter au moins 7 caractères");
                    } else {
                        try {
                            updatePassword();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
        edt_newPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_confirm_pas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void updatePassword() throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("code", code);
        map.put("password", edt_newPassword.getText().toString());
        map.put("passwordConfirm", edt_confirm_pas.getText().toString());
        Log.i("map" , String.valueOf(map));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, UPDATE_PASSWORD_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("Register_Activity", result.toString());
                loader.hideLoader();
                UtilsForPopups.SuccessPopup(UpdatePasswordActivity.this, "Updated","Votre mot de passe a été mis à jour. Vous pouvez vous connecter avec vos nouveaux identifiants!");
                Intent signIn = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                startActivity(signIn);
                finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                if (error != null && error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 400) {
                        UtilsForPopups.processingError(UpdatePasswordActivity.this);
                    } else if (error.networkResponse.statusCode == 401) {
                        UtilsForPopups.SuccessPopup(UpdatePasswordActivity.this, "Alert","Le code n'est pas correct");

                    } else if (error.networkResponse.statusCode == 404) {
                        UtilsForPopups.alertPopup(UpdatePasswordActivity.this, "Alert","Network error");

                    }
                }
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
        Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean checkAllFields() {
        if (TextUtils.isEmpty(edt_newPassword.getText().toString()) ||
                TextUtils.isEmpty(edt_confirm_pas.getText().toString())
        )
            return true;
        else
            return false;
    }



    private void checkFieldsForEmptyValues() {
        String password = edt_newPassword.getText().toString();
        String confirm_pass = edt_confirm_pas.getText().toString();

        if (!password.equals("") || !confirm_pass.equals("")) {
            UtilsForPopups.alertPopup(UpdatePasswordActivity.this, "Error","Veuillez saisir tous les champs");

        } else {
            finish();
        }

    }

    private Boolean checkPasswords() {
      //  String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{7,}$";
        if (edt_newPassword.getText().toString().length()>=7) {
            return false;
        } else {
            UtilsForPopups.alertPopup(UpdatePasswordActivity.this, "Error","Le mot de passe doit comporter au moins 7 caractères");
            return true;
        }
    }

}
