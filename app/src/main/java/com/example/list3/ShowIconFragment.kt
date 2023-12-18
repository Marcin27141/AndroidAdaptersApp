package com.example.list3

import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ICON_SRC = "iconSrc"
private const val FILE_URI = "fileUri"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowIconFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowIconFragment : Fragment() {
    private var iconSrc: Int = 0
    private var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            iconSrc = it.getInt(ICON_SRC)
            fileUri = it.getParcelable(FILE_URI)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview =  inflater.inflate(R.layout.fragment_show_icon, container, false)

        val imageView: ImageView = rootview.findViewById(R.id.iconView)


        if (iconSrc != 0) {
            imageView.setImageResource(iconSrc)
        }
        else if (fileUri != null) {
            val imageRepo = ImageRepo.getInstance(requireContext())
            imageView.setImageBitmap(imageRepo.getFileBitmap(fileUri!!))
        }

        return rootview
    }

    companion object {
        @JvmStatic
        fun newInstance(iconId: Int) =
            ShowIconFragment().apply {
                arguments = Bundle().apply {
                    putInt(ICON_SRC, iconId)
                }
            }

        @JvmStatic
        fun newInstance(file: FileItem) =
            ShowIconFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FILE_URI, file.contentUri)
                }
            }
    }
}