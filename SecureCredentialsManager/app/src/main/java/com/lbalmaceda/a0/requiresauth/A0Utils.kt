package com.lbalmaceda.a0.requiresauth

import android.content.Context
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage

fun a0Account() = run {
    val account = Auth0("PX2vJhpALUNT66NHdCdD30XWqlIR4oEZ", "lbalmaceda.auth0.com")
    account.isOIDCConformant = true
    account
}

fun a0Client() = AuthenticationAPIClient(a0Account())
fun a0Manager(context: Context) =
    SecureCredentialsManager(context, a0Client(), SharedPreferencesStorage(context))

fun visibleLog(message: String, throwable: Throwable?) {
    Log.e("SampleApp", message, throwable)
}