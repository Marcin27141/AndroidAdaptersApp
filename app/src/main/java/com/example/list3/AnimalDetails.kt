package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.list3.Database.DBItem
import com.example.list3.Database.MyRepository
import com.google.android.material.slider.Slider
import org.w3c.dom.Text

class AnimalDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_animal_details, container, false)

        val nameLabel: TextView = rootview.findViewById(R.id.name)
        val latinLabel: TextView = rootview.findViewById(R.id.latinName)
        val animalIcon: ImageView = rootview.findViewById(R.id.animalIcon)
        val animalTypeLabel: TextView = rootview.findViewById(R.id.animalTypeLabel)
        val healthValue: TextView = rootview.findViewById(R.id.healthValue)
        val strengthRating: RatingBar = rootview.findViewById(R.id.strengthRating)
        val deadlyLabel: TextView = rootview.findViewById(R.id.deadlyLabel)

        val arguments = requireArguments()
        if (arguments.containsKey("animalItemId")) {
            val animalItemId = arguments.getInt("animalItemId")
            val animalItem = MyRepository.getInstance(requireContext()).getAnimalById(animalItemId)
            nameLabel.text = animalItem.name
            latinLabel.text = animalItem.latinName
            animalIcon.setImageResource(getIconSrc(animalItem))
            animalTypeLabel.text = animalItem.animalType.toString()
            healthValue.text = animalItem.health.toString()
            strengthRating.rating = animalItem.strength
            deadlyLabel.text = if (animalItem.isDeadly) "This is a deadly animal" else "This animal is not deadly"
        }

        return rootview
    }

    private fun getIconSrc(animal: DBItem) : Int {
        return when (animal.animalType) {
            DBItem.AnimalType.BIRD -> R.drawable.bird_icon
            DBItem.AnimalType.PREDATOR -> R.drawable.predator_icon
            DBItem.AnimalType.INSECT -> R.drawable.insect_icon
            DBItem.AnimalType.RODENT -> R.drawable.rodent_icon
        }
    }
}