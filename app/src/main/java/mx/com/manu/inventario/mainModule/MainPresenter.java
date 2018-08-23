package mx.com.manu.inventario.mainModule;

import mx.com.manu.inventario.common.pojo.Product;
import mx.com.manu.inventario.mainModule.events.MainEvent;

public interface MainPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void remove(Product product);
    void onEventListener(MainEvent event);
}
