package com.example.pokedex.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pokedex.R
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.Food
import com.example.pokedex.viewmodels.CartViewModel
import java.text.DecimalFormat

class CartAdapter(private var listFoods: List<Food>,  private var cartViewModel: CartViewModel): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cartName: TextView = view.findViewById(R.id.nameItem)
        val priceName: TextView = view.findViewById(R.id.priceItem)
        val quantityName: TextView = view.findViewById(R.id.quantityItem)
        val imageItem: ImageView = view.findViewById(R.id.imageItem)
        val minusButton: ImageView = view.findViewById(R.id.buttonMinus)
        val plusButton: ImageView = view.findViewById(R.id.buttonPlus)
    }

    fun setData(listFoods: List<Food>) {
        this.listFoods = listFoods
    }

    fun calculateTotalPrice(foods: List<Food>): Double {
        var totalPrice = 0.0
        for (food in foods) {
            totalPrice += food.price * food.quantity!!
        }
        cartViewModel._totalPrice.postValue(totalPrice)
        return totalPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.food_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listFoods.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = listFoods[position]

        var initialQuantity = item.quantity

        var initialTotalPrice = calculateTotalPrice(listFoods)

        holder.cartName.text = item.name
        holder.priceName.text = "$${roundToTwoDecimalPlaces(item.price)}"
        holder.quantityName.text = initialQuantity.toString()
        holder.imageItem.setImageResource(item.imageFood)

        if (initialQuantity != null) {
            holder.plusButton.setOnClickListener {
                initialQuantity += 1
                initialTotalPrice += item.price
                cartViewModel._totalPrice.postValue(initialTotalPrice)
                holder.quantityName.text = initialQuantity.toString()
            }

            holder.minusButton.setOnClickListener {
                if (initialQuantity > 1) {
                    initialQuantity -= 1
                    initialTotalPrice -= item.price
                    cartViewModel._totalPrice.postValue(initialTotalPrice)
                    holder.quantityName.text = initialQuantity.toString()
                }
            }
        }
    }

    fun roundToTwoDecimalPlaces(value: Double): Double {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value).toDouble()
    }

}