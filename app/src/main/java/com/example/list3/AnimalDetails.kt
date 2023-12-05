package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.list3.Database.DBItem
import com.example.list3.Database.MyRepository
import com.google.android.material.slider.Slider
import org.w3c.dom.Text

class AnimalDetails : Fragment() {
    private lateinit var nameLabel: TextView
    private lateinit var latinLabel: TextView
    private lateinit var animalIcon: ImageView
    private lateinit var animalTypeLabel: TextView
    private lateinit var healthValue: TextView
    private lateinit var strengthRating: RatingBar
    private lateinit var deadlyLabel: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_animal_details, container, false)

        nameLabel = rootview.findViewById(R.id.name)
        latinLabel = rootview.findViewById(R.id.latinName)
        animalIcon = rootview.findViewById(R.id.animalIcon)
        animalTypeLabel = rootview.findViewById(R.id.animalTypeLabel)
        healthValue = rootview.findViewById(R.id.healthValue)
        strengthRating = rootview.findViewById(R.id.strengthRating)
        deadlyLabel = rootview.findViewById(R.id.deadlyLabel)

        val backButton : Button = rootview.findViewById(R.id.backButton)
        val modifyButton : Button = rootview.findViewById(R.id.modifyButton)

        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val arguments = requireArguments()
        if (arguments.containsKey("animalItemId")) {
            val animalItemId = arguments.getInt("animalItemId")
            val animalItem = MyRepository.getInstance(requireContext()).getAnimalById(animalItemId)!!
            setInputsForAnimal(animalItem)



            modifyButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("animalItemId", animalItemId)
                }
                findNavController().navigate(R.id.action_animalDetails_to_addAnimalFragment, bundle)
            }

            parentFragmentManager.setFragmentResultListener("item_modified", viewLifecycleOwner) {
                _, bundle ->
                    val updatedAnimalId = bundle.getInt("animalItemId")
                    if (updatedAnimalId == animalItemId) {
                        val updatedAnimal = MyRepository.getInstance(requireContext()).getAnimalById(updatedAnimalId)!!
                        setInputsForAnimal(updatedAnimal)
                    }
            }
        }

        return rootview
    }

    private fun setInputsForAnimal(animalItem: DBItem) {
        nameLabel.setText(animalItem.name)
        latinLabel.setText(animalItem.latinName)
        animalIcon.setImageResource(getIconSrc(animalItem))
        animalTypeLabel.text = animalItem.animalType.toString()
        healthValue.text = animalItem.health.toString()
        strengthRating.rating = animalItem.strength
        deadlyLabel.text = if (animalItem.isDeadly) "This is a deadly animal" else "This animal is not deadly"
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