// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class ActivityTutorialsScreensBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView btnPasser;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final ViewPager viewPager;

  @NonNull
  public final LinearLayout viewPagerCountDots;

  @NonNull
  public final RelativeLayout viewPagerIndicator;

  private ActivityTutorialsScreensBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView btnPasser, @NonNull RelativeLayout mainContainer,
      @NonNull ViewPager viewPager, @NonNull LinearLayout viewPagerCountDots,
      @NonNull RelativeLayout viewPagerIndicator) {
    this.rootView = rootView;
    this.btnPasser = btnPasser;
    this.mainContainer = mainContainer;
    this.viewPager = viewPager;
    this.viewPagerCountDots = viewPagerCountDots;
    this.viewPagerIndicator = viewPagerIndicator;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTutorialsScreensBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTutorialsScreensBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_tutorials_screens, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTutorialsScreensBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_passer;
      TextView btnPasser = ViewBindings.findChildViewById(rootView, id);
      if (btnPasser == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.view_pager;
      ViewPager viewPager = ViewBindings.findChildViewById(rootView, id);
      if (viewPager == null) {
        break missingId;
      }

      id = R.id.viewPagerCountDots;
      LinearLayout viewPagerCountDots = ViewBindings.findChildViewById(rootView, id);
      if (viewPagerCountDots == null) {
        break missingId;
      }

      id = R.id.viewPagerIndicator;
      RelativeLayout viewPagerIndicator = ViewBindings.findChildViewById(rootView, id);
      if (viewPagerIndicator == null) {
        break missingId;
      }

      return new ActivityTutorialsScreensBinding((RelativeLayout) rootView, btnPasser,
          mainContainer, viewPager, viewPagerCountDots, viewPagerIndicator);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}