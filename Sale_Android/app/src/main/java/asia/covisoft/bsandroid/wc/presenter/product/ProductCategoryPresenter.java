package asia.covisoft.bsandroid.wc.presenter.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import asia.covisoft.bsandroid.prefs.WCApi;
import asia.covisoft.bsandroid.utils.HttpUtils;
import asia.covisoft.bsandroid.wc.model.get.product.category.ProductCategory;
import asia.covisoft.bsandroid.wc.presenter.AuthorizePresenter;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by Leon on 2/21/2017.
 */

public class ProductCategoryPresenter {
    private static final String TAG = ProductCategoryPresenter.class.getSimpleName();

    private static final String API = WCApi.PRODUCT_CATEGORY;

    public static void retrieve(Context context, long id, final RetrieveCallback callback) {

        String authorizeApi = API + "/" + id;

        AuthorizePresenter.authorize(context, authorizeApi, new AuthorizePresenter.Callback() {
            @Override
            public AsyncTask onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                return new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        return HttpUtils.requestHttpGET(wcRequestURL);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            Log.d(TAG, "onPostExecute: " + result);
                            try {
                                ProductCategory productCategory = new Gson().fromJson(result, ProductCategory.class);
                                callback.onResponse(productCategory);
                                return;
                            } catch (JsonSyntaxException | IllegalStateException ex) {
                                ex.printStackTrace();
                            }
                        }
                        callback.onConnectionTimeOut();
                    }
                }.execute();
            }

            @Override
            public void onConnectionTimeOut() {
                callback.onConnectionTimeOut();
            }

        });
    }

    public interface RetrieveCallback extends WCCallback {

        void onResponse(ProductCategory productCategory);
    }

    public static void listAll(Context context, final ListAllCallback callback) {

        AuthorizePresenter.authorize(context, API, new AuthorizePresenter.Callback() {
            @Override
            public AsyncTask onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                return new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        return HttpUtils.requestHttpGET(wcRequestURL);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            Log.d(TAG, "onPostExecute: " + result);
                            result = result.replace("\"image\":[]", "\"image\":null");
                            try {
                                ProductCategory[] productCategoryArray = new Gson().fromJson(result, ProductCategory[].class);
                                List<ProductCategory> productCategories = new ArrayList<>(Arrays.asList(productCategoryArray));
                                callback.onResponse(productCategories);
                                return;
                            } catch (JsonSyntaxException | IllegalStateException ex) {
                                ex.printStackTrace();
                            }
                        }
                        callback.onConnectionTimeOut();
                    }
                }.execute();
            }

            @Override
            public void onConnectionTimeOut() {
                callback.onConnectionTimeOut();
            }
        });
    }

    public interface ListAllCallback extends WCCallback {

        void onResponse(List<ProductCategory> productCategories);
    }
}
