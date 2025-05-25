package com.example.trueffel_app.models

import android.content.Context
import com.example.trueffel_app.R
import org.xmlpull.v1.XmlPullParser

data class Toasts(
    val text: String
)




fun readToastsFromXML(context: Context): List<String> {
    val toasts = mutableListOf<String>()
    val parser = context.resources.getXml(R.xml.toasts)

    var eventType = parser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "toast") {
            toasts.add(parser.nextText())
        }
        eventType = parser.next()
    }
    return toasts
}

