package asia.covisoft.bsandroid.wc.presenter;

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
import asia.covisoft.bsandroid.wc.model.get.webhook.Webhook;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by An Pham on 17-Feb-17.
 * Last modifined on 17-Feb-17
 */

public class WebhookPresenter {
    private static final String TAG = WebhookPresenter.class.getSimpleName();

    private static final String API = WCApi.WEBHOOK;

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
                                Webhook webhook = new Gson().fromJson(result, Webhook.class);
                                callback.onResponse(webhook);
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

        void onResponse(Webhook webhook);
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
//                            try {
                            Webhook[] webhookArray = new Gson().fromJson(result, Webhook[].class);
                            List<Webhook> webhooks = new ArrayList<>(Arrays.asList(webhookArray));
                            callback.onResponse(webhooks);
                            return;
//                            } catch (JsonSyntaxException | IllegalStateException ex) {
//                                ex.printStackTrace();
//                            }

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

        void onResponse(List<Webhook> webhooks);
    }
}
