/*
    CardHelper.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

const val THRESHOLD = 0.66

class CardHelper(private var numWrong: Int = 0,
                 private var numAsked: Int = 0) {
    fun update(isWrong: Boolean) {
        numAsked += 1
        if (isWrong) numWrong += 1
    }

    fun getNumWrong(): Int {
        return numWrong
    }

    fun getNumAsked(): Int {
        return numAsked
    }

    override fun toString() : String {
        return "$numWrong/$numAsked"
    }

    fun setNumWrong(num: Int) {
        numWrong = num
    }

    fun setNumAsked(num: Int) {
        numAsked = num
    }
}
