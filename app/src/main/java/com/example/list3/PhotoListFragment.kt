package com.example.list3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list3.databinding.FragmentPhotoListBinding
import com.example.list3.databinding.ImageGridViewBinding

class PhotoListFragment : Fragment() {
    private lateinit var binding: FragmentPhotoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)

        val dataRepo = ImageRepo.getInstance(requireContext())
        val imagesList = dataRepo.getFilesList()
        val adapter = imagesList?.let {
            SimplePhotoListAdapter(requireContext(), it)
        }
        if (adapter == null) {
            Toast.makeText(requireContext(), "Invalid data", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }
        binding.recyclerView.adapter = adapter

        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.layoutManager = layoutManager

        return binding.root
    }

    inner class SimplePhotoListAdapter(val appContext: Context, val dataList: MutableList<FileItem>)
        : RecyclerView.Adapter<SimplePhotoListAdapter.MyViewHolder>(){
        inner class MyViewHolder(viewBinding: ImageGridViewBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val img: ImageView = viewBinding.imageView
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ImageGridViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.img.setImageBitmap(ImageRepo.getInstance(appContext).getFileBitmap(dataList[position].contentUri!!))

            holder.itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("startingPosition", position)
                }
                findNavController().navigate(R.id.action_photoListFragment_to_swipeAndTabs, bundle)
            }
        }
    }
}