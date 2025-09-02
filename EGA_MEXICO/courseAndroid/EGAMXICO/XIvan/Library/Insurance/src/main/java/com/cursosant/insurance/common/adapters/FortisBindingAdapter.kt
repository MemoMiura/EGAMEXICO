package com.cursosant.insurance.common.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.Constants
import com.google.android.material.textfield.TextInputLayout
import kotlin.coroutines.coroutineContext

/****
 * Project: Xpenses
 * From: com.cursosant.insurance.common.adapters
 * Created by Alain Nicolás Tello on 13/05/23 at 14:20
 * All rights reserved 2023.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/

@BindingAdapter("setVisibility")
fun bindSetVisibility(view: View, inProgress: Boolean) {
    view.visibility = if (inProgress) View.VISIBLE else View.GONE
}

@BindingAdapter("clearError")
fun bindClearError(view: TextInputLayout, isActive: Boolean) {
    if (isActive)
        view.editText?.addTextChangedListener {
            view.error = null
            view.isErrorEnabled = false
        }
}

@BindingAdapter("imgSubramo")
fun bindSubramo(view: ImageView, subramo: String){
    view.setImageResource(when(subramo){
        Constants.SUBRAMO_CAR -> R.drawable.ic_car
        Constants.SUBRAMO_DAMAGES,
        Constants.SUBRAMO_DAMAGES_MULTI,
        Constants.SUBRAMO_DAMAGES_FIRE -> R.drawable.ic_house
        Constants.SUBRAMO_HEALTH,
        Constants.SUBRAMO_MEDIC_EXPENSES -> R.drawable.ic_local_hospital
        Constants.SUBRAMO_LIFE -> R.drawable.ic_health_and_safety
        else -> R.drawable.ic_price_check
    })
}

@BindingAdapter("drawableSubramo")
fun bindDrawableSubramo(view: TextView, subramo: String){
    view.text = subramo
    view.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(view.context, when(subramo){
        Constants.SUBRAMO_CAR -> R.drawable.ic_car
        Constants.SUBRAMO_DAMAGES,
        Constants.SUBRAMO_DAMAGES_MULTI,
        Constants.SUBRAMO_DAMAGES_FIRE -> R.drawable.ic_house
        Constants.SUBRAMO_HEALTH,
        Constants.SUBRAMO_MEDIC_EXPENSES -> R.drawable.ic_local_hospital
        Constants.SUBRAMO_LIFE -> R.drawable.ic_health_and_safety
        else -> R.drawable.ic_price_check
    }), null, null, null)
}

@BindingAdapter("textDecimal")
fun bindTextDecimal(view: EditText, value: Double) {
    view.setText( if (value == 0.0) "" else value.toString())
}

@BindingAdapter("glideUrl")
fun bindLoadImage(view: ImageView, uri: String?){
    Glide.with(view.context)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        //.circleCrop()
        .error(R.drawable.ic_broken_image)
        .into(view)
}
@BindingAdapter("glideDrawable")
fun bindLoadDrawable(view: ImageView, drawableRes: Drawable){
    Glide.with(view.context)
        .load(drawableRes)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .circleCrop()
        .error(R.drawable.ic_broken_image)
        .into(view)
}