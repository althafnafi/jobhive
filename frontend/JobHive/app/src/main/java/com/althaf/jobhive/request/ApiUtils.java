package com.althaf.jobhive.request;

public class ApiUtils {
    public static final String BASE_URL_API = "http://10.0.2.2:4881";

    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
