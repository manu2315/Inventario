package mx.com.manu.inventario.mainModule.model.dataAccess;

import mx.com.manu.inventario.common.pojo.Product;

public interface ProductsEventListener {
    void onChildAdded(Product product);
    void onChildUpdated(Product product);
    void onChildRemoved(Product product);

    void onError(int resMsg);
}
