/*
    FlashCard.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class FlashCard(@PrimaryKey val id: UUID = UUID.randomUUID(),
                     var question: String = "",
                     var answer: String = "",
                     var isStarred: Boolean = false,
                     var cardStats: CardHelper = CardHelper())

