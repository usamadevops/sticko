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

public final class TutorialScreenSixBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView btnAdd;

  @NonNull
  public final ImageView btnShare;

  @NonNull
  public final RelativeLayout mainContainer;

  @NonNull
  public final TextView txtHeading;

  @NonNull
  public final TextView txtHeading2;

  private TutorialScreenSixBinding(@NonNull RelativeLayout rootView, @NonNull ImageView btnAdd,
      @NonNull ImageView btnShare, @NonNull RelativeLayout mainContainer,
      @NonNull TextView txtHeading, @NonNull TextView txtHeading2) {
    this.rootView = rootView;
    this.btnAdd = btnAdd;
    this.btnShare = btnShare;
    this.mainContainer = mainContainer;
    this.txtHeading = txtHeading;
    this.txtHeading2 = txtHeading2;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TutorialScreenSixBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TutorialScreenSixBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tutorial_screen_six, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TutorialScreenSixBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_add;
      ImageView btnAdd = ViewBindings.findChildViewById(rootView, id);
      if (btnAdd == null) {
        break missingId;
      }

      id = R.id.btn_share;
      ImageView btnShare = ViewBindings.findChildViewById(rootView, id);
      if (btnShare == null) {
        break missingId;
      }

      RelativeLayout mainContainer = (RelativeLayout) rootView;

      id = R.id.txt_heading;
      TextView txtHeading = ViewBindings.findChildViewById(rootView, id);
      if (txtHeading == null) {
        break missingId;
      }

      id = R.id.txt_heading_2;
      TextView txtHeading2 = ViewBindings.findChildViewById(rootView, id);
      if (txtHeading2 == null) {
        break missingId;
      }

      return new TutorialScreenSixBinding((RelativeLayout) rootView, btnAdd, btnShare,
          mainContainer, txtHeading, txtHeading2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
