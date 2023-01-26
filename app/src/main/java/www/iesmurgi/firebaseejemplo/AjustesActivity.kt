package www.iesmurgi.firebaseejemplo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseejemplo.databinding.AjustesperfilActivityBinding

class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: AjustesperfilActivityBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AjustesperfilActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        var btnRestablecerClave = binding.btnRestablecer

        var btnBorrar = binding.btnBorrar

        btnRestablecerClave.setOnClickListener {

            restablecerClave()

        }

        btnBorrar.setOnClickListener {

            borrarUsuario()

        }


        actualizarUser()
        actualizarEdad()
        actualizarNacio()

    }

    fun actualizarUser() {

        var btnActualizarUser = binding.btnActualizarUsuario


        btnActualizarUser.setOnClickListener {
            var txtCambio: String = ""
            val builder = AlertDialog.Builder(this)
            builder.setTitle( this.resources.getString(R.string.actualizarUser))

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                // Asigna el texto ingresado en el EditText a la variable "textoIngresado"
                txtCambio = input.text.toString()
                val documentReference =
                    Firebase.firestore.collection("user").document(user.email.toString())
                documentReference.update("usuario", txtCambio)

                Toast.makeText(
                    this,
                    this.resources.getString(R.string.msg_actualizarUser),
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, PerfilActivity::class.java))
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()


        }

    }

    fun actualizarNacio() {

        var btnActualizarNacio= binding.btnActualizarNacio


        btnActualizarNacio.setOnClickListener {
            var txtCambio: String = ""
            val builder = AlertDialog.Builder(this)
            builder.setTitle( this.resources.getString(R.string.actualizarNacio))

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                // Asigna el texto ingresado en el EditText a la variable "textoIngresado"
                txtCambio = input.text.toString()
                val documentReference =
                    Firebase.firestore.collection("user").document(user.email.toString())
                documentReference.update("nacionalidad", txtCambio)

                Toast.makeText(
                    this,
                    this.resources.getString(R.string.msg_actualizarNacio),
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, PerfilActivity::class.java))
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()


        }

    }

    fun actualizarEdad() {

        var btnActualizarEdad= binding.btnActualizarEdad


        btnActualizarEdad.setOnClickListener {
            var txtCambio: String = ""
            val builder = AlertDialog.Builder(this)
            builder.setTitle( this.resources.getString(R.string.actualizarEdad))

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                // Asigna el texto ingresado en el EditText a la variable "textoIngresado"
                txtCambio = input.text.toString()
                val documentReference =
                    Firebase.firestore.collection("user").document(user.email.toString())
                documentReference.update("edad", txtCambio)

                Toast.makeText(
                    this,
                    this.resources.getString(R.string.actualizarEdad),
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, PerfilActivity::class.java))
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()


        }

    }

    fun restablecerClave() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(this.resources.getString(R.string.resetclave))
        builder.setMessage(this.resources.getString(R.string.msg_restablecerclave2))
        builder.setPositiveButton("Ok") { dialog, which ->

            auth.sendPasswordResetEmail(user.email.toString()).addOnCompleteListener {
                if (it.isSuccessful) {


                    Toast.makeText(
                        this,
                        this.resources.getString(R.string.msg_restablecerclave),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        this,
                        it.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }


        }

        builder.setNegativeButton("Cancel") { dialog, which ->


        }


        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(this, R.color.verde))

            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(this, R.color.rojo))
        }
        dialog.show()

    }

    fun borrarUsuario() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(this.resources.getString(R.string.borrarUser))
        builder.setMessage(this.resources.getString(R.string.msg_borraruser))
        builder.setPositiveButton("Ok") { dialog, which ->

            user.delete().addOnCompleteListener {
                val dialog = builder.create()
                if (it.isSuccessful) {

                    startActivity(Intent(this, MainActivity::class.java))

                    Toast.makeText(
                        this,
                        this.resources.getString(R.string.msg_borraruser2),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        this,
                        it.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }


        }

        builder.setNegativeButton("Cancel") { dialog, which ->


        }


        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(this, R.color.verde))

            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(this, R.color.rojo))
        }
        dialog.show()

    }

}