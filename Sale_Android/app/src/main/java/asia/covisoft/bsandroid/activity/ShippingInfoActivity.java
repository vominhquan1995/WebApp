package asia.covisoft.bsandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseToolbarActivity;
import asia.covisoft.bsandroid.order.OrderHelper;
import asia.covisoft.bsandroid.utils.ValidateUtils;

public class ShippingInfoActivity extends BaseToolbarActivity implements View.OnClickListener {

    // payment method
    private String[] payments;

    // UI elements
    private EditText edtLastName;
    private EditText edtFirstName;
    private EditText edtEmail;
    private EditText edtPhone;
    private EditText edtAddress;
    private Button btnPay;

    @Override
    public int getView() {
        return R.layout.activity_shipping_info;
    }

    @Override
    public void initView() {
        super.initView();
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        btnPay = (Button) findViewById(R.id.btnPay);
    }

    @Override
    public void initValue() {
        updateToolbar(getString(R.string.up_payment), true);
        payments = context.getResources().getStringArray(R.array.payment_method);
    }

    @Override
    public void initAction() {
        btnPay.setOnClickListener(this);

        if (OrderHelper.customer != null) {

            edtLastName.setText(OrderHelper.customer.getLastName());
            edtFirstName.setText(OrderHelper.customer.getFirstName());
            edtEmail.setText(OrderHelper.customer.getEmail());
            edtPhone.setText(OrderHelper.customer.getBilling().getPhone());
            edtAddress.setText(OrderHelper.customer.getBilling().getAddress1());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPay:

//                if (validForm()) {
//                    View paymentMethodView = LayoutInflater.from(context)
//                            .inflate(R.layout.dialog_payment_method, (ViewGroup) findViewById(R.id.dialog_payment_method));
//
//                    // get UI elements on dialog
//                    ListView lvPayment = (ListView) paymentMethodView.findViewById(R.id.lvPaymentMethod);
//                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_single_choice, payments);
//                    lvPayment.setAdapter(adapter);
//
//                    final AlertDialog paymentMethodDialog = new AlertDialog.Builder(context)
//                            .setTitle(null)
//                            .setView(paymentMethodView)
//                            .setCancelable(false)
//                            .setNegativeButton(getString(R.string.up_cancel), null)
//                            .setPositiveButton(getString(R.string.up_continue), null)
//                            .create();
//
//                    paymentMethodDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                        @Override
//                        public void onShow(final DialogInterface dialog) {
//                            paymentMethodDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
//                            paymentMethodDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//
//                                    Intent intent = new Intent(context, PaymentActivity.class);
//                                    intent.putExtra(PaymentActivity.LAST_NAME, lastName);
//                                    intent.putExtra(PaymentActivity.FIRST_NAME, firstName);
//                                    intent.putExtra(PaymentActivity.EMAIL, email);
//                                    intent.putExtra(PaymentActivity.PHONE, phoneNumber);
//                                    intent.putExtra(PaymentActivity.ADDRESS, address);
//                                    startActivity(intent);
//                                }
//                            });
//                        }
//                    });
//                    paymentMethodDialog.show();
//                }
                if (validForm()) {

                    Intent intent = new Intent(context, PaymentActivity.class);
                    intent.putExtra(PaymentActivity.LAST_NAME, lastName);
                    intent.putExtra(PaymentActivity.FIRST_NAME, firstName);
                    intent.putExtra(PaymentActivity.EMAIL, email);
                    intent.putExtra(PaymentActivity.PHONE, phoneNumber);
                    intent.putExtra(PaymentActivity.ADDRESS, address);
                    startActivity(intent);
                }
                break;
        }
    }

    private String lastName, firstName, email, phoneNumber, address;

    private boolean validForm() {
        edtLastName.setError(null);
        edtFirstName.setError(null);
        edtEmail.setError(null);
        edtPhone.setError(null);
        edtAddress.setError(null);

        lastName = edtLastName.getText().toString().trim();
        firstName = edtFirstName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        phoneNumber = edtPhone.getText().toString().trim();
        address = edtAddress.getText().toString().trim();

        if (lastName.equalsIgnoreCase("")) {
            edtLastName.setError(getString(R.string.error_fullname_empty));
            edtLastName.requestFocus();
            return false;
        } else if (!ValidateUtils.isFullNameValid(lastName)) {
            edtLastName.setError(getString(R.string.error_fullname_invalid));
            edtLastName.requestFocus();
            edtLastName.setText(lastName);
            edtLastName.setSelection(lastName.length());
            return false;
        } else if (firstName.equalsIgnoreCase("")) {
            edtFirstName.setError(getString(R.string.error_fullname_empty));
            edtFirstName.requestFocus();
            return false;
        } else if (!ValidateUtils.isFullNameValid(firstName)) {
            edtFirstName.setError(getString(R.string.error_fullname_invalid));
            edtFirstName.requestFocus();
            edtFirstName.setText(firstName);
            edtFirstName.setSelection(firstName.length());
            return false;
        } else if (email.equalsIgnoreCase("")) {
            edtEmail.setError(getString(R.string.error_email_empty));
            edtEmail.requestFocus();
            return false;
        } else if (!ValidateUtils.isEmailValid(email)) {
            edtEmail.setError(getString(R.string.error_email_invalid));
            edtEmail.requestFocus();
            edtEmail.setText(email);
            edtEmail.setSelection(edtEmail.length());
            return false;
        } else if (phoneNumber.equalsIgnoreCase("")) {
            edtPhone.setError(getString(R.string.error_phone_empty));
            edtPhone.requestFocus();
            return false;
        } else if (!ValidateUtils.isPhoneValid(phoneNumber)) {
            edtPhone.setError(getString(R.string.error_phone_invalid));
            edtPhone.requestFocus();
            edtPhone.setText(phoneNumber);
            edtPhone.setSelection(phoneNumber.length());
            return false;
        } else if (address.equalsIgnoreCase("")) {
            edtAddress.setError(getString(R.string.error_address_invalid));
            edtAddress.requestFocus();
            return false;
        }

        return true;
    }

}
