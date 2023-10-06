package com.example.pokedex.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.pokedex.view.adapters.CategoryAdapter
import com.example.pokedex.view.adapters.PopularAdapter
import com.example.pokedex.data.dao.FoodDao
import com.example.pokedex.data.dao.TypeDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.models.Food
import com.example.pokedex.models.Type
import com.example.pokedex.data.repository.FoodRepository
import com.example.pokedex.data.repository.TypeRepository
import com.example.pokedex.viewmodels.FoodViewModel
import com.example.pokedex.viewmodels.TypeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var foodDao: FoodDao
    private lateinit var foodRepository: FoodRepository
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var popularAdapter: PopularAdapter
    private var foodList = listOf<Food>()
    private var typeList = listOf<Type>()
    private lateinit var typeDao: TypeDao
    private lateinit var typeRepository: TypeRepository
    private lateinit var typeViewModel: TypeViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = AppDatabase.getDatabase(requireContext())

        val textViewOrder: TextView = view.findViewById(R.id.textView3)
        val buttonOrder: Button = view. findViewById(R.id.button)

        foodDao = appDatabase.foodDao()
        foodRepository = FoodRepository(foodDao, appDatabase)
        foodViewModel = FoodViewModel(foodRepository)

        typeDao = appDatabase.typeDao()
        typeRepository = TypeRepository(typeDao, appDatabase)
        typeViewModel = TypeViewModel(typeRepository)

        foodList = listOf(
            Food("Pizza Tomatoes", R.drawable.pop_1, 5.05, null),
            Food("Burger Beer", R.drawable.pop_2, 6.15, null),
            Food("Pizza haisan", R.drawable.pop_3, 7.00, null)
        )
        typeList = listOf(
            Type("Pizza", R.drawable.cat_1),
            Type("Burger", R.drawable.cat_2),
            Type("Hot dog", R.drawable.cat_3),
            Type("Drink", R.drawable.cat_4),
            Type("Pie", R.drawable.cat_5)
        )

        typeViewModel.insertAll(typeList)
        foodViewModel.insertAll(foodList)

        typeViewModel.updatedList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    typeViewModel.getAllTypes()
                }
            }
        }

        foodViewModel.updatedList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    foodViewModel.getAllFoods()
                }
            }
        }

        typeViewModel.typeList.observe(viewLifecycleOwner) { typeList ->
            categoryAdapter.setData(typeList)
            categoryAdapter.notifyDataSetChanged()
        }

        foodViewModel.foodList.observe(viewLifecycleOwner) { foodList ->
            popularAdapter.setData(foodList)
            popularAdapter.notifyDataSetChanged()
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPopular)

        popularAdapter = PopularAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = popularAdapter

        val recyclerType: RecyclerView = view.findViewById(R.id.recyclerViewCategory)

        categoryAdapter = CategoryAdapter(emptyList())
        recyclerType.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerType.adapter = categoryAdapter

        if (DarkModeUtil.isDarkMode) {
            val textColorStateList = ContextCompat.getColorStateList(requireContext(), R.drawable.button_text_color_night)

            textViewOrder.setTextColor(textColorStateList)
            buttonOrder.setTextColor(textColorStateList)
        }
    }
}