package asia.covisoft.bsandroid.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asia.covisoft.bsandroid.MainActivity;

/**
 * Created by thong on 2/15/2017. PROJECT BS_ANDROID
 */

public abstract class BaseFragment extends BaseWAFragment implements Init {

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getRootView(), container, false);

        initBaseValue();
        initView();
        initValue();
        initAction();

        return rootView;
    }

    public abstract int getRootView();

    protected Bundle arguments;
    protected Context context;
    protected Activity activity;

    private void initBaseValue() {

        arguments = getArguments();
        context = getActivity();
        activity = getActivity();
    }

    protected void gotoFragment(Fragment fragment) {
        ((MainActivity) context).switchFragment(fragment);
    }

    protected void gotoActivity(Class clazz) {
        startActivity(new Intent(context, clazz));
    }
}
