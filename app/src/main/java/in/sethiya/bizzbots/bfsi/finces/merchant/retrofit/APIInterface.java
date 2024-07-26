package in.sethiya.bizzbots.bfsi.finces.merchant.retrofit;

import java.util.List;

import in.sethiya.bizzbots.bfsi.finces.merchant.model.Login;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("server/Procurement.php")
    Call<ResponseBody> getStates(@Query("axn") String axn);

    @POST("Procurement.php")
    Call<ResponseBody> getDistricts(@Query("axn") String axn,
                                    @Query("stateCode") String stateCode);
}
