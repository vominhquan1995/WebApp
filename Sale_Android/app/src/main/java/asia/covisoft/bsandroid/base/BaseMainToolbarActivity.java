package asia.covisoft.bsandroid.base;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import asia.covisoft.bsandroid.R;

/**
 * Created by thong on 2/15/2017. PROJECT BS_ANDROID
 */

public abstract class BaseMainToolbarActivity extends BaseLoadingActivity implements View.OnClickListener {

    protected Toolbar mainToolbar;
    protected TextView toolbarTitle;
    protected ViewGroup menuBasket;

    @Override
    public void initView() {
        initToolbar();
    }

    private void initToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbarTitle = (TextView) mainToolbar.findViewById(R.id.toolbar_title);
        menuBasket = (ViewGroup) mainToolbar.findViewById(R.id.menuBasket);

        menuBasket.setOnClickListener(this);
    }

    public void updateToolbar(String title, boolean isShowBasketMenu) {
        // set title
        if (title != null) {
            toolbarTitle.setText(title);
        }

        // show/hide basket menu
//        if (isShowBasketMenu) menuBasket.setVisibility(View.VISIBLE);
//        else menuBasket.setVisibility(View.GONE);
    }

    public abstract void menuBasketOnClick();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_basket:
                menuBasketOnClick();
                break;
        }
    }

}
