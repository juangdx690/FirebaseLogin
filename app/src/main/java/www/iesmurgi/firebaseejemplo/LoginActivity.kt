package www.iesmurgi.firebaseejemplo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseejemplo.databinding.IntroActivityBinding
import www.iesmurgi.firebaseejemplo.databinding.LoginActivityBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private val PASSWORD_PATTER: Pattern = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$"
    )

    private lateinit var correo: TextView
    private lateinit var clave: TextView

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth



        correo = binding.txtCorreo
        clave = binding.txtClave
        var btnIniciar = binding.btnIniciar
        var btnGoogle = binding.btnGoogle

        btnIniciar.setOnClickListener {


            if (comprobarEmail() && comprobarClave()) {


                iniciarSesion(correo.text.toString(), clave.text.toString())


            }


        }

        btnGoogle.setOnClickListener{

            iniciarSesiongoogle()

        }

    }




    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, PerfilActivity::class.java))
            finish()
        }
    }

    companion object{

        private const val RC_SIGN_IN = 423

    }


    fun iniciarSesiongoogle() {

         val providerGoogle = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providerGoogle).build(),
            RC_SIGN_IN
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Companion.RC_SIGN_IN){

            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK){

                val user = FirebaseAuth.getInstance().currentUser

                startActivity(Intent(this, PerfilActivity::class.java))
                finish()

            }

        }

    }

    fun iniciarSesion(email: String, clave: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            clave
        ).addOnCompleteListener {

            if (it.isSuccessful) {

                abrirPerfil()


            } else {

                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

            }

        }

    }


    fun abrirPerfil() {

        val enviar = Intent(this, PerfilActivity::class.java).apply {

        }
        startActivity(enviar)

    }

    fun comprobarEmail(): Boolean {


        if (correo.text.toString().length == 0) {

            correo.error = this.resources.getString(R.string.falloEmailVacio)

            return false

        } else {

            if (!Patterns.EMAIL_ADDRESS.matcher(correo.text.toString()).matches()) {
                correo.error =
                    this.resources.getString(R.string.falloEmailPattern) + "\n" + this.resources.getString(
                        R.string.modeloEmail
                    )
                return false

            } else {

                return true
            }
        }

    }

    fun comprobarClave(): Boolean {

        if (clave.text.toString().length == 0) {

            clave.error = this.resources.getString(R.string.falloClaveVacio)
            return false

        } else {

            if (!PASSWORD_PATTER.matcher(clave.text.toString()).matches()) {

                clave.error =
                    this.resources.getString(R.string.falloClavePattern) + "\n" + this.resources.getString(
                        R.string.modeloClave
                    )

                return false

            } else {
                return true

            }


        }


    }




}
