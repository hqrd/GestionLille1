package com.tac.hqrd.gestionlille1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Issue(
    @PrimaryKey private var uid: Long,
    var type: String,
    var latGps: Float,
    var longGps: Float
) {
    var adress: String = ""
    var description: String = ""

    override fun toString(): String {
        return "Issue(uid=$uid, type='$type', latGps=$latGps, longGps=$longGps, adress='$adress', description='$description')"
    }


}