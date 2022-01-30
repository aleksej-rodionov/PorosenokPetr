package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentCollectionBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseFragment

@AndroidEntryPoint
class CollectionFragment : BaseFragment(
    R.layout.fragment_collection
) {

    private val vmCollection: CollectionViewModel by viewModels()
    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionBinding.bind(view)

        initViewModel()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}







