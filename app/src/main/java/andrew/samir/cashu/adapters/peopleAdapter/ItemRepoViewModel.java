package andrew.samir.cashu.adapters.peopleAdapter;

import android.content.Context;
import android.databinding.BaseObservable;


import andrew.samir.cashu.Data.ResposData.RepoData;


public class ItemRepoViewModel extends BaseObservable {

    private RepoData repoDetailsData;
    private Context context;

    public ItemRepoViewModel(RepoData repo, Context context) {
        this.repoDetailsData = repo;
        this.context = context;
    }


    public String getPersonalDetailsName() {
        return repoDetailsData.getName();
    }




    void setPersonDetailsData(RepoData repo) {
        this.repoDetailsData = repo;
        notifyChange();
    }


}
