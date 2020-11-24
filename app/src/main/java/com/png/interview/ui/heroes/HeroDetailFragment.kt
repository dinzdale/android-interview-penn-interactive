package com.png.interview.ui.heroes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.png.interview.databinding.FragmentHeroesDetailBinding
import com.png.interview.extensions.onMain
import com.png.interview.extensions.viewLifecycleScope
import com.png.interview.ui.InjectedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HeroDetailFragment : InjectedFragment() {

    @Inject lateinit var viewBinder: HeroDetailFragmentViewBinder

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentHeroesDetailBinding.inflate(inflater, container, false)
        binding.viewBinder = viewBinder
        binding.lifecycleOwner = viewLifecycleOwner
        val args by navArgs<HeroDetailFragmentArgs>()

        val heroName = args.heroName
        val viewModel = getViewModel<HeroDetailFragmentViewModel>()
        viewLifecycleScope.launch(Dispatchers.IO) {
            viewModel.getHeroeDetails(heroName).let {
                onMain {
                    viewBinder.bind(it)
                }
            }
        }
        return binding.root
    }

    companion object {
        fun newInstance() = HeroDetailFragment()
    }

}
