package com.example.trueffel_app.repository

import android.content.res.Resources
import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import androidx.lifecycle.ViewModel
import com.example.trueffel_app.R

class ToastViewModel : ViewModel() {

    var currentToast = "Heute saufen wir richtig einen,... oder?"


    private var toasts = mutableListOf(
        "Das ist der erste Toast.",
        "Ein weiterer Toast erscheint.",
        "XML kann für gespeicherte Daten genutzt werden.",
        "XML kann für gespeicherte Daten genutzt werden. XML kann für gespeicherte Daten genutzt werden. XML kann für gespeicherte Daten genutzt werden. XML kann für gespeicherte Daten genutzt werden."
    )

    fun getRandomToast(): String? {
        return if (toasts.isNotEmpty()) {
            val toast = toasts.random()
            toasts.remove(toast)
            toast
        } else {
            "Alle Sprüche sind gesprochen. Ich hoffe ihr seid schon bei Marv im Garten angekrochen."
        }
    }

    fun resetToasts(){
        this.toasts = mutableListOf(
            "Das ist der erste Toast.",
            "Ein weiterer Toast erscheint.",
            "XML kann für gespeicherte Daten genutzt werden.",
            "XML kann für gespeicherte Daten genutzt werden. XML kann für gespeicherte Daten genutzt werden. XML kann für gespeicherte Daten genutzt werden. XML kann für gespeicherte Daten genutzt werden."
        )
        this.currentToast = "Also auf ein Neues. Ein Frohes Neues!"
    }
}