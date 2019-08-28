package andrew.samir.cashu.interfaces;

import android.support.v7.widget.RecyclerView;

public interface OnRequestMoreListener
{
    void requestMoreData(final RecyclerView.Adapter adapter, final int position);
}
