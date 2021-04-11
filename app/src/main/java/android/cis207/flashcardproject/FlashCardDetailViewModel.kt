/*
    FlashCardDetailViewModel.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class FlashCardDetailViewModel() : ViewModel() {
    private val cardRepository = FlashCardRepository.get()
    private val cardIdLiveData = MutableLiveData<UUID>()

    var cardLiveData: LiveData<FlashCard?> =
        Transformations.switchMap(cardIdLiveData) { cardId ->
            cardRepository.getCard(cardId)
        }

    fun loadCard(cardId: UUID) {
        cardIdLiveData.value = cardId
    }

    fun saveCard(card: FlashCard) {
        cardRepository.updateCard(card)
    }
}