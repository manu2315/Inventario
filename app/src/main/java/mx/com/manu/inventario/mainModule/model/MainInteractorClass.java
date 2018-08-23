package mx.com.manu.inventario.mainModule.model;

import org.greenrobot.eventbus.EventBus;

import mx.com.manu.inventario.common.BasicErrorEventCallback;
import mx.com.manu.inventario.common.pojo.Product;
import mx.com.manu.inventario.mainModule.events.MainEvent;
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
                post(product, MainEvent.SUCCESS_ADD);
            }

            @Override
            public void onChildUpdated(Product product) {
                post(product,MainEvent.SUCCESS_UPDATE);
            }

            @Override
            public void onChildRemoved(Product product) {
                post(product,MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int resMsg) {
                post(MainEvent.ERROR_SERVER,resMsg);
            }
        });
    }

    @Override
    public void unsubscribeToProducts() {
        mDatabase.unsubscribeToProducts();
    }

    @Override
    public void removeProduct(Product product) {
        mDatabase.removeProduct(product, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    private void post(int typeEvent){
        post(null,typeEvent,0);
    }
    private void post(int typeEvent,int resMsg){
        post(null,typeEvent,resMsg);
    }

    private void post(Product product, int typeEvent){
        post(product,typeEvent,0);
    }
    private void post(Product product, int typeEvent, int resMsg) {
        MainEvent event = new MainEvent();
        event.setProduct(product);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);

    }
}
