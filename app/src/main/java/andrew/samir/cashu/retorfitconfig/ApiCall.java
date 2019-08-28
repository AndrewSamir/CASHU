package andrew.samir.cashu.retorfitconfig;

import java.util.List;

import andrew.samir.cashu.Data.ResposData.RepoData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCall {

    @GET("users/JakeWharton/repos")
    Observable<List<RepoData>> callGetReposList(@Query("page") int page, @Query("per_page") int per_page);



}

