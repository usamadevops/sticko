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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class TutorialScreenEightBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AppBarMainBinding appBar;

  @NonNull
  public final AppCompatButton btnFollowing;

  @NonNull
  public final CircleImageView ivProfile;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final RelativeLayout rlFields;

  @NonNull
  public final RelativeLayout rlProfile;

  @NonNull
  public final RecyclerView rvHomeScreen;

  @NonNull
  public final TextView tvBio;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView txtHeading;

  private TutorialScreenEightBinding(@NonNull RelativeLayout rootView,
      @NonNull AppBarMainBinding appBar, @NonNull AppCompatButton btnFollowing,
      @NonNull CircleImageView ivProfile, @NonNull RelativeLayout mainContainer,
      @NonNull RelativeLayout rlFields, @NonNull RelativeLayout rlProfile,
      @NonNull RecyclerView rvHomeScreen, @NonNull TextView tvBio, @NonNull TextView tvName,
      @NonNull TextView txtHeading) {
    this.rootView = rootView;
    this.appBar = appBar;
    this.btnFollowing = btnFollowing;
    this.ivProfile = ivProfile;
    this.mainContainer = mainContainer;
    this.rlFields = rlFields;
    this.rlProfile = rlProfile;
    this.rvHomeScreen = rvHomeScreen;
    this.tvBio = tvBio;
    this.tvName = tvName;
    this.txtHeading = txtHeading;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TutorialScreenEightBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TutorialScreenEightBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tutorial_screen_eight, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TutorialScreenEightBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_bar;
      View appBar = ViewBindings.findChildViewById(rootView, id);
      if (appBar == null) {
        break missingId;
      }
      AppBarMainBinding binding_appBar = AppBarMainBinding.bind(appBar);

      id = R.id.btn_following;
      AppCompatButton btnFollowing = ViewBindings.findChildViewById(rootView, id);
      if (btnFollowing == null) {
        break missingId;
      }

      id = R.id.iv_profile;
      CircleImageView ivProfile = ViewBindings.findChildViewById(rootView, id);
      if (ivProfile == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.rl_fields;
      RelativeLayout rlFields = ViewBindings.findChildViewById(rootView, id);
      if (rlFields == null) {
        break missingId;
      }

      id = R.id.rl_profile;
      RelativeLayout rlProfile = ViewBindings.findChildViewById(rootView, id);
      if (rlProfile == null) {
        break missingId;
      }

      id = R.id.rv_homeScreen;
      RecyclerView rvHomeScreen = ViewBindings.findChildViewById(rootView, id);
      if (rvHomeScreen == null) {
        break missingId;
      }

      id = R.id.tv_bio;
      TextView tvBio = ViewBindings.findChildViewById(rootView, id);
      if (tvBio == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.txt_heading;
      TextView txtHeading = ViewBindings.findChildViewById(rootView, id);
      if (txtHeading == null) {
        break missingId;
      }

      return new TutorialScreenEightBinding((RelativeLayout) rootView, binding_appBar, btnFollowing,
          ivProfile, mainContainer, rlFields, rlProfile, rvHomeScreen, tvBio, tvName, txtHeading);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
