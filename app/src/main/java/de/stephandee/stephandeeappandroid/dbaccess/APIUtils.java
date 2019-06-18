package de.stephandee.stephandeeappandroid.dbaccess;

public class APIUtils {

    public  static final String API_URL = "localhost:3000";

    private APIUtils(){
    };

    public static IProductService getProductService() {
        return RetrofitClient.getClient(API_URL).create(IProductService.class);
    }
}
