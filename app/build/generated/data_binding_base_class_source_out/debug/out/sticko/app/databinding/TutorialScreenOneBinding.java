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
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class TutorialScreenOneBinding implements ViewBinding {
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
  public final RelativeLayout mainContainer;

  @NonNull
  public final RelativeLayout rlFields;

  @NonNull
  public final RelativeLayout rlForgotSect;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final TextView tvNotAcc;

  @NonNull
  public final TextView txtScreenOne;

  private TutorialScreenOneBinding(@NonNull RelativeLayout rootView,
      @NonNull AppCompatButton btnRegister, @NonNull AppCompatButton btnSignIn,
      @NonNull CheckBox checkbox, @NonNull EditText edtEmail,
      @NonNull TextInputEditText edtPassword, @NonNull TextView forgotPassword,
      @NonNull ImageView ivLogo, @NonNull TextInputLayout layoutNewPass,
      @NonNull RelativeLayout mainContainer, @NonNull RelativeLayout rlFields,
      @NonNull RelativeLayout rlForgotSect, @NonNull TextView tvHeading, @NonNull TextView tvNotAcc,
      @NonNull TextView txtScreenOne) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.btnSignIn = btnSignIn;
    this.checkbox = checkbox;
    this.edtEmail = edtEmail;
    this.edtPassword = edtPassword;
    this.forgotPassword = forgotPassword;
    this.ivLogo = ivLogo;
    this.layoutNewPass = layoutNewPass;
    this.mainContainer = mainContainer;
    this.rlFields = rlFields;
    this.rlForgotSect = rlForgotSect;
    this.tvHeading = tvHeading;
    this.tvNotAcc = tvNotAcc;
    this.txtScreenOne = txtScreenOne;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TutorialScreenOneBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TutorialScreenOneBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tutorial_screen_one, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TutorialScreenOneBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_register;
      AppCompatButton btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.btn_signIn;
      AppCompatButton btnSignIn = ViewBindings.findChildViewById(rootView, id);
      if (btnSignIn == null) {
        break missingId;
      }

      id = R.id.checkbox;
      CheckBox checkbox = ViewBindings.findChildViewById(rootView, id);
      if (checkbox == null) {
        break missingId;
      }

      id = R.id.edt_email;
      EditText edtEmail = ViewBindings.findChildViewById(rootView, id);
      if (edtEmail == null) {
        break missingId;
      }

      id = R.id.edt_password;
      TextInputEditText edtPassword = ViewBindings.findChildViewById(rootView, id);
      if (edtPassword == null) {
        break missingId;
      }

      id = R.id.forgot_password;
      TextView forgotPassword = ViewBindings.findChildViewById(rootView, id);
      if (forgotPassword == null) {
        break missingId;
      }

      id = R.id.iv_logo;
      ImageView ivLogo = ViewBindings.findChildViewById(rootView, id);
      if (ivLogo == null) {
        break missingId;
      }

      id = R.id.layout_new_pass;
      TextInputLayout layoutNewPass = ViewBindings.findChildViewById(rootView, id);
      if (layoutNewPass == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.rl_fields;
      RelativeLayout rlFields = ViewBindings.findChildViewById(rootView, id);
      if (rlFields == null) {
        break missingId;
      }

      id = R.id.rl_forgot_sect;
      RelativeLayout rlForgotSect = ViewBindings.findChildViewById(rootView, id);
      if (rlForgotSect == null) {
        break missingId;
      }

      id = R.id.tv_heading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.tv_not_acc;
      TextView tvNotAcc = ViewBindings.findChildViewById(rootView, id);
      if (tvNotAcc == null) {
        break missingId;
      }

      id = R.id.txt_screen_one;
      TextView txtScreenOne = ViewBindings.findChildViewById(rootView, id);
      if (txtScreenOne == null) {
        break missingId;
      }

      return new TutorialScreenOneBinding((RelativeLayout) rootView, btnRegister, btnSignIn,
          checkbox, edtEmail, edtPassword, forgotPassword, ivLogo, layoutNewPass, mainContainer,
          rlFields, rlForgotSect, tvHeading, tvNotAcc, txtScreenOne);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
