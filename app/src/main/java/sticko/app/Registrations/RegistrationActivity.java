package sticko.app.Registrations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import sticko.app.Loader;
import sticko.app.LoginScreen.LoginActivity;
import sticko.app.ProcessingError;
import sticko.app.Session;
import sticko.app.SettingsScreen.Profile.ProfileFragment;
import sticko.app.R;
import sticko.app.Utils.UtilsForPopups;
import sticko.app.VerificationsScreen.VerificationActivity;

public class RegistrationActivity extends AppCompatActivity {
    private static final String REGISTER_URL = "https://profile.sticko.fr/api/v1/register";
    private AppCompatButton btn_registration, btn_login;
    private EditText edt_email, edt_newPassword, edt_username, edt_confirm_pass;
    private TextInputLayout layout_new_pass, layout_confirm_pass;
    private Loader loader;
    public Boolean checkbox_check = false, email_check = false, username_check = false, password_check = false, confirm_pass_check = false, city_check = false;
    View parentLayout;
    private CheckBox checkbox;
    private TextView privacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        parentLayout = findViewById(android.R.id.content);
        btn_registration = findViewById(R.id.btn_registration);
        btn_login = findViewById(R.id.btn_login);
        edt_email = findViewById(R.id.edt_email);
        checkbox = findViewById(R.id.checkbox);
        edt_newPassword = findViewById(R.id.edt_newPassword);
        edt_username = findViewById(R.id.edt_username);
        edt_confirm_pass = findViewById(R.id.edt_confirm_pass);
        layout_new_pass = findViewById(R.id.layout_new_pass);
        layout_confirm_pass = findViewById(R.id.layout_confirm_pass);
        privacyPolicy = findViewById(R.id.privacyPolicy);
        loader = new Loader(this);


        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email_check && !username_check && !password_check && !confirm_pass_check) {

                    if (checkAllFields()) {
                        UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Veuillez saisir tous les champs");

                    } else if (checkEmail()) {
                        UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Veuillez saisir une adresse e-mail valide");

                    } else if (!edt_newPassword.getText().toString().equals(edt_confirm_pass.getText().toString())) {
                        UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Le mot de passe et le mot de passe de confirmation ne correspondent pas");

                    } else if (checkPasswords()) {
                        UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Le mot de passe doit comporter au moins 7 caractères");

                    } else if (!checkbox_check) {
                        UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Veuillez accepter les termes et conditions");


                    } else {
                        try {
                            registerUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        checkbox_check = true;
                                                    } else
                                                        checkbox_check = false;

                                                }
                                            }
        );
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  checkFieldsForEmptyValues();

                Intent signIn = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(signIn);
                finish();
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
        edt_newPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_confirm_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        };

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sticko.fr/pages/mentions-legales"));
                startActivity(browserIntent);
            }
        });

        edt_username.addTextChangedListener(textWatcher);
        edt_email.addTextChangedListener(textWatcher);
        edt_newPassword.addTextChangedListener(textWatcher);
        edt_confirm_pass.addTextChangedListener(textWatcher);

    }


    private boolean checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Veuillez saisir une adresse e-mail valide");

            return true;
        } else if (!edt_email.getText().toString().matches(reg_ex)) {
            UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Veuillez saisir une adresse e-mail valide");

            return true;
        } else {
            return false;
        }
    }

    private boolean checkAllFields() {
        if (TextUtils.isEmpty(edt_email.getText().toString()) ||
                TextUtils.isEmpty(edt_username.getText().toString()) ||
                TextUtils.isEmpty(edt_newPassword.getText().toString()) ||
                TextUtils.isEmpty(edt_confirm_pass.getText().toString())
        )
            return true;
        else
            return false;
    }



    private void registerUser() throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("username", edt_username.getText().toString());
        map.put("email", edt_email.getText().toString());
        map.put("password", edt_newPassword.getText().toString());
        map.put("passwordConfirm", edt_confirm_pass.getText().toString());
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("Register_Activity", result.toString());
                try {
                    loader.hideLoader();
                    JSONObject jo = result.getJSONObject("success");
                    String token = jo.getString("token");
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new ProfileFragment());
                    fragmentTransaction.commit();
                    SharedPreferences sharedPreferences = getSharedPreferences("Login_data",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.putBoolean("remember_me", true);
                    editor.apply();
                    ProfileFragment.checkUser = false;

                    //update session
                    Session session = new Session(getApplicationContext());
                    session.setToken(token);
                    session.setUsername(edt_username.getText().toString());
                    session.setRememberMe(true);
                } catch (JSONException e) {
                    loader.hideLoader();

                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                if (error != null && error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 400) {
                        new ProcessingError().showError(RegistrationActivity.this);
                    } else if (error.networkResponse.statusCode == 401) {
                        String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        try {
                            JSONObject jsonObject = new JSONObject(body);
//                            String username = null;
//                            String email = null;
                            if (jsonObject.getJSONObject("error").has("username")) {
                                JSONArray jsonArray = (JSONArray) jsonObject.getJSONObject("error").get("username");
                                UtilsForPopups.alertPopup(RegistrationActivity.this, "Error",jsonArray.get(0).toString());

                            } else if (jsonObject.getJSONObject("error").has("email")) {
                                JSONArray jsonArray = (JSONArray) jsonObject.getJSONObject("error").get("email");
                                UtilsForPopups.alertPopup(RegistrationActivity.this, "Error",jsonArray.get(0).toString());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (error.networkResponse.statusCode == 404) {
                        new ProcessingError().showError(RegistrationActivity.this);
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
//        checkFieldsForEmptyValues();
        finish();
    }

    private Boolean checkPasswords() {
       // String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{7,}$";
        if (edt_newPassword.getText().toString().length()>=7) {
            return false;
        } else {
            UtilsForPopups.alertPopup(RegistrationActivity.this, "Error","Le mot de passe doit comporter au moins 7 caractères");

            return true;
        }
    }



}