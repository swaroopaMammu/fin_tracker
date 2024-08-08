package com.example.expensetracker.login.viewmodel

import androidx.lifecycle.ViewModel
import com.example.expensetracker.login.ui.binder.LoginBinder

class LoginViewModel : ViewModel(){

    private val loginBinder = LoginBinder()

    fun setDataBinderOtp(){
        loginBinder.isOtpScreen = true
    }
}