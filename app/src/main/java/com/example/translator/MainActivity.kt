package com.example.translator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottomNavigation)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_translate -> {
                    replaceFragment(TranslatorFragment())
                    Toast.makeText(this, "Переводчик!", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true

                }

                R.id.navigation_history -> {
                    replaceFragment(HistoryFragment())
                    Toast.makeText(this, "История!", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true

                }

                R.id.navigation_favorite -> {
                    replaceFragment(FavoriteFragment())
                    Toast.makeText(this, "Избранное!", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true

                }

                else -> false
            }
        }
        replaceFragment(TranslatorFragment())
    }


    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
