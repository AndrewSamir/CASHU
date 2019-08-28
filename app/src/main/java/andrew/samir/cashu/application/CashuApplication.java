
package andrew.samir.cashu.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import andrew.samir.cashu.retorfitconfig.ApiCall;
import andrew.samir.cashu.retorfitconfig.RestCashuMovies;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class CashuApplication extends MultiDexApplication {

    private ApiCall apiCallInterface;
    private Scheduler scheduler;

    private static CashuApplication get(Context context) {
        return (CashuApplication) context.getApplicationContext();
    }

    public static CashuApplication create(Context context) {
        return CashuApplication.get(context);
    }

    public ApiCall getApiCall() {
        if (apiCallInterface == null) {
            apiCallInterface = RestCashuMovies.create();
        }

        return apiCallInterface;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setApiCall(ApiCall apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
