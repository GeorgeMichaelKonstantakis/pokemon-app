package com.gkonstantakis.pokemon.ui.activities

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.gkonstantakis.pokemon.R
import com.gkonstantakis.pokemon.databinding.ActivityMainBinding

class MainActivity : androidx.fragment.app.FragmentActivity() {

    private lateinit var activityViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityViewBinding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        navigateToPokemonScreen()
    }

    fun navigateToPokemonScreen() {
        val navHostFragment =
            supportFragmentManager.findFragmentByTag("fragment_nav") as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.startDestination = R.id.pokemonFragment
        navHostFragment.navController.graph = graph
    }
}