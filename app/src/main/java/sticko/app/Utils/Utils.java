package sticko.app.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utils {

    public static MultipartBody.Part createMultipartBodyForImage(File file, String partName) {
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static File bitmapToFile(File file, Bitmap bitmap) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File bitmapToFile(Context context, Bitmap bitmap, String name, int imageQuality) {
        File dir = context.getFilesDir();
        File imageFile = new File(dir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("Image-Converting", "Giving Error while converting bitmap to file", e);
        }
        return imageFile;
    }

    public static class BitmapResize {

        public static Bitmap scaleToFitWidth(Bitmap bitmap, int width) {
            float factor = width / (float) bitmap.getWidth();
            return Bitmap.createScaledBitmap(bitmap, width, (int) (bitmap.getHeight() * factor), true);
        }

        public static Bitmap scaleToFitHeight(Bitmap bitmap, int height) {
            float factor = height / (float) bitmap.getHeight();
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * factor), height, true);
        }

        public static Bitmap fixResolution(Bitmap bitmap, int width, int height) {
            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        }
    }
}
