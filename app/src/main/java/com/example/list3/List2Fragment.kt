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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.list3.Database.DBItem
import com.example.list3.Database.MyRepository
import com.example.list3.Database.MyViewModel
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
    private lateinit var myVModel: MyViewModel
    private lateinit var binding: FragmentList2Binding
    private lateinit var myAdapter: MyListAdapter

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

        val myModel : MyViewModel by activityViewModels { MyViewModel.Factory }
        myVModel = myModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())




        myAdapter = MyListAdapter(onItemAction)
        //val adapter = MyAdapter(dataRepo.getAnimals()!!)

        val floatingButton: FloatingActionButton = binding.FAB
        floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_recyclerViewList_to_addAnimalFragment)
        }

        val dividerItemDecoration = DividerItemDecoration(requireActivity(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.animal_border_decorator, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)




        recyclerView.adapter = myAdapter
        myAdapter.submitList(myVModel.getAllItems())

    }

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<DBItem>() {
            override fun areItemsTheSame(oldItem: DBItem, newItem: DBItem): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: DBItem, newItem: DBItem): Boolean {
                return oldItem.name == newItem.name &&
                oldItem.latinName == newItem.latinName &&
                oldItem.animalType == newItem.animalType &&
                oldItem.strength == newItem.strength &&
                oldItem.health == newItem.health &&
                oldItem.isDeadly == newItem.isDeadly;
            }
        }
    }

    var onItemAction : (animal:DBItem, action:Int) -> Unit = { item, action ->
        when (action) {
            1 -> Toast.makeText(requireContext(), "You clicked: " + item.name, Toast.LENGTH_SHORT).show()
            2 -> {
                myVModel.deleteItem(item)
                Toast.makeText(requireContext(), "Deleted: " + item.name, Toast.LENGTH_SHORT).show()
                myAdapter.submitList(myVModel.getAllItems())
            }
        }
    }

    inner class MyListAdapter(private val onItemAction: (item: DBItem, action:Int)-> Unit) :
        ListAdapter<DBItem, MyListAdapter.MyViewHolder>(DiffCallback) {
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

        private fun sunItemIcon(holder: MyViewHolder, animal: DBItem) {
            val iconSrc = when (animal.animalType) {
                DBItem.AnimalType.BIRD -> R.drawable.bird_icon
                DBItem.AnimalType.PREDATOR -> R.drawable.predator_icon
                DBItem.AnimalType.INSECT -> R.drawable.insect_icon
                DBItem.AnimalType.RODENT -> R.drawable.rodent_icon
            }
            holder.icon.setImageResource(iconSrc)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val currItem = getItem(position)

            holder.title.text = currItem.name
            holder.subtitle.text = currItem.latinName
            sunItemIcon(holder, currItem)
            holder.title.setTextColor(if (currItem.isDeadly) resources.getColor(R.color.red) else resources.getColor(R.color.green))

            holder.itemView.setOnClickListener {
                val position = holder.adapterPosition
                onItemAction(getItem(position), 1)
            }

            holder.itemView.setOnLongClickListener {
                val position = holder.adapterPosition
                val item = getItem(position)
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder
                    .setTitle("Are you sure?")
                    .setMessage("You are going to delete " + item.name + ". Do you want to continue?")
                    .setPositiveButton("Yes") { dialog, _ ->
                        onItemAction(item, 2)
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                builder.create().show()
                true
            }
        }
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