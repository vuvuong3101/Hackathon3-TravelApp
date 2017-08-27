package vu.travelapp.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by trongphuong1011 on 8/27/2017.
 */

public class DataApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
