// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class FragmentActivationBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AppBarSecondaryBinding appBar;

  @NonNull
  public final RelativeLayout background;

  @NonNull
  public final AppCompatButton btnActivateSticko;

  @NonNull
  public final AppCompatButton btnRegister;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final TextView tvProfile;

  @NonNull
  public final TextView tvTop50;

  private FragmentActivationBinding(@NonNull RelativeLayout rootView,
      @NonNull AppBarSecondaryBinding appBar, @NonNull RelativeLayout background,
      @NonNull AppCompatButton btnActivateSticko, @NonNull AppCompatButton btnRegister,
      @NonNull RelativeLayout mainContainer, @NonNull TextView tvProfile,
      @NonNull TextView tvTop50) {
    this.rootView = rootView;
    this.appBar = appBar;
    this.background = background;
    this.btnActivateSticko = btnActivateSticko;
    this.btnRegister = btnRegister;
    this.mainContainer = mainContainer;
    this.tvProfile = tvProfile;
    this.tvTop50 = tvTop50;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentActivationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentActivationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_activation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentActivationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_bar;
      View appBar = ViewBindings.findChildViewById(rootView, id);
      if (appBar == null) {
        break missingId;
      }
      AppBarSecondaryBinding binding_appBar = AppBarSecondaryBinding.bind(appBar);

      id = R.id.background;
      RelativeLayout background = ViewBindings.findChildViewById(rootView, id);
      if (background == null) {
        break missingId;
      }

      id = R.id.btn_activate_sticko;
      AppCompatButton btnActivateSticko = ViewBindings.findChildViewById(rootView, id);
      if (btnActivateSticko == null) {
        break missingId;
      }

      id = R.id.btn_register;
      AppCompatButton btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.tv_profile;
      TextView tvProfile = ViewBindings.findChildViewById(rootView, id);
      if (tvProfile == null) {
        break missingId;
      }

      id = R.id.tv_top_50;
      TextView tvTop50 = ViewBindings.findChildViewById(rootView, id);
      if (tvTop50 == null) {
        break missingId;
      }

      return new FragmentActivationBinding((RelativeLayout) rootView, binding_appBar, background,
          btnActivateSticko, btnRegister, mainContainer, tvProfile, tvTop50);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
