package com.example.list3

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Data
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
import androidx.core.os.bundleOf
import androidx.fragment.app.findFragment

class AddAnimalFragment : Fragment() {
    class FormsNotFilledException(message: String) : Exception(message)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview =  inflater.inflate(R.layout.fragment_add_animal, container, false)

        val nameEdit: EditText = rootview.findViewById(R.id.nameEdit)
        val latinEdit: EditText = rootview.findViewById(R.id.latinEdit)
        val radioGroup: RadioGroup = rootview.findViewById(R.id.radioGroup)
        val healthRating: RatingBar = rootview.findViewById(R.id.healthRating)
        val strengthRating: RatingBar = rootview.findViewById(R.id.strengthRating)
        val isDeadlyCheckbox: CheckBox = rootview.findViewById(R.id.isDeadly)
        val saveButton: Button = rootview.findViewById(R.id.saveButton)
        val cancelButton: Button = rootview.findViewById(R.id.cancelButton)

        cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        saveButton.setOnClickListener {
            try {
                val name = getNameFromEdit(nameEdit)
                val latin = getLatinFromEdit(latinEdit)
                val animalType = getTypeFromRadioGroup(radioGroup)
                val animal = AnimalItem(
                    name, latin, animalType, healthRating.rating.toInt(), strengthRating.rating.toInt(), isDeadlyCheckbox.isChecked
                )
                DataRepo.getInstance().addAnimalItem(animal)
                parentFragmentManager.setFragmentResult("valueFromChildEx",
                    bundleOf("addedItem" to true)
                )
                requireActivity().onBackPressed()

            } catch (ex: FormsNotFilledException) {
                showErrors(requireActivity(), ex.message!!)
            }
        }

        return rootview
    }

    private fun getNameFromEdit(nameEdit: EditText): String {
        if (nameEdit.text.isNullOrBlank())
            throw FormsNotFilledException("Animal name is mandatory")
        else return nameEdit.text.toString()
    }
    private fun getLatinFromEdit(latinEdit: EditText): String {
        if (latinEdit.text.isNullOrBlank())
            throw FormsNotFilledException("Latin name is mandatory")
        else return latinEdit.text.toString()
    }


    private fun getTypeFromRadioGroup(radioGroup: RadioGroup) : AnimalItem.AnimalType {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.predatorRadio -> AnimalItem.AnimalType.PREDATOR
            R.id.insectRadio -> AnimalItem.AnimalType.INSECT
            R.id.rodentRadio -> AnimalItem.AnimalType.RODENT
            R.id.birdRadio -> AnimalItem.AnimalType.BIRD
            else -> throw FormsNotFilledException("Animal type selection is mandatory")
        }
    }

    private fun showErrors(activity: Activity, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder
            .setTitle("Error")
            .setMessage(message)
            .setNegativeButton("OK") { dialog, _ ->
                dialog.cancel()
            }
        builder.create().show()
    }
}