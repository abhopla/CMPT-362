package com.veryfi.lens.receipts.demo

import android.app.Application
import com.veryfi.lens.VeryfiLens
import com.veryfi.lens.VeryfiLensCredentials
import com.veryfi.lens.VeryfiLensSettings
import com.veryfi.lens.helpers.DocumentType

class Application: Application() {

    companion object {
        //REPLACE YOUR KEYS HERE
        const val CLIENT_ID = "vrfsFgmce3rPoR6P6w5mxDggngzrRdVwzdWo7xp"
        const val AUTH_USERNAME = "tlaruddo2"
        const val AUTH_API_KEY = "cf515870c4360221f22cc82a02a1dd2f"
        const val URL = "https://api.verify.com/"
    }

    override fun onCreate() {
        super.onCreate()
        //set credentials
        val veryfiLensCredentials = VeryfiLensCredentials()
        veryfiLensCredentials.clientId = CLIENT_ID
        veryfiLensCredentials.username = AUTH_USERNAME
        veryfiLensCredentials.apiKey = AUTH_API_KEY
        veryfiLensCredentials.url = URL

        //optional settings
        val veryfiLensSettings = VeryfiLensSettings()
        veryfiLensSettings.documentTypes = arrayListOf(DocumentType.RECEIPT)
        veryfiLensSettings.showDocumentTypes = true

        //configure lens
        VeryfiLens.configure(this, veryfiLensCredentials, veryfiLensSettings)
    }
}