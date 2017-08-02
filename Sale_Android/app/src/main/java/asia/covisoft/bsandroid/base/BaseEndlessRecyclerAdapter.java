package asia.covisoft.bsandroid.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by Leon on 12/30/2016.
 */

public abstract class BaseEndlessRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    private boolean isLoading;
    private int lastVisibleItem, totalItemCount;

    private int visibleThreshold = 2;

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    private boolean endlessLoadingEnable = true;

    public void setEndlessLoadingEnable(boolean endlessLoadingEnable) {
        this.endlessLoadingEnable = endlessLoadingEnable;
    }

    private int page = 1;

    public void resetPage() {
        page = 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private RecyclerView recyclerView;

    public void setEndlessLoadingListener(RecyclerView recyclerView, final OnEndlessLoadListener onEndlessLoadingListener) {
        this.recyclerView = recyclerView;
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (endlessLoadingEnable) {
                    totalItemCount = layoutManager.getItemCount();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }
                    if (layoutManager instanceof GridLayoutManager) {
                        lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onEndlessLoadingListener != null) {
                            onEndlessLoadingListener.onEndlessLoad(page);
                            page++;
                        }
                        showFootLoading();
                    }
                }
            }
        });
    }

    private void showFootLoading() {

        items.add(null);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(items.size());
            }
        });

        isLoading = true;
    }

    private void hideFootLoading() {

        if (items.size() > 0) {
            items.remove(null);
//            for (int i = items.size(); i >= 0; i--) {
//                if (items.get(i) == null) {
//                    items.remove(i);
//                }
//            }
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(items.size() - 1);
//                    notifyDataSetChanged();
                }
            });
        }

        isLoading = false;
    }

    public void insertLoadmoreItems(List<T> loadmoreItems) {

        hideFootLoading();
        insertItems(loadmoreItems);
    }


    public interface OnEndlessLoadListener {
        void onEndlessLoad(int page);
    }

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return super.onCreateViewHolder(parent, viewType);
        } else if (viewType == VIEW_TYPE_LOADING) {
            if (loadingView == null) {
                ProgressBar progressBar = new ProgressBar(context);
                progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                return new LoadingViewHolder(progressBar);
            }
            return new LoadingViewHolder(loadingView);
        }
        return null;
    }

    private View loadingView;

    public void setLoadingFooter(View loadingView) {
        loadingView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.loadingView = loadingView;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, int position, T item) {

        if (item != null) {
            onBindItemViewHolder(holder, position, item);
        }
    }

    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position, T item);
}
