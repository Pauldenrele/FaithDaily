package com.bibleapp.faithdaily.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.model.ImageModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 *   Created by OLABODE WILSON on 2/17/21.
 */
class FaithDailyBottomSheet() : BottomSheetDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
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
        with(view) {
            findViewById<ImageView>(R.id.share).setOnClickListener {
                //TODO implement share
            }

            findViewById<ImageView>(R.id.close).setOnClickListener {
               dismiss()
            }

            arguments?.getInt("imageRes")?.let {
                findViewById<ImageView>(R.id.image).setImageResource(
                   it
                )
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

}