/*
    FlashCardApplication.kt
    Final Project / CIS 207
    Cuesta College / R. Scovil
    Arrian Chi / arrian_chi@my.cuesta.edu
*/
package android.cis207.flashcardproject

import android.app.Application

class FlashCardApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FlashCardRepository.initialize(this)
    }
}