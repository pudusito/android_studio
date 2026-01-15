package cl.maldonado.app_v2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = Firebase.auth


        /* aqui vamos reciviendo  los datos desde los layout*/
        val editUsuario = findViewById<EditText>(R.id.inputUsuario)
        val editClave = findViewById<EditText>(R.id.inputClave)
        val botonIniciar = findViewById<Button>(R.id.botonIniciarSesion)


        botonIniciar.setOnClickListener {
            val usuarioIngresado = editUsuario.text.toString()
            val claveIngresada = editClave.text.toString()
/*
            val respuestaFuncion = iniciarSesionBasico(usuarioIngresado, claveIngresada)

            if(respuestaFuncion){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, "Compita no lo conozco", Toast.LENGTH_SHORT).show()
            } */

            if(usuarioIngresado.isEmpty() || claveIngresada.isEmpty()){
                Toast.makeText(this, "porfavor ingresar datos", Toast.LENGTH_SHORT).show()
            }else{
                iniciarSesionPro(usuarioIngresado,claveIngresada)
            }

            editUsuario.setText("")
            editClave.setText("")
        }

    }


    fun iniciarSesionBasico(usuario: String, clave: String) : Boolean{
        val usuarioXDefecto = "admin"
        val claveXDefecto = "admin"

        if(usuario == usuarioXDefecto && clave == claveXDefecto){
            return true
        }else{
            return false
        }

    }

    fun iniciarSesionPro(usuario: String, clave: String) {
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(usuario,clave).addOnCompleteListener { task ->
            //AQUI VA LO QUE QUEREMOS QUE PASE CUANDO LA TAREA SE COMPLETA "TASK"
            if(task.isSuccessful){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "credenciales incorrectas!!!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"error en FIREBASE", Toast.LENGTH_SHORT).show()
        }

    }
}