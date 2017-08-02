package asia.covisoft.bsandroid.wc.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import asia.covisoft.bsandroid.prefs.WCApi;
import asia.covisoft.bsandroid.utils.HttpUtils;
import asia.covisoft.bsandroid.wc.model.get.customer.Customer;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by Leon on 2/18/2017.
 */

public class CustomerPresenter {
    private static final String TAG = CustomerPresenter.class.getSimpleName();

    private static final String API = WCApi.CUSTOMER;

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
                                Customer customer = new Gson().fromJson(result, Customer.class);
                                callback.onResponse(customer);
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

        void onResponse(Customer customer);
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
                                Customer[] customerArray = new Gson().fromJson(result, Customer[].class);
                                List<Customer> customers = new ArrayList<>(Arrays.asList(customerArray));
                                callback.onResponse(customers);
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

        void onResponse(List<Customer> customers);
    }

    public static void createCustomer(Context context, final asia.covisoft.bsandroid.wc.model.post.customer.Customer customer, final CreateCallback callback) {

        AuthorizePresenter.authorize(context, API, new AuthorizePresenter.Callback() {
            @Override
            public AsyncTask onAuthentication(final String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature) {

                return new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        String json = new Gson().toJson(customer);
                        return HttpUtils.requestHttpPOST(wcRequestURL, json);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);

                        if (result != null) {
                            Log.d(TAG, "onPostExecute: " + result);
                            try {
                                Customer customer = new Gson().fromJson(result, Customer.class);
                                callback.onResponse(customer, new JSONObject(result).optString("message"));
                                return;
                            } catch (JsonSyntaxException | IllegalStateException | JSONException ex) {
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

        void onResponse(Customer customer, String message);
    }
}
