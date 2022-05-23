package com.example.constantes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PantallaLogin : AppCompatActivity() {

    private lateinit var contraseña: EditText
    private lateinit var correo: EditText
    private lateinit var registrarse: Button
    private lateinit var iniciarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_login)

        contraseña = findViewById(R.id.password)
        correo = findViewById(R.id.email)
        registrarse = findViewById(R.id.buttonRegistrarse)
        iniciarSesion = findViewById(R.id.buttonIniciarSesion)

        iniciarSesion()
        registrarse.setOnClickListener {
            val intent = Intent(this, RegistarseActivity::class.java).apply {
                startActivity(this)
            }

        }
    }


        private fun iniciarSesion() {
            iniciarSesion.setOnClickListener {
                if (correo.text.isNotEmpty() && contraseña.text.isNotEmpty()) {
                    //buscar el usuario en la base de datos
                    Firebase.auth.signInWithEmailAndPassword(
                        correo.text.toString().trim(), contraseña.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, PaginaPrincipal::class.java).apply {
                                startActivity(this)
                            }
                            correo.setText("")
                            contraseña.setText("")
                        } else {
                            showErrorInicioSesion("Se ha producido un error al iniciar sesión")
                        }
                    }
                }
            }
        }


        private fun showErrorInicioSesion(mensaje: String) {
            //creamos el cuadro de dialogo
            val builder = AlertDialog.Builder(this)
            //le añadimos las propiedades
            builder.setTitle("Error de autenticación")
            builder.setMessage(mensaje)
            builder.setPositiveButton("Aceptar", null)
            //mostrar el cuadro
            builder.show()
        }

        private fun showExitoInicioSesion(mensaje: String) {
            //creamos el cuadro de dialogo
            val builder = AlertDialog.Builder(this)
            //le añadimos las propiedades
            builder.setTitle("Autenticación con éxito")
            builder.setMessage(mensaje)
            builder.setPositiveButton("Aceptar", null)
            //mostrar el cuadro
            builder.show()
        }
    }
