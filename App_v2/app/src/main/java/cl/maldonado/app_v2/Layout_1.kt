package cl.maldonado.app_v2

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Layout_1 : AppCompatActivity() {

    private lateinit var mp3: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_layout1)

        val texto= findViewById<TextView>(R.id.textoUno)
        val botonPudu= findViewById<ImageButton>(R.id.botonImagen)

        botonPudu.setOnClickListener {
            texto.text = "texto cambiado"

            mp3 = MediaPlayer.create(this, R.raw.miau)
            mp3.start()
        }
    }
}