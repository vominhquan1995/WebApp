package asia.covisoft.bsandroid.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseLoadingActivity;
import asia.covisoft.bsandroid.prefs.WAApi;
import asia.covisoft.bsandroid.utils.Html;
import im.delight.android.webview.AdvancedWebView;

/**
 * Created by Leon on 4/10/2017.
 */

public class NewsDetailsActivity extends BaseLoadingActivity {

    public static final String IMAGE = "IMAGE";
    public static final String TITLE = "TITLE";
    public static final String DATE = "DATE";
    public static final String CONTENT = "CONTENT";
    public static final String NEWS_ID = "NOTIFICATION_ID";

    @Override
    public int getView() {
        return R.layout.activity_news_details;
    }

    private AdvancedWebView wvNews;
    private ImageView imgNews;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvContent;

    @Override
    public void initView() {
        super.initView();

        updateToolbar(getString(R.string.up_news), true);

        imgNews = (ImageView) findViewById(R.id.imgNews);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvContent = (TextView) findViewById(R.id.tvContent);
        wvNews = (AdvancedWebView) findViewById(R.id.wvNews);
    }

    private String image, title, date, content, newsId;

    @Override
    public void initValue() {

        image = extras.getString(IMAGE);
        title = extras.getString(TITLE);
        date = extras.getString(DATE);
        content = extras.getString(CONTENT);
        newsId = extras.getString(NEWS_ID);
    }

    @Override
    public void initAction() {

        tvTitle.setText(title);
        tvDate.setText(date);
        tvContent.setText(Html.fromHtml(content));
        if (image != null) {
            Picasso.with(context)
                    .load(image)
                    .into(imgNews);
        } else {
            imgNews.setVisibility(View.GONE);
        }

//        wvNews.getSettings().setLoadsImagesAutomatically(true);
//        wvNews.setInitialScale(1);
//        wvNews.getSettings().setLoadWithOverviewMode(true);
//        wvNews.getSettings().setUseWideViewPort(true);
//        wvNews.getSettings().setJavaScriptEnabled(true);
//        wvNews.getSettings().setSupportZoom(true);
//        wvNews.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        wvNews.getSettings().setBuiltInZoomControls(true);
//        wvNews.getSettings().mo
//        wvNews.loadHtml(content);

//        wvNews.getSettings().setJavaScriptEnabled(true);
//        wvNews.getSettings().setUserAgentString("Android");
//        wvNews.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//        wvNews.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
        wvNews.loadUrl(WAApi.NEWS_PRODUCT_CONTENT + newsId);
    }

//    private int getScale(){
//        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//        int width = display.getWidth();
//        Double val = new Double(width)/new Double(PIC_WIDTH);
//        val = val * 100d;
//        return val.intValue();
//    }

}
