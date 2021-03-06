package asia.covisoft.miali.mvp.presenter.tax;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import asia.covisoft.miali.mvp.model.coupon.Coupon;
import asia.covisoft.miali.mvp.model.tax.TaxRate;
import asia.covisoft.miali.mvp.presenter.AuthorizePresenter;
import asia.covisoft.miali.mvp.view.WCCallback;
import asia.covisoft.miali.pref.Host;
import asia.covisoft.miali.pref.WCApi;
import asia.covisoft.miali.utils.HttpUtils;

/**
 * Created by An Pham on 17-Feb-17.
 * Last modifined on 17-Feb-17
 */

public class TaxRatePresenter {
    private static final String TAG = TaxRatePresenter.class.getSimpleName();

    private static final String API = Host.DEFAULT_HOST + WCApi.TAX;

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
                                TaxRate[] taxRateArray = new Gson().fromJson(result, TaxRate[].class);
                                List<TaxRate> taxRates = new ArrayList<>(Arrays.asList(taxRateArray));
                                callback.onResponse(taxRates);
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

        void onResponse(List<TaxRate> taxRates);
    }
}
