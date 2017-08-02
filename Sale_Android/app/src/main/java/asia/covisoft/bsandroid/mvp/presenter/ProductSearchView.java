package asia.covisoft.bsandroid.mvp.presenter;

import java.util.List;

import asia.covisoft.bsandroid.mvp.view.ConnectionCallback;
import asia.covisoft.bsandroid.wc.model.get.product.Product;

/**
 * Created by Leon on 4/19/2017.
 */

public interface ProductSearchView extends ConnectionCallback{

    void onPreSearch();

    void onProductResult(List<Product> products);
}
