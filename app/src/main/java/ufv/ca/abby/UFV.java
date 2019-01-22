package ufv.ca.abby;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Vrushabh on 17-06-2017.
 */

public class UFV extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);


    }
}
