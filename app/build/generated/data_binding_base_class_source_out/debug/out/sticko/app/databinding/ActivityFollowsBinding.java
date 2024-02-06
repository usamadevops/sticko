// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class ActivityFollowsBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final TabLayout tabs;

  @NonNull
  public final ViewPager viewPager;

  private ActivityFollowsBinding(@NonNull CoordinatorLayout rootView, @NonNull TabLayout tabs,
      @NonNull ViewPager viewPager) {
    this.rootView = rootView;
    this.tabs = tabs;
    this.viewPager = viewPager;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFollowsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFollowsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_follows, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFollowsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tabs;
      TabLayout tabs = rootView.findViewById(id);
      if (tabs == null) {
        break missingId;
      }

      id = R.id.view_pager;
      ViewPager viewPager = rootView.findViewById(id);
      if (viewPager == null) {
        break missingId;
      }

      return new ActivityFollowsBinding((CoordinatorLayout) rootView, tabs, viewPager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}