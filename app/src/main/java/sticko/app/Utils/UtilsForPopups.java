package sticko.app.Utils;

import android.app.Activity;
import android.view.ViewGroup;

import com.irozon.sneaker.Sneaker;

import sticko.app.LoginScreen.LoginActivity;
import sticko.app.R;

public class UtilsForPopups {
    public static void alertPopup(Activity activity, String heading, String msg) {
        Sneaker.with(activity).setTitle(heading, R.color.white)
                .setMessage(msg, R.color.white)
                .setHeight(100)
                .setIcon(R.drawable.alert_icon, R.color.white, false) // Icon, icon tint color and circular icon view
                .setCornerRadius(5)
                .sneak(R.color.alert_bg);
    }
    public static void SuccessPopup(Activity activity, String heading, String msg) {
        Sneaker.with(activity).setTitle(heading, R.color.white)
                .setMessage(msg, R.color.white)
                .setHeight(100)
                .setCornerRadius(5).sneak(R.color.primary_color);
    }
    public static void processingError(Activity activity) {
        Sneaker.with(activity).setTitle("Processing Error", R.color.white)
                .setMessage("An error occurred while processing your request. Please try again later. If the problem persists, contact support.", R.color.white)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setCornerRadius(5).sneak(R.color.alert_bg);
    }
}
