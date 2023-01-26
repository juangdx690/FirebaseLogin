package www.iesmurgi.firebaseejemplo

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import android.util.Patterns
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseejemplo.databinding.SignupActivityBinding
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.regex.Pattern


class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
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

    private lateinit var binding: SignupActivityBinding

    private lateinit var correo: TextView
    private lateinit var clave: TextView
    private lateinit var claveRe: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val btnRegistro = binding.btnRegistro
        correo = binding.txtEmail
        clave = binding.txtClave
        claveRe = binding.txtClaveRe

        btnRegistro.setOnClickListener {

            if (comprobarEmail() && comprobarClave() && comprobarClaveRe() && comprobarClavesIguales()) {

                crearNuevoUsuario(correo.text.toString(), clave.text.toString())

            }

        }

    }

    fun crearNuevoUsuario(email: String, clave: String) {


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email,
            clave
        ).addOnCompleteListener {

            if (it.isSuccessful) {


                writeNewUser(email)
                abrirPerfil()


            } else {

                showErrorAlert()

            }

        }


    }

    fun writeNewUser(email:String) {

        val db = Firebase.firestore

        val data = hashMapOf(
            "email" to email,
            "usuario" to "nouser",
            "nacionalidad" to "nonacionality",
            "edad" to "0"
        )

        db.collection("user").document(email)
            .set(data)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

    }

    fun abrirPerfil() {

        val enviar = Intent(this, PerfilActivity::class.java).apply {

        }
        startActivity(enviar)

    }

    fun showErrorAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un errror autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

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

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, PerfilActivity::class.java))
            finish()
        }
    }

    fun comprobarClaveRe(): Boolean {

        if (claveRe.text.toString().length == 0) {

            claveRe.error = this.resources.getString(R.string.falloClaveReVacio)
            return false

        } else {

            if (!PASSWORD_PATTER.matcher(claveRe.text.toString()).matches()) {

                claveRe.error =
                    this.resources.getString(R.string.falloClavePattern) + "\n" + this.resources.getString(
                        R.string.modeloClave
                    )

                return false

            } else {
                return true

            }


        }
    }

    fun comprobarClavesIguales(): Boolean {

        if (clave.text.toString().equals(claveRe.text.toString())) {


            return true
        } else {

            claveRe.error = this.resources.getString(R.string.clavesiguales)

            return false
        }

    }

}
