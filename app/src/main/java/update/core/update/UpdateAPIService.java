package update.core.update;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by subhashhardaha on 03/12/17.
 */

public interface UpdateAPIService {

    @POST("/post")
    Call<MessageResponse> postMessages(@Body MessageResponse post);
}
