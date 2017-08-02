package asia.covisoft.bsandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import asia.covisoft.bsandroid.activity.NotificationDetailsActivity;
import asia.covisoft.bsandroid.activity.ProductSearchActivity;
import asia.covisoft.bsandroid.base.BaseFirebaseActivity;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.fragment.CartFragment;
import asia.covisoft.bsandroid.fragment.ContactFragment;
import asia.covisoft.bsandroid.fragment.FavoriteFragment;
import asia.covisoft.bsandroid.fragment.HomeFragment;
import asia.covisoft.bsandroid.fragment.LoginFragment;
import asia.covisoft.bsandroid.fragment.NewsFragment;
import asia.covisoft.bsandroid.fragment.NotificationFragment;
import asia.covisoft.bsandroid.fragment.ProductsFragment;
import asia.covisoft.bsandroid.fragment.RegisterFragment;
import asia.covisoft.bsandroid.fragment.SettingsFragment;
import asia.covisoft.bsandroid.fragment.SupportFragment;
import asia.covisoft.bsandroid.helper.FragmentNavigator;
import asia.covisoft.bsandroid.options.Options;
import asia.covisoft.bsandroid.options.OptionsPresenter;
import asia.covisoft.bsandroid.order.OrderHelper;
import asia.covisoft.bsandroid.prefs.Constant;
import asia.covisoft.bsandroid.prefs.PrefParams;
import asia.covisoft.bsandroid.wa.presenter.LoginPresenter;
import asia.covisoft.bsandroid.wc.model.get.customer.Customer;

public class MainActivity extends BaseFirebaseActivity {

    // Navigation menu
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Options option;

    // Fragment
    protected FragmentNavigator fragmentNavigator;

    // click back 2 times to exit app
    private boolean doubleBackToExit = false;

