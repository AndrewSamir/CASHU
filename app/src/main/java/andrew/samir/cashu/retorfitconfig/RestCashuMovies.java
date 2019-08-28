package andrew.samir.cashu.retorfitconfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestCashuMovies {

    public static ApiCall create() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
               /* .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.SECONDS);*/


        //comment start
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        //comment end
        //  httpClient.addInterceptor(interceptor).build();


        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Request newRequest;
                newRequest = request.newBuilder()

                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(newRequest);
            }
        });

        OkHttpClient httpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();

        return retrofit.create(ApiCall.class);
    }

}