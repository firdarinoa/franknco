package co.id.franknco.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static co.id.franknco.api.ServiceConfig.BASE_URL;

/**
 * Created by ILM on 6/29/2016.
 */
public class RestApi {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
