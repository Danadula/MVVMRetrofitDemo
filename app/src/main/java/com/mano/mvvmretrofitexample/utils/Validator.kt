package com.mano.mvvmretrofitexample.utils

import android.text.TextUtils
import java.util.regex.Pattern

object Validator {

    private const val EMAIL_REGEX = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

    fun isValidEmail(input: String?): Boolean {
        return !TextUtils.isEmpty(input) && Pattern.matches(EMAIL_REGEX, input.toString())
    }

}