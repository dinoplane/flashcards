/*
    CardDao.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject.database

import android.cis207.flashcardproject.FlashCard
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface CardDao {
    @Query("SELECT * FROM flashcard")
    fun getCards(): LiveData<List<FlashCard>>

    @Query("SELECT * FROM flashcard WHERE id=(:id)")
    fun getCard(id: UUID): LiveData<FlashCard?>

    @Query("SELECT id FROM flashcard")
    fun getIds(): LiveData<List<UUID>>

    @Query("SELECT id FROM flashcard WHERE isStarred=(1)")
    fun getStarred(): LiveData<List<UUID>>

    @Update
    fun updateCard(card: FlashCard)

    @Insert
    fun addCard(card: FlashCard)
}