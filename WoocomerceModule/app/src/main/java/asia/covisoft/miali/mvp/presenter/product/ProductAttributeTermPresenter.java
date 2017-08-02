package asia.covisoft.miali.mvp.presenter.product;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import asia.covisoft.miali.mvp.model.order.refund.Refund;
import asia.covisoft.miali.mvp.model.product.attribute.term.ProductAttributeTerm;
import asia.covisoft.miali.mvp.presenter.AuthorizePresenter;
import asia.covisoft.miali.mvp.presenter.order.RefundPresenter;
import asia.covisoft.miali.mvp.view.WCCallback;
import asia.covisoft.miali.pref.Host;
import asia.covisoft.miali.pref.WCApi;
import asia.covisoft.miali.utils.HttpUtils;

/**
 * Created by Leon on 2/22/2017.
 */

public class ProductAttributeTermPresenter {
    private static final String TAG = ProductAttributeTermPresenter.class.getSimpleName();

    private static final String ATTRIBUTE_ID = "$attributeId$";
    private static final String API = Host.DEFAULT_HOST + WCApi.PRODUCT_ATTRIBUTE + "/" + ATTRIBUTE_ID + "/" + WCApi.TERM;

    public static void retrieve(long attributeId, long id, final RetrieveCallback callback) {

        String authorizeApi = API.replace(ATTRIBUTE_ID, attributeId + "") + "/" + id;

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
                                ProductAttributeTerm productAttributeTerm = new Gson().fromJson(result, ProductAttributeTerm.class);
                                callback.onResponse(productAttributeTerm);
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

        void onResponse(ProductAttributeTerm productAttributeTerm);
    }

    public static void listAll(long attributeId, final ListAllCallback callback) {

        String authorizeApi = API.replace(ATTRIBUTE_ID, attributeId + "");

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
                                ProductAttributeTerm[] productAttributeTermArray = new Gson().fromJson(result, ProductAttributeTerm[].class);
                                List<ProductAttributeTerm> productAttributeTerms = new ArrayList<>(Arrays.asList(productAttributeTermArray));
                                callback.onResponse(productAttributeTerms);
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

        void onResponse(List<ProductAttributeTerm> productAttributeTerms);
    }
}
