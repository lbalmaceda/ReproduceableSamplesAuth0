package com.lbalmaceda.a0.requiresauth

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var manager: SecureCredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        manager = a0Manager(this@LoginActivity)
        refreshUi()

        btn_login.setOnClickListener {
            startLogin()
        }
        btn_logout.setOnClickListener {
            clearCredentials()
            refreshUi()
        }
        btn_activity.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshUi() {
        val loggedIn = manager.hasValidCredentials()
        btn_login.isEnabled = !loggedIn
        btn_logout.isEnabled = loggedIn
        btn_activity.isEnabled = loggedIn
    }

    private fun startLogin() {
        WebAuthProvider.login(a0Account())
            .withScheme("demo")
            .withScope("openid offline_access")
            .withAudience("https://jwks.lbalmaceda.auth0.com")
            .start(this@LoginActivity, object : AuthCallback {
                override fun onSuccess(credentials: Credentials) {
                    saveCredentials(credentials)
                    runOnUiThread { refreshUi() }
                }

                override fun onFailure(dialog: Dialog) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(e: AuthenticationException) {
                    visibleLog(
                        "Error logging in - ${e.message}", e.cause
                    )
                }
            })
    }

    private fun clearCredentials() {
        manager.clearCredentials()
    }

    private fun saveCredentials(credentials: Credentials) {
        try {
            manager.saveCredentials(credentials)
        } catch (e: CredentialsManagerException) {
            visibleLog(
                "Error saving - Device compatible = ${!e.isDeviceIncompatible} - ${e.message}",
                e.cause
            )
        }
    }
}