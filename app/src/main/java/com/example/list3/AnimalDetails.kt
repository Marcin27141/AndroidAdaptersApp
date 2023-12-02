package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
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
        val healthRating: RatingBar = rootview.findViewById(R.id.healthRating)
        val strengthRating: RatingBar = rootview.findViewById(R.id.strengthRating)
        val deadlyLabel: TextView = rootview.findViewById(R.id.deadlyLabel)

        val arguments = requireArguments()
        if (arguments.containsKey("animalItem")) {
            val animalItem = arguments.getSerializable("animalItem") as AnimalItem
            nameLabel.text = animalItem.name
            latinLabel.text = animalItem.latinName
            animalIcon.setImageResource(AnimalItem.getAnimalIconId(animalItem))
            animalTypeLabel.text = animalItem.animalType.toString()
            healthRating.rating = animalItem.health.toFloat()
            strengthRating.rating = animalItem.strength.toFloat()
            deadlyLabel.text = if (animalItem.isDeadly) "This is a deadly animal" else "This animal is not deadly"
        }

        return rootview
    }
}