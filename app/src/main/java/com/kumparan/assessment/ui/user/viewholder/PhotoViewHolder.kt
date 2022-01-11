package com.kumparan.assessment.ui.user.viewholder

import android.content.Context
import android.graphics.Point
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kumparan.assessment.R
import com.kumparan.assessment.databinding.ItemPhotoBinding
import com.kumparan.assessment.model.Photo
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PhotoViewHolder(private val context: Context, private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: Photo, onItemClicked: (Photo) -> Unit) {
        val displayMetrics = context.resources.displayMetrics
        val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, displayMetrics)
        val columnWidth = ((getScreenWidth() - (3 + 1) * padding) / 4).toInt()
        binding.photoThumbnail.layoutParams = ConstraintLayout.LayoutParams(columnWidth, columnWidth)

        binding.photoThumbnail.load(photo.thumbnailUrl) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        binding.root.setOnClickListener {
            onItemClicked(photo)
        }
    }

    private fun getScreenWidth(): Int {
        val columnWidth: Int
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var display: Display? = null
        if (wm != null) {
            display = wm.defaultDisplay
        }
        val point = Point()
        point.x = display!!.width
        point.y = display.height
        columnWidth = point.x
        return columnWidth
    }
}