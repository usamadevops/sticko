// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class FragmentDeactivatedAlertBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AppBarSecondaryBinding appBar;

  @NonNull
  public final ImageView ivLogo;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final TextView tvHeading;

  private FragmentDeactivatedAlertBinding(@NonNull RelativeLayout rootView,
      @NonNull AppBarSecondaryBinding appBar, @NonNull ImageView ivLogo,
      @NonNull RelativeLayout mainContainer, @NonNull TextView tvHeading) {
    this.rootView = rootView;
    this.appBar = appBar;
    this.ivLogo = ivLogo;
    this.mainContainer = mainContainer;
    this.tvHeading = tvHeading;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDeactivatedAlertBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDeactivatedAlertBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_deactivated_alert, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDeactivatedAlertBinding bind(@NonNull View rootView) {
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

      id = R.id.iv_logo;
      ImageView ivLogo = ViewBindings.findChildViewById(rootView, id);
      if (ivLogo == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.tv_heading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      return new FragmentDeactivatedAlertBinding((RelativeLayout) rootView, binding_appBar, ivLogo,
          mainContainer, tvHeading);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
