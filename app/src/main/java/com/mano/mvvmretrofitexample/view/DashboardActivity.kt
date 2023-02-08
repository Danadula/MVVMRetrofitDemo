package com.mano.mvvmretrofitexample.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mano.mvvmretrofitexample.R
import com.mano.mvvmretrofitexample.constants.SessionManager
import com.mano.mvvmretrofitexample.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "DASHBOARD VIEW"

        binding.welcomeUser.text = "Hello, ${SessionManager.getUserName(this@DashboardActivity)} "

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        SessionManager.clearData(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
        finish()
    }

    @Deprecated("Deprecated in Java", ReplaceWith("onBackPressedDispatcher.onBackPressed()"))
    override fun onBackPressed() {
        // onBackPressedDispatcher.onBackPressed()

        showDialog()

        /*onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog()
            }
        })*/
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this).setTitle(R.string.conform)
            .setMessage(R.string.logoutFromApp)
            .setPositiveButton(R.string.logout) { _, _ ->
                logout()
            }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
        val alert = builder.create()
        alert.show()

    }


}