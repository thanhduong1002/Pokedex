package com.example.pokedex.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pokedex.DarkModeUtil
import com.example.pokedex.R
import com.example.pokedex.data.dao.CartDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.models.Cart
import com.example.pokedex.models.Food
import com.example.pokedex.data.repository.CartRepository
import com.example.pokedex.viewmodels.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

class DetailFoodFragment : Fragment() {
    private lateinit var cartDao: CartDao
    private lateinit var cartRepository: CartRepository
    private lateinit var cartViewModel: CartViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_food, container, false)
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = AppDatabase.getDatabase(requireContext())

        cartDao = appDatabase.cartDao()
        cartRepository = CartRepository(cartDao, appDatabase)
        cartViewModel = CartViewModel(cartRepository)

        val nameFood = arguments?.getString(nameFood)
        val priceFood = arguments?.getString(priceFood)
        val imageFood = arguments?.getString(imageFood)

        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewPrice: TextView = view.findViewById(R.id.textViewPrice)
        val imageView: ImageView = view.findViewById(R.id.imageFood)
        val textViewQuantity: TextView = view.findViewById(R.id.textViewQuantity)
        val buttonPlus: ImageView = view.findViewById(R.id.buttonPlus)
        val buttonMinus: ImageView = view.findViewById(R.id.buttonMinus)
        val textViewGredients: TextView = view.findViewById(R.id.textViewGredients)
        val buttonAdd: Button = view.findViewById(R.id.buttonAdd)

        var initialQuantity = 1
        val priceDouble = priceFood?.toDouble() ?: 0.0

        textViewName.text = nameFood
        textViewPrice.text = "$${priceDouble * initialQuantity}"
        imageFood?.toInt()?.let { imageView.setImageResource(it) }
        textViewQuantity.text = initialQuantity.toString()

        buttonPlus.setOnClickListener {
            initialQuantity += 1
            textViewQuantity.text = initialQuantity.toString()
            textViewPrice.text = "$${roundToTwoDecimalPlaces(priceDouble * initialQuantity)}"
        }

        buttonMinus.setOnClickListener {
            if (initialQuantity > 1) {
                initialQuantity -= 1
                textViewQuantity.text = initialQuantity.toString()
                textViewPrice.text = "$${roundToTwoDecimalPlaces(priceDouble * initialQuantity)}"
            }
        }

        textViewGredients.text =
            "tomato pizza sauce, 2 mozzerella balls, sliced or a packet of grated mozzerella, a handful flour for dusting, salt and pepper, some of olive oil."

        buttonAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            val textView = TextView(requireContext())

            with(textView) {
                text = "Our alert"
                textSize = 20.0F
                setTypeface(null, Typeface.BOLD)
                gravity = Gravity.CENTER
            }

            dialog.setMessage("Add to cart successful!")
            dialog.setNeutralButton("Dismiss") { dialog, _ ->
                dialog.dismiss()
            }
            dialog.show()

            val newFood = nameFood?.let { it1 ->
                imageFood?.let { it2 ->
                    Food(
                        it1,
                        it2.toInt(),
                        priceDouble,
                        initialQuantity
                    )
                }
            }
            CoroutineScope(Dispatchers.IO).launch {
                val currentCart = withContext(Dispatchers.IO) {
                    cartViewModel.getCartById1()
                }
                Log.d("Cart", "$currentCart")
                val foodList = mutableListOf<Food>()
                if (currentCart == null) {
                    if (newFood != null) {
                        foodList.add(newFood)
                    }

                    val newCart = Cart(id = 1, listFoods = foodList)

                    cartViewModel.insertCart(newCart)
                } else {
                    foodList.addAll(currentCart.listFoods.filterNotNull())

                    val existingFood = foodList.find { it.name == newFood?.name }

                    if (existingFood != null) {
                        existingFood.quantity = existingFood.quantity?.plus(newFood?.quantity ?: 0)
                    } else {
                        if (newFood != null) {
                            foodList.add(newFood)
                        }
                    }

                    cartViewModel.updateListFoods(foodList)
                }
            }
        }

        if (DarkModeUtil.isDarkMode) {
            val textColorStateList = ContextCompat.getColorStateList(requireContext(), R.drawable.button_detail_food_night)

            buttonAdd.setTextColor(textColorStateList)
        }
    }

    private fun roundToTwoDecimalPlaces(value: Double): Double {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value).toDouble()
    }

    companion object {
        const val nameFood = "nameFood"
        const val priceFood = "priceFood"
        const val imageFood = "imageFood"
    }
}
