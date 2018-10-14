package com.lychee.ui.add.adapter.image

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.lychee.databinding.ItemRecyclerViewCameraAddBinding
import com.lychee.databinding.ItemRecyclerViewImageAddBinding
import com.lychee.ui.add.AddActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddImageRecyclerViewAdapter(
        private val context: Context
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var images = mutableListOf<String>()
        set(value) {
            field.clear()
            images.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) TYPE_CAMERA
        else TYPE_GALLERY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_CAMERA) {
            val binding = ItemRecyclerViewCameraAddBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false)

            binding.root.setOnClickListener { takePhoto() }

            AddCameraRecyclerViewHolder(binding)
        } else {
            val binding = ItemRecyclerViewImageAddBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false)

            AddImageRecyclerViewHolder(binding, context)
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        var photo: File? = null

        try {
            photo = createImageFile()
        } catch (exception: Exception) {
            Toast.makeText(context, "이미지 처리 오류 발생", Toast.LENGTH_LONG).show()
        }

        photo?.let { photoFile ->
            val uri = FileProvider.getUriForFile(context, "com.lychee.fileprovider", photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            (context as Activity).startActivityForResult(intent, AddActivity.TAKE_PHOTO_REQUEST_CODE)
        }
    }

    private fun createImageFile(): File {
        val calendar = Calendar.getInstance()

        val formatted = SimpleDateFormat("yyyyMMdd-kkmmss").format(calendar.time)

        val fileName = "JPEG_${formatted}_"

        val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val storageDir = File(dir, "test")

        if (!storageDir.exists()) { storageDir.mkdir() }

        val image: File = File.createTempFile(
                fileName,
                ".jpg",
                storageDir)

        return image
    }

    override fun getItemCount(): Int {
        return when {
            images.size == 0 -> 1
                        /*inclusive inclusive*/
            images.size in 1..9 -> images.size + 1
            else -> 10
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position != 0 && images.size > 0) {
            val index = position - 1
            val uri = images[index]

            (holder as? AddImageRecyclerViewHolder)?.bind(uri)
        }
    }

    companion object {
        private const val TYPE_CAMERA = 0
        private const val TYPE_GALLERY = 1
    }
}