package mx.com.manu.inventario.mainModule.view;

import mx.com.manu.inventario.common.pojo.Product;

public interface MainView {
    void showProgress();
    void hideProgress();

    void add(Product product);
    void update(Product product);
    void remove(Product product);

    void removeFail();
    void onShowError(int resMsg);

}
