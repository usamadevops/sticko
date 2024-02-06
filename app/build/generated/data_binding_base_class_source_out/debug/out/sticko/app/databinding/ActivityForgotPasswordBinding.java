// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class ActivityForgotPasswordBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton btnBack;

  @NonNull
  public final AppCompatButton btnSendCode;

  @NonNull
  public final EditText edtEmail;

  @NonNull
  public final ImageView ivLogo;

  @NonNull
  public final TextView tvDescription;

  @NonNull
  public final TextView tvHeading;

  private ActivityForgotPasswordBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageButton btnBack, @NonNull AppCompatButton btnSendCode,
      @NonNull EditText edtEmail, @NonNull ImageView ivLogo, @NonNull TextView tvDescription,
      @NonNull TextView tvHeading) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnSendCode = btnSendCode;
    this.edtEmail = edtEmail;
    this.ivLogo = ivLogo;
    this.tvDescription = tvDescription;
    this.tvHeading = tvHeading;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_forgot_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityForgotPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_back;
      ImageButton btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btn_send_code;
      AppCompatButton btnSendCode = ViewBindings.findChildViewById(rootView, id);
      if (btnSendCode == null) {
        break missingId;
      }

      id = R.id.edt_email;
      EditText edtEmail = ViewBindings.findChildViewById(rootView, id);
      if (edtEmail == null) {
        break missingId;
      }

      id = R.id.iv_logo;
      ImageView ivLogo = ViewBindings.findChildViewById(rootView, id);
      if (ivLogo == null) {
        break missingId;
      }

      id = R.id.tv_description;
      TextView tvDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvDescription == null) {
        break missingId;
      }

      id = R.id.tv_heading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      return new ActivityForgotPasswordBinding((RelativeLayout) rootView, btnBack, btnSendCode,
          edtEmail, ivLogo, tvDescription, tvHeading);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
