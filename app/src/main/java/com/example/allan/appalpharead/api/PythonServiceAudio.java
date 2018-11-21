package com.example.allan.appalpharead.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PythonServiceAudio {

    @POST("audio_to_text")
    Call<Data> postAudio(
            @Header("Content-Type") String contentType,
            @Body Data data
    );

}
