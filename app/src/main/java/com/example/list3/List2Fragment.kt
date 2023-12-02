package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list3.databinding.ComplexListItemBinding
import com.example.list3.databinding.FragmentList1Binding
import com.example.list3.databinding.FragmentList2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [List2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class List2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val dataRepo = DataRepo.getInstance()
    private lateinit var binding: FragmentList2Binding

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
        binding = FragmentList2Binding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(dataRepo.getComplexData())
        recyclerView.adapter = adapter

    }

    inner class MyAdapter(var data: MutableList<DataItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
            inner class MyViewHolder(viewBinding : ComplexListItemBinding) :
                    RecyclerView.ViewHolder(viewBinding.root) {
                        val title: TextView = viewBinding.itemTitle
                        val subtitle: TextView = viewBinding.itemSubtitle
                        val icon: ImageView = viewBinding.itemIcon
                        val checkBox: CheckBox = viewBinding.checkBox
                    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ComplexListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        private fun sunItemIcon(holder: MyViewHolder, position: Int) {
            when (data[position].item_type) {
                false -> holder.icon.setImageResource(R.drawable.airplane_icon)
                true -> holder.icon.setImageResource(R.drawable.bus_icon)
            }
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.title.text = data[position].text_main
            holder.subtitle.text = data[position].text2
            holder.checkBox.isChecked = data[position].item_checked
            sunItemIcon(holder, position)

            holder.itemView.setOnClickListener {
                Toast.makeText(requireContext(), "You clicked " + (position+1), Toast.LENGTH_SHORT).show()
            }

            holder.itemView.setOnLongClickListener {
                if (dataRepo.deleteComplexItem(position)) {
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount);
                }
                true
            }

            holder.checkBox.setOnClickListener { v ->
                data[position].item_checked = (v as CheckBox).isChecked
                Toast.makeText(requireContext(), "Selected/Unselected: " + (position + 1), Toast.LENGTH_SHORT).show()
            }

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment List2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            List2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}