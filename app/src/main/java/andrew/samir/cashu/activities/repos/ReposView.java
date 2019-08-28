package andrew.samir.cashu.activities.repos;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Observable;

import andrew.samir.cashu.R;
import andrew.samir.cashu.adapters.peopleAdapter.RepoAdapter;
import andrew.samir.cashu.databinding.ReposetriesListBinding;
import andrew.samir.cashu.interfaces.OnRequestMoreListener;
import andrew.samir.cashu.utlities.HelpMe;

public class ReposView extends AppCompatActivity implements java.util.Observer, OnRequestMoreListener {
    private ReposViewModel peopleViewModel;
    ReposetriesListBinding peopleListBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupListPeopleView(peopleListBinding.listPeople);
        setupObserver(peopleViewModel);
        HelpMe.getInstance(this).hideKeyBoard(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        peopleViewModel.reset();
    }

    private void initDataBinding() {
        peopleListBinding = DataBindingUtil.setContentView(this, R.layout.reposetries_list);
        peopleViewModel = new ReposViewModel(this);
        peopleListBinding.setReposViewModel(peopleViewModel);
    }

    private void setupListPeopleView(RecyclerView listPeople) {
        RepoAdapter adapter = new RepoAdapter(this);
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(this));
    }


    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        String flag = (String) arg;

        switch (flag) {
            case "call":
                if (o instanceof ReposViewModel) {
                    RepoAdapter peopleAdapter = (RepoAdapter) peopleListBinding.listPeople.getAdapter();
                    ReposViewModel peopleViewModel = (ReposViewModel) o;
                    peopleAdapter.setPeopleList(peopleViewModel.getPeopleList());
                }
                break;

            case "dataEnded":
                if (o instanceof ReposViewModel) {
                    RepoAdapter peopleAdapter = (RepoAdapter) peopleListBinding.listPeople.getAdapter();
                    ReposViewModel peopleViewModel = (ReposViewModel) o;
                    peopleAdapter.setDataEnded(true);
                }
                break;
            case "cont":
                if (o instanceof ReposViewModel) {
                    RepoAdapter peopleAdapter = (RepoAdapter) peopleListBinding.listPeople.getAdapter();
                    ReposViewModel peopleViewModel = (ReposViewModel) o;
                    peopleAdapter.setDataEnded(false);
                }
                break;
        }
    }

    @Override
    public void requestMoreData(RecyclerView.Adapter adapter, int position) {
        peopleViewModel.fetchCategoriesMore();
    }
}
