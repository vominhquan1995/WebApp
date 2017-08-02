package asia.covisoft.bsandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import asia.covisoft.bsandroid.utils.SystemHelper;

/**
 * Created by Leon on 4/14/2017.
 */

public abstract class BaseWAFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemHelper.hideKeyboard(getContext());
    }
}
