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
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class FollowersTapLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AppCompatButton btnRemove;

  @NonNull
  public final RelativeLayout rlItem;

  @NonNull
  public final TextView tvFollow;

  @NonNull
  public final TextView tvUserName;

  @NonNull
  public final CircleImageView userDp;

  private FollowersTapLayoutBinding(@NonNull RelativeLayout rootView,
      @NonNull AppCompatButton btnRemove, @NonNull RelativeLayout rlItem,
      @NonNull TextView tvFollow, @NonNull TextView tvUserName, @NonNull CircleImageView userDp) {
    this.rootView = rootView;
    this.btnRemove = btnRemove;
    this.rlItem = rlItem;
    this.tvFollow = tvFollow;
    this.tvUserName = tvUserName;
    this.userDp = userDp;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FollowersTapLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FollowersTapLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.followers_tap_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FollowersTapLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_remove;
      AppCompatButton btnRemove = rootView.findViewById(id);
      if (btnRemove == null) {
        break missingId;
      }

      id = R.id.rl_item;
      RelativeLayout rlItem = rootView.findViewById(id);
      if (rlItem == null) {
        break missingId;
      }

      id = R.id.tv_follow;
      TextView tvFollow = rootView.findViewById(id);
      if (tvFollow == null) {
        break missingId;
      }

      id = R.id.tv_user_name;
      TextView tvUserName = rootView.findViewById(id);
      if (tvUserName == null) {
        break missingId;
      }

      id = R.id.user_dp;
      CircleImageView userDp = rootView.findViewById(id);
      if (userDp == null) {
        break missingId;
      }

      return new FollowersTapLayoutBinding((RelativeLayout) rootView, btnRemove, rlItem, tvFollow,
          tvUserName, userDp);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
