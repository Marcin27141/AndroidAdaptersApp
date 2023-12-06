package com.example.list3

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list3.Database.DBItem
import com.example.list3.Database.MyRepository
import com.example.list3.databinding.AnimalItemBinding
import com.example.list3.databinding.FragmentList2Binding
import com.google.android.material.floatingactionbutton.FloatingActionButton


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
    private lateinit var dataRepo: MyRepository
    private lateinit var binding: FragmentList2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        dataRepo = MyRepository.getInstance(requireContext())
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
        val adapter = MyAdapter(dataRepo.getAnimals()!!)

        val floatingButton: FloatingActionButton = binding.FAB
        floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_recyclerViewList_to_addAnimalFragment)
        }

        parentFragmentManager.setFragmentResultListener("item_added", viewLifecycleOwner) {
                _, _ -> adapter.refreshList()
        }

        parentFragmentManager.setFragmentResultListener("item_modified", viewLifecycleOwner) {
                _, _ -> adapter.refreshList()
        }

        val dividerItemDecoration = DividerItemDecoration(requireActivity(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.animal_border_decorator, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter

    }

    inner class MyAdapter(var data: MutableList<DBItem>) :
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

        fun refreshList() {
            data = dataRepo.getAnimals()!!
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return data.size
        }

        private fun sunItemIcon(holder: MyViewHolder, position: Int) {
            val iconSrc = when (data[position].animalType) {
                DBItem.AnimalType.BIRD -> R.drawable.bird_icon
                DBItem.AnimalType.PREDATOR -> R.drawable.predator_icon
                DBItem.AnimalType.INSECT -> R.drawable.insect_icon
                DBItem.AnimalType.RODENT -> R.drawable.rodent_icon
            }
            holder.icon.setImageResource(iconSrc)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.title.text = data[position].name
            holder.subtitle.text = data[position].latinName
            sunItemIcon(holder, position)
            holder.title.setTextColor(if (data[position].isDeadly) resources.getColor(R.color.red) else resources.getColor(R.color.green))

            holder.itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("animalItemId", data[position].id)
                }
                findNavController().navigate(R.id.action_recyclerViewList_to_animalDetails, bundle)
            }

            holder.itemView.setOnLongClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder
                    .setTitle("Are you sure?")
                    .setMessage("You are going to delete " + data[position].name + ". Do you want to continue?")
                    .setPositiveButton("Yes") { dialog, _ ->
                        if (dataRepo.deleteAnimal(data[position])) {
                            data = dataRepo.getAnimals()!!
                            notifyDataSetChanged()
                        }
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                builder.create().show()
                true
            }
        }
    }
}