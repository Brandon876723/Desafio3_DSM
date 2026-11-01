package com.example.recursos_aplicacion.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recursos_aplicacion.R

class ResourceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_detail)

        val imgDetalle = findViewById<ImageView>(R.id.imgDetalleRecurso)
        val txtTitulo = findViewById<TextView>(R.id.txtDetalleTitulo)
        val txtTipo = findViewById<TextView>(R.id.txtDetalleTipo)
        val txtDesc = findViewById<TextView>(R.id.txtDetalleDescripcion)
        val btnEnlace = findViewById<Button>(R.id.btnAbrirEnlace)

        val titulo = intent.getStringExtra("titulo")
        val tipo = intent.getStringExtra("tipo")
        val descripcion = intent.getStringExtra("descripcion")
        val imagen = intent.getStringExtra("imagen")
        val enlace = intent.getStringExtra("enlace")

        txtTitulo.text = titulo
        txtTipo.text = tipo
        txtDesc.text = descripcion

        Glide.with(this)
            .load(imagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(imgDetalle)

        btnEnlace.setOnClickListener {
            if (!enlace.isNullOrEmpty()) {
                val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse(enlace))
                startActivity(intentWeb)
            }
        }
    }
}