package com.maxab.baseproject.helpers


import atiaf.redstone.Models.UserModel
import com.maxab.baseproject.Models.UserRealm

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException


object RealmController {
    var realm = try {
        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }

    //clear all objects from ProductRealm.class
    fun clearAll() {

        realm.beginTransaction()

        val userRealm = realm.where(UserRealm::class.java!!).findAll()

        if (userRealm != null)
            while (userRealm.size != 0) {
                userRealm.get(0)!!.deleteFromRealm()
            }
        realm.commitTransaction()
    }

    fun isExist(id: String): Boolean {

        val userRealm = realm.where(UserRealm::class.java!!).equalTo("id", id).findFirst()

        return userRealm != null
    }


    fun remove(id: String) {
        realm.beginTransaction()

        val userRealm = realm.where(UserRealm::class.java!!).equalTo("id", id).findAll()

        if (userRealm != null)
            while (userRealm.size != 0) {
                userRealm.get(0)!!.deleteFromRealm()
            }
        realm.commitTransaction()
    }

    fun addUser(userModel: UserModel) {

        realm.beginTransaction()
        val userRealm = realm.createObject(UserRealm::class.java!!, userModel.id)
        realm.commitTransaction()

    }

    fun updateUser(userModel: UserModel) {

        realm.beginTransaction()

        val UserRealm = realm.where(UserRealm::class.java!!).equalTo("id", userModel.id).findFirst()
        realm.commitTransaction()

    }

}
