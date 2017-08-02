package asia.covisoft.bsandroid.wc.presenter.tax;

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
import asia.covisoft.bsandroid.wc.model.get.tax.TaxClass;
import asia.covisoft.bsandroid.wc.presenter.AuthorizePresenter;
import asia.covisoft.bsandroid.wc.view.WCCallback;

/**
 * Created by An Pham on 17-Feb-17.
 * Last modifined on 17-Feb-17
 */

public class TaxClassPresenter {
    private static final String TAG = TaxClassPresenter.class.getSimpleName();

    private static final String API = WCApi.TAX_CLASS;

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
                                TaxClass[] taxClassArray = new Gson().fromJson(result, TaxClass[].class);
                                List<TaxClass> taxClasses = new ArrayList<>(Arrays.asList(taxClassArray));
                                callback.onResponse(taxClasses);
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

        void onResponse(List<TaxClass> taxClasses);
    }
}
