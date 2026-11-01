package com.example.recursos_aplicacion.view

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recursos_aplicacion.R
import com.example.recursos_aplicacion.controller.ResourceController
import com.example.recursos_aplicacion.model.Recurso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentMainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchInput: EditText
    private lateinit var adapter: RecursoAdapter
    private val controller = ResourceController()
    private var listaCompleta: List<Recurso> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main)

        recyclerView = findViewById(R.id.recyclerRecursos)
        searchInput = findViewById(R.id.searchInput)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RecursoAdapter(emptyList(), isTeacher = false,
            onEditClick = {},
            onDeleteClick = {},
            onFavoriteClick = { recurso ->
                // Guardar en SharedPreferences como favorito local
                val prefs = getSharedPreferences("FavoritosPrefs", MODE_PRIVATE)
                prefs.edit().putBoolean(recurso.id, true).apply()
                Toast.makeText(this, "Guardado en favoritos: ${recurso.titulo}", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = adapter

        cargarDatos()

        // Búsqueda avanzada por ID, Título o Tipo
        searchInput.addTextChangedListener { text ->
            val query = text.toString().lowercase()
            val filtrados = listaCompleta.filter {
                it.titulo.lowercase().contains(query) ||
                        it.tipo.lowercase().contains(query) ||
                        it.id.contains(query)
            }
            adapter.updateList(filtrados)
        }
    }

    private fun cargarDatos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaCompleta = controller.obtenerRecursos()
            adapter.updateList(listaCompleta)
        }
    }
}