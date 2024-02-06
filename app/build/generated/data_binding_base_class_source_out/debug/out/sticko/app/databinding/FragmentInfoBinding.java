// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class FragmentInfoBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton btnClose;

  @NonNull
  public final ImageView ivLogo;

  @NonNull
  public final ImageView ivLogo2;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final TextView tvProfile;

  @NonNull
  public final TextView tvTop50;

  private FragmentInfoBinding(@NonNull RelativeLayout rootView, @NonNull ImageButton btnClose,
      @NonNull ImageView ivLogo, @NonNull ImageView ivLogo2, @NonNull RelativeLayout mainContainer,
      @NonNull TextView tvProfile, @NonNull TextView tvTop50) {
    this.rootView = rootView;
    this.btnClose = btnClose;
    this.ivLogo = ivLogo;
    this.ivLogo2 = ivLogo2;
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
  public static FragmentInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_close;
      ImageButton btnClose = rootView.findViewById(id);
      if (btnClose == null) {
        break missingId;
      }

      id = R.id.iv_logo;
      ImageView ivLogo = rootView.findViewById(id);
      if (ivLogo == null) {
        break missingId;
      }

      id = R.id.iv_logo2;
      ImageView ivLogo2 = rootView.findViewById(id);
      if (ivLogo2 == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.tv_profile;
      TextView tvProfile = rootView.findViewById(id);
      if (tvProfile == null) {
        break missingId;
      }

      id = R.id.tv_top_50;
      TextView tvTop50 = rootView.findViewById(id);
      if (tvTop50 == null) {
        break missingId;
      }

      return new FragmentInfoBinding((RelativeLayout) rootView, btnClose, ivLogo, ivLogo2,
          mainContainer, tvProfile, tvTop50);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
