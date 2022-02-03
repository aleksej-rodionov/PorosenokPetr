package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.MainActivity
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentWordlistBinding

@AndroidEntryPoint
class WordlistFragment : Fragment(R.layout.fragment_wordlist) {

    private val vmWordlist: WordlistViewModel by viewModels()
    private var _binding: FragmentWordlistBinding? = null
    val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWordlistBinding.bind(view)

        binding?.apply {
            rvWords.apply {

            }

            btnBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        vmWordlist.catToSearchIn.observe(viewLifecycleOwner) {
            binding?.apply {
                if (it == null) tvTitle.text = getString(R.string.search_among_all_words)
                val cat = it ?: return@observe
                tvTitle.text = getString(R.string.search_in, cat.name)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // OBSERVERS
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}