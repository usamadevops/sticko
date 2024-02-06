// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public final class ErrorPopupBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageButton imageButton;

  @NonNull
  public final RelativeLayout rl1;

  @NonNull
  public final TextView txtDetails;

  @NonNull
  public final TextView txtHeader1;

  private ErrorPopupBinding(@NonNull LinearLayout rootView, @NonNull ImageButton imageButton,
      @NonNull RelativeLayout rl1, @NonNull TextView txtDetails, @NonNull TextView txtHeader1) {
    this.rootView = rootView;
    this.imageButton = imageButton;
    this.rl1 = rl1;
    this.txtDetails = txtDetails;
    this.txtHeader1 = txtHeader1;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ErrorPopupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ErrorPopupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.error_popup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ErrorPopupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.image_button;
      ImageButton imageButton = ViewBindings.findChildViewById(rootView, id);
      if (imageButton == null) {
        break missingId;
      }

      id = R.id.rl_1;
      RelativeLayout rl1 = ViewBindings.findChildViewById(rootView, id);
      if (rl1 == null) {
        break missingId;
      }

      id = R.id.txt_details;
      TextView txtDetails = ViewBindings.findChildViewById(rootView, id);
      if (txtDetails == null) {
        break missingId;
      }

      id = R.id.txt_header1;
      TextView txtHeader1 = ViewBindings.findChildViewById(rootView, id);
      if (txtHeader1 == null) {
        break missingId;
      }

      return new ErrorPopupBinding((LinearLayout) rootView, imageButton, rl1, txtDetails,
          txtHeader1);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
