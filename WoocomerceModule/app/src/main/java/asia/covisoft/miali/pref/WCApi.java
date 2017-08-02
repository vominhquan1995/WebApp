package asia.covisoft.miali.pref;

/**
 * Created by Leon on 2/11/2017.
 */

public final class WCApi {

    public static final String AUTHORIZE = "wc-auth/v1/authorize";

    private static final String WC_V1 = "wp-json/wc/v1/";

    public static final String COUPON = WC_V1 + "coupons";
    public static final String CUSTOMER = WC_V1 + "customers";

    public static final String ORDER = WC_V1 + "orders";
    public static final String ORDER_NOTE = WC_V1 + "notes";
    public static final String REFUND = WC_V1 + "refunds";

    public static final String PRODUCT = WC_V1 + "products";
    public static final String PRODUCT_ATTRIBUTE = PRODUCT + "/attributes";
    public static final String PRODUCT_CATEGORY = PRODUCT + "/categories";//TODO "curently return both object & array"
    public static final String PRODUCT_SHIPPING_CLASS = PRODUCT + "/shipping_classes";
    public static final String PRODUCT_TAG = PRODUCT + "/tags";
    public static final String TERM = "terms";

    public static final String REPORT = WC_V1 + "reports";//TODO
    public static final String TAX = WC_V1 + "taxes";
    public static final String TAX_CLASS = TAX + "/classes";

    public static final String WEBHOOK = WC_V1 + "webhooks";//TODO "currently return object instead of array"
}
