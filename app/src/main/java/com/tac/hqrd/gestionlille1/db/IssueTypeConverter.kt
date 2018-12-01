package com.tac.hqrd.gestionlille1.db

import androidx.room.TypeConverter

class IssueTypeConverter {

    @TypeConverter
    fun toStatus(type: String): IssueType {
        return IssueType.valueOf(type)
    }

    @TypeConverter
    fun toString(type: IssueType): String? {
        return type.toString()
    }
}