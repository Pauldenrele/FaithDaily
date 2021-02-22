package com.bibleapp.faithdaily.ui.bottomsheet

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.model.ImageModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 *   Created by OLABODE WILSON on 2/17/21.
 */
class FaithDailyBottomSheet() : BottomSheetDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { l ->
                val behaviour = BottomSheetBehavior.from(l)
                setupFullHeight(l)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.image)
         arguments?.getInt("imageRes")?.also {
            imageView.setImageResource(it)
        }
        val uri = getLocalBitmapUri(imageView)
        with(view) {
            findViewById<ImageView>(R.id.share).setOnClickListener {
                uri?.let {
                    Intent(Intent.ACTION_SEND).apply {
                        type = "image/*";
                        putExtra(Intent.EXTRA_STREAM, it)
                        startActivity(Intent.createChooser(this, "Share Image using"));
                    }
                }
            }

            findViewById<ImageView>(R.id.close).setOnClickListener {
                dismiss()
            }


        }

    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    companion object {
        fun newInstance(imageModel: ImageModel) = FaithDailyBottomSheet().apply {
            arguments = Bundle().apply {
                putInt("imageRes", imageModel.image_drawable)
            }
        }
    }

    fun getLocalBitmapUri(imageView: ImageView): Uri? {
        // Extract Bitmap from ImageView drawable
        val drawable = imageView.drawable
        var bmp: Bitmap? = null
        bmp = if (drawable is BitmapDrawable) {
            (imageView.drawable as BitmapDrawable).bitmap
        } else {
            return null
        }
        // Store image to default external storage directory
        var bmpUri: Uri? = null
        try {

            val file = File(
                context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                bmpUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.bibleapp.faithdaily.fileprovider",
                    file
                )
            } else {
                bmpUri = Uri.fromFile(file)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }


}