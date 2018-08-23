package mx.com.manu.inventario.mainModule.model;

import mx.com.manu.inventario.common.pojo.Product;
import mx.com.manu.inventario.mainModule.model.dataAccess.ProductsEventListener;
import mx.com.manu.inventario.mainModule.model.dataAccess.RealtimeDatabase;

public class MainInteractorClass implements MainInteractor {
    private RealtimeDatabase mDatabase;

    public MainInteractorClass() {
        mDatabase= new RealtimeDatabase();
    }

    @Override
    public void subscribeToProducts() {
        mDatabase.subscribeToProducts(new ProductsEventListener() {
            @Override
            public void onChildAdded(Product product) {

            }

            @Override
            public void onChildUpdated(Product product) {

            }

            @Override
            public void onChildRemoved(Product product) {

            }

            @Override
            public void onError(int resMsg) {

            }
        });
    }

    @Override
    public void unsubscribeToProducts() {

    }

    @Override
    public void removeProduct(Product product) {

    }
}
