/*
    FlashCardRepository.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import android.cis207.flashcardproject.database.CardDatabase
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "card-database"

private const val TAG = "FlashCardRepository"

class FlashCardRepository private constructor(context: Context) {

    private val database : CardDatabase = Room.databaseBuilder(
        context.applicationContext,
        CardDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val cardDao = database.cardDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCards(): LiveData<List<FlashCard>> = cardDao.getCards()
    fun getCard(id: UUID): LiveData<FlashCard?> = cardDao.getCard(id)

    fun getIds(): LiveData<List<UUID>> = cardDao.getIds()
    fun getStarred(): LiveData<List<UUID>> = cardDao.getStarred()

    fun updateCard(card: FlashCard) {
        executor.execute {
            cardDao.updateCard(card)
        }
    }

    fun addCard(card: FlashCard) {
        executor.execute {
            cardDao.addCard(card)
        }
    }


    companion object {
        private var INSTANCE: FlashCardRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FlashCardRepository(context)
            }

        }

        fun get(): FlashCardRepository {
            return INSTANCE ?: throw IllegalStateException("CardRepository must be initialized")
        }
    }
}