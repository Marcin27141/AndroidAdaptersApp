package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list3.databinding.AnimalItemBinding
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
        val adapter = MyAdapter(dataRepo.getAnimalsData())
        recyclerView.adapter = adapter

    }

    inner class MyAdapter(var data: MutableList<AnimalItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
            inner class MyViewHolder(viewBinding : AnimalItemBinding) :
                    RecyclerView.ViewHolder(viewBinding.root) {
                        val title: TextView = viewBinding.animalName
                        val subtitle: TextView = viewBinding.latinName
                        val icon: ImageView = viewBinding.itemIcon
                    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = AnimalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        private fun sunItemIcon(holder: MyViewHolder, position: Int) {
            holder.icon.setImageResource(AnimalItem.getAnimalIconId(data[position]))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.title.text = data[position].name
            holder.subtitle.text = data[position].latinName
            sunItemIcon(holder, position)

            holder.itemView.setOnClickListener {
                //Toast.makeText(requireContext(), data[position].name, Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putSerializable("animalItem", data[position])
                }
                findNavController().navigate(R.id.action_recyclerViewList_to_animalDetails, bundle)
            }

//            holder.itemView.setOnLongClickListener {
//                if (dataRepo.deleteComplexItem(position)) {
//                    notifyItemRemoved(position)
//                    notifyItemRangeChanged(position, itemCount);
//                }
//                true
//            }
        }

    }
}