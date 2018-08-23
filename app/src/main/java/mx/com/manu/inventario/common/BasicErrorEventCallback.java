package mx.com.manu.inventario.common;

public interface BasicErrorEventCallback {
    void onSuccess();
    void onError(int typeEvent,int resMsg);

}
