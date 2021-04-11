/*
    CardTypeConverters.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject.database

import android.cis207.flashcardproject.CardHelper
import android.util.Log
import androidx.room.TypeConverter
import java.util.*
private const val TAG = "TypeConverter"

class CardTypeConverters {
    @TypeConverter
    fun fromCardHelper(helper: CardHelper?): String? {
        return helper.toString()
    }
    @TypeConverter
    fun toCardHelper(convertedString: String): CardHelper? {
        var cardHelper = CardHelper()
        val stringLists = convertedString.split('/')
        cardHelper.setNumWrong(stringLists[0].toInt())
        cardHelper.setNumAsked(stringLists[1].toInt())
        return cardHelper
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        Log.d(TAG, "In Converions: ${uuid ?: "GOTCHA"}")

        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}