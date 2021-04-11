/*
    CardStatsDialog.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.card_stats_dialog.*

private const val TAG = "CardStatsDialog"

class CardStatsDialog(context: Context, private val numWrong: Int, private val numAsked: Int) : Dialog(context)  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.card_stats_dialog)

        stat_wrong.text = context.getString(R.string.stat_wrong, numWrong)
        stat_asked.text = context.getString(R.string.stat_asked, numAsked)
        if (numAsked == 0) {
            stat_percent.text = context.getString(R.string.no_percent)
        } else{
            val percent =  100.0 * (1.00 - numWrong.toDouble() / numAsked)
            stat_percent.text = context.getString(R.string.stat_percent, percent)
        }

        ok_button.setOnClickListener{view: View ->
            dismiss()
        }
    }
}