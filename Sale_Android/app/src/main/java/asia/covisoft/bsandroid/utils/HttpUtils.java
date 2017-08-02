package asia.covisoft.bsandroid.utils;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    private static final int CONNECTION_TIMEOUT = 30;

    public static String requestHttpGET(String requestUrl) {

        Request request = new Request.Builder().url(requestUrl).build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
        String responseStr = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    public static String requestHttpPOST(String requestUrl, Map<String, Object> params) {

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        String json = buildJson(params);

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
        String responseStr = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    public static String requestHttpPOST(String requestUrl, String json) {

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
        String responseStr = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    public static String requestHttpPUT(String requestUrl, String json) {

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(requestUrl)
                .put(body)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
        String responseStr = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    public static String requestHttpPOST(String requestUrl, String[] keys, Object[] values) {

        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }

        return requestHttpPOST(requestUrl, map);
    }

    public static String buildJson(Map<String, Object> params) {

        JSONObject json = new JSONObject();
        try {
            if (params.entrySet().size() > 0) {
                for (String key : params.keySet()) {
                    json.putOpt(key, params.get(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static void checkInternetConnection(final int timeout /*seconds*/, final OnConnectionListener listener) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                listener.onPreConnect();
            }

            @Override
            protected Boolean doInBackground(Void... params) {

                try {
                    Request request = new Request.Builder().url("http://google.com").build();
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    return response != null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean responded) {
                super.onPostExecute(responded);
                if (responded) {
                    listener.onConnected();
                } else {
                    listener.onFail();
                }
            }
        }.execute();
    }

    public interface OnConnectionListener {

        void onPreConnect();

        void onConnected();

        void onFail();
    }
}
