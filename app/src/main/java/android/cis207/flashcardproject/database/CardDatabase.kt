/*
    CardDatabase.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject.database

import android.cis207.flashcardproject.FlashCard
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FlashCard::class ], version=1)
@TypeConverters(CardTypeConverters::class)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}