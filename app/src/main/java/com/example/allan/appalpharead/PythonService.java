package com.example.allan.appalpharead;

import com.example.allan.appalpharead.models.Data;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PythonService {

    @POST("/")
    Call<Data> postImage(@Body RequestBody requestBody);

}
