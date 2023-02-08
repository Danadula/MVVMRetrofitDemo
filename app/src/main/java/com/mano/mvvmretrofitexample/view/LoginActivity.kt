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
import com.mano.mvvmretrofitexample.databinding.ActivityLoginBinding
import com.mano.mvvmretrofitexample.models.LoginResponse
import com.mano.mvvmretrofitexample.utils.CommonUtils
import com.mano.mvvmretrofitexample.utils.Validator
import com.mano.mvvmretrofitexample.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Login View"

        val token = SessionManager.getToken(this)

        if (!token.isNullOrBlank()) {
            navigateToHome()
        }

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.btnRegister.setOnClickListener {
            doSignup()
        }

    }

    /**
     * Navigate to Registration Activity
     */
    private fun doSignup() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }

    private fun doLogin() {
        val email = binding.username.text.toString()
        val pwd = binding.password.text.toString()

        if (isValidated(email, pwd)) {
            viewModel.loginUser(email = email, pwd = pwd)
        }
    }

    /**
     * Input Validation Parts
     */
    private fun isValidated(email: String, pwd: String): Boolean {
        var hasError = true

        if (TextUtils.isEmpty(email)) {
            binding.usernameInputLayout.isErrorEnabled = true
            binding.usernameInputLayout.error = "Enter Username"
            hasError = false
        } else if (email.length < 4) {
            binding.usernameInputLayout.isErrorEnabled = true
            binding.usernameInputLayout.error = "Enter user name at least 4 characters"
            hasError = false

        } else if (!Validator.isValidEmail(email)) {
            binding.usernameInputLayout.isErrorEnabled = true
            binding.usernameInputLayout.error = "Enter valid email address"
            hasError = false
        } else {
            binding.usernameInputLayout.isErrorEnabled = false
        }

        if (TextUtils.isEmpty(pwd)) {
            binding.passwordLayout.isErrorEnabled = true
            binding.passwordLayout.error = "Enter password"
            hasError = false
        } else {
            binding.passwordLayout.isErrorEnabled = false
        }

        return hasError
    }

    private fun processLogin(data: LoginResponse?) {
        showToast("Success:" + data?.message)
        if (!data?.data?.token.isNullOrEmpty()) {
            data?.data?.token?.let {
                SessionManager.saveAuthToken(this, it)
            }
            data?.data?.name?.let {
                SessionManager.saveUserName(this, it)
            }
            navigateToHome()
        }
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