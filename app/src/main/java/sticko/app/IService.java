package sticko.app;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import sticko.app.Models.Message;


public interface IService {

    @POST("/api/v1/updateImage")
    @Multipart
    Call<Message<String>> AddImage(@Header("Authorization") String header, @Part MultipartBody.Part imageFile);

//
}
