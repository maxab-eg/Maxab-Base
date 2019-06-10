package com.maxab.helpers

import org.joda.time.DateTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {

    fun getDay(currentDate: String): DateTime {
        val sdf = SimpleDateFormat("MM/dd", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = sdf.parse(currentDate.replace("Z$".toRegex(), "+0000"))

        } catch (ex: ParseException) {
        }

        val dateTime = DateTime(date)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, dateTime.getMonthOfYear() - 1)
        calendar.set(Calendar.DAY_OF_MONTH, dateTime.getDayOfMonth())
        return DateTime(calendar.time)
    }

    fun setTime(mins: Int): String {
        return mins.toString() + " " + "minutes"
    }


    fun getTimeAmAndPM(date: DateTime): String {
        var pmOrAm = if (date.getHourOfDay() > 12) "pm" else "am"
        var hour = if (date.getHourOfDay() > 12) date.getHourOfDay() - 12 else date.getHourOfDay()

        if (hour == 12)
            pmOrAm = "pm"

        if (hour == 0)
            hour = 12
        return pad(hour) + ":" + pad(date.getMinuteOfHour()) + " " + pmOrAm
    }

    fun getTime(date: DateTime): String {
        return pad(date.getHourOfDay()) + ":" + pad(date.getMinuteOfHour())

    }


    fun getLongTime(dateStr: String): Long {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        var date: Date? = null
        try {
            date = sdf.parse(dateStr.replace("Z$".toRegex(), "+0000"))

        } catch (ex: ParseException) {
        }


        return date!!.time
    }

    fun getCurrentTimeDay(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
    }


    val format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"


    fun getPassedTime(date: DateTime, current_Date: DateTime): String {

        var text = ""

        val hour1 = date.getHourOfDay()
        val hour2 = current_Date.getHourOfDay()

        val min1 = date.getMinuteOfHour()
        val min2 = current_Date.getMinuteOfHour()

        val day1 = date.getDayOfMonth()
        val day2 = current_Date.getDayOfMonth()

        if (date.isBefore(current_Date))
            if (date.getYear() === current_Date.getYear()) {
                if (date.getMonthOfYear() === current_Date.getMonthOfYear()) {


                    if (day1 == day2) {
                        if (hour1 == hour2) {


                            if (min1 == min2)
                                text = (min2 - min1).toString() + " just now"
                            else
                                text = (min2 - min1).toString() + " mins ago"

                        } else {

                            if (hour1 > hour2) {
                                if (hour2 - hour1 >= 1)
                                    text = (hour2 - hour1).toString() + " hrs ago"
                                else
                                    text = (hour2 - hour1).toString() + " hrs ago"

                            }

                        }
                    } else {

                        text = (day2 - day1).toString() + " days ago"

                    }

                }
            }
        return text
    }

    fun getPassedTime(date: DateTime): String {

        val currentDate = DateTime()
        var text = ""

        val diff = currentDate.getMillis() - date.getMillis()
        val days = diff / (24 * 60 * 60 * 1000)
        val hours = diff / (60 * 60 * 1000) % 24
        val minutes = diff / (60 * 1000) % 60
        var timePeriod = ""
        var timeValue: Long = 0
        if (date.isBefore(currentDate)) {

            if (days >= 5)
                text =
                    date.getYear().toString() + "-" + pad(date.getMonthOfYear()) + "-" + pad(date.getDayOfMonth()) + " " + getTimeAmAndPM(
                        date
                    )
            else if (days != 0L) {
                timeValue = days
                timePeriod = "days"
            } else if (hours != 0L) {
                timeValue = hours
                if (hours >= 3 && hours <= 10)
                    timePeriod = "hours"
                else
                    timePeriod = "hour"

            } else if (minutes != 0L) {
                timeValue = minutes
                if (hours >= 3 && hours <= 10)
                    timePeriod = "minutes"
                else
                    timePeriod = "minutes"
            } else {
                timeValue = -1
                timePeriod = "just_now"
            }

            if (timeValue == -1L) {
                text = timePeriod
            } else {
                if (text.length == 0) {
                    text = timeValue.toString() + " " + timePeriod + " " + "ago"
                }
            }

        } else
            text =
                date.getYear().toString() + "-" + pad(date.getMonthOfYear()) + "-" + pad(date.getDayOfMonth()) + " " + getTimeAmAndPM(
                    date
                )


        return text
    }

    fun getPassedTimeByYear(date: DateTime): String {

        return date.getYear().toString() + "-" + pad(date.getMonthOfYear()) + "-" + pad(date.getDayOfMonth())
    }


    fun getTime(dateStr: String): String {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        var date: Date? = null
        try {
            date = sdf.parse(dateStr.replace("Z$".toRegex(), "+0000"))

        } catch (ex: ParseException) {
        }

        val dateTime = DateTime(date)

        return setTimeHelper(dateTime.getHourOfDay()) + ":" + setTimeHelper(dateTime.getMinuteOfHour())
    }

    fun getDateTime(dateStr: String): DateTime {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        var date: Date? = null
        try {
            date = sdf.parse(dateStr.replace("Z$".toRegex(), "+0000"))

        } catch (ex: ParseException) {
        }

        return DateTime(date)
    }


    fun getDateDay(dateStr: String): String {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        var date: Date? = null
        try {
            date = sdf.parse(dateStr.replace("Z$".toRegex(), "+0000"))

        } catch (ex: ParseException) {
        }

        val dateTime = DateTime(date)
        return setTimeHelper(dateTime.getYear()) + "-" + setTimeHelper(dateTime.getMonthOfYear() + 1) + "-" + setTimeHelper(
            dateTime.getDayOfMonth()
        )
    }

    fun setTimeHelper(comp: Int): String {
        return if (comp < 10) "0$comp" else comp.toString() + ""
    }


    fun diff(num1: Int, num2: Int, total: Int): String {
        return if (num1 == num2) pad(0) else pad(if (num1 >= num2) num1 - num2 - 1 else total + (num1 - num2))
    }

    fun max(num1: Int, num2: Int): Int {
        return if (num1 > num2) num1 else num2
    }

    fun min(num1: Int, num2: Int): Int {
        return if (num1 < num2) num1 else num2
    }

    fun pad(c: Int): String {
        return if (c >= 10)
            c.toString()
        else
            "0$c"
    }
}