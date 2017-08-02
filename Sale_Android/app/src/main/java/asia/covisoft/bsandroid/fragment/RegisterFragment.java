package asia.covisoft.bsandroid.fragment;


import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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
import asia.covisoft.bsandroid.wc.model.post.customer.Billing;
import asia.covisoft.bsandroid.wc.model.post.customer.Customer;
import asia.covisoft.bsandroid.wc.model.post.customer.Shipping;
import asia.covisoft.bsandroid.wc.presenter.CustomerPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseLoadingFragment {

    // Declare UI elements
    private ImageView imgLogo;
    private EditText edtLastName;
    private EditText edtFirstName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;
    private EditText edtPhone;
    private EditText edtAddress;
    private Spinner spnCity;
    private CheckBox chkConfirmTerm;
    private Button btnRegister;

    @Override
    public int getRootView() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        imgLogo = (ImageView) rootView.findViewById(R.id.imgLogo);
        edtLastName = (EditText) rootView.findViewById(R.id.edtLastName);
        edtFirstName = (EditText) rootView.findViewById(R.id.edtFirstName);
        edtEmail = (EditText) rootView.findViewById(R.id.edtEmail);
        edtPassword = (EditText) rootView.findViewById(R.id.edtPassword);
        edtPasswordConfirm = (EditText) rootView.findViewById(R.id.edtPasswordConfirm);
        edtPhone = (EditText) rootView.findViewById(R.id.edtPhone);
        edtAddress = (EditText) rootView.findViewById(R.id.edtAddress);
        spnCity = (Spinner) rootView.findViewById(R.id.spnCity);
        chkConfirmTerm = (CheckBox) rootView.findViewById(R.id.chkConfirmTerm);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validInput()) {

                    register();
                }
            }
        });
    }

    @Override
    public void initValue() {
        String[] cities = getResources().getStringArray(R.array.city);
        ArrayAdapter<String> cityAdapter
                = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, cities);
        spnCity.setAdapter(cityAdapter);
    }

    @Override
    public void initAction() {

        Options options = Options.getOptions(context);
        Picasso.with(context)
                .load(options.getShopLogo())
                .into(imgLogo);
    }

    private String firstName, lastName, email, password, phone, address;

    private boolean validInput() {

        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString();
        String passwordConfirm = edtPasswordConfirm.getText().toString();

        firstName = edtFirstName.getText().toString().trim();
        lastName = edtLastName.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        address = edtAddress.getText().toString().trim();

        if (email.equals("")) {
            edtEmail.setError(getString(R.string.error_email_empty));
            edtEmail.requestFocus();
            return false;
        }
        if (!ValidateUtils.isEmailValid(email)) {
            edtEmail.setError(getString(R.string.error_email_invalid));
            edtEmail.requestFocus();
            return false;
        }
        if (password.equals("")) {
            edtPassword.requestFocus();
            edtPassword.setError(getString(R.string.error_password_empty));
            return false;
        }
        if (!ValidateUtils.isPasswordValid(password)) {
            edtPassword.setError(getString(R.string.error_password_invalid));
            edtPassword.setSelection(password.length());
            return false;
        }
        if (!password.equals(passwordConfirm)) {
            edtPasswordConfirm.setError(getString(R.string.error_password_confirm_wrong));
            edtPasswordConfirm.requestFocus();
            return false;
        }
        if (lastName.equals("")) {
            edtLastName.requestFocus();
            edtFirstName.setError(getString(R.string.error_fullname_empty));
            return false;
        }
        if (!ValidateUtils.isFullNameValid(lastName)) {
            edtLastName.setError(getString(R.string.error_fullname_invalid));
            edtLastName.requestFocus();
            edtLastName.setText(lastName);
            edtLastName.setSelection(lastName.length());
            return false;
        }
        if (firstName.equals("")) {
            edtFirstName.requestFocus();
            edtFirstName.setError(getString(R.string.error_fullname_empty));
            return false;
        }
        if (!ValidateUtils.isFullNameValid(firstName)) {
            edtFirstName.setError(getString(R.string.error_fullname_invalid));
            edtFirstName.requestFocus();
            edtFirstName.setText(firstName);
            edtFirstName.setSelection(firstName.length());
            return false;
        }
        if (phone.trim().equalsIgnoreCase("")) {
            edtPhone.setError(getString(R.string.error_phone_empty));
            edtPhone.requestFocus();
            return false;
        }
        if (!ValidateUtils.isPhoneValid(phone)) {
            edtPhone.setError(getString(R.string.error_phone_invalid));
            edtPhone.requestFocus();
            edtPhone.setText(phone);
            edtPhone.setSelection(phone.length());
            return false;
        }
        if (address.trim().equalsIgnoreCase("")) {
            edtAddress.setError(getString(R.string.error_address_invalid));
            edtAddress.requestFocus();
            return false;
        }

        return true;
    }

    private void register() {

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(MD5.encrypt(password));
        Billing billing = new Billing();
        billing.setFirstName(firstName);
        billing.setLastName(lastName);
        billing.setEmail(email);
        billing.setAddress1(address);
        billing.setAddress2("");
        billing.setPhone(phone);
        billing.setCompany("");
        billing.setCity("VN");
        billing.setState("VN");
        billing.setPostcode("700000");
        billing.setCountry("VN");
        customer.setBilling(billing);
        Shipping shipping = new Shipping();
        shipping.setFirstName(firstName);
        shipping.setLastName(lastName);
        shipping.setAddress1(address);
        shipping.setAddress2("");
        shipping.setCompany("");
        shipping.setCity("VN");
        shipping.setState("VN");
        shipping.setPostcode("700000");
        shipping.setCountry("VN");
        customer.setShipping(shipping);

        SystemHelper.hideKeyboard(context);
        showLoading();
        CustomerPresenter.createCustomer(context, customer, new CustomerPresenter.CreateCallback() {
            @Override
            public void onResponse(asia.covisoft.bsandroid.wc.model.get.customer.Customer customer, String message) {

                dismissLoading();
                if (message.equals("")) {

                    ((BaseActivity) activity).sharedPreferences.edit()
                            .putString(PrefParams.EMAIL, email)
                            .putString(PrefParams.PASSWORD, MD5.encrypt(password))
                            .apply();
                    OrderHelper.customer = customer;
                    ((MainActivity) activity).setLoggedIn(true);
                    new AlertDialog.Builder(context)
                            .setTitle("ĐĂNG KÝ THÀNH CÔNG")
                            .setPositiveButton("ĐÓNG", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    ((MainActivity) activity).switchFragment(new HomeFragment());
                                }
                            })
                            .show();
                } else {

                    final Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(getString(R.string.cap_close), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }
            }

            @Override
            public void onConnectionTimeOut() {

                dismissLoading();
                ErrorDialog.showConnectionTimeOutDialog(context);
            }
        });
    }
}
