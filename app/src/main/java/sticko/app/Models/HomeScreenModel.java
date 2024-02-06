package sticko.app.Models;

import android.graphics.drawable.Drawable;

public class HomeScreenModel {
    public Drawable integer;

    public HomeScreenModel(Drawable integer) {
        this.integer = integer;
    }

    public Drawable getInteger() {
        return integer;
    }

    public void setInteger(Drawable integer) {
        this.integer = integer;
    }
}
