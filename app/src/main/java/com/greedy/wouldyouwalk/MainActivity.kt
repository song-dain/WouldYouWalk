package com.greedy.wouldyouwalk



import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.greedy.wouldyouwalk.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)







        val fragmentList = listOf(MainFragment(), TimerFragment(), RouteRecordFragment(), ParkInfoFragment(), SettingsFragment())

        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList

        binding.viewPager.adapter = adapter

        var tabTitles = listOf<String>("날씨어때", "산책기록", "코스어때", "공원정복", "설정")

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}





