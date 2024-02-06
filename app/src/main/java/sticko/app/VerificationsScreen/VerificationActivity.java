package sticko.app.VerificationsScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sticko.app.Loader;
import sticko.app.ProcessingError;
import sticko.app.R;
import sticko.app.UpdatePassword.UpdatePasswordActivity;
import sticko.app.Utils.UtilsForPopups;

public class VerificationActivity extends AppCompatActivity {
    private String VERIFICATION_URL = "https://profile.sticko.fr/api/v1/reset/code";
    private AppCompatButton btn_verify;
    private ImageView btn_back;
    private Loader loader;
    View parentLayout;
    public Boolean code1_check = false, code2_check = false, code3_check = false, code4_check = false, code5_check = false;
    private EditText code_1, code_2, code_3, code_4, code_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        btn_back = findViewById(R.id.btn_back);
        parentLayout = findViewById(android.R.id.content);
        btn_verify = findViewById(R.id.btn_verify);
        loader = new Loader(this);
        code_1 = findViewById(R.id.code_1);
        code_2 = findViewById(R.id.code_2);
        code_3 = findViewById(R.id.code_3);
        code_4 = findViewById(R.id.code_4);
        code_5 = findViewById(R.id.code_5);
        parentLayout = findViewById(android.R.id.content);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFieldsForEmptyValues();
                finish();

            }
        });
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!code1_check && !code2_check && !code3_check && !code4_check && !code5_check){
                    if (checkAllFields()) {
                        UtilsForPopups.alertPopup(VerificationActivity.this, "Error","Veuillez saisir tous les champs");
                    }
                    else {
                        try {
                            verifyCode();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }                }
//

            }
        });
//        code_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                   hideKeyboard(v);
//                }
//            }
//        });
//        code_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
//        code_3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
//        code_4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
//        code_5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
        changeFocus();

    }
    private boolean checkAllFields() {
        if (TextUtils.isEmpty(code_1.getText().toString()) ||
                TextUtils.isEmpty(code_2.getText().toString()) ||
                TextUtils.isEmpty(code_3.getText().toString()) ||
                TextUtils.isEmpty(code_4.getText().toString()) ||
                TextUtils.isEmpty(code_5.getText().toString())
        )
            return true;
        else
            return false;
    }

    private void verifyCode() throws JSONException {
        loader.showLoader();
        List<String> codelist = new ArrayList<>();
        codelist.add(code_1.getText().toString());
        codelist.add(code_2.getText().toString());
        codelist.add(code_3.getText().toString());
        codelist.add(code_4.getText().toString());
        codelist.add(code_5.getText().toString());
        StringBuilder code = new StringBuilder();
        for(String s : codelist){
            code.append(s);
        }


        JSONObject map = new JSONObject();
        map.put("code", code);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, VERIFICATION_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("Register_Activity", result.toString());
                try {
                    loader.hideLoader();
                    String token = result.getString("success");
                    Intent signIn = new Intent(VerificationActivity.this, UpdatePasswordActivity.class);
                    UpdatePasswordActivity.code = code;
                    startActivity(signIn);
                finish();
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
                        new ProcessingError().showError(VerificationActivity.this);
                    } else if (error.networkResponse.statusCode == 401) {
                        UtilsForPopups.alertPopup(VerificationActivity.this, "Error","Le code n'est pas correct");
                    }
                    else if (error.networkResponse.statusCode == 404) {
                        UtilsForPopups.alertPopup(VerificationActivity.this, "Error","Network error");


                    }
                }
                error.printStackTrace();
            }

        });
        Volley.newRequestQueue(this).add(sr);
    }


    private void changeFocus() {
        StringBuilder sb = new StringBuilder();

        code_1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & code_1.length() == 1) {
                    sb.append(s);
//                    code_1.clearFocus();
                    code_2.requestFocus();
                    code_2.setCursorVisible(true);

                }
                code_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if (sb.length() == 1) {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {

                    code_1.requestFocus();
                }

            }
        });
        code_2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & code_2.length() == 1) {
                    sb.append(s);
                    code_1.clearFocus();
                    code_3.requestFocus();
                    code_3.setCursorVisible(true);

                }
                code_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if (sb.length() == 1) {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {

                    code_1.requestFocus();
                }

            }
        });
        code_3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & code_3.length() == 1) {
                    sb.append(s);
                    code_3.clearFocus();
                    code_4.requestFocus();
                    code_4.setCursorVisible(true);

                }
                code_3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if (sb.length() == 1) {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {

                    code_2.requestFocus();
                }

            }
        });
        code_4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & code_4.length() == 1) {
                    sb.append(s);
                    code_4.clearFocus();
                    code_5.requestFocus();
                    code_5.setCursorVisible(true);

                }
                code_4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if (sb.length() == 1) {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {

                    code_3.requestFocus();
                }

            }
        });
        code_5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & code_5.length() == 1) {
                    sb.append(s);
//                    code_1.clearFocus();
                    code_5.requestFocus();
                    code_5.setCursorVisible(true);

                }
                code_5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if (sb.length() == 1) {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {

                    code_4.requestFocus();
                }

            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        checkFieldsForEmptyValues();
        finish();
    }
//    private void showDiscardDialog() {
//        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View view_popup = inflater.inflate(R.layout.discard_changes, null);
//        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
//        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
//        alertDialog.setView(view_popup);
//        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
//        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
//        layoutParams.y = 200;
//        layoutParams.x = -70;// top margin
//        alertDialog.getWindow().setAttributes(layoutParams);
//        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
//        btn_discard.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Log.i("CreatePayment", "Button Clicked");
//                alertDialog.dismiss();
//                finish();
//            }
//        });
//
//        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
//        img_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//
//            }
//        });
//        if (!alertDialog.isShowing())
//            alertDialog.show();
//    }
    private void checkFieldsForEmptyValues() {
        String code1 = code_1.getText().toString();
        String code2 = code_2.getText().toString();
        String code3 = code_3.getText().toString();
        String code4 = code_4.getText().toString();
        String code5 = code_5.getText().toString();

        if ( !code1.equals("") || !code2.equals("") || !code3.equals("") ||!code4.equals("") || !code5.equals("")) {
            UtilsForPopups.alertPopup(VerificationActivity.this, "Error","Veuillez saisir tous les champs");

        } else {
            finish();
        }

    }
}
