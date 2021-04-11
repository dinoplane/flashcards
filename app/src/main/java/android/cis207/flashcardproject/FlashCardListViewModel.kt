/*
    FlashCardListViewModel.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import androidx.lifecycle.ViewModel

class FlashCardListViewModel : ViewModel() {

    private val cardRepository = FlashCardRepository.get()
    val cardListLiveData = cardRepository.getCards()

    fun addCard(card: FlashCard) {
        cardRepository.addCard(card)
    }
}