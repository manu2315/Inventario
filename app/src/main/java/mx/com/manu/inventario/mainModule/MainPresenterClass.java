package mx.com.manu.inventario.mainModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import mx.com.manu.inventario.common.pojo.Product;
import mx.com.manu.inventario.mainModule.events.MainEvent;
import mx.com.manu.inventario.mainModule.model.MainInteractor;
import mx.com.manu.inventario.mainModule.model.MainInteractorClass;
import mx.com.manu.inventario.mainModule.view.MainView;

public class MainPresenterClass implements MainPresenter {

    private MainView mView;
    private MainInteractor mInteractor;

    public MainPresenterClass(MainView mView){
        this.mView=mView;
        //Inicializamos minteractor con la clase existente
        this.mInteractor= new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mInteractor.unsubscribeToProducts();
    }

    @Override
    public void onResume() {
        mInteractor.subscribeToProducts();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void remove(Product product) {
        if(setProgress()){
            mInteractor.removeProduct(product);
        }
    }

    private boolean setProgress() {
        if(mView!= null){
            mView.showProgress();
            return true;
        }
        return false;
    }


    //Vital usar @Subscribe
    @Subscribe
    @Override
    public void onEventListener(MainEvent event) {

        //Verificar la operacion si es un fragment y el usuario decide cancelar el fragment
        if(mView!= null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case MainEvent.SUCCESS_ADD:
                    mView.add(event.getProduct());
                    break;
                case MainEvent.SUCCESS_UPDATE:
                    mView.update(event.getProduct());
                    break;
                case MainEvent.SUCCESS_REMOVE:
                    mView.remove(event.getProduct());
                    break;
                case MainEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());
                    break;
                case MainEvent.ERROR_TO_REMOVE:
                    mView.removeFail();
                    break;

            }
        }
    }
}
