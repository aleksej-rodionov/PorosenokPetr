package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.MainActivity
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.FragmentCollectionBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.presentation.base.viewBinding
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizationHelper
import javax.inject.Inject


class CollectionFragment : Fragment(R.layout.fragment_collection) {

//    @Inject
//    lateinit var factory: ViewModelFactory //todo перенести в базовый фрагмент
//    private val vmCollection: CollectionViewModel by viewModels<CollectionViewModel>(
//        ownerProducer = { requireActivity() },
//        factoryProducer = { factory }
//    )
    @Inject
    lateinit var assistedFactory: CollectionViewModelAssistedFactory //todo 1)объединить в одну фабрику и 2)перенести в базовый фрагмент
    private val vmCollection: CollectionViewModel by viewModels { assistedFactory.create(this) }


    private val binding by viewBinding<FragmentCollectionBinding>()

    private val collectionAdapter: CollectionAdapter by lazy {
        CollectionAdapter(
            onSwitchCatActive = { cat, isChecked ->
                onSwitchActive(cat, isChecked)
            },
            onClickCat = { cat ->
                vmCollection.onCategoryClick(cat)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentCollectionBinding.bind(view)

        binding?.apply {
            rvCats.apply {
                adapter = collectionAdapter
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            tvSearch.setOnClickListener {
                vmCollection.onSearchClick()
            }

            btnBack.setOnClickListener {
                (activity as MainActivity)?.onBackPressed()
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmCollection.categories.collectLatest {
                val cwws = it?.toMutableList() ?: return@collectLatest
//                val cats = cwws.map { cww ->
//                    cww.category
//                }.toMutableList()
                collectionAdapter.submitList(cwws)
                //todo лучше чтобы адаптер брал CWW а не просто Category
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmCollection.activeCatsFlow.collectLatest {
                val activeCats = it?: return@collectLatest
                vmCollection.refreshActiveCatsAmount(activeCats.size)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmCollection.nativeLanguage.collectLatest {
                binding?.tvTitle?.text = resources.getString(LocalizationHelper.collection.getIdByLang(it))
                binding?.tvSearch?.text = getString(LocalizationHelper.searchWord.getIdByLang(it))
                collectionAdapter.updateNativeLang(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmCollection.eventFlow.collectLatest { event ->
                when (event) {
                    is CollectionViewModel.CollectionEvent.NavigateToWordlistScreen -> {
                        val action =
                            CollectionFragmentDirections.actionCollectionFragmentToWordlistFragment()
                        if (event.cat != null) {
                            action.category = event.cat
                        } else {
                            action.category = null
                        }
                        Log.d(TAG_PETR, "NAVIGATOR: action.category = ${action.category?.resourceName}")
                        findNavController().navigate(action)
                    }
                    is CollectionViewModel.CollectionEvent.RefreshCatSwitch -> {
                        collectionAdapter.refreshCatSwitchState(event.cat)
                    }
                    is CollectionViewModel.CollectionEvent.ShowSnackbar -> {
                        binding?.root?.let {
                            Snackbar.make(it, event.msg, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmCollection.mode.collectLatest {
                val mode = it ?: return@collectLatest
                (binding?.root as ViewGroup).redrawViewGroup(mode)
                collectionAdapter.updateMode(mode)
            }
        }
    }

    private fun onSwitchActive(cat: Category, isChecked: Boolean) {
        if (isChecked) {
            vmCollection.activateCategory(cat.resourceName)
        } else {
            if (vmCollection.howManyActiveCats() < 2) {
                vmCollection.updateCatSwitchState(cat)
                vmCollection.shoeSnackbar(getString(R.string.cannot_turn_all_cats_off))
            } else {
                vmCollection.inactivateCategory(cat.resourceName)
            }
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}







