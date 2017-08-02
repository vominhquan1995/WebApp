package asia.covisoft.bsandroid.base;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import asia.covisoft.bsandroid.R;

/**
 * Created by thong on 2/15/2017. PROJECT BS_ANDROID
 */

public abstract class BaseToolbarActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected TextView toolbarTitle;

    @Override
    public void initView() {
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
    }

    protected void updateToolbar(String title, boolean isShowBackButton) {
        if (title != null) {
            toolbarTitle.setText(title);
        }

        // set back button
        if (isShowBackButton) {
            toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_white_24dp, null));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            toolbar.setNavigationIcon(null);
        }
    }

}
