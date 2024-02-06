package sticko.app;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Window;

public class Loader {
    private Dialog fbDialogue;
    private Context mContext;

    public Loader(Context context) {
        mContext = context;
        fbDialogue = new Dialog(mContext, R.style.theme_loader);

        fbDialogue.setContentView(R.layout.loader);
        Window window = fbDialogue.getWindow();

        //window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        window.setBackgroundDrawableResource(R.color.dialog_back);
    }

    public void showLoader() {
//        fbDialogue = new Dialog(mContext);
////        fbDialogue.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
////        fbDialogue.getWindow().setDimAmount(0);
//        fbDialogue.setContentView(R.layout.loader);
//        fbDialogue.setCancelable(false);
//        fbDialogue.show();
        Log.i("mContext", String.valueOf(mContext));


//        if(fbDialogue.isShowing())
//            fbDialogue.dismiss();
        fbDialogue.show();

        new CountDownTimer(80000, 3000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Log.i("mContext_hide", String.valueOf(mContext));
                fbDialogue.dismiss();

            }
        }.start();
    }

    public void hideLoader() {
        Log.i("mContext_hide", String.valueOf(mContext));
//        if (fbDialogue.isShowing())
        try {
            fbDialogue.dismiss();
        } catch (IllegalArgumentException ex) {

        }
    }

    public boolean isShowing() {
        return fbDialogue.isShowing();
    }
}
