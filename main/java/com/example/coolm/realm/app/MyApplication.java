package com.example.coolm.realm.app;



import io.realm.Realm;
import io.realm.RealmConfiguration;
import android.app.Application;

/**
 * Created by coolm on 10/17/2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}