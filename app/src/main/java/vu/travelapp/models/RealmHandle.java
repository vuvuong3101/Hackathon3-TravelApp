package vu.travelapp.models;

import java.util.List;

import io.realm.Realm;

/**
 * Created by trongphuong1011 on 8/27/2017.
 */

public class RealmHandle {
    public static Realm realm = Realm.getDefaultInstance();

    public static void addMusicTypes(DataModel dataModel){
        realm.beginTransaction();
        realm.copyToRealm(dataModel);
        realm.commitTransaction();
    }

    public static List<DataModel> getDataModel(){
        return realm.where(DataModel.class).findAll();
    }
}
