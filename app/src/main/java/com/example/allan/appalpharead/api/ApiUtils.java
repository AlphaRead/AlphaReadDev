package com.example.allan.appalpharead.api;

public class ApiUtils {
    private ApiUtils(){}

    public static final String BASE_URL = "http://172.27.1.166:5000/";

    public static PythonService getCreateUser(){
        return ApiClient.getClient(BASE_URL).create(PythonService.class);
    }

}
