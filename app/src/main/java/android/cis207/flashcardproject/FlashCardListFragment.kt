/*
    FlashCardListFragment.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/

package android.cis207.flashcardproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "FlashCardListFragment"

class FlashCardListFragment: Fragment() {

    interface Callbacks {
        fun onCardSelected(cardId: UUID)
        fun onPracticeStarted()
    }
    private var callbacks: Callbacks? = null

    private lateinit var cardRecyclerView: RecyclerView
    private var adapter: CardAdapter? = CardAdapter(emptyList())

    private val cardListViewModel: FlashCardListViewModel by lazy {
        ViewModelProviders.of(this).get(FlashCardListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcard_list,
            container, false)
        cardRecyclerView =
            view.findViewById(R.id.card_recycler_view) as
                    RecyclerView
        cardRecyclerView.layoutManager =
            LinearLayoutManager(context)

        cardRecyclerView.adapter = adapter
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardListViewModel.cardListLiveData.observe(
            viewLifecycleOwner,
            Observer { cards ->
                cards?.let {
                    updateUI(cards)
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_flashcard_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_card -> {
                val card = FlashCard()
                cardListViewModel.addCard(card)
                callbacks?.onCardSelected(card.id)
                true
            }

            R.id.practice -> {
                if (adapter?.cards?.let { it.size >= 2 } == true){
                    callbacks?.onPracticeStarted()
                }
                else{
                    val message = "You need at least 2 cards created to practice!"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(cards: List<FlashCard>) {
        //val cards = cardListViewModel.cards
        adapter = CardAdapter(cards)
        cardRecyclerView.adapter = adapter
    }

    private inner class CardHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var card : FlashCard

        private val questionTextView: TextView =
            itemView.findViewById(R.id.card_question)
        private val answerTextView: TextView =
            itemView.findViewById(R.id.card_answer)
        private val solvedImageView: ImageView =
            itemView.findViewById(R.id.card_starred)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(card: FlashCard) {
            this.card = card
            questionTextView.text = this.card.question
            answerTextView.text = this.card.answer
            solvedImageView.visibility = if (this.card.isStarred) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View) {
            callbacks?.onCardSelected(card.id)
        }

    }

    private inner class CardAdapter(var cards: List<FlashCard>)
        :RecyclerView.Adapter<CardHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            val view =
                layoutInflater.inflate(R.layout.list_item_flashcard, parent, false)
            return CardHolder(view)
        }

        override fun getItemCount() = cards.size

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            val card = cards[position]
            holder.bind(card)
        }

    }

    companion object {
        fun newInstance(): FlashCardListFragment {
            return FlashCardListFragment()
        }
    }
}