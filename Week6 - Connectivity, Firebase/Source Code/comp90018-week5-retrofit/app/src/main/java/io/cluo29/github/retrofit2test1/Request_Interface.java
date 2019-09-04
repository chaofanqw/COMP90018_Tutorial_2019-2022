package io.cluo29.github.retrofit2test1;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by CLUO29 on 13/08/18.
 */

public interface Request_Interface {

    // put the request string inside annotation
    @GET("ajax.php")
    Call<MainActivity.Translation> getCall(@QueryMap Map<String, String> params);

}
