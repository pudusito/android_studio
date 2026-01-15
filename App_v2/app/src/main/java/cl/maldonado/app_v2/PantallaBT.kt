package cl.maldonado.app_v2

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PantallaBT : AppCompatActivity() {
    //aqui van las variables globales
    private val deviceName = "ESP32-BT-Profe2"
    private val REQUEST_BLUETOOTH_CONNECT = 1001

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var isConnected = false

    private lateinit var textoEstado: TextView
    private lateinit var botonEnviar: Button
    private lateinit var botonConectar: Button

    //oncreate do.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_bt)

        textoEstado = findViewById(R.id.Texto)
        botonEnviar = findViewById(R.id.BotonInstruccion)
        botonConectar = findViewById(R.id.BotonConectar)

        // Comprobar permisos ANTES de inicializar Bluetooth
        if (tienePermisoBluetooth()) {
            inicializarBluetooth()
        } else {
            solicitarPermisoBluetooth()
        }
    }

    // Inicializa el adaptador y listeners
    private fun inicializarBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        mostrarEstado(false)

        botonConectar.setOnClickListener {
            if (tienePermisoBluetooth()) {
                abrirConfiguracionBluetooth()
            } else {
                solicitarPermisoBluetooth()
            }
        }

        botonEnviar.setOnClickListener {
            if (tienePermisoBluetooth()) {
                Thread {
                    if (!isConnected) {
                        isConnected = conectarBluetooth()
                    }

                    runOnUiThread {
                        if (isConnected) {
                            enviarComando("1")
                        } else {
                            Toast.makeText(this, "No se pudo conectar al ESP32", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.start()
            } else {
                solicitarPermisoBluetooth()
            }
        }
    }

    private fun tienePermisoBluetooth(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun solicitarPermisoBluetooth() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
            REQUEST_BLUETOOTH_CONNECT
        )
    }

    private fun abrirConfiguracionBluetooth() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        startActivity(intent)
    }

    private fun mostrarEstado(conectado: Boolean) {
        textoEstado.text = if (conectado) "CONECTADO" else "NO CONECTADO"
        val color = if (conectado) android.R.color.holo_green_dark else android.R.color.holo_red_dark
        textoEstado.setTextColor(ContextCompat.getColor(this, color))
    }

    @SuppressLint("MissingPermission")
    private fun conectarBluetooth(): Boolean {
        val device = bluetoothAdapter?.bondedDevices?.find { it.name == deviceName } ?: return false

        return try {
            val uuid = device.uuids[0].uuid
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
            bluetoothSocket?.connect()
            runOnUiThread {
                mostrarEstado(true)
                Toast.makeText(this, "Conectado a $deviceName", Toast.LENGTH_SHORT).show()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            runOnUiThread { mostrarEstado(false) }
            false
        }
    }

    private fun enviarComando(comando: String) {
        try {
            bluetoothSocket?.outputStream?.write(comando.toByteArray())
            Toast.makeText(this, "Comando enviado", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            runOnUiThread { mostrarEstado(false) }
            Toast.makeText(this, "Error enviando datos. Intentando reconectar...", Toast.LENGTH_SHORT).show()

            Thread {
                isConnected = conectarBluetooth()
            }.start()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_CONNECT) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso Bluetooth concedido", Toast.LENGTH_SHORT).show()
                //Se inicializa recién cuando el permiso es otorgado
                inicializarBluetooth()
            } else {
                Toast.makeText(
                    this,
                    "Se necesita el permiso para usar Bluetooth",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mostrarEstado(isConnected)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            bluetoothSocket?.close()
            isConnected = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}