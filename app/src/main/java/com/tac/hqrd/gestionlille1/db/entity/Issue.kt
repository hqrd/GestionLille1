package com.tac.hqrd.gestionlille1.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tac.hqrd.gestionlille1.db.IssueType

@Entity
data class Issue(
    var type: IssueType,
    var latGps: Float,
    var longGps: Float
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
    var adress: String = ""
    var description: String = ""

    override fun toString(): String {
        return "Issue(uid=$uid, stringType='$type', latGps=$latGps, longGps=$longGps, adress='$adress', description='$description')"
    }


}