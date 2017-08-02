package asia.covisoft.bsandroid.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.activity.NewsDetailsActivity;
import asia.covisoft.bsandroid.adapter.NewsRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseLoadingFragment;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.wa.model.News;
import asia.covisoft.bsandroid.wa.presenter.NewsPresenter;
import asia.covisoft.bsandroid.wc.WCDefaultValue;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseLoadingFragment {

    @Override
    public int getRootView() {
        return R.layout.fragment_news;
    }

    private RecyclerView rvNews;

    @Override
    public void initView() {

        rvNews = (RecyclerView) rootView.findViewById(R.id.rvNews);
    }

    private NewsRecyclerAdapter newsAdapter;

    @Override
    public void initValue() {

        rvNews.setLayoutManager(new LinearLayoutManager(context));

        newsAdapter = new NewsRecyclerAdapter(context);
        newsAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long productId) {

                News news = newsAdapter.getItem(position);
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra(NewsDetailsActivity.NEWS_ID, news.getID() + "");
                intent.putExtra(NewsDetailsActivity.IMAGE, news.getImage());
                intent.putExtra(NewsDetailsActivity.TITLE, news.getPostTitle());
                intent.putExtra(NewsDetailsActivity.DATE, news.getPostDate());
                intent.putExtra(NewsDetailsActivity.CONTENT, news.getPostContent());
                startActivity(intent);
            }
        });
        newsAdapter.setEndlessLoadingListener(rvNews, new BaseEndlessRecyclerAdapter.OnEndlessLoadListener() {
            @Override
            public void onEndlessLoad(int page) {

                loadNews(page + 1);
            }
        });

        rvNews.setAdapter(newsAdapter);
    }

    @Override
    public void initAction() {

        showLoading();
        loadNews(1);
    }

    private NewsPresenter.ListAllCallback callback = new NewsPresenter.ListAllCallback() {
        @Override
        public void onResponse(List<News> newses) {
            dismissLoading();

            if (newses.size() < WCDefaultValue.MAX_ITEM_PER_PAGE) {
                newsAdapter.setEndlessLoadingEnable(false);
            }

            newsAdapter.insertLoadmoreItems(newses);
        }

        @Override
        public void onConnectionTimeOut() {

            dismissLoading();
            ErrorDialog.showConnectionTimeOutDialog(context);
        }
    };

    private void loadNews(int page) {

        NewsPresenter.listAll(context, page, callback);
    }
}
