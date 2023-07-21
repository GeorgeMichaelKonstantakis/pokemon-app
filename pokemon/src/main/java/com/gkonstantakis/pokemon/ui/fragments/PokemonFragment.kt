package com.gkonstantakis.pokemon.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gkonstantakis.pokemon.ModuleApplication
import com.gkonstantakis.pokemon.R
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import com.gkonstantakis.pokemon.databinding.FragmentPokemonBinding
import com.gkonstantakis.pokemon.ui.adapters.EventsAdapter
import com.gkonstantakis.pokemon.ui.adapters.PokemonAdapter
import com.gkonstantakis.pokemon.ui.mappers.UiMapper
import com.gkonstantakis.pokemon.ui.models.PokemonAdapterItem
import com.gkonstantakis.pokemon.ui.models.PokemonWithAbilitiesItem
import com.gkonstantakis.pokemon.ui.utils.Events
import com.gkonstantakis.pokemon.ui.utils.GeneralUtils
import com.gkonstantakis.pokemon.ui.viewModels.PokemonViewModel


class PokemonFragment : Fragment() {

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PokemonViewModel

    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var pokemonAdapter: PokemonAdapter

    private lateinit var eventsListRecyclerView: RecyclerView
    private lateinit var pokemonListRecyclerView: RecyclerView

    private var pokemonList: MutableList<PokemonAdapterItem> = ArrayList<PokemonAdapterItem>()

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ModuleApplication.appContainer.pokemonViewModelFactory.create()

        setupUI()

        subscribeObservers()

        viewModel.setStateEvent(PokemonViewModel.StateEvent.GetPokemons)
    }

    fun setupUI() {
        setupRecyclerViews()
        setupPokemonAdapterItemClick()
        initScrollListener()
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

    private fun initScrollListener() {
        pokemonListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == pokemonAdapter.itemCount - 1) {
                        if (GeneralUtils().isInternetConnected(requireContext())) {
                            viewModel.setStateEvent(PokemonViewModel.StateEvent.GetPagingPokemons)
                            isLoading = true
                        }
                    }
                }
            }
        })
    }

    private fun subscribeObservers() {
        viewModel.pokemonState.observe(viewLifecycleOwner, Observer { pokemonState ->
            when (pokemonState) {
                is PokemonState.SuccessPokemon<List<Pokemon>> -> {
                    dataLoading(false)
                    binding.networkErrorText.visibility = View.GONE
                    if (pokemonState.data.isNullOrEmpty()) {
                        binding.networkErrorText.visibility = View.VISIBLE
                    } else {
                        viewModel.setStateEvent(
                            PokemonViewModel.StateEvent.LoadPokemonImages(
                                pokemonState.data
                            )
                        )
                        pokemonList =
                            UiMapper().mapDomainToUIPokemonList(pokemonState.data) as MutableList<PokemonAdapterItem>
                        pokemonAdapter.differ.submitList(pokemonList)
                    }
                }
                is PokemonState.SuccessPagingPokemon<List<Pokemon>> -> {
                    dataLoading(false)
                    binding.networkErrorText.visibility = View.GONE
                    if (!pokemonState.data.isNullOrEmpty()) {
                        viewModel.setStateEvent(
                            PokemonViewModel.StateEvent.LoadPokemonImages(
                                pokemonState.data
                            )
                        )
                        pokemonList =
                            (pokemonList + (UiMapper().mapDomainToUIPokemonList(pokemonState.data) as MutableList<PokemonAdapterItem>)).toMutableList()
                        pokemonAdapter.differ.submitList(pokemonList)

                    }
                    isLoading = false
                }
                is PokemonState.SuccessDatabasePokemon<List<Pokemon>> -> {
                    dataLoading(false)
                    binding.networkErrorText.visibility = View.GONE
                    if (!pokemonState.data.isNullOrEmpty()) {
                        pokemonList =
                            (pokemonList + (UiMapper().mapDomainToUIPokemonList(pokemonState.data) as MutableList<PokemonAdapterItem>)).toMutableList()
                        pokemonAdapter.differ.submitList(pokemonList)

                    }
                    isLoading = false
                }
                is PokemonState.Error -> {
                    if(pokemonState.message == "network_pokemon_error") {
                        viewModel.setStateEvent(PokemonViewModel.StateEvent.GetDatabasePokemons)
                    } else {
                        binding.networkErrorText.visibility = View.VISIBLE
                        dataLoading(false)
                    }
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
                    if (!pokemonInfoState.data.isNullOrEmpty()) {
                        pokemonInfoState.data.first().abilities.forEach {
                            Log.e("AbilitiyUI","AbilityUI: " + it.name)
                        }
                        val pokemonWithAbilities =
                            UiMapper().mapDomainToUIPokemonWithAbilitiesList(pokemonInfoState.data)
                                .first()
                        showPokemonInfo(pokemonWithAbilities)
                    }
                }
                is PokemonInfoState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        requireContext().resources.getString(R.string.pokemon_info_error),
                        Toast.LENGTH_LONG
                    ).show()
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
        binding.pokemonInfoArea.visibility = View.VISIBLE

        binding.pokemonNameTv.text =
            requireContext().resources.getString(R.string.name_txt) + " : " + (pokemonWIthAbilitiesItem.name).toUpperCase()
        binding.pokemonBaseExperienceTv.text =
            requireContext().resources.getString(R.string.base_experience_txt) + " : " + pokemonWIthAbilitiesItem.baseExperience
        binding.pokemonWeightTv.text =
            requireContext().resources.getString(R.string.weight_txt) + " : " + pokemonWIthAbilitiesItem.weight
        binding.pokemonHeightTv.text =
            requireContext().resources.getString(R.string.height_txt) + " : " + pokemonWIthAbilitiesItem.height

        var abilitiesText = requireContext().resources.getString(R.string.abilities_txt) + " : "
        pokemonWIthAbilitiesItem.abilities.forEach { ability ->
            abilitiesText = "$abilitiesText$ability - "
        }

        binding.pokemonAbilitiesTv.text =
            abilitiesText.substring(0, abilitiesText.lastIndexOf("-"));

        binding.pokemonDismissButton.setOnClickListener {
            binding.pokemonInfoArea.visibility = View.GONE
            binding.pokemonListRv.visibility = View.VISIBLE
        }
    }
}