package com.example.pokedex.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.models.Type

class CategoryAdapter(private var arrCategories: List<Type>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.textViewName)
        val categoryImage: ImageView = view.findViewById(R.id.imageCategory)
    }

    fun setData(arrData: List<Type>){
        arrCategories = arrData.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))
    }

    override fun getItemCount(): Int {
        return arrCategories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = arrCategories[position]

        holder.categoryName.text = item.name
        holder.categoryImage.setImageResource(item.imageType)
    }
}