package andrew.samir.cashu.activities.repos;

import android.content.Context;
import android.databinding.ObservableField;

import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import andrew.samir.cashu.Data.ResposData.RepoData;
import andrew.samir.cashu.R;
import andrew.samir.cashu.activities.BaseViewModel;
import andrew.samir.cashu.application.CashuApplication;
import andrew.samir.cashu.retorfitconfig.ApiCall;
import andrew.samir.cashu.utlities.HelpMe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ReposViewModel extends BaseViewModel {

    //region fields
    public ObservableField<String> queryString = new ObservableField<>();
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<RepoData> reposList;
    private int page = 1;

    //endregion

    //region constructor
    ReposViewModel(@NonNull Context context) {
        this.context = context;
        reposList = new ArrayList<>();
        fetchCategories();
    }
    //endregion


    //region calls

    private void fetchCategories() {
        HelpMe.showLoading(true, context);
        CashuApplication peopleApplication = CashuApplication.create(context);
        ApiCall peopleService = peopleApplication.getApiCall();

        Disposable disposable = peopleService.callGetReposList(page, 15)
                .subscribeOn(peopleApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RepoData>>() {
                    @Override
                    public void accept(List<RepoData> repos) {
                        HelpMe.showLoading(false, context);
                        page++;
                        if (repos.size() > 0) {
                            TinyDB tinydb = new TinyDB(context);
                            ArrayList<Object> playerObjects = new ArrayList<Object>();

                            for (RepoData a : repos) {
                                playerObjects.add((Object) a);
                            }

                            tinydb.putListObject("offlineList", playerObjects);
                            changePeopleDataSet(repos);
                        } else
                            changePeopleDataSet(true);
//                        Log.d("calls", peopleResponse.getResults().get(0).getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        HelpMe.showLoading(false, context);
//                        showMessage(R.string.connection_error, context);

                        TinyDB tinydb = new TinyDB(context);
                        ArrayList<Object> playerObjects = tinydb.getListObject("offlineList", RepoData.class);
                        ArrayList<RepoData> players = new ArrayList<RepoData>();

                        for (Object objs : playerObjects) {
                            players.add((RepoData) objs);
                        }

                        changePeopleDataSet(players);
                        changePeopleDataSet(true);
                    }
                });

        compositeDisposable.add(disposable);
    }


    void fetchCategoriesMore() {
        HelpMe.showLoading(true, context);
        changePeopleDataSet(true);

        CashuApplication peopleApplication = CashuApplication.create(context);
        ApiCall peopleService = peopleApplication.getApiCall();

        Disposable disposable = peopleService.callGetReposList(page, 15)
                .subscribeOn(peopleApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RepoData>>() {
                    @Override
                    public void accept(List<RepoData> repoDataList) {
                        HelpMe.showLoading(false, context);
                        page++;
                        if (repoDataList.size() > 0) {
                            changePeopleDataSet(repoDataList);
                            changePeopleDataSet(false);

                        } else
                            changePeopleDataSet(true);
//                        Log.d("calls", peopleResponse.getResults().get(0).getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        HelpMe.showLoading(false, context);
                        showMessage(R.string.connection_error, context);
                    }
                });

        compositeDisposable.add(disposable);
    }

    //endregion

    //region functions
    private void changePeopleDataSet(List<RepoData> repoDataList) {
        reposList.addAll(repoDataList);
        setChanged();
        notifyObservers("call");
    }

    private void changePeopleDataSet(boolean pause) {
        setChanged();
        if (pause)
            notifyObservers("dataEnded");
        else
            notifyObservers("cont");
    }

    private void changePeopleDataSetSearch(List<RepoData> repoDataList) {
        reposList.clear();
        reposList.addAll(repoDataList);
        setChanged();
        notifyObservers("call");
    }

    List<RepoData> getPeopleList() {
        return reposList;
    }


    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
    //endregion
}
