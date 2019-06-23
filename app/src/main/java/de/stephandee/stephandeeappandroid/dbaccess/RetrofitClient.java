package de.stephandee.stephandeeappandroid.dbaccess;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The RetrofitClient.
 *
 * To access the remote webserver.
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    /**
     * Gets the Client.
     *
     * @param url the API_URL
     * @return the client
     */
    public static Retrofit getClient(String url) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