    @Override
    public int getView() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        IS_WAITING_FOR_OPTIONS = true;
        super.onCreate(savedInstanceState);
        getOptions();
    }

    private void getOptions() {

        showLoading();
        OptionsPresenter.getOptions(MainActivity.this, new OptionsPresenter.Callback() {
            @Override
            public void onOptionsCallback(Options options) {

                dismissLoading();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.parseColor(options.getDarkPrimaryColor()));
                }
                option=options;
                initActivity();
            }

            @Override
            public void onConnectionTimeOut() {

                dismissLoading();
                ErrorDialog.showTryAgainDialog(MainActivity.this, new ErrorDialog.TryAgainCallback() {
                    @Override
                    public void onTryAgain() {

                        getOptions();
                    }
                });
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navView = (NavigationView) findViewById(R.id.navView);
        initNavMenu();
        initAvatarAndBanner();
        mainToolbar.inflateMenu(R.menu.toolbar_menu);
        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:

                        Intent intent = new Intent(context, ProductSearchActivity.class);
                        startActivity(intent);

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initValue() {
        fragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), R.id.container);
    }

    @Override
    public void initAction() {

        fragmentNavigator.setRootFragment(new HomeFragment());
        autoLogin();

        checkNotification();
    }

    private void checkNotification() {

        if (extras != null) {
            try {
                JSONObject data = new JSONObject(extras.getString("data"));

                String title = data.getString("title");
//            String date = extras.getString(NotificationDetailsActivity.DATE);
//            String content = extras.getString(NotificationDetailsActivity.CONTENT);
                String notifcationId = data.getString("id");

                Intent intent = new Intent(context, NotificationDetailsActivity.class);
                intent.putExtra(NotificationDetailsActivity.NOTIFICATION_ID, notifcationId);
                intent.putExtra(NotificationDetailsActivity.TITLE, title);
                startActivity(intent);

            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    private void autoLogin() {

        final String email = sharedPreferences.getString(PrefParams.EMAIL, "");
        String password = sharedPreferences.getString(PrefParams.PASSWORD, "");
        if (!email.equals("") && !password.equals("")) {
            LoginPresenter.login(context, email, password, new LoginPresenter.LoginCallback() {
                @Override
                public void onLoginSuccess(Customer customer) {

                    OrderHelper.customer = customer;
                    setLoggedIn(true);
                }

                @Override
                public void onLoginFail() {

                    sharedPreferences.edit()
                            .putString(PrefParams.EMAIL, "")
                            .putString(PrefParams.PASSWORD, "")
                            .apply();
                    OrderHelper.customer = null;
                }

                @Override
                public void onConnectionTimeOut() {

                    OrderHelper.customer = null;
                }
            });
        }
    }

    private TextView tvEmail;
    private Button btnLogin;
    private ImageButton btnLogout;

    private void initNavMenu() {
        navView.getMenu().getItem(0).setChecked(true);
        navView.getMenu().getItem(0).setTitle(option.getShopName());
        updateToolbar(option.getShopName(), true);
        tvEmail = (TextView) navView.getHeaderView(0).findViewById(R.id.tvEmail);
        btnLogin = (Button) navView.getHeaderView(0).findViewById(R.id.btnLogin);
        btnLogout = (ImageButton) navView.getHeaderView(0).findViewById(R.id.btnLogout);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.closeDrawers();
                switchFragment(new LoginFragment());
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle(R.string.up_logout)
                        .setMessage(R.string.dialog_logout)
                        .setNegativeButton(R.string.up_no, null)
                        .setPositiveButton(R.string.up_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                setLoggedIn(false);
                                OrderHelper.customer = null;
                            }
                        })
                        .show();
            }
        });
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        // Closing drawer on item click
                        drawerLayout.closeDrawers();

                        // Check to see which item was being clicked
                        int menuId = item.getItemId();
                        switch (menuId) {
                            case R.id.menu_home:
                                switchFragment(new HomeFragment());
                                return true;
                            case R.id.menu_product:
                                switchFragment(new ProductsFragment());
                                return true;
                            case R.id.menu_basket:
                                switchFragment(new CartFragment());
                                return true;
                            case R.id.menu_favorite:
                                switchFragment(new FavoriteFragment());
                                return true;
                            case R.id.menu_notification:
                                switchFragment(new NotificationFragment());
                                return true;
                            case R.id.menu_news:
                                switchFragment(new NewsFragment());
                                return true;
                            case R.id.menu_settings:
                                switchFragment(new SettingsFragment());
                                return true;
                            case R.id.menu_contact:
                                switchFragment(new ContactFragment());
                                return true;
                            case R.id.menu_support:
                                switchFragment(new SupportFragment());
                                return true;
                        }

                        return false;
                    }
                });

        // Initializing Drawer Layout and ActionBarToggle
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                mainToolbar,
                R.string.cap_open,
                R.string.cap_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        // Setting the actionBarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // calling sync state is necessary or else your hamburger icon wont show
        actionBarDrawerToggle.syncState();
    }

    private void initAvatarAndBanner() {

        SliderLayout sldBanners = (SliderLayout) navView.getHeaderView(0).findViewById(R.id.sldBanners);
        ImageView imgShopAvatar = (ImageView) navView.getHeaderView(0).findViewById(R.id.imgShopAvatar);

//        Options options = Options.getOptions(context);

        sldBanners.setPresetTransformer(SliderLayout.Transformer.Default);
        sldBanners.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sldBanners.setDuration(Constant.SLIDER_DURATION);
        DefaultSliderView sliderView = new DefaultSliderView(context);
        for (String bannerUrl : option.getShopBanners()) {
            sliderView.image(bannerUrl)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
        }
        sldBanners.addSlider(sliderView);

        Picasso.with(context)
                .load(option.getShopAvatar())
                .into(imgShopAvatar);
    }

    public void switchFragment(Fragment fragment) {
        boolean clear = false;

        mainToolbar.getMenu().findItem(R.id.action_search).setVisible(false);

//        mainToolbar.getMenu().clear();
        int size = navView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navView.getMenu().getItem(i).setChecked(false);
        }
        if (fragment instanceof HomeFragment) {
            updateToolbar(option.getShopName(), true);
            navView.getMenu().getItem(0).setChecked(true);
            clear = true;
        }
        if (fragment instanceof ProductsFragment) {
            updateToolbar(getString(R.string.up_product), false);
            navView.getMenu().getItem(1).setChecked(true);
            mainToolbar.getMenu().findItem(R.id.action_search).setVisible(true);
            clear = true;
        }
        if (fragment instanceof CartFragment) {
            updateToolbar(getString(R.string.up_basket), false);
            navView.getMenu().getItem(2).setChecked(true);
            clear = true;
        }
        if (fragment instanceof FavoriteFragment) {
            updateToolbar(getString(R.string.up_favorite), false);
            menuBasket.setVisibility(View.GONE);
            navView.getMenu().getItem(3).setChecked(true);
            clear = true;
        }
        if (fragment instanceof NotificationFragment) {
            updateToolbar(getString(R.string.up_notifications), false);
            navView.getMenu().getItem(4).setChecked(true);
            clear = true;
        }
        if (fragment instanceof NewsFragment) {
            updateToolbar(getString(R.string.up_news), false);
            navView.getMenu().getItem(5).setChecked(true);
            clear = true;
        }
        if (fragment instanceof SettingsFragment) {
            updateToolbar(getString(R.string.up_settings), false);
            navView.getMenu().getItem(6).setChecked(true);
            clear = true;
        }
        if (fragment instanceof ContactFragment) {
            updateToolbar(getString(R.string.up_contact), false);
            navView.getMenu().getItem(7).setChecked(true);
            clear = true;
        }
        if (fragment instanceof SupportFragment) {
            updateToolbar(getString(R.string.up_support), false);
            navView.getMenu().getItem(8).setChecked(true);
            clear = true;
        }
        if (fragment instanceof LoginFragment) {
            updateToolbar(getString(R.string.cap_login), false);
            clear = true;
        }
        if (fragment instanceof RegisterFragment) {
            updateToolbar(getString(R.string.cap_register), false);
            clear = true;
        }

        if (clear) {
            fragmentNavigator.goToRoot();
        }
        fragmentNavigator.goTo(fragment);
    }

    public void onBackPressed() {
        if (fragmentNavigator.getActiveFragment() instanceof ProductsFragment ||
                fragmentNavigator.getActiveFragment() instanceof CartFragment ||
                fragmentNavigator.getActiveFragment() instanceof FavoriteFragment ||
                fragmentNavigator.getActiveFragment() instanceof NewsFragment ||
                fragmentNavigator.getActiveFragment() instanceof SettingsFragment ||
                fragmentNavigator.getActiveFragment() instanceof ContactFragment ||
                fragmentNavigator.getActiveFragment() instanceof SupportFragment ||
                fragmentNavigator.getActiveFragment() instanceof LoginFragment) {
            drawerLayout.closeDrawers();
            switchFragment(new HomeFragment());
        } else if (fragmentNavigator.getActiveFragment() instanceof RegisterFragment) {
            drawerLayout.closeDrawers();
            switchFragment(new LoginFragment());
        } else {
            doubleBackToExit();
        }
    }

    private void doubleBackToExit() {
        if (doubleBackToExit) {
            moveTaskToBack(true);
            return;
        }

        doubleBackToExit = true;
        Toast.makeText(context, R.string.double_back_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit = false;
            }
        }, 2000); // reset the variable after 2s
    }

    @Override
    public void menuBasketOnClick() {
        switchFragment(new CartFragment());
    }

    public void setLoggedIn(boolean isLoggedIn) {
        if (isLoggedIn && OrderHelper.customer != null) {
            tvEmail.setText(OrderHelper.customer.getEmail());
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            tvEmail.setText(null);
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }
    }
}
