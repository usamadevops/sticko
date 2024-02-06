// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AppCompatButton btnRegister;

  @NonNull
  public final AppCompatButton btnSignIn;

  @NonNull
  public final CheckBox checkbox;

  @NonNull
  public final EditText edtEmail;

  @NonNull
  public final TextInputEditText edtPassword;

  @NonNull
  public final TextView forgotPassword;

  @NonNull
  public final ImageView ivLogo;

  @NonNull
  public final TextInputLayout layoutNewPass;

  @NonNull
  public final RelativeLayout rlForgotSect;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final TextView tvNotAcc;

  private ActivityLoginBinding(@NonNull RelativeLayout rootView,
      @NonNull AppCompatButton btnRegister, @NonNull AppCompatButton btnSignIn,
      @NonNull CheckBox checkbox, @NonNull EditText edtEmail,
      @NonNull TextInputEditText edtPassword, @NonNull TextView forgotPassword,
      @NonNull ImageView ivLogo, @NonNull TextInputLayout layoutNewPass,
      @NonNull RelativeLayout rlForgotSect, @NonNull TextView tvHeading,
      @NonNull TextView tvNotAcc) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.btnSignIn = btnSignIn;
    this.checkbox = checkbox;
    this.edtEmail = edtEmail;
    this.edtPassword = edtPassword;
    this.forgotPassword = forgotPassword;
    this.ivLogo = ivLogo;
    this.layoutNewPass = layoutNewPass;
    this.rlForgotSect = rlForgotSect;
    this.tvHeading = tvHeading;
    this.tvNotAcc = tvNotAcc;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_register;
      AppCompatButton btnRegister = rootView.findViewById(id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.btn_signIn;
      AppCompatButton btnSignIn = rootView.findViewById(id);
      if (btnSignIn == null) {
        break missingId;
      }

      id = R.id.checkbox;
      CheckBox checkbox = rootView.findViewById(id);
      if (checkbox == null) {
        break missingId;
      }

      id = R.id.edt_email;
      EditText edtEmail = rootView.findViewById(id);
      if (edtEmail == null) {
        break missingId;
      }

      id = R.id.edt_password;
      TextInputEditText edtPassword = rootView.findViewById(id);
      if (edtPassword == null) {
        break missingId;
      }

      id = R.id.forgot_password;
      TextView forgotPassword = rootView.findViewById(id);
      if (forgotPassword == null) {
        break missingId;
      }

      id = R.id.iv_logo;
      ImageView ivLogo = rootView.findViewById(id);
      if (ivLogo == null) {
        break missingId;
      }

      id = R.id.layout_new_pass;
      TextInputLayout layoutNewPass = rootView.findViewById(id);
      if (layoutNewPass == null) {
        break missingId;
      }

      id = R.id.rl_forgot_sect;
      RelativeLayout rlForgotSect = rootView.findViewById(id);
      if (rlForgotSect == null) {
        break missingId;
      }

      id = R.id.tv_heading;
      TextView tvHeading = rootView.findViewById(id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.tv_not_acc;
      TextView tvNotAcc = rootView.findViewById(id);
      if (tvNotAcc == null) {
        break missingId;
      }

      return new ActivityLoginBinding((RelativeLayout) rootView, btnRegister, btnSignIn, checkbox,
          edtEmail, edtPassword, forgotPassword, ivLogo, layoutNewPass, rlForgotSect, tvHeading,
          tvNotAcc);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
