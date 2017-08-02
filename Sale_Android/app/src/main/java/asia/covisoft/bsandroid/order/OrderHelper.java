package asia.covisoft.bsandroid.order;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.utils.NumberFormatter;
import asia.covisoft.bsandroid.wc.model.get.customer.Customer;

/**
 * Created by Leon on 3/6/2017.
 */

public class OrderHelper {

    public static Customer customer;

    public static Map<OrderProduct, Integer> orderProducts;

    public static void addCart(OrderProduct orderProduct, int quantity) {

        if (orderProducts == null) {
            orderProducts = new HashMap<>();
        }

//        if (orderProducts.get(orderProduct) == null || orderProducts.get(orderProduct) == 0) {
//            orderProducts.put(orderProduct, quantity);
//        } else {
//            quantity += orderProducts.get(orderProduct);
//            orderProducts.put(orderProduct, quantity);
//        }

        for (OrderProduct keyOrderProduct : orderProducts.keySet()) {

            if (keyOrderProduct.getProduct().getId().equals(orderProduct.getProduct().getId())) {

                if ((keyOrderProduct.getVariation() == null && orderProduct.getVariation() == null) ||
                        keyOrderProduct.getVariation().getId().equals(orderProduct.getVariation().getId())) {

                    quantity += orderProducts.get(keyOrderProduct);
                    orderProducts.put(keyOrderProduct, quantity);
                    return;
                }
            }
        }
        orderProducts.put(orderProduct, quantity);
    }

    public static String getTotalCost(Context context) {

        return NumberFormatter.formatCurrency(getTotalCost()) + " " + context.getString(R.string.unit_vnd);
    }

    public static long getTotalCost() {

        long total = 0;
        if (orderProducts != null) {

            for (OrderProduct orderProduct : orderProducts.keySet()) {

                String priceStr;
                if (orderProduct.getVariation() != null) {
                    priceStr = orderProduct.getVariation().getPrice();
                } else {
                    priceStr = orderProduct.getProduct().getPrice();
                }
                long price = Long.parseLong(priceStr) * orderProducts.get(orderProduct);
                total += price;
            }
        }
        return total;
    }
}
