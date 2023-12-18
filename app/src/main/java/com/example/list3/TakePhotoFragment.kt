package com.example.list3

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract.Data
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.list3.databinding.FragmentTakePhotoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class TakePhotoFragment : Fragment() {
    private lateinit var binding: FragmentTakePhotoBinding
    var lastFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTakePhotoBinding.inflate(inflater, container, false);

        val photoPreviewLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            result: Bitmap? ->
                if (result != null) {
                    Toast.makeText(requireContext(), "PREVIEW RECEIVED", Toast.LENGTH_LONG).show()
                    binding.image.setImageBitmap(result)
                } else {
                    Toast.makeText(requireContext(), "PREVIEW NOT RECEIVED", Toast.LENGTH_LONG).show()
                }
        }

        binding.storageInfo.text = when (ImageRepo.getInstance(requireContext()).getStorage()) {
            ImageRepo.SHARED_STORAGE_ID -> "Photo will be saved to shared storage"
            ImageRepo.APP_STORAGE_ID -> "Photo will be saved to private app storage"
            else -> ""
        }

        binding.previewBtn.setOnClickListener { view ->
            try {
                photoPreviewLauncher.launch()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val photoLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            result : Boolean ->
                if (result) {
                    Toast.makeText(requireContext(), "Photo was taken", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Photo wasn't taken", Toast.LENGTH_LONG).show()
                    lastFile?.delete()
                }
        }

        binding.takePictureBtn.setOnClickListener { view ->
            val lastFileUri = getNewFileUri()
            try {
                photoLauncher.launch(lastFileUri)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "Camer doesn't work", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun getNewFileUri() : Uri {
        val timeStamp : String = SimpleDateFormat("yyyMMdd_HHmmss").format(Date())
        val dir = when(ImageRepo.getInstance(requireContext()).getStorage()) {
            ImageRepo.SHARED_STORAGE_ID -> Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            else -> requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        }
        val tmpFile = File.createTempFile("Photo_" + timeStamp ,".jpg", dir)
        lastFile = tmpFile
        return FileProvider.getUriForFile(requireContext(), requireContext().packageName + ".provider", tmpFile)
    }
}