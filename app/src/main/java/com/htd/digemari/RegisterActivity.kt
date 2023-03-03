package com.htd.digemari

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.htd.digemari.databinding.ActivityLoginBinding
import com.htd.digemari.databinding.ActivityRegisterBinding
import com.htd.digemari.rest.ApiClient
import com.htd.digemari.rest.ApiInterface

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var mApiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        mApiInterface = ApiClient.client!!.create(ApiInterface::class.java)

        val roles = resources.getStringArray(R.array.roles)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, roles)
        (binding.role.editText as AutoCompleteTextView).setAdapter(arrayAdapter)
    }
}