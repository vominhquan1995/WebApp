package asia.covisoft.bsandroid.activity;

import android.widget.TextView;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseLoadingActivity;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.mvp.presenter.InfoPresenter;
import asia.covisoft.bsandroid.mvp.view.InfoCallback;
import asia.covisoft.bsandroid.utils.Html;
import im.delight.android.webview.AdvancedWebView;

public class InfoActivity extends BaseLoadingActivity {

    public static final String INFO_API = "INFO_API";

    @Override
    public int getView() {
        return R.layout.activity_info;
    }

    private TextView tvContent;
    private AdvancedWebView wvContent;

    @Override
    public void initView() {
        super.initView();

        tvContent = (TextView) findViewById(R.id.tvContent);
        wvContent = (AdvancedWebView) findViewById(R.id.wvContent);
    }

    private String infoApi;

    @Override
    public void initValue() {

        infoApi = extras.getString(INFO_API);
    }

    @Override
    public void initAction() {

        showLoading();
        InfoPresenter.getInfo(infoApi, new InfoCallback() {
            @Override
            public void onInfoCallback(String title, String content, String contentUrl) {

                dismissLoading();
                updateToolbar(title, true);
                tvContent.setText(Html.fromHtml(content));
                wvContent.loadUrl(contentUrl);
            }

            @Override
            public void onConnectionTimeOut() {

                dismissLoading();
                ErrorDialog.showConnectionTimeOutDialog(context);
            }
        });
    }
}
