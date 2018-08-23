package mx.com.manu.inventario.common.pojo.model.dataAccess;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseRealtimeDatabaseAPI {
    private DatabaseReference mReference;

    private static FireBaseRealtimeDatabaseAPI INSTANCE=null;

    private FireBaseRealtimeDatabaseAPI() {
        mReference= FirebaseDatabase.getInstance().getReference();
    }

    public static FireBaseRealtimeDatabaseAPI getInstance(){
        if(INSTANCE==null){
            INSTANCE= new FireBaseRealtimeDatabaseAPI();
        }
        return INSTANCE;
    }

    //Referencias
    public DatabaseReference getmReference(){
        return mReference;
    }
}
