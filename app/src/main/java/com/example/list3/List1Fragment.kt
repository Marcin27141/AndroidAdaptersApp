package com.example.list3

import android.content.Context
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.list3.databinding.FragmentList1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [List1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class List1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var listView: ListView
    var listData = DataRepo.getInstance().item_text_list


    private lateinit var binding: FragmentList1Binding

    class CustomArrayAdapter(context: Context, data: Array<String>) :
        ArrayAdapter<String>(context, R.layout.simple_list_item, R.id.listItemText, data) {
        var checkedStates = BooleanArray(data.size)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val layout: RelativeLayout = view.findViewById(R.id.itemLayout)

            val checkBox: CheckBox = view.findViewById(R.id.checkBox)

            layout.setOnClickListener { view ->
                checkBox.isChecked = !checkBox.isChecked
            }

            checkBox.setOnCheckedChangeListener { _, checked ->
                checkedStates[position] = checked
                var anyChecked = false;
                var text = "Clicked " + position
                for (i in checkedStates.indices) {
                    if (checkedStates[i]) {
                        if (!anyChecked) {
                            text = text + " : Checked"
                            anyChecked = true
                        }
                        text += " " + i
                    }
                }
                if (!anyChecked)
                    text = text + " : No items checked"
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }

            return view
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentList1Binding.inflate(inflater, container, false);
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = binding.listView
        val adapter = CustomArrayAdapter(requireContext(), listData)
        listView.adapter = adapter
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment List1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            List1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}