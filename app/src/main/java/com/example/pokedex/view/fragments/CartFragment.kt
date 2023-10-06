package com.example.pokedex.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.DarkModeUtil
import com.example.pokedex.R
import com.example.pokedex.view.adapters.CartAdapter
import com.example.pokedex.data.dao.CartDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.data.repository.CartRepository
import com.example.pokedex.models.Cart
import com.example.pokedex.view.activities.CheckoutActivity
import com.example.pokedex.viewmodels.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {
    private lateinit var cartDao: CartDao
    private lateinit var cartRepository: CartRepository
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    @SuppressLint("NotifyDataSetChanged", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = AppDatabase.getDatabase(requireContext())

        val totalPriceText: TextView = view.findViewById(R.id.totalPrice)
        val orderButton: Button = view.findViewById(R.id.buttonOrder)

        cartDao = appDatabase.cartDao()
        cartRepository = CartRepository(cartDao, appDatabase)
        cartViewModel = CartViewModel(cartRepository)

        lifecycleScope.launch {
            val cart: Cart = cartViewModel.getCartById1()

            if (cart == null) {
                val dialog = AlertDialog.Builder(requireContext())
                val textView = TextView(requireContext())

                with(textView) {
                    text = "Our alert"
                    textSize = 20.0F
                    setTypeface(null, Typeface.BOLD)
                    gravity = Gravity.CENTER
                }

                dialog.setMessage("Empty cart!")
                dialog.setNeutralButton("Dismiss") { dialog, _ ->
                    dialog.dismiss()
                }
                dialog.show()
            } else {
                totalPriceText.text = "$${cartAdapter.calculateTotalPrice(cart.listFoods)}"
                cartAdapter.setData(cart.listFoods)
                cartAdapter.notifyDataSetChanged()
            }
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) {totalPrice ->
            totalPriceText.text = "$${cartAdapter.roundToTwoDecimalPlaces(totalPrice)}"
        }

        val recyclerFood: RecyclerView = view.findViewById(R.id.recyclerViewFood)
        cartAdapter = CartAdapter(emptyList(), cartViewModel)
        recyclerFood.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerFood.adapter = cartAdapter

        orderButton.setOnClickListener {
            val bundle = Bundle()

            bundle.putString(CheckoutActivity.TotalPrice, cartViewModel._totalPrice.value.toString())

            val intent = Intent(requireContext(), CheckoutActivity::class.java)

            intent.putExtras(bundle)
            startActivity(intent)
        }

        if (DarkModeUtil.isDarkMode) {
            val textColorStateList = ContextCompat.getColorStateList(requireContext(), R.drawable.button_text_color_night)

            orderButton.setTextColor(textColorStateList)
        }
    }
}