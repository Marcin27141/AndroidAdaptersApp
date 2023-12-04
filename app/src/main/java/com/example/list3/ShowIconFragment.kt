package com.example.list3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ICON_SRC = "iconSrc"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowIconFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowIconFragment : Fragment() {
    private var iconSrc: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            iconSrc = it.getInt(ICON_SRC)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview =  inflater.inflate(R.layout.fragment_show_icon, container, false)

        if (iconSrc != -1) {
            val imageView: ImageView = rootview.findViewById(R.id.iconView)
            imageView.setImageResource(iconSrc)
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
    }
}