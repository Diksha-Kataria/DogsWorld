package com.example.dogsworld.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.dogsworld.R
import com.example.dogsworld.localStorage.LocalPreferences
import com.example.dogsworld.utils.Validator
import com.example.dogsworld.utils.Validator.isPhoneNumber

class LoginActivity : AppCompatActivity() {

    lateinit var mEditTextPhoneNum : EditText
    lateinit var mEditTextOTP : EditText
    lateinit var mButtonLogin : Button
    lateinit var mCvLogin : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);

        initViews()
    }

    private fun initViews() {
        mEditTextPhoneNum = findViewById(R.id.editTextPhone);
        mEditTextOTP = findViewById(R.id.editTextPassword);
        mButtonLogin = findViewById(R.id.loginButton)
        mCvLogin = findViewById(R.id.cvLogin)

        setListeners()
        if(isUSerLoggedIn()){
            navigateToHome()
        }else{
            mCvLogin.visibility = View.VISIBLE
        }
    }

    private fun isUSerLoggedIn(): Boolean {
        val pref = LocalPreferences(this)
        return pref.getIsLoggedIn()
    }

    private fun setListeners() {
        mButtonLogin.setOnClickListener {
            verifyDetails()
        }

    }

    private fun verifyDetails() {
        if(!mEditTextPhoneNum.text.toString().isPhoneNumber()){
            mEditTextPhoneNum.error ="Invalid phone number, please enter correct number and try again."
        }else if(!Validator.isValidOtp(mEditTextOTP.text)){
            mEditTextOTP.error ="Invalid otp, please enter correct otp and try again."
        }else{
            setDataInStore()
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this@LoginActivity,HomeActivity::class.java)
        startActivity(intent);
        this.finish()
        this.finishAffinity()
    }

    private fun setDataInStore() {
        val pref = LocalPreferences(this)
        pref.setIsLoggedIn(true)
        pref.setUser(mEditTextPhoneNum.text.toString())
    }

    private fun clearErrors() {
        mEditTextPhoneNum.error = null
        mEditTextOTP.error = null
    }
}

