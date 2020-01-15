package com.onemain.challenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.onemain.challenge.R
import org.koin.android.ext.android.inject

/**
 * Main activity which acts like a container and holds up different Fragments which acts as UI layer
 */
class MainActivity : AppCompatActivity() {
    val mainFragment:MainFragment by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
        }
    }
}
