package com.example.recursos_aplicacion.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.example.recursos_aplicacion.model.Recurso
import com.example.recursos_aplicacion.R

class RecursoAdapter(
    private var recursos: List<Recurso>,
    private val isTeacher: Boolean,
    private val onEditClick: (Recurso) -> Unit,
    private val onDeleteClick: (Recurso) -> Unit,
    private val onFavoriteClick: (Recurso) -> Unit
) : RecyclerView.Adapter<RecursoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgRecurso: ImageView = view.findViewById(R.id.imgRecurso)
        val txtTitulo: TextView = view.findViewById(R.id.txtTitulo)
        val txtTipo: TextView = view.findViewById(R.id.txtTipo)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcion)
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)
        val btnDelete: MaterialButton = view.findViewById(R.id.btnDelete)
        val btnFavorite: MaterialButton = view.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recurso, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recurso = recursos[position]
        holder.txtTitulo.text = recurso.titulo
        holder.txtTipo.text = recurso.tipo
        holder.txtDescripcion.text = recurso.descripcion

        Glide.with(holder.itemView.context)
            .load(recurso.imagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.imgRecurso)

        if (isTeacher) {
            holder.btnEdit.visibility = View.VISIBLE
            holder.btnDelete.visibility = View.VISIBLE
            holder.btnFavorite.visibility = View.GONE

            holder.btnEdit.setOnClickListener { onEditClick(recurso) }
            holder.btnDelete.setOnClickListener { onDeleteClick(recurso) }
        } else {
            holder.btnEdit.visibility = View.GONE
            holder.btnDelete.visibility = View.GONE
            holder.btnFavorite.visibility = View.VISIBLE

            holder.btnFavorite.setOnClickListener { onFavoriteClick(recurso) }
        }
    }

    override fun getItemCount() = recursos.size

    fun updateList(nuevaLista: List<Recurso>) {
        recursos = nuevaLista
        notifyDataSetChanged()
    }
}