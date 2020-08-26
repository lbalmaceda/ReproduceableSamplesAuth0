package com.lbalmaceda.a0.requiresauth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.BaseCallback
import com.auth0.android.result.Credentials
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var localCredentials: Credentials? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUi()
        loadCredentials()

        btn_finish.setOnClickListener { finish() }
    }

    private fun loadCredentials() {
        a0Manager(this)
            .getCredentials(object : BaseCallback<Credentials, CredentialsManagerException> {
                override fun onSuccess(credentials: Credentials?) {
                    localCredentials = credentials
                    runOnUiThread { updateUi() }
                }

                override fun onFailure(e: CredentialsManagerException) {
                    visibleLog(
                        "Error loading - Device compatible = ${!e.isDeviceIncompatible} - ${e.message}",
                        e.cause
                    )
                    finish()
                }
            })
    }

    private fun updateUi() {
        txt_id_token.text = "ID Token:\n${localCredentials?.idToken}"
    }
}