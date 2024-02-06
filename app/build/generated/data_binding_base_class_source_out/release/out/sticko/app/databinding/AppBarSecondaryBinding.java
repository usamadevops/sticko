// Generated by view binder compiler. Do not edit!
package sticko.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sticko.app.R;

public final class AppBarSecondaryBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final TextView appBarHeading;

  @NonNull
  public final ImageButton btnAdd;

  @NonNull
  public final ImageButton btnClose;

  @NonNull
  public final ImageButton btnCloseStart;

  @NonNull
  public final ImageButton btnDone;

  @NonNull
  public final ImageButton btnShare;

  @NonNull
  public final Toolbar toolbarSecondary;

  private AppBarSecondaryBinding(@NonNull CoordinatorLayout rootView, @NonNull AppBarLayout appBar,
      @NonNull TextView appBarHeading, @NonNull ImageButton btnAdd, @NonNull ImageButton btnClose,
      @NonNull ImageButton btnCloseStart, @NonNull ImageButton btnDone,
      @NonNull ImageButton btnShare, @NonNull Toolbar toolbarSecondary) {
    this.rootView = rootView;
    this.appBar = appBar;
    this.appBarHeading = appBarHeading;
    this.btnAdd = btnAdd;
    this.btnClose = btnClose;
    this.btnCloseStart = btnCloseStart;
    this.btnDone = btnDone;
    this.btnShare = btnShare;
    this.toolbarSecondary = toolbarSecondary;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AppBarSecondaryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AppBarSecondaryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.app_bar_secondary, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AppBarSecondaryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_bar;
      AppBarLayout appBar = ViewBindings.findChildViewById(rootView, id);
      if (appBar == null) {
        break missingId;
      }

      id = R.id.app_bar_heading;
      TextView appBarHeading = ViewBindings.findChildViewById(rootView, id);
      if (appBarHeading == null) {
        break missingId;
      }

      id = R.id.btn_add;
      ImageButton btnAdd = ViewBindings.findChildViewById(rootView, id);
      if (btnAdd == null) {
        break missingId;
      }

      id = R.id.btn_close;
      ImageButton btnClose = ViewBindings.findChildViewById(rootView, id);
      if (btnClose == null) {
        break missingId;
      }

      id = R.id.btn_close_start;
      ImageButton btnCloseStart = ViewBindings.findChildViewById(rootView, id);
      if (btnCloseStart == null) {
        break missingId;
      }

      id = R.id.btn_done;
      ImageButton btnDone = ViewBindings.findChildViewById(rootView, id);
      if (btnDone == null) {
        break missingId;
      }

      id = R.id.btn_share;
      ImageButton btnShare = ViewBindings.findChildViewById(rootView, id);
      if (btnShare == null) {
        break missingId;
      }

      id = R.id.toolbar_secondary;
      Toolbar toolbarSecondary = ViewBindings.findChildViewById(rootView, id);
      if (toolbarSecondary == null) {
        break missingId;
      }

      return new AppBarSecondaryBinding((CoordinatorLayout) rootView, appBar, appBarHeading, btnAdd,
          btnClose, btnCloseStart, btnDone, btnShare, toolbarSecondary);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}