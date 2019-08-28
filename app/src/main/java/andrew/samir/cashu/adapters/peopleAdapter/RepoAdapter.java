
package andrew.samir.cashu.adapters.peopleAdapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import andrew.samir.cashu.Data.ResposData.RepoData;
import andrew.samir.cashu.R;
import andrew.samir.cashu.databinding.RvItemReposetoryBinding;
import andrew.samir.cashu.interfaces.OnRequestMoreListener;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.PeopleAdapterViewHolder> {

    private List<RepoData> reposList;
    private OnRequestMoreListener onRequestMoreListener;
    private boolean dataEnded = false;

    public RepoAdapter(OnRequestMoreListener onRequestMoreListener) {
        this.reposList = Collections.emptyList();
        this.onRequestMoreListener = onRequestMoreListener;
    }

    @NonNull
    @Override
    public PeopleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemReposetoryBinding itemPeopleBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_reposetory,
                        parent, false);
        return new PeopleAdapterViewHolder(itemPeopleBinding);
    }

    @Override
    public void onBindViewHolder(PeopleAdapterViewHolder holder, int position) {
        holder.bindPeople(reposList.get(position));

        if (position >= getItemCount() - 4 && onRequestMoreListener != null && !dataEnded) {
            onRequestMoreListener.requestMoreData(this, position);
        }
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }

    public void setPeopleList(List<RepoData> reposList) {
        this.reposList = reposList;
        notifyDataSetChanged();
    }

    public void setDataEnded(boolean dataEnded) {
        this.dataEnded = dataEnded;
    }
    static class PeopleAdapterViewHolder extends RecyclerView.ViewHolder {
        RvItemReposetoryBinding mRvItemRepoBinding;

        PeopleAdapterViewHolder(RvItemReposetoryBinding itemPeopleBinding) {
            super(itemPeopleBinding.itemPeople);
            this.mRvItemRepoBinding = itemPeopleBinding;
        }

        void bindPeople(RepoData people) {
            if (mRvItemRepoBinding.getItemPeopleViewModel() == null) {
                mRvItemRepoBinding.setItemPeopleViewModel(
                        new ItemRepoViewModel(people, itemView.getContext()));
            } else {
                mRvItemRepoBinding.getItemPeopleViewModel().setPersonDetailsData(people);
            }
        }
    }


}
