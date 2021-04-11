/*
    FlashCardFragment.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.
import java.util.UUID

private const val TAG = "CardFragment"

private const val ARG_CARD_ID = "card_id"

private const val ARG_CARD = "flashcard"

class FlashCardFragment : Fragment() {

    private lateinit var flashCard: FlashCard
    private lateinit var questionField: EditText
    private lateinit var answerField: EditText
    private lateinit var statsButton: Button
    private lateinit var starredCheckBox: CheckBox
    private val cardDetailViewModel: FlashCardDetailViewModel by lazy {
        ViewModelProvider(this).get(FlashCardDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flashCard = FlashCard()
        val cardId: UUID = arguments?.getSerializable(ARG_CARD_ID)
                as UUID
        cardDetailViewModel.loadCard(cardId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcard, container, false)

        questionField = view.findViewById(R.id.card_question) as EditText
        answerField = view.findViewById(R.id.card_answer) as EditText
        starredCheckBox = view.findViewById(R.id.card_starred) as CheckBox
        statsButton = view.findViewById(R.id.stats_button) as Button

        statsButton.apply {
            isEnabled = true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardDetailViewModel.cardLiveData.observe(
            viewLifecycleOwner,
            Observer { flashCard ->
                flashCard?.let {
                    this.flashCard = flashCard
                    updateUI()
                }
            })
        }


    override fun onStart() {
        super.onStart()

        val questionWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                flashCard.question = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        questionField.addTextChangedListener(questionWatcher)

        val answerWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                flashCard.answer = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        answerField.addTextChangedListener(answerWatcher)

        starredCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                flashCard.isStarred = isChecked
            }
        }

        statsButton.setOnClickListener{
            CardStatsFragment.newInstance(flashCard.cardStats.toString()).apply{
                show(this@FlashCardFragment.requireFragmentManager(), ARG_CARD)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        cardDetailViewModel.saveCard(flashCard)
    }

    private fun updateUI() {
        questionField.setText(flashCard.question)
        answerField.setText(flashCard.answer)
        starredCheckBox.apply {
            isChecked = flashCard.isStarred
            jumpDrawablesToCurrentState()
        }
    }

    companion object {
        fun newInstance(cardId: UUID): FlashCardFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CARD_ID, cardId)
            }
            return FlashCardFragment().apply {
                arguments = args
            }
        }
    }
}