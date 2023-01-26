package www.iesmurgi.firebaseejemplo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import www.iesmurgi.firebaseejemplo.databinding.IntroActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: IntroActivityBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IntroActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val btnIniciar = binding.btnIniciar
        val btnRegistro = binding.btnRegistro

        btnIniciar.setOnClickListener {


            val enviar = Intent(this, LoginActivity::class.java)


            this.startActivity(enviar)


        }

        btnRegistro.setOnClickListener{

            val enviar = Intent(this, RegistroActivity::class.java)


            this.startActivity(enviar)
        }

    }



    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(this, PerfilActivity::class.java))
            finish()
        }
    }
}