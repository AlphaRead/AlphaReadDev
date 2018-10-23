package com.example.allan.appalpharead;

import com.example.allan.appalpharead.models.DicionarioOnline;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DicionarioService {

    String BASE_URL= "http://dicionario-aberto.net/";

    @GET
    Call<DicionarioOnline> searchWord(@Url String anEmptyString);
}
