package www.iesmurgi.firebaseejemplo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseejemplo.databinding.ActivityMainBinding
import www.iesmurgi.firebaseejemplo.databinding.IntroActivityBinding
import www.iesmurgi.firebaseejemplo.databinding.LoginActivityBinding

class PerfilActivity : AppCompatActivity() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var user: FirebaseUser? = auth.currentUser

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val btnAjustes = binding.btnAjustes

        btnAjustes.setOnClickListener {

            startActivity(Intent(this, AjustesActivity::class.java))

        }

        if (user != null) {

            setup()

        }

    }

    fun setup() {

        var titutlo = binding.txtTittle
        var correo = binding.txtCorreo
        var usuario = binding.txtUsuario
        var nacionalidad = binding.txtNacionalidad
        var edad = binding.txtEdad
        val db = Firebase.firestore
        db.collection("user").document(user?.email.toString()).get().addOnSuccessListener {

                documento ->


            val email: String? = documento.getString("email")
            val user: String? = documento.getString("usuario")
            val nacionality: String? = documento.getString("nacionalidad")
            val age: String? = documento.getString("edad")

            titutlo.text = titutlo.text.toString()+" "+user
            correo.text = email
            usuario.text = user
            nacionalidad.text = nacionality
            edad.text = age

        }

        val btnCerrar = binding.btnCerrarS
        btnCerrar.setOnClickListener {

            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))

        }

    }

    override fun onBackPressed() {}


}