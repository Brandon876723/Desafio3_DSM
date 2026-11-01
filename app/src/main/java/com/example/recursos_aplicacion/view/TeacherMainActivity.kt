package com.example.recursos_aplicacion.view

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.recursos_aplicacion.R
import com.example.recursos_aplicacion.controller.ResourceController
import com.example.recursos_aplicacion.model.Recurso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeacherMainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var adapter: RecursoAdapter
    private val controller = ResourceController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_main)

        recyclerView = findViewById(R.id.recyclerTeacherRecursos)
        fabAdd = findViewById(R.id.fabAddRecurso)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RecursoAdapter(emptyList(), isTeacher = true,
            onEditClick = { recurso -> mostrarFormulario(recurso) },
            onDeleteClick = { recurso -> confirmarEliminacion(recurso) },
            onFavoriteClick = {}
        )
        recyclerView.adapter = adapter

        cargarDatos()

        fabAdd.setOnClickListener { mostrarFormulario(null) }
    }

    private fun cargarDatos() {
        CoroutineScope(Dispatchers.Main).launch {
            val lista = controller.obtenerRecursos()
            adapter.updateList(lista)
        }
    }

    private fun mostrarFormulario(recursoExistente: Recurso?) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_recurso_form, null)

        val edtTitulo = dialogView.findViewById<EditText>(R.id.edtTitulo)
        val edtDesc = dialogView.findViewById<EditText>(R.id.edtDescripcion)
        val edtTipo = dialogView.findViewById<EditText>(R.id.edtTipo)
        val edtEnlace = dialogView.findViewById<EditText>(R.id.edtEnlace)
        val edtImagen = dialogView.findViewById<EditText>(R.id.edtImagen)

        recursoExistente?.let {
            edtTitulo.setText(it.titulo)
            edtDesc.setText(it.descripcion)
            edtTipo.setText(it.tipo)
            edtEnlace.setText(it.enlace)
            edtImagen.setText(it.imagen)
        }

        builder.setView(dialogView)
            .setTitle(if (recursoExistente == null) "Crear Recurso (POST)" else "Modificar Recurso (PUT)")
            .setPositiveButton("Guardar") { _, _ ->
                val titulo = edtTitulo.text.toString()
                val desc = edtDesc.text.toString()
                val tipo = edtTipo.text.toString()
                val enlace = edtEnlace.text.toString()
                val imagen = edtImagen.text.toString()

                if (titulo.isNotEmpty() && desc.isNotEmpty()) {
                    val nuevoRecurso = Recurso(
                        id = recursoExistente?.id ?: "",
                        titulo = titulo,
                        descripcion = desc,
                        tipo = tipo,
                        enlace = enlace,
                        imagen = imagen
                    )

                    CoroutineScope(Dispatchers.Main).launch {
                        val exito = controller.guardarRecurso(nuevoRecurso, recursoExistente != null, recursoExistente?.id)
                        if (exito) {
                            Toast.makeText(this@TeacherMainActivity, "Operación exitosa", Toast.LENGTH_SHORT).show()
                            cargarDatos()
                        } else {
                            Toast.makeText(this@TeacherMainActivity, "Error en la operación", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun confirmarEliminacion(recurso: Recurso) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Recurso (DELETE)")
            .setMessage("¿Deseas eliminar '${recurso.titulo}'?")
            .setPositiveButton("Sí, eliminar") { _, _ ->
                CoroutineScope(Dispatchers.Main).launch {
                    val exito = controller.eliminarRecurso(recurso.id)
                    if (exito) {
                        Toast.makeText(this@TeacherMainActivity, "Eliminado correctamente", Toast.LENGTH_SHORT).show()
                        cargarDatos()
                    } else {
                        Toast.makeText(this@TeacherMainActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}