package asia.covisoft.bsandroid.wc.presenter.order;

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
import asia.covisoft.bsandroid.wc.model.get.order.Order;
import asia.covisoft.bsandroid.wc.presenter.AuthorizePresenter;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by Leon on 2/21/2017.
 */

public class OrderPresenter {
    private static final String TAG = OrderPresenter.class.getSimpleName();

    private static final String API = WCApi.ORDER;

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
                                Order order = new Gson().fromJson(result, Order.class);
                                callback.onResponse(order);
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

        void onResponse(Order order);
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
                            try {
                                Order[] orderArray = new Gson().fromJson(result, Order[].class);
                                List<Order> orders = new ArrayList<>(Arrays.asList(orderArray));
                                callback.onResponse(orders);
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

        void onResponse(List<Order> orders);
    }


    public static void createOrder(Context context, final asia.covisoft.bsandroid.wc.model.post.order.Order order, final CreateCallback callback) {

        AuthorizePresenter.authorize(context, API, new AuthorizePresenter.Callback() {
            @Override
            public AsyncTask onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                return new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        String json = new Gson().toJson(order);
                        return HttpUtils.requestHttpPOST(wcRequestURL, json);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            Log.d(TAG, "onPostExecute: " + result);
                            try {
                                Order order = new Gson().fromJson(result, Order.class);
                                callback.onResponse(order);
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

    public interface CreateCallback extends WCCallback {

        void onResponse(Order order);
    }
}
