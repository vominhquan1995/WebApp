package asia.covisoft.bsandroid.prefs;

/**
 * Created by Leon on 3/31/2017.
 */

public final class WAApi {

    private static final String MA_V1 = Host.DEFAULT_HOST + "wp-json/mobileapp/v1/";

    public static final String LOGIN = MA_V1 + "login";
    public static final String NEWS = MA_V1 + "news";
    public static final String NOTIFICATION = MA_V1 + "notify";

    private static final String PREVIEW = MA_V1 + "productreview/";
    public static final String LIST_PREVIEW = PREVIEW + "list";
    public static final String ADD_PREVIEW = PREVIEW + "add";

    public static final String NEWS_PRODUCT_CONTENT = Host.DEFAULT_HOST + "wp-admin/admin-ajax.php?action=cwvn_show_post_content_ajax_request&ID=";
}
