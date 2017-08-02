package asia.covisoft.miali;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import asia.covisoft.miali.mvp.model.coupon.Coupon;
import asia.covisoft.miali.mvp.model.product.category.ProductCategory;
import asia.covisoft.miali.mvp.presenter.CouponPresenter;
import asia.covisoft.miali.mvp.presenter.product.ProductCategoryPresenter;
import asia.covisoft.miali.utils.WCUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WCUtils.WCAuthenticate();

//        CouponPresenter.listAll(new CouponPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<Coupon> coupons) {
//
//                Log.d(TAG, "onResponse");
//
//                for (Coupon c : coupons) {
//                    CouponPresenter.retrieve(c.getId(), new CouponPresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(Coupon coupon) {
//
//                            Log.d(TAG, "onResponse");
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });

//        CustomerPresenter.listAll(new CustomerPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<Customer> customers) {
//
//                Log.d(TAG, "onResponse");
//
//                for (Customer c : customers) {
//
//                    CustomerPresenter.retrieve(c.getId(), new CustomerPresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(Customer customer) {
//
//                            Log.d(TAG, "onResponse");
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });


//        OrderPresenter.listAll(new OrderPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<Order> orders) {
//
//                Log.d(TAG, "onResponse");
//
//                for (final Order o : orders) {
//
////                    OrderPresenter.retrieve(o.getId(), new OrderPresenter.RetrieveCallback() {
////                        @Override
////                        public void onResponse(Order order) {
////
////                            Log.d(TAG, "onResponse");
////                        }
////
////                        @Override
////                        public void onConnectionTimeOut() {
////
////                        }
////                    });
//
////                    OrderNotePresenter.listAll(o.getId(), new OrderNotePresenter.ListAllCallback() {
////                        @Override
////                        public void onResponse(List<OrderNote> orderNotes) {
////
////                            for (OrderNote on : orderNotes) {
////
////                                OrderNotePresenter.retrieve(o.getId(), on.getId(), new OrderNotePresenter.RetrieveCallback() {
////                                    @Override
////                                    public void onResponse(OrderNote orderNote) {
////
////                                        Log.d(TAG, "onResponse");
////                                    }
////
////                                    @Override
////                                    public void onConnectionTimeOut() {
////
////                                    }
////                                });
////                            }
////                        }
////
////                        @Override
////                        public void onConnectionTimeOut() {
////
////                        }
////                    });
//
////                    RefundPresenter.listAll(o.getId(), new RefundPresenter.ListAllCallback() {
////                        @Override
////                        public void onResponse(List<Refund> refunds) {
////
////                            for (Refund r : refunds) {
////
////                                RefundPresenter.retrieve(o.getId(), r.getId(), new RefundPresenter.RetrieveCallback() {
////                                    @Override
////                                    public void onResponse(Refund refund) {
////
////                                        Log.d(TAG, "onResponse");
////                                    }
////
////                                    @Override
////                                    public void onConnectionTimeOut() {
////
////                                    }
////                                });
////                            }
////                        }
////
////                        @Override
////                        public void onConnectionTimeOut() {
////
////                        }
////                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });

        /**
         * Sample API
         * List all products
         */
//        ProductPresenter.listAll(new ProductPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<Product> products) {
//
//                Log.d(TAG, "onResponse");
//
//                for (Product p : products) {
//                    ProductPresenter.retrieve(p.getId(), new ProductPresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(Product product) {
//
//                            Log.d(TAG, "onResponse");
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });


//        ProductPresenter.listAll(new ProductPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<Product> products) {
//
//                for (Product p : products) {
//
//                    ProductPresenter.retrieve(p.getId(), new ProductPresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(Product product) {
//
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });
//    }


//        ProductAttributePresenter.listAll(new ProductAttributePresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<ProductAttribute> productAttributes) {
//
//                Log.d(TAG, "onResponse");
//
//                for(final ProductAttribute pa: productAttributes){
//
//                    ProductAttributePresenter.retrieve(pa.getId(), new ProductAttributePresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(ProductAttribute productAttribute) {
//
//                            Log.d(TAG, "onResponse");
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//
//                    ProductAttributePresenter.retrieve(pa.getId(), new ProductAttributePresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(ProductAttribute productAttribute) {
//
//                            Log.d(TAG, "onResponse");
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//
//                    ProductAttributeTermPresenter.listAll(pa.getId(), new ProductAttributeTermPresenter.ListAllCallback() {
//                        @Override
//                        public void onResponse(List<ProductAttributeTerm> productAttributeTerms) {
//
//                            Log.d(TAG, "onResponse");
//
//                            for(ProductAttributeTerm pat: productAttributeTerms){
//
//                                ProductAttributeTermPresenter.retrieve(pa.getId(), pat.getId(), new ProductAttributeTermPresenter.RetrieveCallback() {
//                                    @Override
//                                    public void onResponse(ProductAttributeTerm productAttributeTerm) {
//
//                                        Log.d(TAG, "onResponse");
//                                    }
//
//                                    @Override
//                                    public void onConnectionTimeOut() {
//
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });


        //TODO
        ProductCategoryPresenter.listAll(new ProductCategoryPresenter.ListAllCallback() {
            @Override
            public void onResponse(List<ProductCategory> productCategories) {

                Log.d(TAG, "onResponse");

                for (ProductCategory pc : productCategories) {

                    ProductCategoryPresenter.retrieve(pc.getId(), new ProductCategoryPresenter.RetrieveCallback() {
                        @Override
                        public void onResponse(ProductCategory productCategory) {

                            Log.d(TAG, "onResponse");
                        }

                        @Override
                        public void onConnectionTimeOut() {

                        }
                    });
                }
            }

            @Override
            public void onConnectionTimeOut() {

            }
        });


//        ProductShippingClassPresenter.listAll(new ProductShippingClassPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<ProductShippingClass> productShippingClasses) {
//
//                Log.d(TAG, "onResponse");
//
//                for (ProductShippingClass psc : productShippingClasses) {
//
//                    ProductShippingClassPresenter.retrieve(psc.getId(), new ProductShippingClassPresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(ProductShippingClass productShippingClass) {
//
//                            Log.d(TAG, "onResponse");
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });


//        TaxRatePresenter.listAll(new TaxRatePresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<TaxRate> taxRates) {
//
//                Log.d(TAG, "onResponse");
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });
//
//        TaxClassPresenter.listAll(new TaxClassPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<TaxClass> taxClasses) {
//
//                Log.d(TAG, "onResponse");
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });


        //TODO
//        WebhookPresenter.listAll(new WebhookPresenter.ListAllCallback() {
//            @Override
//            public void onResponse(List<Webhook> webhooks) {
//
//                Log.d(TAG, "onResponse");
//
//                for(Webhook w : webhooks){
//
//                    WebhookPresenter.retrieve(w.getId(), new WebhookPresenter.RetrieveCallback() {
//                        @Override
//                        public void onResponse(Webhook webhook) {
//
//                            Log.d(TAG, "onResponse");
//                        }
//
//                        @Override
//                        public void onConnectionTimeOut() {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionTimeOut() {
//
//            }
//        });
    }
}

