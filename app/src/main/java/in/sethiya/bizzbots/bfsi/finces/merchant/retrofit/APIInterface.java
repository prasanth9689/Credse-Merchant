package in.sethiya.bizzbots.bfsi.finces.merchant.retrofit;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("server/common.php")
    Call<ResponseBody> getStates(@Query("axn") String axn);

    @POST("server/common.php")
    Call<ResponseBody> getDistricts(@Query("axn") String axn,
                                    @Query("stateCode") String stateCode);

    @POST("server/common.php")
    Call<ResponseBody> saveAddressDetails(@Body RequestBody params);

    @POST("server/common.php")
    Call<ResponseBody> saveContactDetails(@Body RequestBody params);

    @POST("server/common.php")
    Call<ResponseBody> savePersonalDetails(@Body RequestBody params);

    @POST("server/common.php")
    Call<ResponseBody> saveEduOccupation(@Body RequestBody params);

    @POST("server/common.php")
    Call<ResponseBody> saveIdentity(@Body RequestBody params);

    @POST("/credse_main.php")
    Call<ResponseBody> login(@Body RequestBody params);
}
