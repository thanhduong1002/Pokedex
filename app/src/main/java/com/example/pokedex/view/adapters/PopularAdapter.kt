package com.example.pokedex.view.adapters

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.view.fragments.DetailFoodFragment
import com.example.pokedex.models.Food

class PopularAdapter(private var arrFoods: List<Food>): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    class PopularViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val foodName: TextView = view.findViewById(R.id.textViewName)
        val imageFood: ImageView = view.findViewById(R.id.imageFood)
        val priceFood: TextView = view.findViewById(R.id.textViewPrice)
        val addButton: Button = view.findViewById(R.id.buttonAdd)
    }

    fun setData(arrData: List<Food>){
        arrFoods = arrData.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.popular_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrFoods.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = arrFoods[position]

        holder.foodName.text = item.name
        holder.imageFood.setImageResource(item.imageFood)
        holder.priceFood.text = Html.fromHtml("<font color='#FF8122'>$</font> ${item.price}", Html.FROM_HTML_MODE_LEGACY)
        holder.addButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(DetailFoodFragment.nameFood, item.name)
            bundle.putString(DetailFoodFragment.imageFood, item.imageFood.toString())
            bundle.putString(DetailFoodFragment.priceFood, item.price.toString())
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_detailFoodFragment, bundle)
        }
    }
}