package asia.covisoft.miali.utils;


import android.util.Log;


import java.util.List;

import asia.covisoft.miali.pref.Consumer;
import asia.covisoft.miali.pref.Host;
import asia.covisoft.miali.pref.WCApi;

/**
 * Created by Leon on 2/15/2017.
 */

public class WCUtils {

    private static final String TAG = WCUtils.class.getSimpleName();

    public static void WCAuthenticate(){

//        OAuthConfig config = new OAuthConfig(Host.DEFAULT_HOST + WCApi.AUTHORIZE, Consumer.KEY, Consumer.SECRET);
//        WooCommerce wooCommerce = new WooCommerceAPI(config);
//
//        List<Object> customer = wooCommerce.getAll(EndpointBaseType.CUSTOMERS.getValue());

        Log.d(TAG, "WCAuthenticate");
    }
}
