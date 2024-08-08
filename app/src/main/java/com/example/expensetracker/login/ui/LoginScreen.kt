package com.example.expensetracker.login.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityLoginScreenBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginScreen : AppCompatActivity() {

    private var binding : ActivityLoginScreenBinding? =  null;
    private lateinit var fAuth: FirebaseAuth
    private var storedVerificationId:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil. setContentView(this,R.layout.activity_login_screen)
        fAuth = FirebaseAuth.getInstance()
        clickActions()
    }

    private fun validateMobileNumber(mobile:String):Boolean{
       // val mobile = binding?.mobileNumber?.text?:""
        return !(mobile.isEmpty() || mobile.length <10)
    }


    private fun sendVerificationOtp(phoneNumber:String){
        val options = PhoneAuthOptions.newBuilder(fAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun clickActions(){
        binding?.nextButton?.setOnClickListener {
            val mobile = binding?.mobileNumber?.text?.toString() ?:""
            if(validateMobileNumber(mobile)){
                sendVerificationOtp(mobile)
                binding?.titleText?.text = "OTP\nverification"
                binding?.timerText?.isVisible = true
                binding?.mobileNumberSaved?.isVisible = true
                binding?.mobileNumberSaved?.text = mobile
                binding?.mobileNumber?.isVisible = false
                binding?.subHeadline?.isVisible = false
                binding?.mobileNumber?.isVisible = false
                binding?.otpNumber?.isVisible = true
                binding?.otpLayout?.isVisible = true
                binding?.otpSubHeadline?.isVisible = true
                binding?.mobileLayout?.isVisible = false
            }
        }
    }

    private fun verifySignInCode(){
        val otp =  binding?.otpNumber?.text?.toString()?:""
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, otp)
        signInWithPhoneAuthCredential(credential)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            verifySignInCode()
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
          //  resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        fAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                } else {

                    // Update UI
                }
            }
    }

}