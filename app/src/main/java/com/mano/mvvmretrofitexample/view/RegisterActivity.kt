package com.mano.mvvmretrofitexample.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mano.mvvmretrofitexample.api.BaseResponse
import com.mano.mvvmretrofitexample.constants.SessionManager
import com.mano.mvvmretrofitexample.databinding.ActivityRegisterBinding
import com.mano.mvvmretrofitexample.models.RegisterUserResponse
import com.mano.mvvmretrofitexample.utils.CommonUtils
import com.mano.mvvmretrofitexample.utils.Validator
import com.mano.mvvmretrofitexample.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegistrationViewModel by viewModels()

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Registration View"

        viewModel.registrationResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    registrationSuccess(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.emailId.text.toString()
            val pwd = binding.pwd.text.toString()

            if (isValidated(name, email, pwd)) {
                doRegistration(name, email, pwd)
            }
        }

    }

    private fun doRegistration(name: String, email: String, pwd: String) {
        viewModel.doRegistration(name, email, pwd)
    }

    private fun isValidated(name: String, email: String, pwd: String): Boolean {
        var hasError = true

        if (TextUtils.isEmpty(name)) {
            binding.nameInputLayout.isErrorEnabled = true
            binding.nameInputLayout.error = "Enter name"
            hasError = false
        } else {
            binding.nameInputLayout.isErrorEnabled = false
        }

        if (TextUtils.isEmpty(email)) {
            binding.emailInputLayout.isErrorEnabled = true
            binding.emailInputLayout.error = "Enter email id"
            hasError = false
        } else if (!Validator.isValidEmail(email)) {
            binding.emailInputLayout.isErrorEnabled = true
            binding.emailInputLayout.error = "Enter valid email id"
            hasError = false
        } else {
            binding.emailInputLayout.isErrorEnabled = false
        }

        if (TextUtils.isEmpty(pwd)) {
            binding.pwdInputLayout.isErrorEnabled = true
            binding.pwdInputLayout.error = "Enter password"
            hasError = false
        } else {
            binding.pwdInputLayout.isErrorEnabled = false
        }

        return hasError
    }

    /**
     * Registration Success
     */
    private fun registrationSuccess(registerUserResponse: RegisterUserResponse?) {
        showToast("Success:" + registerUserResponse?.message)

        if (!registerUserResponse?.data?.Token.isNullOrEmpty()) {
            registerUserResponse?.data?.Token?.let {
                SessionManager.saveAuthToken(this, it)
            }
            registerUserResponse?.data?.Name?.let {
                SessionManager.saveUserName(this, it)
            }
        }

        navigateToHome()

    }

    private fun showLoading() {
        dialog = CommonUtils.setProgressDialog(this)
        dialog!!.show()
    }

    private fun stopLoading() {
        dialog!!.cancel()
    }

    private fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

}