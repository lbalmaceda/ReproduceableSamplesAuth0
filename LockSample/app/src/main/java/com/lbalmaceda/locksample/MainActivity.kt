package com.lbalmaceda.locksample

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0
import com.auth0.android.lock.AuthenticationCallback
import com.auth0.android.lock.Lock
import com.auth0.android.lock.utils.LockException
import com.auth0.android.result.Credentials


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener { showLock() }
    }

    private fun showLock() {
        //Configure the account
        val account = Auth0(this)
        account.isOIDCConformant = true

        //Set up Lock
        val lock = Lock.newBuilder(account, object : AuthenticationCallback() {
            override fun onAuthentication(credentials: Credentials) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        credentials.accessToken,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onCanceled() {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Canceled",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onError(error: LockException) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        String.format("E: %s", error.message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }).closable(true)
            .withScheme("demo")
            .build(this)

        //Show
        startActivity(lock.newIntent(this))
    }
}