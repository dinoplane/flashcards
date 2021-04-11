/*
    FlashCardQuizFragment.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.UUID

private const val TOTAL_STARRED_IDS = 5
private const val TAG = "FlashCardQuizFragment"

class FlashCardQuizFragment : Fragment() {
    private lateinit var currCard: FlashCard

    private lateinit var questionField: TextView
    private lateinit var answerField: EditText

    private val cardQuizViewModel: FlashCardQuizViewModel by lazy {
        ViewModelProvider(this).get(FlashCardQuizViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcard_quiz, container, false)

        cardQuizViewModel.cardIdListLiveData.observe(
            viewLifecycleOwner,
            Observer { ids ->
                ids?.let {
                    for (i in ids.indices) {
                        cardQuizViewModel.deck.add(ids[i])
                    }
                    cardQuizViewModel.loadCard()
                    cardQuizViewModel.cardIdListLiveData.removeObservers(viewLifecycleOwner)
                }
            }
        )

        cardQuizViewModel.cardIdStarredListLiveData.observe(
            viewLifecycleOwner,
            Observer { starred ->
                starred?.let {
                    if (starred.isNotEmpty()){
                        for (i in 1 until TOTAL_STARRED_IDS) {
                            for (j in starred.indices) {
                                cardQuizViewModel.deck.add(starred[j])
                            }
                        }
                        cardQuizViewModel.loadCard()
                    }
                    cardQuizViewModel.cardIdStarredListLiveData.removeObservers(viewLifecycleOwner)
                }
            }
        )

        cardQuizViewModel.cardLiveData.observe(
            viewLifecycleOwner,
            Observer { card ->
                card?.let {
                    currCard = card
                    updateUI()
                }
            }
        )

        questionField = view.findViewById(R.id.curr_question)
        answerField = view.findViewById(R.id.answer_box) as EditText
        return view
    }

    override fun onStart() {
        super.onStart()

        answerField.setOnEditorActionListener { v: TextView, actionId: Int, event: KeyEvent? ->
            var isWrong = true
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (answerField.text.toString() == currCard.answer){
                    Toast.makeText(context, "Nice!", Toast.LENGTH_SHORT).show()
                    isWrong = false
                }
                else{
                    Toast.makeText(context, "WRONG! The answer was: ${currCard.answer}", Toast.LENGTH_SHORT).show()
                }
                currCard.cardStats.update(isWrong)
                cardQuizViewModel.saveCard(currCard)

                cardQuizViewModel.loadCard()
            }
            true
        }
    }

    private fun updateUI() {
        questionField.text = currCard.question
        answerField.setText("")
    }

    companion object {
        fun newInstance(): FlashCardQuizFragment {
            return FlashCardQuizFragment()
        }
    }
}