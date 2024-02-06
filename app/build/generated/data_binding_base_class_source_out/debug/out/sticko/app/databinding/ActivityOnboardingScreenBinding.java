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
import androidx.viewpager2.widget.ViewPager2;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class ActivityOnboardingScreenBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout layoutOnboardingIndicators;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final ViewPager2 onboardingViewpager;

  @NonNull
  public final TextView tvPasser;

  private ActivityOnboardingScreenBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout layoutOnboardingIndicators, @NonNull RelativeLayout mainContainer,
      @NonNull ViewPager2 onboardingViewpager, @NonNull TextView tvPasser) {
    this.rootView = rootView;
    this.layoutOnboardingIndicators = layoutOnboardingIndicators;
    this.mainContainer = mainContainer;
    this.onboardingViewpager = onboardingViewpager;
    this.tvPasser = tvPasser;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOnboardingScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOnboardingScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_onboarding_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOnboardingScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.layout_onboardingIndicators;
      LinearLayout layoutOnboardingIndicators = rootView.findViewById(id);
      if (layoutOnboardingIndicators == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.onboarding_viewpager;
      ViewPager2 onboardingViewpager = rootView.findViewById(id);
      if (onboardingViewpager == null) {
        break missingId;
      }

      id = R.id.tv_passer;
      TextView tvPasser = rootView.findViewById(id);
      if (tvPasser == null) {
        break missingId;
      }

      return new ActivityOnboardingScreenBinding((RelativeLayout) rootView,
          layoutOnboardingIndicators, mainContainer, onboardingViewpager, tvPasser);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}