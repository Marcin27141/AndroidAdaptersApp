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
import com.example.list3.Database.DBItem
import com.example.list3.Database.MyRepository
import com.google.android.material.slider.Slider

class AddAnimalFragment : Fragment() {
    private lateinit var nameEdit: EditText
    private lateinit var latinEdit: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var healthSlider: Slider
    private lateinit var strengthRating: RatingBar
    private lateinit var isDeadlyCheckbox: CheckBox
    class FormsNotFilledException(message: String) : Exception(message)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview =  inflater.inflate(R.layout.fragment_add_animal, container, false)

        nameEdit = rootview.findViewById(R.id.nameEdit)
        latinEdit = rootview.findViewById(R.id.latinEdit)
        radioGroup = rootview.findViewById(R.id.radioGroup)
        healthSlider = rootview.findViewById(R.id.healthSlider)
        strengthRating = rootview.findViewById(R.id.strengthRating)
        isDeadlyCheckbox = rootview.findViewById(R.id.isDeadly)
        val saveButton: Button = rootview.findViewById(R.id.saveButton)
        val cancelButton: Button = rootview.findViewById(R.id.cancelButton)

        val arguments = getArguments()
        if (arguments != null && arguments.containsKey("animalItemId")) {
            val animalItemId = arguments.getInt("animalItemId")
            val animalItem = MyRepository.getInstance(requireContext()).getAnimalById(animalItemId)!!

            nameEdit.setText(animalItem.name)
            latinEdit.setText(animalItem.latinName)
            radioGroup.check(getRadioFromAnimal(animalItem))
            healthSlider.value = animalItem.health.toFloat()
            strengthRating.rating = animalItem.strength
            isDeadlyCheckbox.isChecked = animalItem.isDeadly

            saveButton.setOnClickListener {
                try {
                    val animal = tryGetAnimal()
                    animal.id = animalItemId

                    val repo = MyRepository.getInstance(requireContext())

                    repo.updateAnimal(animalItem.id, animal)
                    parentFragmentManager.setFragmentResult("item_modified",
                        bundleOf("animalItemId" to animalItemId)
                    )
                    requireActivity().onBackPressed()

                } catch (ex: FormsNotFilledException) {
                    showErrors(requireActivity(), ex.message!!)
                }
            }
        }
        else {
            saveButton.setOnClickListener {
                try {
                    val animal = tryGetAnimal()

                    if (MyRepository.getInstance(requireContext()).addAnimal(animal))
                        parentFragmentManager.setFragmentResult("item_added", Bundle.EMPTY)
                    requireActivity().onBackPressed()

                } catch (ex: FormsNotFilledException) {
                    showErrors(requireActivity(), ex.message!!)
                }
            }
        }

        cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return rootview
    }

    private fun tryGetAnimal(): DBItem {
        val name = getNameFromEdit(nameEdit)
        val latin = getLatinFromEdit(latinEdit)
        val animalType = getTypeFromRadioGroup(radioGroup)
        return DBItem(
            0, name, latin, animalType, healthSlider.value.toInt(), strengthRating.rating, isDeadlyCheckbox.isChecked
        )
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


    private fun getTypeFromRadioGroup(radioGroup: RadioGroup) : DBItem.AnimalType {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.predatorRadio -> DBItem.AnimalType.PREDATOR
            R.id.insectRadio -> DBItem.AnimalType.INSECT
            R.id.rodentRadio -> DBItem.AnimalType.RODENT
            R.id.birdRadio -> DBItem.AnimalType.BIRD
            else -> throw FormsNotFilledException("Animal type selection is mandatory")
        }
    }

    private fun getRadioFromAnimal(animal: DBItem) : Int {
        return when (animal.animalType) {
            DBItem.AnimalType.PREDATOR -> R.id.predatorRadio
            DBItem.AnimalType.INSECT -> R.id.insectRadio
            DBItem.AnimalType.RODENT -> R.id.rodentRadio
            DBItem.AnimalType.BIRD -> R.id.birdRadio
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