package cl.maldonado.app_v2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        //ESTE ES UN COMENTARIO
        //ENLAZAR LOS BOTONES
        val btnPantallaUno = findViewById<Button>(R.id.BottonPantallaUno)
        val btnPantallaDos = findViewById<Button>(R.id.BottonPantallaDos)
        val btnPantallaTres = findViewById<Button>(R.id.BottonPantallaTres)
        val btnCerrarSesion= findViewById<Button>(R.id.BottonCerrarSesion)

        btnPantallaUno.setOnClickListener {
            //aca va la Logica del boton
            Toast.makeText(this, "Hola Mundo movile P1", Toast.LENGTH_SHORT).show()//modal al clickear con toast
            startActivity(Intent(this, Layout_1::class.java))//direcciona A.
        }

        btnPantallaDos.setOnClickListener {
            Toast.makeText(this, "Hola P2", Toast.LENGTH_SHORT).show() //modal al clickear con toast
            startActivity(Intent(this, PantallaDos::class.java)) //direcciona A.
        }


        btnPantallaTres.setOnClickListener {
            Toast.makeText(this, "Hola BT", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, PantallaBT::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

 }

    private fun cerrarSesion(){
        auth.signOut()
        finish()

    }
}