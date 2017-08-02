package asia.covisoft.bsandroid.wc.presenter;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import asia.covisoft.bsandroid.wc.view.WCCallback;
import asia.covisoft.bsandroid.prefs.AuthorizeParams;
import asia.covisoft.bsandroid.utils.HttpUtils;

/**
 * Created by Leon on 2/17/2017.
 */

public class AuthorizePresenterBak {
    private static final String TAG = AuthorizePresenterBak.class.getSimpleName();

    public static void authorize(final String api, final Callback callback) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String authorizeRequest = AuthorizeParams.REQUEST_URL + api;
                return HttpUtils.requestHttpGET(authorizeRequest);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result != null) {
                    Log.d(TAG, "onPostExecute: " + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String consumerKey = jsonObject.optString(AuthorizeParams.CONSUMER_KEY, "");
                        String signatureMethod = jsonObject.optString(AuthorizeParams.SIGNATURE_METHOD, "");
                        long timestamp = jsonObject.optLong(AuthorizeParams.TIMESTAMP, 0);
                        String nonce = jsonObject.optString(AuthorizeParams.NONCE, "");
                        String version = jsonObject.optString(AuthorizeParams.VERSION, "");
                        String signature = jsonObject.optString(AuthorizeParams.SIGNATURE, "");
                        if (!consumerKey.equals("") && !signatureMethod.equals("") && timestamp != 0 && !nonce.equals("") && !version.equals("") && !signature.equals("")) {

                            String wcRequestURL = api + "?" +
                                    AuthorizeParams.CONSUMER_KEY + "=" + consumerKey + "&" +
                                    AuthorizeParams.SIGNATURE_METHOD + "=" + signatureMethod + "&" +
                                    AuthorizeParams.TIMESTAMP + "=" + timestamp + "&" +
                                    AuthorizeParams.NONCE + "=" + nonce + "&" +
                                    AuthorizeParams.VERSION + "=" + version + "&" +
                                    AuthorizeParams.SIGNATURE + "=" + signature;

                            callback.onAuthentication(wcRequestURL, consumerKey, signatureMethod, timestamp, nonce, version, signature);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onConnectionTimeOut();
            }
        }.execute();
    }

    public interface Callback extends WCCallback {

        void onAuthentication(String wcRequestURL, String consumerKey, String signatureMethod, long timestamp, String nonce, String version, String signature);
    }
}
