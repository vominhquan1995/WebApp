package asia.covisoft.bsandroid.order;

import asia.covisoft.bsandroid.wc.model.get.product.Product;
import asia.covisoft.bsandroid.wc.model.get.product.Variation;

/**
 * Created by Leon on 4/13/2017.
 */

public class OrderProduct {

    private Product product;
    private Variation variation;

    public OrderProduct(Product product, Variation variation) {
        this.product = product;
        this.variation = variation;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Variation getVariation() {
        return variation;
    }

    public void setVariation(Variation variation) {
        this.variation = variation;
    }
}
