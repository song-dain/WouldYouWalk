package com.greedy.wouldyouwalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.wouldyouwalk.databinding.ActivityJoinBinding
import com.greedy.wouldyouwalk.databinding.ActivityLoginBinding

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        
        /* 가입하기 버튼을 클릭하면 파이어베이스로 계정 등록 요청 */
        binding.joinButton.setOnClickListener{ 
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            /* 유효성 검사 항목 */
            if(email.isNotEmpty() && password.isNotEmpty()) {
                createAccount(email, password)
            } else {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()

            }
        }

        binding.cancelButton.setOnClickListener { finish() }



    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원 가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "회원 가입에 실패했습니다. 입력 정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        /* 로그인 되어 있는지의 상태를 확인, 로그인 되어 있다면 액티비티를 종료시킨다. */
        val currentUser = auth.currentUser
        if(currentUser != null){
            finish()
        }
    }

}