package com.tac.hqrd.gestionlille1.model

class Issue(
    private var id: Long,
    private var type: String,
    private var latGps: Float,
    private var longGps: Float
) {
    private var adress: String = ""
    private var description: String = ""

    override fun toString(): String {
        return "Issue(id=$id, type='$type', latGps=$latGps, longGps=$longGps, adress='$adress', description='$description')"
    }


}