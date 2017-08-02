package asia.covisoft.bsandroid.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.helper.FragmentNavigator;

/**
 * Created by thong on 2/15/2017.
 * PROJECT BS_ANDROID
 */

public abstract class BaseActivity extends AppCompatActivity implements Init {

    public SharedPreferences sharedPreferences;
    protected Context context;
    public FragmentNavigator fragmentNavigator;
    protected Bundle extras;

    protected boolean IS_WAITING_FOR_OPTIONS;

    public abstract int getView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!IS_WAITING_FOR_OPTIONS){
            initActivity();
        }
    }

    protected void initActivity(){
        setContentView(getView());

        // hide keyboard every time the activity stats
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /**
         * Init
         */
        initBaseValue();
        initView();
        initValue();
        initAction();
    }

    private void initBaseValue() {
        context = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        fragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), R.id.container);
        extras = getIntent().getExtras();
    }

    protected void gotoActivity(Class clazz) {
        startActivity(new Intent(context, clazz));
    }

}
