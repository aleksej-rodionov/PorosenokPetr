package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentDrillerBinding

@AndroidEntryPoint
class DrillerFragment : Fragment(R.layout.fragment_driller) {

    private val vmDriller: DrillerViewModel by viewModels()
    private var _binding: FragmentDrillerBinding? = null
    private val binding get() = _binding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDrillerBinding.bind(view)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





