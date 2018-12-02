package com.tac.hqrd.gestionlille1.db

import java.util.*


enum class IssueType(val stringType: String) {
    TREE_TO_TRIM("Arbre à tailler"),
    TREE_TO_CUT_DOWN("Arbre à abattre"),
    HEDGE_TO_TRIM("Haie à tailler"),
    TRASH("Détritus"),
    WEED("Mauvaise herbe"),
    OTHER("Autre");


    companion object {
        fun randomType(): IssueType {
            return values()[Random().nextInt(values().size)]
        }
    }

}