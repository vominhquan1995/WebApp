package asia.covisoft.bsandroid.base;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import asia.covisoft.bsandroid.R;

/**
 * Created by thong on 2/23/2017.
 * PROJECT BS_ANDROID
 */

public abstract class BaseLoadingFragment extends BaseFragment {

    protected boolean isLoading;
    protected ProgressDialog progressDialog;

    public void showLoading(String message) {

        dismissLoading();
        isLoading = true;
        progressDialog = ProgressDialog.show(context, null, message, true, false);
    }

    public void showLoading() {

//        progressDialog = ProgressDialog.show(this, null, getString(R.string.dialog_loading), true, false);
        dismissLoading();
        isLoading = true;
        progressDialog = new ProgressDialog(context);
        Window dialogWindow = progressDialog.getWindow();
        if(dialogWindow!= null){
            dialogWindow.setBackgroundDrawable(new
                    ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(new ProgressBar(context));
    }

    public void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        isLoading = false;
    }

}
