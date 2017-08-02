package asia.covisoft.bsandroid.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.base.BaseLoadingActivity;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.order.OrderHelper;
import asia.covisoft.bsandroid.order.OrderProduct;
import asia.covisoft.bsandroid.utils.SystemHelper;
import asia.covisoft.bsandroid.wc.model.post.order.Billing;
import asia.covisoft.bsandroid.wc.model.post.order.LineItem;
import asia.covisoft.bsandroid.wc.model.post.order.Order;
import asia.covisoft.bsandroid.wc.model.post.order.ShippingLine;
import asia.covisoft.bsandroid.wc.presenter.order.OrderPresenter;

public class PaymentActivity extends BaseLoadingActivity {

    @Override
    public int getView() {
        return R.layout.activity_payment;
    }

    private TextView tvTotal;

    @Override
    public void initView() {
        super.initView();

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Order order = new Order();
                Billing billing = new Billing();
                billing.setAddress1(adddress);
                billing.setCity("Ho Chi Minh");
                billing.setCountry("VN");
                billing.setEmail(email);
                billing.setFirstName(firstName);
                billing.setLastName(lastName);
                billing.setPhone(phone);
                billing.setPhone("700000");
                billing.setState("VN");
                order.setBilling(billing);
                order.setPaymentMethod("bacs");
                order.setPaymentMethodTitle("Direct Bank Transfer");
                order.setSetPaid(true);
                List<ShippingLine> shippingLines = new ArrayList<>();
                ShippingLine shippingLine = new ShippingLine();
                shippingLine.setMethodId("flat_rate");
                shippingLine.setMethodTitle("Flat Rate");
                shippingLine.setTotal(10L);
                order.setShippingLines(shippingLines);

                List<LineItem> lineItems = new ArrayList<>();
                for (OrderProduct orderProduct : OrderHelper.orderProducts.keySet()) {
                    LineItem item = new LineItem();
                    item.setProductId(orderProduct.getProduct().getId());
                    if (orderProduct.getVariation() != null) {
                        item.setVariationId(String.valueOf(orderProduct.getVariation().getId()));
                    }
                    item.setQuantity(OrderHelper.orderProducts.get(orderProduct));
                    lineItems.add(item);
                }
                order.setLineItems(lineItems);

                showLoading();
                OrderPresenter.createOrder(context, order, new OrderPresenter.CreateCallback() {
                    @Override
                    public void onResponse(asia.covisoft.bsandroid.wc.model.get.order.Order order) {

                        if (order != null) {

                            dismissLoading();
                            new AlertDialog.Builder(context)
                                    .setTitle(R.string.dialog_sendorder_title)
                                    .setMessage(R.string.dialog_senđorder_message)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.up_close, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            OrderHelper.orderProducts.clear();
                                            SystemHelper.restartApp(context);
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onConnectionTimeOut() {

                        dismissLoading();
                        ErrorDialog.showConnectionTimeOutDialog(context);
                    }
                });


            }
        });
    }

    public static final String LAST_NAME = "LAST_NAME";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String ADDRESS = "ADDRESS";

    private String lastName, firstName, email, phone, adddress;

    @Override
    public void initValue() {
        updateToolbar(getString(R.string.cap_payment_confirm), true);

        lastName = extras.getString(LAST_NAME);
        firstName = extras.getString(FIRST_NAME);
        email = extras.getString(EMAIL);
        phone = extras.getString(PHONE);
        adddress = extras.getString(ADDRESS);
    }

    @Override
    public void initAction() {

        tvTotal.setText(OrderHelper.getTotalCost(context));
    }
}
