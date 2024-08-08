package com.example.expensetracker.login.ui.binder

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


class LoginBinder :BaseObservable() {


    @get:Bindable
    var isOtpScreen:Boolean = false
        set(value) {
            field = value
           // notifyPropertyChanged(BR.isOtpScreen)
        }

}
