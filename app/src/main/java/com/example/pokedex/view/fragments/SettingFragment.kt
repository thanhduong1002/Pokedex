package com.example.pokedex.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.example.pokedex.DarkModeUtil
import com.example.pokedex.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment() {
    private var isContextAvailable = false

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val switchButton: SwitchMaterial = view.findViewById(R.id.material_switch)
        val textSettings: TextView = view.findViewById(R.id.textView6)

        switchButton.setOnCheckedChangeListener { _, isChecked ->
            DarkModeUtil.isDarkMode = isChecked
            if (isContextAvailable) {
                if (DarkModeUtil.isDarkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    val textColorStateList = ContextCompat.getColorStateList(requireContext(), R.drawable.button_text_color_night)

                    textSettings.setTextColor(textColorStateList)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isContextAvailable = true
    }
}