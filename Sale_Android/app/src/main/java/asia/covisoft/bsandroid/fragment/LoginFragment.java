package asia.covisoft.bsandroid.fragment;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import asia.covisoft.bsandroid.MainActivity;
import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseActivity;
import asia.covisoft.bsandroid.base.BaseLoadingFragment;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.options.Options;
import asia.covisoft.bsandroid.order.OrderHelper;
import asia.covisoft.bsandroid.prefs.PrefParams;
import asia.covisoft.bsandroid.utils.MD5;
import asia.covisoft.bsandroid.utils.SystemHelper;
import asia.covisoft.bsandroid.utils.ValidateUtils;
import asia.covisoft.bsandroid.wa.presenter.LoginPresenter;
import asia.covisoft.bsandroid.wc.model.get.customer.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseLoadingFragment {

    // Declare UI elements
    private ImageView imgLogo;
    private EditText edtEmail;
    private EditText edtPassword;
    private CheckBox chkRememberPassword;
    private Button btnForgotPassword;
    private Button btnLogin;
    private Options options;
    private String email, password;

    @Override
    public int getRootView() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {

        // Get UI elements
        imgLogo = (ImageView) rootView.findViewById(R.id.imgLogo);
        edtEmail = (EditText) rootView.findViewById(R.id.edtEmail);
        edtPassword = (EditText) rootView.findViewById(R.id.edtPassword);
        chkRememberPassword = (CheckBox) rootView.findViewById(R.id.chkRememberPassword);
        btnForgotPassword = (Button) rootView.findViewById(R.id.btnForgotPassword);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validInput()) {

                    login();
                }
            }
        });
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lostPasswordUrl = options.getLostPasswordUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lostPasswordUrl));
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).switchFragment(new RegisterFragment());
            }
        });
    }

    @Override
    public void initValue() {
    }

    @Override
    public void initAction() {

        options = Options.getOptions(context);
        Picasso.with(context)
                .load(options.getShopLogo())
                .into(imgLogo);
    }

    private boolean validInput() {

        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString();

        if (email.equals("")) {
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.error_email_empty));
            return false;
        }
        if (!ValidateUtils.isEmailValid(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.error_email_invalid));
            edtEmail.setText(email);
            edtEmail.setSelection(email.length());
            return false;
        }
        if (password.equals("")) {
            edtPassword.requestFocus();
            edtPassword.setError(getString(R.string.error_password_empty));
            return false;
        }

        return true;
    }

    private void login() {

        SystemHelper.hideKeyboard(context);
        showLoading();
        LoginPresenter.login(context, email, MD5.encrypt(password), new LoginPresenter.LoginCallback() {
            @Override
            public void onLoginSuccess(Customer customer) {

                dismissLoading();

                ((BaseActivity) activity).sharedPreferences.edit()
                        .putString(PrefParams.EMAIL, email)
                        .putString(PrefParams.PASSWORD, MD5.encrypt(password))
                        .apply();
                OrderHelper.customer = customer;
                ((MainActivity) activity).setLoggedIn(true);
                ((MainActivity) activity).switchFragment(new HomeFragment());
            }

            @Override
            public void onLoginFail() {
                dismissLoading();
                Snackbar.make(rootView, R.string.error_login, Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onConnectionTimeOut() {

                dismissLoading();
                ErrorDialog.showConnectionTimeOutDialog(context);
            }
        });
    }
}
