package in.eweblabs.careeradvance.Network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

;

/**
 * Created by Anwar on 5/21/2016.
 */
public class RetrofitInstance {


    private static RetrofitInterface mService;


    public static synchronized RetrofitInterface getInstance() {
        if (mService == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseNetwork.URL_HOST)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mService = retrofit.create(RetrofitInterface.class);
        }
        return mService;
    }

    public static RetrofitInterface getInstance(String URL) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        return service;
    }
}

