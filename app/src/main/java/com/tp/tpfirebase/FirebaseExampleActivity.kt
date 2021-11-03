package com.tp.tpfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.tp.tpfirebase.databinding.ActivityFirebaseExampleBinding

class FirebaseExampleActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirebaseExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            //Si on a un utilisateur connecté
            setBtVisibility(true)
            if (FirebaseAuth.getInstance().currentUser?.displayName != null) {
                binding.tvName.text = currentUser.displayName
            }else{
                binding.tvName.text = currentUser.email.toString()
            }
        }else  {
            //Si notre utilisateur n'est pas connecté
            setVisible(false)
        }
    }

    private val signInLauncher = //A mettre en attribut
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            //Callback de la connexion
            onSignInResult(it)
        }

    fun onBtDeconnexionClick(view: View) {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                binding.textView2.text = "Déconnecté"
            }
    }

    fun onBtAddGameClick(view: android.view.View) {}

    fun onBtConnexionClick(view: android.view.View) {
        //Liste et ordre des types de connexion voulus
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        //Lance l'Activité de connexion
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.mipmap.ic_launcher)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult?) {
        val response = result?.idpResponse
        if (result?.resultCode == RESULT_OK) {
            //Succesfully signed id
            val user = FirebaseAuth.getInstance().currentUser
            println("Connect : ${user?.email})")
        } else {
            //Sign in failed if response is null the user canceled
            //sign-in flow using the back bt. Otherwise
            //check response.getErrror().getErrorCode() and handle the error
            println("erreur : ${response?.error?.errorCode})")
            response?.error?.printStackTrace()
        }
    }

    fun setBtVisibility(isConnected: Boolean) {
        if (isConnected) {
            binding.tvDeconnexion.visibility = View.VISIBLE
            binding.tvConnexion.visibility = View.GONE
        } else {
            binding.tvDeconnexion.visibility = View.GONE
            binding.tvConnexion.visibility = View.VISIBLE
        }
    }
}