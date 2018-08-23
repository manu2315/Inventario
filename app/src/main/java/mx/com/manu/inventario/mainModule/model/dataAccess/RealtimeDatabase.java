package mx.com.manu.inventario.mainModule.model.dataAccess;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import mx.com.manu.inventario.R;
import mx.com.manu.inventario.common.BasicErrorEventCallback;
import mx.com.manu.inventario.common.pojo.Product;
import mx.com.manu.inventario.common.pojo.model.dataAccess.FireBaseRealtimeDatabaseAPI;
import mx.com.manu.inventario.mainModule.events.MainEvent;
import mx.com.manu.inventario.mainModule.model.dataAccess.ProductsEventListener;

public class RealtimeDatabase {
    private static final String PATH_PRODUCTS = "products";

    private FireBaseRealtimeDatabaseAPI mDatabaseAPI;
    private ChildEventListener mProductChildEventListener;

    public RealtimeDatabase(){
        mDatabaseAPI=FireBaseRealtimeDatabaseAPI.getInstance();

    }
    private DatabaseReference getProductReference(){
        return mDatabaseAPI.getmReference().child(PATH_PRODUCTS);
    }

    public void subscribeToProducts(final ProductsEventListener listener){

        if(mProductChildEventListener==null){
            mProductChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listener.onChildAdded(getProduct(dataSnapshot));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    listener.onChildUpdated(getProduct(dataSnapshot));
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    listener.onChildRemoved(getProduct(dataSnapshot));
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    switch (databaseError.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            listener.onError(R.string.main_error_permission_denied);
                            break;
                        default:
                            listener.onError(R.string.main_error_server);
                            break;
                    }
                }
            };
        }
        getProductReference().addChildEventListener(mProductChildEventListener);
    }

    private Product getProduct(DataSnapshot dataSnapshot) {
        Product product = dataSnapshot.getValue(Product.class);
        if (product!=null){
            product.setId(dataSnapshot.getKey());
        }
        return product;
    }
    public void unsubscribeToProducts(){
        //evitamos que se consuma una conexion
        if(mProductChildEventListener!=null){
            getProductReference().removeEventListener(mProductChildEventListener);
        }
    }
    public void removeProduct(Product product, final BasicErrorEventCallback callback){
        getProductReference().child(product.getId())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null){
                            callback.onSuccess();
                        }else{
                            switch (databaseError.getCode()){
                                case DatabaseError.PERMISSION_DENIED:
                                    callback.onError(MainEvent.ERROR_TO_REMOVE,R.string.main_error_remove);
                                    break;
                                default:
                                    callback.onError(MainEvent.ERROR_SERVER,R.string.main_error_server);
                                    break;
                            }
                        }
                    }
                });
    }
}
