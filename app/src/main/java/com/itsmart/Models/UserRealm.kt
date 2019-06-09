package com.itsmart.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass


@RealmClass
open class UserRealm : RealmObject() {
    @PrimaryKey
    var id: String = ""
}