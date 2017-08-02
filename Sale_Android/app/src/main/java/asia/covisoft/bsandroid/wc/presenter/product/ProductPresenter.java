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
import asia.covisoft.bsandroid.wc.WCParams;
import asia.covisoft.bsandroid.wc.model.get.product.Product;
import asia.covisoft.bsandroid.wc.presenter.AuthorizePresenter;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by Leon on 2/21/2017.
 */

public class ProductPresenter {
    private static final String TAG = ProductPresenter.class.getSimpleName();

    private static final String API = WCApi.PRODUCT;

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
                                Product product = new Gson().fromJson(result, Product.class);
                                callback.onResponse(product);
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

        void onResponse(Product product);
    }

    public static AsyncTask listAll(Context context, final String params, final ListAllCallback callback) {

        return AuthorizePresenter.authorize(context, API, new AuthorizePresenter.Callback() {
            @Override
            public AsyncTask onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                return new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        return HttpUtils.requestHttpGET(wcRequestURL + params);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            Log.d(TAG, "onPostExecute: " + result);
                            try {
                                Product[] productArray = new Gson().fromJson(result, Product[].class);
                                if (productArray != null) {
                                    List<Product> products = new ArrayList<>(Arrays.asList(productArray));
                                    callback.onResponse(products);
                                }
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

    public static AsyncTask listAll(Context context, final ListAllCallback callback) {

        return listAll(context, 1, callback);
    }

    public static AsyncTask listAll(Context context, int page, final ListAllCallback callback) {

        return listAll(context, page, 0, callback);
    }

    public static AsyncTask listAll(Context context, int page, long categoryId, final ListAllCallback callback) {

        String params = "&" + WCParams.PAGE + "=" + page;
        if (categoryId != 0) {
            params += "&" + WCParams.CATEGORY + "=" + categoryId;
        }
        return listAll(context, params, callback);
    }

    public static AsyncTask search(Context context, int page, String searchKeyword, final ListAllCallback callback) {

        String params = "&" + WCParams.PAGE + "=" + page;
        params += "&" + WCParams.SEARCH + "=" + searchKeyword;
        return listAll(context, params, callback);
    }

    public interface ListAllCallback extends WCCallback {

        void onResponse(List<Product> products);
    }
}
