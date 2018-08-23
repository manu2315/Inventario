package mx.com.manu.inventario.mainModule.model;

import mx.com.manu.inventario.common.pojo.Product;

public interface MainInteractor {
    void subscribeToProducts();
    void unsubscribeToProducts();

    void removeProduct(Product product);

}
