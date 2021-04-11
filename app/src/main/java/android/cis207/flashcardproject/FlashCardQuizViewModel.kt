/*
    FlashCardQuizViewModel.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

private const val TAG = "FlashCardQuizViewModel"

private const val TOTAL_STARRED_IDS = 5

class FlashCardQuizViewModel : ViewModel() {
    private val cardRepository = FlashCardRepository.get()
    var cardIdListLiveData: LiveData<List<UUID>> = cardRepository.getIds()
    var cardIdStarredListLiveData: LiveData<List<UUID>> = cardRepository.getStarred()
    var deck : MutableList<UUID> = arrayListOf()

    private var currIndex = loadNextIndex()

    private val cardIdLiveData = MutableLiveData<UUID>()

    var cardLiveData: LiveData<FlashCard?> =
        Transformations.switchMap(cardIdLiveData) { cardId ->
            cardRepository.getCard(cardId)
        }

    fun FlashCardQuizViewModel(){

    }



    fun loadCard() {
        cardIdLiveData.value = deck.get(loadNextIndex())
    }

    fun loadNextIndex() : Int{
        val ret = try { (0 until deck.size).random() } catch (e: Exception) {return 0}
        return ret
    }

    fun saveCard(card: FlashCard) {
        cardRepository.updateCard(card)
    }
}