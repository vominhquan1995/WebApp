package asia.covisoft.bsandroid.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import asia.covisoft.bsandroid.wc.model.get.product.Product;
import asia.covisoft.bsandroid.wc.presenter.product.ProductPresenter;

/**
 * Created by Leon on 4/19/2017.
 */

public class ProductSearchPresenter {

    private ProductSearchView view;
    private Context context;

    public ProductSearchPresenter(ProductSearchView view) {
        this.view = view;
        this.context = (Context) view;
    }

    private Timer searchTimer;
    private AsyncTask requestTask;

    public void search(final String keyWord) {

        if (searchTimer != null) {
            searchTimer.cancel();
        }
        if (requestTask != null) {
            requestTask.cancel(true);
        }
        searchTimer = new Timer();
        searchTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        view.onPreSearch();
                    }
                });
                getProduct(keyWord, 1);
            }

        }, 500);
    }

    public void getProduct(String keyword, int page) {

        requestTask = ProductPresenter.search(context, page, keyword, new ProductPresenter.ListAllCallback() {
            @Override
            public void onResponse(List<Product> products) {

                view.onProductResult(products);
            }

            @Override
            public void onConnectionTimeOut() {

                view.onConnectionTimeOut();
            }
        });
    }
}
