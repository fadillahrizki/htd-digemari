package com.htd.digemari

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.htd.digemari.R
import com.htd.digemari.SplashScreenActivity
import com.htd.digemari.databinding.ActivityLoginBinding
import com.htd.digemari.model.LoginModel
import com.htd.digemari.rest.ApiClient
import com.htd.digemari.rest.ApiInterface
import com.htd.digemari.rest.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    lateinit var mApiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        mApiInterface = ApiClient.client!!.create(ApiInterface::class.java)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_login -> //                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
                login()
        }
    }

    private fun login() {
        var allTrue = true
        if (binding.username.editText?.text.toString().isEmpty()) {
            binding.username.editText?.error = "Username tidak boleh kosong"
            allTrue = false
        }
        if (binding.password.editText?.text.toString().isEmpty()) {
            binding.password.editText?.error = "Kata Sandi tidak boleh kosong"
            allTrue = false
        }
        if (allTrue) {
            binding.btnLogin.setText("Loading...")
            val loginData = getSharedPreferences("login_data", MODE_PRIVATE)
            val username: String = binding.username.editText?.text.toString()
            val password: String = binding.password.editText?.text.toString()
            mApiInterface.login(username, password).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val error: String = response.body().error
                    if (error != null) {
                        binding.cardError.setVisibility(View.VISIBLE)
                        binding.tvError.setText(error)
                    } else {
                        val user: LoginModel = response.body().user
                        val editor = loginData.edit()
                        editor.putString("id", user.id)
                        editor.putString("name", user.name)
                        editor.putString("username", username)
                        editor.putString("password", password)
                        editor.putString("userId", user.userId)
                        editor.apply()
                        binding.cardError.setVisibility(View.GONE)
                        val intent = Intent(applicationContext, SplashScreenActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    binding?.btnLogin.setText("Masuk")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(packageName, t.toString())
                    binding.btnLogin.setText("Masuk")
                }
            })
        }
    }
}
