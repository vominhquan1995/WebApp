package asia.covisoft.bsandroid.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asia.covisoft.bsandroid.R;

/**
 * Created by thong on 2/21/2017.
 * PROJECT BS_ANDROID
 */

public abstract class BaseDialogFragment extends DialogFragment  {

    protected Context context;
    protected View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.MYDIALOG);
        context = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setView(), container, false);
        initView();
        initValue();
        initAction();
        return rootView;
    }

    protected abstract int setView();

    protected abstract void initView();

    protected abstract void initValue();

    protected abstract void initAction();

    protected void gotoActivity(Class clazz){
        startActivity(new Intent(context, clazz));
    }

}
