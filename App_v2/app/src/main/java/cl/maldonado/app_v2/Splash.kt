package cl.maldonado.app_v2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Splash : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        Handler(Looper.getMainLooper()).postDelayed({
            //AQUI VA LO QUE QUEREMOS QUE SUCEDA DESPUES DE 2 SEGUNDOS
            //startActivity(Intent(this, MainActivity::class.java))
            //finish()
            verificarSesion()
        }, 2000)
    }

    private fun verificarSesion() {
        val usurioActual = auth.currentUser
        if(usurioActual != null){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            startActivity(Intent(this,Login::class.java))
        }
        finish()
    }
}