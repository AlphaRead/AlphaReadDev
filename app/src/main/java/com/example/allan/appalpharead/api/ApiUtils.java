package com.example.allan.appalpharead.api;

public class ApiUtils {
    private ApiUtils(){}

    public static final String BASE_URL = "http://192.168.100.4:5000/";

    public static PythonService getCreateUser(){
        return ApiClient.getClient(BASE_URL).create(PythonService.class);
    }

}
