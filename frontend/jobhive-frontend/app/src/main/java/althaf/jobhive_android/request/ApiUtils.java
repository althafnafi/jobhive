package althaf.jobhive_android.request;

public class ApiUtils {
    public static final String BASE_URL_API = "http://localhost:4781/";

    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
