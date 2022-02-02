package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentWordlistBinding

@AndroidEntryPoint
class WordlistFragment : Fragment(R.layout.fragment_wordlist) {

    private var _binding: FragmentWordlistBinding? = null
    val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWordlistBinding.bind(view)

        binding.apply {
//            rvWords
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}