package com.greedy.wouldyouwalk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.wouldyouwalk.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val LOCATION_PERMISSION_ACCESS = 100
//    private var googleSignInClient: GoogleSignInClient? = null
//    private val GOOGLE_SIGN_IN = 99
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        /* 회원 가입 버튼 클릭 시 동작하는 이벤트 */
        binding.join.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }



        /* 로그인 버튼 클릭 시 동작하는 이벤트 */
        binding.mainLogin.setOnClickListener {
            checkPermission()


        }
//        auth = FirebaseAuth.getInstance()
        /*--- 구글 로그인 연동 작업중 ----*/
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                            .requestIdToken(getString(R.string.default_web_client_id))
//                            .requestEmail()
//                            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)



        /* 구글 버튼 클릭 시 동작하는 이벤트 */
        binding.googleLogin.setOnClickListener {
            Toast.makeText(this, "서비스 준비중입니다!", Toast.LENGTH_SHORT).show()
//            googleLogin()

        }

        binding.kakaoLogin.setOnClickListener {
            Toast.makeText(this, "서비스 준비중입니다!", Toast.LENGTH_SHORT).show()

        }
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

//    fun googleLogin() {
//        var signInIntent = googleSignInClient?.signInIntent
////        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == GOOGLE_SIGN_IN) {
//            var result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
//            if(result!!.isSuccess) {
//                var account = result.signInAccount
//                firebaseAuthWithGoogle(account)
//            }
//        }
//    }

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                task ->
                if(task.isSuccessful) {
                    moveMainPage()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun checkPermission() {
        Log.d("check", "checkPermission")
        Log.d("check", "${ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED}")
        Log.d("check", "${ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED}")
        /*---위치권한 받기---*/
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            var permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_ACCESS)
        } else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            /* 위치 권한을 이미 받았을 경우, 바로 로그인 */
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            RequestLogin(email, password)
        }
        /*-----*/

    }
    /*------위치 권한 응답 -------*/
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("check", "onRequestPermissionsResult")
        Log.d("check", "${requestCode == LOCATION_PERMISSION_ACCESS}")
        Log.d("check", "${grantResults.isNotEmpty()}")
        Log.d("check", "${grantResults[0] == PackageManager.PERMISSION_GRANTED}")
        when (requestCode) {
            LOCATION_PERMISSION_ACCESS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("check", "도착")
                    val email = binding.email.text.toString()
                    val password = binding.password.text.toString()
                    RequestLogin(email, password)
                    return
                }
            }

        }



    }


    private fun RequestLogin(email: String, password: String) {

        /* 여러 유효성 검사 추가할 수 있음 */
        if(email.isNotEmpty() && password.isNotEmpty()) {
            signIn(email, password)
        } else {
            Toast.makeText(this, "이메일과 패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                    moveMainPage()
                } else {
                    Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /* 로그인 된 상태라면 로그인 이후 화면인 MainActivity로 이동 */
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            moveMainPage()
        }
    }

    /* 로그인이 이미 되어 있는 상태 또는 로그인에 성공하는 상태에 호출할 메소드 */
    fun moveMainPage() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }





    /*-------------------*/


}