package com.cursosant.insurance.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 20/06/23 at 14:34
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
class DateUtils @Inject constructor() {
    fun formatDate(pattern: String, date: Long = System.currentTimeMillis()): String =
        SimpleDateFormat(pattern, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(date)

    fun formatDateFromString(date: String?): String{// FIXME: migrate to customFormatDateFromString
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

        var newDate = Date()
        try {
            date?.let {
                newDate = dateFormat.parse(it) as Date
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(newDate)
    }

    fun getTotalValidity(startDate: String?, endDate: String?) =
        "${formatDateFromString(startDate)} - ${formatDateFromString(endDate)}"

    fun getCustomTotalValidity(startDate: String?, endDate: String?, pattern: String?) =
        "${customFormatDateFromString(startDate, pattern)} - ${customFormatDateFromString(endDate, pattern)}"

    fun customFormatDateFromString(date: String?, pattern: String?): String{
        if (pattern == null) return ""

        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())

        var newDate = Date()
        try {
            date?.let {
                newDate = dateFormat.parse(it) as Date
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(newDate)
    }

    fun getAge(dateStr: String): Int {
        //if (dateStr == null) return 0

        var date: Date? = null
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            date = sdf.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()//java.text.ParseException: Unparseable date: "2023-07-25"
        }
        if (date == null) return 0
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob.time = date
        val year = dob[Calendar.YEAR]
        val month = dob[Calendar.MONTH]
        val day = dob[Calendar.DAY_OF_MONTH]
        dob[year, month + 1] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        return age
    }
}