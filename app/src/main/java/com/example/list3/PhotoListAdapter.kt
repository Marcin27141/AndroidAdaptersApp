package com.example.list3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.list3.databinding.PhotoListItemBinding
import java.io.FileNotFoundException
import java.io.InputStream

class PhotoListAdapter(val appContext: Context, val dataList: MutableList<FileItem>)
    : RecyclerView.Adapter<PhotoListAdapter.MyViewHolder>(){
    inner class MyViewHolder(viewBinding: PhotoListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val tv1: TextView = viewBinding.itemTitle
        val tv2: TextView = viewBinding.itemSubtitle
        val tv3: TextView = viewBinding.itemSubSubtitle
        val img: ImageView = viewBinding.itemImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewBinding = PhotoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv1.text = dataList[position].name
        holder.tv2.text = dataList[position].uriPath
        holder.tv3.text = dataList[position].contentUri?.path

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            dataList[position].contentUri?.let {
                holder.img.setImageBitmap(appContext.contentResolver.loadThumbnail(it, Size(72,72), null))
            }
        } else {
            holder.img.setImageBitmap(getBitmapFromUri(appContext, dataList[position].contentUri))
        }
    }

    private fun getBitmapFromUri(appContext: Context, contentUri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val image_stream: InputStream
            try {
                image_stream = contentUri?.let {
                    appContext.contentResolver.openInputStream(it)
                }!!
                bitmap = BitmapFactory.decodeStream(image_stream)
            } catch(e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}