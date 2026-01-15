package cl.maldonado.app_v2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PantallaDos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_dos)

        val input = findViewById<EditText>(R.id.InputPin)
        val boton = findViewById<Button>(R.id.BotonValidar)

        boton.setOnClickListener {
            val textoIngresado = input.text.toString() // "ESTO ES UN STRING"
            Toast.makeText(this, textoIngresado, Toast.LENGTH_SHORT).show()
            input.setText("") //limpiamos el campo input
        }




    }
}