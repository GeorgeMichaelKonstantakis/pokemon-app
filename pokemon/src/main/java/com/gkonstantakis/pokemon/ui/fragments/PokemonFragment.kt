package com.gkonstantakis.pokemon.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkonstantakis.pokemon.ModuleApplication
import com.gkonstantakis.pokemon.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import com.gkonstantakis.pokemon.databinding.FragmentPokemonBinding
import com.gkonstantakis.pokemon.ui.adapters.EventsAdapter
import com.gkonstantakis.pokemon.ui.adapters.PokemonAdapter
import com.gkonstantakis.pokemon.ui.mappers.UiMapper
import com.gkonstantakis.pokemon.ui.models.EventAdapterItem
import com.gkonstantakis.pokemon.ui.models.PokemonAdapterItem
import com.gkonstantakis.pokemon.ui.models.PokemonWithAbilitiesItem
import com.gkonstantakis.pokemon.ui.utils.Events
import com.gkonstantakis.pokemon.ui.viewModels.PokemonViewModel
import kotlinx.coroutines.runBlocking

class PokemonFragment : Fragment() {

    private var _binding: FragmentPokemonBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: PokemonViewModel

    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var pokemonAdapter: PokemonAdapter

    private lateinit var eventsListRecyclerView: RecyclerView
    private lateinit var pokemonListRecyclerView: RecyclerView

    private var pokemonList: MutableList<PokemonAdapterItem> = ArrayList<PokemonAdapterItem>()
    private lateinit var pokemonAdapterLinearLayoutManager: LinearLayoutManager

    private var eventList: MutableList<EventAdapterItem> = ArrayList<EventAdapterItem>()
    private lateinit var eventsAdapterLinearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = PokemonViewModel(ModuleApplication.pokemonRepository)

        setupUI()

        subscribeObservers()

        viewModel.setStateEvent(PokemonViewModel.StateEvent.GetPokemons)
    }

    fun setupUI() {
        setupRecyclerViews()
        setupPokemonAdapterItemClick()
    }

    fun setupRecyclerViews() {
        eventsAdapter = EventsAdapter()
        eventsListRecyclerView = binding.eventsListRv
        eventsListRecyclerView.apply {
            adapter = eventsAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        eventsAdapter.differ.submitList(Events.getEventsList(requireContext()))

        pokemonAdapter = PokemonAdapter(viewModel)
        pokemonListRecyclerView = binding.pokemonListRv
        pokemonListRecyclerView.apply {
            adapter = pokemonAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun setupPokemonAdapterItemClick() {
        pokemonAdapter.setOnItemClickListener {
            viewModel.setStateEvent(PokemonViewModel.StateEvent.GetPokemonInfo(it.name))
        }
    }

    private fun subscribeObservers() {
        viewModel.pokemonState.observe(viewLifecycleOwner, Observer { pokemonState ->
            when (pokemonState) {
                is PokemonState.Success<List<Pokemon>> -> {
                    dataLoading(false)
                    binding.networkErrorText.visibility = View.GONE
                    if (pokemonState.data.isNullOrEmpty()) {
                        binding.networkErrorText.visibility = View.VISIBLE
                    } else {
                        pokemonAdapter.differ.submitList(
                            UiMapper().mapDomainToUIPokemonList(
                                pokemonState.data
                            )
                        )
                    }
                }
                is PokemonState.Error -> {
                    binding.networkErrorText.visibility = View.VISIBLE
                    dataLoading(false)
                }
                is PokemonState.Loading -> {
                    dataLoading(true)
                }
                else -> {
                    binding.networkErrorText.visibility = View.GONE
                    dataLoading(false)
                }
            }
        })

        viewModel.pokemonInfoState.observe(viewLifecycleOwner, Observer { pokemonInfoState ->
            when (pokemonInfoState) {
                is PokemonInfoState.Success<List<PokemonWIthAbilities>> -> {
                    if (pokemonInfoState.data.isNullOrEmpty()) {

                    } else {
                        val pokemonWithAbilities =
                            UiMapper().mapDomainToUIPokemonWithAbilitiesList(pokemonInfoState.data)
                                .first()
                        showPokemonInfo(pokemonWithAbilities)
                    }
                }
                is PokemonInfoState.Error -> {

                }
                is PokemonInfoState.Loading -> {
                }
                else -> {

                }
            }
        })
    }

    private fun dataLoading(isDataLoading: Boolean) {
        if (isDataLoading) {
            binding.pokemonProgressBar.visibility = View.VISIBLE
        } else {
            binding.pokemonProgressBar.visibility = View.GONE
        }
    }

    private fun showPokemonInfo(pokemonWIthAbilitiesItem: PokemonWithAbilitiesItem) {

        binding.pokemonListRv.visibility = View.GONE

        Log.e("pokemonName", "pokemonName: " + pokemonWIthAbilitiesItem.name)

        binding.pokemonInfoArea.visibility = View.VISIBLE
        binding.pokemonNameTv.text =
            requireContext().resources.getString(R.string.name_txt) + pokemonWIthAbilitiesItem.name
        binding.pokemonBaseExperienceTv.text =
            requireContext().resources.getString(R.string.base_experience_txt) + pokemonWIthAbilitiesItem.baseExperience
        binding.pokemonWeightTv.text =
            requireContext().resources.getString(R.string.weight_txt) + pokemonWIthAbilitiesItem.weight
        binding.pokemonHeightTv.text =
            requireContext().resources.getString(R.string.height_txt) + pokemonWIthAbilitiesItem.height

        Log.e("pokemonName", "pokemonName: " + binding.pokemonNameTv.text)

        var abilitiesText = requireContext().resources.getString(R.string.abilities_txt)

        pokemonWIthAbilitiesItem.abilities.forEach { ability ->
            abilitiesText = abilitiesText + ability + ", "
        }

        binding.pokemonAbilitiesTv.text =
            abilitiesText.substring(0, abilitiesText.lastIndexOf(","));

        binding.pokemonDismissButton.setOnClickListener {
            binding.pokemonInfoArea.visibility = View.GONE
            binding.pokemonListRv.visibility = View.VISIBLE
        }
    }
}