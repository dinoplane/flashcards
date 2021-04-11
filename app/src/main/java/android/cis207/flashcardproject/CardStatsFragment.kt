/*
    CardStatsFragment.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val ARG_CARD = "flashcard"

class CardStatsFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cardString = arguments?.getSerializable(ARG_CARD) as String
        val stringLists = cardString.split('/')
        val numWrong = stringLists[0].toInt()
        val numAsked = stringLists[1].toInt()
        return CardStatsDialog(
            requireContext(),
            numWrong,
            numAsked)
    }

    companion object {
        fun newInstance(cardStats: String): CardStatsFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CARD, cardStats)
            }
            return CardStatsFragment().apply {
                arguments = args
            }
        }
    }
}