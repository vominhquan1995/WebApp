package asia.covisoft.miali.mvp.presenter.product;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import asia.covisoft.miali.mvp.model.product.Product;
import asia.covisoft.miali.mvp.presenter.AuthorizePresenter;
import asia.covisoft.miali.mvp.view.WCCallback;
import asia.covisoft.miali.pref.Host;
import asia.covisoft.miali.pref.WCApi;
import asia.covisoft.miali.utils.HttpUtils;

/**
 * Created by Leon on 2/21/2017.
 */

public class ProductPresenter {
    private static final String TAG = ProductPresenter.class.getSimpleName();

    private static final String API = Host.DEFAULT_HOST + WCApi.PRODUCT;

    public static void retrieve(long id, final RetrieveCallback callback) {

        String authorizeApi = API + "/" + id;

        AuthorizePresenter.authorize(authorizeApi, new AuthorizePresenter.Callback() {
            @Override
            public void onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                new AsyncTask<Void, Void, String>() {
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

    public static void listAll(final ListAllCallback callback) {

        AuthorizePresenter.authorize(API, new AuthorizePresenter.Callback() {
            @Override
            public void onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                new AsyncTask<Void, Void, String>() {
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
                                Product[] productArray = new Gson().fromJson(result, Product[].class);
                                List<Product> products = new ArrayList<>(Arrays.asList(productArray));
                                callback.onResponse(products);
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

        void onResponse(List<Product> products);
    }
}
