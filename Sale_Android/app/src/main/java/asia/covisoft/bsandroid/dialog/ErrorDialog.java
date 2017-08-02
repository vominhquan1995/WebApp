package asia.covisoft.bsandroid.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import asia.covisoft.bsandroid.R;

/**
 * Created by thong on 2/23/2017.
 * PROJECT BS_ANDROID
 */

public class ErrorDialog {

    // Declare alert dialog
    private static AlertDialog alertDialog;

    /**
     * If have error, input error message & show error dialog
     *
     * @param context - context
     * @param errMsg  - erorr message
     */
    public static void showErrorDialog(Context context, String errMsg) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.up_error_found))
                    .setMessage(errMsg)
                    .setCancelable(false)
                    .setPositiveButton(context.getString(R.string.up_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    /**
     * If request timeout, show connection timeout dialog
     *
     * @param context - context
     */
    public static void showConnectionTimeOutDialog(Context context) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.up_error_found))
                    .setMessage(context.getString(R.string.up_connection_timeout))
                    .setCancelable(false)
                    .setPositiveButton(context.getString(R.string.up_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    public static void dismissDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();

        }
    }

    public static void showTryAgainDialog(Context context, final TryAgainCallback callback) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.up_error_found))
                    .setMessage(context.getString(R.string.up_connection_timeout))
                    .setCancelable(false)
                    .setPositiveButton(context.getString(R.string.up_tryagain), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            alertDialog = null;
                            callback.onTryAgain();
                        }
                    })
                    .show();
        }
    }

    public interface TryAgainCallback {

        void onTryAgain();
    }
}
