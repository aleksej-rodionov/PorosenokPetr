package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.MainActivity
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

    val collectAdapter : CollectionAdapter by lazy {
        CollectionAdapter(
            onSwitchCatActive = { cat, isChecked ->

            },
            onClickCat = { cat ->

            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionBinding.bind(view)

        binding?.apply {
            rvCats.apply {
                adapter = collectAdapter
                setHasFixedSize(true)
            }

            // todo listeners
            btnStudy.setOnClickListener {
                (activity as MainActivity)?.onBackPressed()
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmCollection.categories.collectLatest {
                val cats = it?: return@collectLatest
                collectAdapter.submitList(cats)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}







