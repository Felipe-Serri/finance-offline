package com.felipeserri.financeoffline.data.local.database

import androidx.room.TypeConverter
import com.felipeserri.financeoffline.domain.model.SyncStatus
import com.felipeserri.financeoffline.domain.model.TransactionType

class Converters {

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String = value.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)

    @TypeConverter
    fun fromSyncStatus(value: SyncStatus): String = value.name

    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus = SyncStatus.valueOf(value)
}