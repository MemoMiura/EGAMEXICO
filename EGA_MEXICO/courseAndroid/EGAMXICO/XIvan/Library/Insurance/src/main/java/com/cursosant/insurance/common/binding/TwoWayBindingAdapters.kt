package com.cursosant.insurance.common.binding

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter("selectedValue")
fun Spinner.setSelectedValue(value: Int?) {
    val pos = value ?: 0
    if (selectedItemPosition != pos) setSelection(pos)
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun Spinner.getSelectedValue(): Int = selectedItemPosition

@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setSelectedValueListener(listener: InverseBindingListener?) {
    if (listener == null) return
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener.onChange()
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
            listener.onChange()
        }
    }
}
