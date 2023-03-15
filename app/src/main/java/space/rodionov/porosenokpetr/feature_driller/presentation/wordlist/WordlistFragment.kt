package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.MainActivity
import space.rodionov.porosenokpetr.core.PorosenokPetrApp
import space.rodionov.porosenokpetr.core.util.GenericSavedStateViewModelFactory
import space.rodionov.porosenokpetr.core.util.redrawViewGroup
import space.rodionov.porosenokpetr.core.util.showKeyboard
import space.rodionov.porosenokpetr.databinding.FragmentWordlistBinding
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_driller.presentation.base.viewBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word.WordlistBottomSheet
import space.rodionov.porosenokpetr.core.util.Constants.EMPTY_STRING
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_SE
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_UA
import space.rodionov.porosenokpetr.core.util.Constants.TAG_NATIVE_LANG
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import space.rodionov.porosenokpetr.core.util.LocalizationHelper
import java.util.*
import javax.inject.Inject

class WordlistFragment : Fragment(R.layout.fragment_wordlist), TextToSpeech.OnInitListener {

    private val binding by viewBinding<FragmentWordlistBinding>()

    val args by navArgs<WordlistFragmentArgs>()

    @Inject
    lateinit var assistedFactory: WordlistViewModelFactory
    private val vmWordlist: WordlistViewModel by viewModels {
        GenericSavedStateViewModelFactory(assistedFactory, this, args.toBundle())
    }

    private var textToSpeech: TextToSpeech? = null

    private val wordlistAdapter: WordlistAdapter by lazy {
        WordlistAdapter(
            onClickLearned = {
                openWordBottomSheet(it)
            },
            onSpeakWord = {
                vmWordlist.speakWord(it)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        PorosenokPetrApp.component?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textToSpeech = TextToSpeech(requireContext(), this)

        binding.apply {
            rvWords.apply {
                adapter = wordlistAdapter
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
            etSearch.showKeyboard()
            etSearch.addTextChangedListener {
                ivClearText.isVisible = !it.toString().isEmpty()

                vmWordlist.onSearch(it.toString())
            }

            ivClearText.setOnClickListener {
                etSearch.text.clear()
            }
            btnBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        vmWordlist.catToSearchIn.observe(viewLifecycleOwner) {
            binding.apply {
                if (it == null) {
                    val langId = vmWordlist.nativeLang.value
                    Log.d(TAG_NATIVE_LANG, "initViewModel: langid = $langId")
                    tvTitle.text = getString(LocalizationHelper.searchAmongAllWords.getIdByLang(langId))
                }
                vmWordlist.updateCatStorage(EMPTY_STRING)
                val cat = it ?: return@observe
                tvTitle.text = getString(R.string.search_in, cat.resourceName)
                vmWordlist.updateCatStorage(cat.resourceName)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmWordlist.words.collectLatest {
                val words = it ?: return@collectLatest
                Log.d(TAG_PETR, "initViewModel: size = ${words.size} words")
                wordlistAdapter.submitList(words)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmWordlist.eventFlow.collectLatest { event ->
                when (event) {
                    is WordlistViewModel.WordlistEvent.OpenWordBottomSheet -> {
                        showWordBottomSheet(event.word)
                    }
                    is WordlistViewModel.WordlistEvent.SpeakWord -> {
                        onSpeakWord(event.word)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmWordlist.mode.collectLatest {
                val mode = it ?: return@collectLatest
                (binding?.root as ViewGroup).redrawViewGroup(mode)
                wordlistAdapter.updateMode(mode)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmWordlist.nativeLang.collectLatest {
                wordlistAdapter.updateNativeLang(it)

                if (vmWordlist.catToSearchIn.value == null) {
                    binding?.tvTitle?.text = getString(LocalizationHelper.searchAmongAllWords.getIdByLang(it))
                } else {
                    val cat = vmWordlist.catToSearchIn.value
                    binding?.tvTitle?.text = getString(
                        LocalizationHelper.searchIn.getIdByLang(it),
                        cat?.getLocalizedName(it)
                    )
                }

                binding?.etSearch?.setHint(getString(LocalizationHelper.searchWord.getIdByLang(it)))
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmWordlist.learnedLang.collectLatest {
                wordlistAdapter.updateLearnedLang(it)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            try {
                if (BuildConfig.FLAVOR == "englishdriller") {
                    textToSpeech?.language = Locale.ENGLISH
                } else if (BuildConfig.FLAVOR == "swedishdriller") {
                    when (vmWordlist.learnedLang.value) {
                        LANGUAGE_EN -> textToSpeech?.language = Locale.ENGLISH
                        LANGUAGE_SE -> textToSpeech?.language = Locale("sv", "SE")
                        LANGUAGE_UA -> textToSpeech?.language = Locale("uk", "UA")
                    }
                } else {
                    textToSpeech?.language = Locale.ENGLISH
                }
            } catch (e: Exception) {
                Log.d(TAG_PETR, "TTS: Exception: ${e.localizedMessage}")
            }
        } else {
            Log.d(TAG_PETR, "TTS Language initialization failed")
        }
    }

    private fun onSpeakWord(word: String) { // todo переменстить во вьюмодель надо?
        textToSpeech?.speak(word, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun openWordBottomSheet(word: Word) {
        vmWordlist.openWordBottomSheet(word)
    }

    private fun showWordBottomSheet(word: Word) {
        val args = Bundle()
        args.putString("rus", word.getTranslation(/*vmWordlist.nativeLang.value*/ LANGUAGE_RU)) // вытаскиваем слова по этим двум языкам просто потому, что эти два языка фигурируют во всех Flavours
        args.putString("eng", word.getTranslation(/*vmWordlist.learnedLang.value*/ LANGUAGE_EN)) // вытаскиваем слова по этим двум языкам просто потому, что эти два языка фигурируют во всех Flavours
        args.putString("categoryName", word.categoryName)
        
        val wordBottomSheet = WordlistBottomSheet()
        wordBottomSheet.arguments = args
        wordBottomSheet.show(
            requireFragmentManager(), // здесь не просто фрагмент манагер?
            WordlistBottomSheet.WORDLIST_BOTTOM_SHEET
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
        textToSpeech?.let {
            it.stop()
            it.shutdown()
        }
    }
}