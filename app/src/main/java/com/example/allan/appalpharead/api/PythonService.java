package com.example.allan.appalpharead.api;

import com.example.allan.appalpharead.api.Data;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface PythonService {

    @POST("image")
    Call<Data> postImage(
            @Header ("Content-Type") String contentType,
            @Body Data data
    );

}
