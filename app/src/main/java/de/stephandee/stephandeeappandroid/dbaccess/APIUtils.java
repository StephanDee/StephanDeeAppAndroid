package de.stephandee.stephandeeappandroid.dbaccess;

public class APIUtils {

    // replace localhost with your IP Address - command prompt - ipconfig - IPv4 Adresse
    public static final String API_URL = "http://localhost:3000"; // http://localhost:3000

    private APIUtils(){
    };

    public static IProductService getProductService() {
        return RetrofitClient.getClient(API_URL).create(IProductService.class);
    }
}
