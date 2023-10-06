package com.example.pokedex.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pokedex.R
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.DarkModeUtil
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
        val textViewQuantity: TextView = view.findViewById(R.id.textQuantity)
    }

    fun setData(listFoods: List<Food?>) {
        this.listFoods = listFoods as List<Food>
    }

    fun calculateTotalPrice(foods: List<Food?>): Double {
        var totalPrice = 0.0
        for (food in foods) {
            totalPrice += food?.price?.times(food.quantity!!) ?: 0.0
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

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = listFoods[position]

        var initialQuantity = item.quantity

        calculateTotalPrice(listFoods)

        holder.cartName.text = item.name
        holder.priceName.text = "$${roundToTwoDecimalPlaces(item.price)}"
        holder.quantityName.text = initialQuantity.toString()
        holder.imageItem.setImageResource(item.imageFood)

        if (initialQuantity != null) {
            holder.plusButton.setOnClickListener {
                initialQuantity += 1
                item.quantity = initialQuantity
                notifyDataSetChanged()
                val total = calculateTotalPrice(listFoods)
                cartViewModel._totalPrice.postValue(total)
            }

            holder.minusButton.setOnClickListener {
                if (initialQuantity > 1) {
                    initialQuantity -= 1
                    item.quantity = initialQuantity
                    notifyDataSetChanged()
                    val total = calculateTotalPrice(listFoods)
                    cartViewModel._totalPrice.postValue(total)
                }
            }
        }
        if (DarkModeUtil.isDarkMode) {
            val textColorStateList = ContextCompat.getColorStateList(holder.textViewQuantity.context, R.drawable.button_text_color_night)

            holder.textViewQuantity.setTextColor(textColorStateList)
            holder.cartName.setTextColor(textColorStateList)
        }
    }

    fun roundToTwoDecimalPlaces(value: Double): Double {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value).toDouble()
    }

}