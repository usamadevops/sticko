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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class FollowingLayoutBinding implements ViewBinding {
  @NonNull
  private final SwipeRefreshLayout rootView;

  @NonNull
  public final ImageView ivIconGroup;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final TextView noDataAvailable;

  @NonNull
  public final RelativeLayout noFollowers;

  @NonNull
  public final SwipeRefreshLayout refreshLayout;

  @NonNull
  public final RecyclerView rvFollowing;

  private FollowingLayoutBinding(@NonNull SwipeRefreshLayout rootView,
      @NonNull ImageView ivIconGroup, @NonNull RelativeLayout mainContainer,
      @NonNull TextView noDataAvailable, @NonNull RelativeLayout noFollowers,
      @NonNull SwipeRefreshLayout refreshLayout, @NonNull RecyclerView rvFollowing) {
    this.rootView = rootView;
    this.ivIconGroup = ivIconGroup;
    this.mainContainer = mainContainer;
    this.noDataAvailable = noDataAvailable;
    this.noFollowers = noFollowers;
    this.refreshLayout = refreshLayout;
    this.rvFollowing = rvFollowing;
  }

  @Override
  @NonNull
  public SwipeRefreshLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FollowingLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FollowingLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.following_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FollowingLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_icon_group;
      ImageView ivIconGroup = rootView.findViewById(id);
      if (ivIconGroup == null) {
        break missingId;
      }

      id = R.id.main_container;
      RelativeLayout mainContainer = rootView.findViewById(id);
      if (mainContainer == null) {
        break missingId;
      }

      id = R.id.no_data_available;
      TextView noDataAvailable = rootView.findViewById(id);
      if (noDataAvailable == null) {
        break missingId;
      }

      id = R.id.no_followers;
      RelativeLayout noFollowers = rootView.findViewById(id);
      if (noFollowers == null) {
        break missingId;
      }

      SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) rootView;

      id = R.id.rv_following;
      RecyclerView rvFollowing = rootView.findViewById(id);
      if (rvFollowing == null) {
        break missingId;
      }

      return new FollowingLayoutBinding((SwipeRefreshLayout) rootView, ivIconGroup, mainContainer,
          noDataAvailable, noFollowers, refreshLayout, rvFollowing);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
