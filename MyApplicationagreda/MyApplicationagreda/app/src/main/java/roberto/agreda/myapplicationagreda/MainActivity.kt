package roberto.agreda.myapplicationagreda

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import roberto.agreda.myapplication.login
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnIrlogin:Button = findViewById(R.id.btnIrLogin)

        btnRegistrar.setOnClickListener {

            val nombre = txtNombre.text.toString()
            val contrasena = txtContrasena.text.toString()

            if (nombre.isEmpty() || contrasena.isEmpty()) {

                Toast.makeText(
                    this,
                    "Error, para crear la cuenta debe llenar todas las casillas.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                Log.i("Test de credenciales", "Nombre: $nombre y Contraseña: $contrasena")
            }
        }

        btnRegistrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{

                val objConexion = ClaseConexion().cadenaConexion()

                val addUsuarios= objConexion?.prepareStatement("insert into Usuario (UUID_Usuario, nombre_usuario, contrasena_usuario) values (?, ?, ?)")!!
                addUsuarios.setString(1, UUID.randomUUID().toString())
                addUsuarios.setString(2, txtNombre.text.toString())
                addUsuarios.setString(3, txtContrasena.text.toString())

                addUsuarios.executeUpdate()

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtNombre.setText("")
                    txtContrasena.setText("")
                }

                btnIrlogin.setOnClickListener {
                    val pantallalogin = Intent(this@MainActivity, login::class.java)
                    startActivity(pantallalogin)
                }
            }
        }
    }
}