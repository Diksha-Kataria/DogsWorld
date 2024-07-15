package com.example.dogsworld.utils

import android.util.Patterns
import java.util.regex.Pattern


object Validator {

    const val REG = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$"
    var PATTERN: Pattern = Pattern.compile(REG)
    fun String.isPhoneNumber() : Boolean = PATTERN.matcher(this).find()

    fun isValidOtp(target: CharSequence?): Boolean {
        if (target != null) {
            return target.toString().equals("1234")
        };
        return false
    }
}